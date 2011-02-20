package ua.com.jozic.quickpatch.components

import swing._

object QuickPatchSettings extends BoxPanel(Orientation.Vertical) {

  val pathText = new Label {
    text = "Path to save your quick patches to:"
  }
  val locationField = new TextField {
    text = "C:/patches"
    columns = 30
  }
  val saveDefaultField = new CheckBox("Save Default change list") {
    selected = true
  }
  val saveEmptyField = new CheckBox("Save empty change lists")
  val addProjectNameField = new CheckBox("Add project name as prefix")

  contents += new FlowPanel {
    contents += pathText
    contents += locationField
  }
  contents += saveDefaultField
  contents += saveEmptyField
  contents += addProjectNameField

  def patchesLocation = locationField.text

  def saveDefault = saveDefaultField.selected

  def saveEmpty = saveEmptyField.selected

  def addProjectName = addProjectNameField.selected
}