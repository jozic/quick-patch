package ua.com.jozic.plugins

import org.jdom.xpath.XPath
import org.jdom.{Attribute, Element}

trait PersistentState[S] extends LoggingExceptions[S] {

  private def elem(name: String): Element = new Element(name)

  private def option(name: String, value: Any): Element =
    elem("option").setAttribute("name", name).setAttribute("value", value.toString)

  protected[plugins] def stringOption(state: Element, name: String): Option[String] =
    Option(XPath.selectSingleNode(state, "./option[@name='" + name + "']/@value").asInstanceOf[Attribute]).map(_.getValue)

  protected[plugins] def stringValue(state: Element, name: String): String = stringOption(state, name).getOrElse("")

  protected[plugins] def booleanValue(state: Element, name: String): Boolean = stringOption(state, name).exists(_.toBoolean)

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