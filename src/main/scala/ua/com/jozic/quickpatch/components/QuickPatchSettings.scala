package ua.com.jozic.quickpatch.components

import org.jdom.Element
import java.io.File
import ua.com.jozic.plugins.PersistentState

case class QuickPatchSettings(location: String = "",
                              saveDefault: Boolean = true,
                              saveEmpty: Boolean = false,
                              addProjectName: Boolean = false,
                              ignorePattern: Option[String] = None) {

  import QuickPatchSettings._

  def options = Map(
    LOCATION -> location,
    SAVE_DEFAULT -> saveDefault,
    SAVE_EMPTY -> saveEmpty,
    ADD_PROJECT_NAME -> addProjectName,
    IGNORE_PATTERN -> ignorePattern.getOrElse(""))

  def notReady = location.isEmpty || !new File(location).exists()

  def locationDoesntExist = !location.isEmpty && !new File(location).exists
}

object QuickPatchSettings extends PersistentState[QuickPatchSettings] {

  val loggerCategory = "#ua.com.jozic.plugins.QuickPatchPlugin"

  val LOCATION = "location"
  val SAVE_DEFAULT = "save_default"
  val SAVE_EMPTY = "save_empty"
  val ADD_PROJECT_NAME = "add_project_name"
  val IGNORE_PATTERN = "ignore_pattern"

  def doLoad(state: Element): QuickPatchSettings =
    QuickPatchSettings(
      location = stringValue(state, LOCATION),
      saveDefault = booleanValue(state, SAVE_DEFAULT),
      saveEmpty = booleanValue(state, SAVE_EMPTY),
      addProjectName = booleanValue(state, ADD_PROJECT_NAME),
      ignorePattern = stringOption(state, IGNORE_PATTERN)
    )
}