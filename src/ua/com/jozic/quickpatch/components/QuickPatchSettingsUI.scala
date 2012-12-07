package ua.com.jozic.quickpatch.components

import swing._
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message
import java.io.File
import ua.com.jozic.plugins.Notifications
import javax.swing.filechooser.FileFilter

class QuickPatchSettingsUI extends Notifications {

  val pathText = new Label(message("location.field.text"))
  val locationField = new TextField(columns = 30)
  val fileChooser = new FileChooser {
    fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
    peer.setAcceptAllFileFilterUsed(false)
    fileFilter = new FileFilter {
      def getDescription = message("filefilter.description.text")

      def accept(f: File) = f.isDirectory
    }
  }
  val locationBrowseButton = Button("...") {
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

  val panel: BorderPanel = new BorderPanel {
    layout(new BorderPanel {
      layout(new FlowPanel {
        contents += pathText
        contents += locationField
        contents += locationBrowseButton
      }) = BorderPanel.Position.West
    }) = BorderPanel.Position.North
    layout(new BoxPanel(Orientation.Vertical) {
      contents += saveDefaultField
      contents += saveEmptyField
      contents += addProjectNameField
    }) = BorderPanel.Position.Center
  }

  def jComponent = panel.peer

  def updateUI(settings: QuickPatchSettings) {
    locationField.text = settings.location
    saveDefaultField.selected = settings.saveDefault
    saveEmptyField.selected = settings.saveEmpty
    addProjectNameField.selected = settings.addProjectName
  }

  def currentSettings = QuickPatchSettings(
    location = locationField.text,
    saveDefault = saveDefaultField.selected,
    saveEmpty = saveEmptyField.selected,
    addProjectName = addProjectNameField.selected)

  def isModified(settings: QuickPatchSettings) = settings.location != locationField.text ||
    settings.saveDefault != saveDefaultField.selected ||
    settings.saveEmpty != saveEmptyField.selected ||
    settings.addProjectName != addProjectNameField.selected

  def checkIfReadyToUse(settings: QuickPatchSettings) {
    checkLocationIsNonEmpty(settings)
    checkLocationExists(settings)
  }

  def checkLocationIsNonEmpty(settings: QuickPatchSettings) {
    if (settings.location.isEmpty) {
      val emptyFieldWarning = warning(message("notifications.group.id"), dialogTitle, message("empty.location.error.message.settings"))
      doNotify(emptyFieldWarning)
    }
  }

  def checkLocationExists(settings: QuickPatchSettings) {
    if (settings.locationDoesntExist)
      Dialog.showConfirmation(panel, message("location.not.exists.confirmation.message"), dialogTitle) match {
        case Dialog.Result.Yes => tryCreateLocation(settings.location)
        case _ =>
      }
  }

  def tryCreateLocation(location: String) {
    if (!new File(location).mkdir())
      Dialog.showMessage(panel, message("cant.create.location.error.message"), dialogTitle, Dialog.Message.Error)
  }

  lazy val dialogTitle = message("dialog.title")
}