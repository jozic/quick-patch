package ua.com.jozic.quickpatch.components

import swing._
import ua.com.jozic.quickpatch.QuickPatchMessageBundle.message
import java.io.File
import ua.com.jozic.plugins.Notifications

class QuickPatchSettingsUI(val settings: QuickPatchSettings) extends Notifications {

  val pathText = new Label(message("location.field.text"))
  val locationField = new TextField {
    columns = 30
  }
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

  def updateUI() {
    locationField.text = settings.location
    saveDefaultField.selected = settings.saveDefault
    saveEmptyField.selected = settings.saveEmpty
    addProjectNameField.selected = settings.addProjectName
  }

  def updateModel() {
    settings.location = locationField.text
    settings.saveDefault = saveDefaultField.selected
    settings.saveEmpty = saveEmptyField.selected
    settings.addProjectName = addProjectNameField.selected
    checkIfReadyToUse()
  }

  def isModified = settings.location != locationField.text ||
          settings.saveDefault != saveDefaultField.selected ||
          settings.saveEmpty != saveEmptyField.selected ||
          settings.addProjectName != addProjectNameField.selected

  def checkIfReadyToUse() {
    checkLocationIsNonEmpty()
    checkLocationExists()
  }

  def checkLocationIsNonEmpty() {
    val emptyFieldWarning = warning(message("notifications.group.id"), dialogTitle, message("empty.location.error.message.settings"))
    if (settings.location.isEmpty) {
      doNotify(emptyFieldWarning)
    }
  }

  def checkLocationExists() {
    if (!settings.location.isEmpty && !new File(settings.location).exists) {
      val answer = Dialog.showConfirmation(panel, message("location.not.exists.confirmation.message"),
        dialogTitle)
      if (answer == Dialog.Result.Yes) {
        tryCreateLocation()
      }
    }
  }

  def tryCreateLocation() {
    if (!new File(settings.location).mkdir()) {
      Dialog.showMessage(panel, message("cant.create.location.error.message"),
        dialogTitle, Dialog.Message.Error)
    }
  }

  def dialogTitle = message("dialog.title")
}