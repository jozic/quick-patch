package ua.com.jozic.plugins

import scala.xml.Utility.trim
import scala.xml.{Elem, Node, XML}

import org.jdom.Element
import org.jdom.output.XMLOutputter
import org.scalatest.funsuite.AnyFunSuite

class PersistentStateTest extends AnyFunSuite {

  lazy val xmlOut: XMLOutputter = new XMLOutputter()

  implicit def javaToScalaXML(jElem: org.jdom.Element): scala.xml.Elem =
    XML.loadString(xmlOut.outputString(jElem))

  val options: Map[String, Any] = Map(
    "stringOption" -> "string",
    "booleanOption" -> true
  )

  val ps = new PersistentState[Unit] {

    def doLoad(state: Element): Unit = ()

    def loggerCategory: String = "logger"
  }

  test("get state") {
    val expected: Node = trim(<settings>
      <option value="string" name="stringOption"></option>
      <option value="true" name="booleanOption"></option>
    </settings>)

    val xml: Elem = ps.getState(options)
    assert(trim(xml) === expected)
  }

  test("get string option") {
    val state: Element = ps.getState(options)

    assert(ps.stringValue(state, "stringOption") === "string")
    assert(ps.stringValue(state, "unknownOption") === "")
  }

  test("get boolean option") {
    val state: Element = ps.getState(options)

    assert(ps.booleanValue(state, "booleanOption") === true)
    assert(ps.booleanValue(state, "unknownOption") === false)
  }

  test("get Some string option") {
    val state: Element = ps.getState(options)

    assert(ps.stringOption(state, "unknownOption") === None)
    assert(ps.stringOption(state, "stringOption") === Some("string"))
  }
}