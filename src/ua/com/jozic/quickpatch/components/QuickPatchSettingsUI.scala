package ua.com.jozic.quickpatch.components

import swing._

class QuickPatchSettingsUI(val settings: QuickPatchSettings) {
  val pathText = new Label("Path to save your quick patches to:")
  val locationField = new TextField {
    columns = 30
  }
  val saveDefaultField = new CheckBox("Save Default change list")
  val saveEmptyField = new CheckBox("Save empty change lists")
  val addProjectNameField = new CheckBox("Add project name as prefix")

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
  }

  def isModified = settings.location != locationField.text ||
          settings.saveDefault != saveDefaultField.selected ||
          settings.saveEmpty != saveEmptyField.selected ||
          settings.addProjectName != addProjectNameField.selected
}