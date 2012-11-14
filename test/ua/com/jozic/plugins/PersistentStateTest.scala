package ua.com.jozic.plugins

import org.scalatest.FunSuite
import org.jdom.Element
import org.jdom.output.XMLOutputter
import scala.xml.{Node, Elem, XML}
import scala.xml.Utility.trim

class PersistentStateTest extends FunSuite {

  lazy val xmlOut: XMLOutputter = new XMLOutputter()

  implicit def javaToScalaXML(jElem: org.jdom.Element): scala.xml.Elem = XML.loadString(xmlOut.outputString(jElem))

  def options: Map[String, Any] = Map(
    "stringOption" -> "string",
    "booleanOption" -> false
  )

  val ps = new PersistentState[Unit] {

    def doLoad(state: Element) {}


    def loggerCategory: String = "logger"
  }

  test("get state") {
    val expected: Node = trim(<settings>
      <option value="string" name="stringOption"></option>
      <option value="false" name="booleanOption"></option>
    </settings>)

    val xml: Elem = ps.getState(options)
    assert(trim(xml) === expected)
  }

  test("get string option") {
    val state: Element = ps.getState(options)

    assert(ps.stringValue(state, "stringOption") === "string")
  }

  test("get boolean option") {
    val state: Element = ps.getState(options)

    assert(ps.booleanValue(state, "booleanOption") === false)
  }
}