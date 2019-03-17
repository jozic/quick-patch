package ua.com.jozic.quickpatch.components

import java.io.File

import com.intellij.openapi.options.{ConfigurationException, SearchableConfigurable}
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import javax.swing.filechooser.FileFilter
import javax.swing.{Icon, JPanel}
import ua.com.jozic.plugins.Notifications
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message

import scala.swing._
import scala.util.{Success, Try}

class QuickPatchSettingsUI(project: Project) extends SearchableConfigurable with Notifications {

  val pathLabel = new Label(message("location.field.text"))
  val locationField = new TextField(columns = 30)
  val fileChooser = new FileChooser {
    fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
    peer.setAcceptAllFileFilterUsed(false)
    fileFilter = new FileFilter {
      def getDescription: String = message("filefilter.description.text")

      def accept(f: File): Boolean = f.isDirectory
    }
  }
  val locationBrowseButton = Button(message("location.button.text")) {
    fileChooser.peer.setCurrentDirectory(new File(locationField.text))
    fileChooser.showDialog(panel, message("filechooser.select.button.text")) match {
      case FileChooser.Result.Approve =>
        locationField.text = fileChooser.selectedFile.getAbsolutePath
      case _ =>
    }
  }
  val saveDefaultField = new CheckBox(message("default.field.text"))
  val saveEmptyField = new CheckBox(message("empty.field.text"))
  val addProjectNameField = new CheckBox(message("project.field.text"))
  val ignorePatternLabel = new Label(message("ignore.pattern.field.text"))
  val ignorePatternField = new TextField(columns = 30)

  val panel: BorderPanel = new BorderPanel {
    layout(new BorderPanel {
      layout(new FlowPanel {
        contents += pathLabel
        contents += locationField
        contents += locationBrowseButton
      }) = BorderPanel.Position.West
    }) = BorderPanel.Position.North
    layout(new BorderPanel {
      layout(new BoxPanel(Orientation.Vertical) {
        contents += saveDefaultField
        contents += saveEmptyField
        contents += addProjectNameField
      }) = BorderPanel.Position.North
      layout(new BorderPanel {
        layout(new FlowPanel {
          contents += ignorePatternLabel
          contents += ignorePatternField
        }) = BorderPanel.Position.West
      }
      ) = BorderPanel.Position.Center
    }) = BorderPanel.Position.Center
  }

  override def getId = "QuickPatchSettingsUI"

  override def getDisplayName = "Quick Patch"

  override def getHelpTopic = ""

  def getIcon: Icon = IconLoader.getIcon("/ua/com/jozic/quickpatch/icons/quickpatch.png")

  override def disposeUIResources(): Unit = ()

  override def reset(): Unit = {
    val settings = currentSettings
    locationField.text = settings.location
    saveDefaultField.selected = settings.saveDefault
    saveEmptyField.selected = settings.saveEmpty
    addProjectNameField.selected = settings.addProjectName
    ignorePatternField.text = settings.ignorePattern
  }

  override def apply(): Unit = {
    checkIfReadyToUse()
    val settings = currentSettings
    settings.location = locationField.text
    settings.saveDefault = saveDefaultField.selected
    settings.saveEmpty = saveEmptyField.selected
    settings.addProjectName = addProjectNameField.selected
    settings.ignorePattern = ignorePatternField.text
  }

  override def isModified: Boolean = {
    val settings = currentSettings
    settings.location != locationField.text ||
      settings.saveDefault != saveDefaultField.selected ||
      settings.saveEmpty != saveEmptyField.selected ||
      settings.addProjectName != addProjectNameField.selected ||
      settings.ignorePattern != ignorePatternField.text
  }

  override def createComponent: JPanel = panel.peer

  def ignorePatternTry: Try[Option[String]] = ignorePatternField.text match {
    case text if text.nonEmpty => Try(Some(text.r.regex))
    case _ => Success(None)
  }

  private def currentSettings = QuickPatchSettings(project)

  private def checkIfReadyToUse(): Unit = {
    def checkLocationIsNonEmpty(): Unit = {
      if (locationField.text.isEmpty)
        doNotify(
          warning(
            groupId,
            dialogTitle,
            emptyLocationErrorMessage),
          project
        )
    }

    def checkLocationExists(): Unit = {
      val location = locationField.text
      if (location.nonEmpty && !new File(location).exists) {
        Dialog.showConfirmation(
          parent = panel,
          message = locationDoesntExistMessage,
          title = dialogTitle) match {
          case Dialog.Result.Yes =>
            if (!new File(location).mkdir())
              throw new ConfigurationException(cantCreateLocationMessage, dialogTitle)
          case _ =>
            throw new ConfigurationException(locationDoesntExistErrorMessage, dialogTitle)
        }
      }
    }

    def checkPatternIsCompilable(): Unit = {
      ignorePatternTry.failed.foreach { e =>
        throw new ConfigurationException(patternIsNotCompilableMessage(e.getMessage), dialogTitle)
      }
    }

    checkLocationIsNonEmpty()
    checkLocationExists()
    checkPatternIsCompilable()
  }

  private lazy val dialogTitle: String = message("dialog.title")

  private lazy val groupId: String = message("notifications.group.id")

  private lazy val emptyLocationErrorMessage = message("empty.location.error.message.settings")

  private lazy val locationDoesntExistMessage = message("location.doesnt.exist.confirmation.message")

  private lazy val locationDoesntExistErrorMessage = message("location.doesnt.exist.error.message")

  private lazy val cantCreateLocationMessage = message("cant.create.location.error.message")

  @inline private def patternIsNotCompilableMessage(error: String) =
    message("pattern.is.not.compilable.error.message", error)
}