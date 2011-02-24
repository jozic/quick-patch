package ua.com.jozic.quickpatch.components

import org.jdom.Element

class QuickPatchSettings {

  private val LOCATION = "location"
  private val SAVE_DEFAULT = "save_default"
  private val SAVE_EMPTY = "save_empty"
  private val ADD_PROJECT_NAME = "add_project_name"

  var location = "C:/patches/123"
  var saveDefault = true
  var saveEmpty = false
  var addProjectName = false

  def getState() = {
    val element = new Element("QuickPatchSettings")
    element.addContent(elem(LOCATION, location))
    element.addContent(elem(SAVE_DEFAULT, saveDefault))
    element.addContent(elem(SAVE_EMPTY, saveEmpty))
    element.addContent(elem(ADD_PROJECT_NAME, addProjectName))
    element
  }

  def loadState(state: Element) {
    location = stringValue(state, LOCATION)
    saveDefault = booleanValue(state, SAVE_DEFAULT)
    saveEmpty = booleanValue(state, SAVE_EMPTY)
    addProjectName = booleanValue(state, ADD_PROJECT_NAME)
  }

  def stringValue(state: Element, name: String) = state.getChild(name).getValue

  def booleanValue(state: Element, name: String) = stringValue(state, name).toBoolean

  private def elem(name: String, value: Any) = {
    val e = new Element(name)
    e.addContent(value.toString)
    e
  }
}