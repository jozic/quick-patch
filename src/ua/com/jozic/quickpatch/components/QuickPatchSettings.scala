package ua.com.jozic.quickpatch.components

import org.jdom.xpath.XPath
import org.jdom.{Attribute, Element}

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
    elem("settings").addContent(option(LOCATION, location)).
            addContent(option(SAVE_DEFAULT, saveDefault)).
            addContent(option(SAVE_EMPTY, saveEmpty)).
            addContent(option(ADD_PROJECT_NAME, addProjectName))
  }


  def loadState(state: Element) {
    try {
      location = stringValue(state, LOCATION)
      saveDefault = booleanValue(state, SAVE_DEFAULT)
      saveEmpty = booleanValue(state, SAVE_EMPTY)
      addProjectName = booleanValue(state, ADD_PROJECT_NAME)
    } catch {
      case _ =>
    }
  }

  def stringValue(state: Element, name: String): String =
    XPath.selectSingleNode(state, "./option[@name='" + name + "']/@value").asInstanceOf[Attribute].getValue

  private def booleanValue(state: Element, name: String) = stringValue(state, name).toBoolean

  private def elem(name: String) = new Element(name)

  private def option(name: String, value: Any) =
    elem("option").setAttribute("name", name).setAttribute("value", value.toString)
}