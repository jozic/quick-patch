package ua.com.jozic.plugins

import org.jdom.xpath.XPath
import org.jdom.{Attribute, Element}

trait PersistentState {

  self: LoggingExceptions =>

  protected def elem(name: String) = new Element(name)

  protected def stringValue(state: Element, name: String): String =
    XPath.selectSingleNode(state, "./option[@name='" + name + "']/@value").asInstanceOf[Attribute].getValue

  protected def booleanValue(state: Element, name: String) = stringValue(state, name).toBoolean

  protected def option(name: String, value: Any) =
    elem("option").setAttribute("name", name).setAttribute("value", value.toString)

  final def getState: Element = options.foldLeft(elem("settings")) {
    case (e, (name, value)) => e.addContent(option(name, value))
  }

  def options: Map[String, Any]

  final def loadState(state: Element) {
    loggingAsWarn("Can't load settings for Quick Patch") {
      doLoad(state)
    }
  }

  def doLoad(state: Element)
}