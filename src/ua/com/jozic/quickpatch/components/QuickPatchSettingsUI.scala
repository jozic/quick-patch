package ua.com.jozic.quickpatch.components

import swing._
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message
import java.io.File
import ua.com.jozic.plugins.Notifications

class QuickPatchSettingsUI extends Notifications {

  val pathText = new Label(message("location.field.text"))
  val locationField = new TextField(columns = 30)
  val saveDefaultField = new CheckBox(message("default.field.text"))
  val saveEmptyField = new CheckBox(message("empty.field.text"))
  val addProjectNameField = new CheckBox(message("project.field.text"))

  val panel = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel {
      contents += pathText
      contents += locationField
    }
    contents += saveDefaultField
    contents += saveEmptyField
    contents += addProjectNameField
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