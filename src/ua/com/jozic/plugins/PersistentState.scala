package ua.com.jozic.plugins

import org.jdom.xpath.XPath
import org.jdom.{Attribute, Element}

trait PersistentState[S] extends LoggingExceptions[S] {

  private def elem(name: String) = new Element(name)

  private def option(name: String, value: Any) =
    elem("option").setAttribute("name", name).setAttribute("value", value.toString)

  protected[plugins] def stringValue(state: Element, name: String): String =
    XPath.selectSingleNode(state, "./option[@name='" + name + "']/@value").asInstanceOf[Attribute].getValue

  protected[plugins] def booleanValue(state: Element, name: String) = stringValue(state, name).toBoolean

  final def getState(options: Map[String, Any]): Element = options.foldLeft(elem("settings")) {
    case (e, (name, value)) => e.addContent(option(name, value))
  }

  final def loadState(state: Element, warning: String = "Can't load settings ..."): Option[S] = {
    logExceptionAsWarn[Exception](warning) {
      doLoad(state)
    }
  }

  def doLoad(state: Element): S
}