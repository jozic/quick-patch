package ua.com.jozic.quickpatch.components

import java.io.File

import com.intellij.openapi.components._
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

import scala.beans.BeanProperty

@State(
  name = "QuickPatchSettings",
  storages = Array(new Storage(StoragePathMacros.WORKSPACE_FILE))
)
class QuickPatchSettings extends PersistentStateComponent[QuickPatchSettings] {

  @BeanProperty var location: String = ""
  @BeanProperty var saveDefault: Boolean = true
  @BeanProperty var saveEmpty: Boolean = false
  @BeanProperty var addProjectName: Boolean = false
  @BeanProperty var ignorePattern: String = ""

  override def loadState(config: QuickPatchSettings): Unit =
    XmlSerializerUtil.copyBean(config, this)

  override def getState: QuickPatchSettings = this

  def notReady: Boolean = location.isEmpty || !new File(location).exists()

  def locationDoesntExist: Boolean = location.nonEmpty && !new File(location).exists

  def maybeIgnorePattern: Option[String] = if (ignorePattern.trim.isEmpty) None else Some(ignorePattern)
}

object QuickPatchSettings {
  def apply(project: Project): QuickPatchSettings =
    ServiceManager.getService(project, classOf[QuickPatchSettings])
}
