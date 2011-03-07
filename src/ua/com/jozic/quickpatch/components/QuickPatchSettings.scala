package ua.com.jozic.quickpatch.components

import org.jdom.Element
import java.io.File
import ua.com.jozic.plugins.{LoggingExceptions, PersistentState}

class QuickPatchSettings extends PersistentState with LoggingExceptions {

  private val LOCATION = "location"
  private val SAVE_DEFAULT = "save_default"
  private val SAVE_EMPTY = "save_empty"
  private val ADD_PROJECT_NAME = "add_project_name"

  var location = ""
  var saveDefault = true
  var saveEmpty = false
  var addProjectName = false

  def options = Map(LOCATION -> location, SAVE_DEFAULT -> saveDefault,
    SAVE_EMPTY -> saveEmpty, ADD_PROJECT_NAME -> addProjectName)

  def doLoad(state: Element) {
    location = stringValue(state, LOCATION)
    saveDefault = booleanValue(state, SAVE_DEFAULT)
    saveEmpty = booleanValue(state, SAVE_EMPTY)
    addProjectName = booleanValue(state, ADD_PROJECT_NAME)
  }


  def loggerCategory = "#ua.com.jozic.plugins.QuickPatchPlugin"

  def notReady = location.isEmpty || !new File(location).exists()
}