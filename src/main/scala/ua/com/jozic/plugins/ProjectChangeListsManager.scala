package ua.com.jozic.plugins

import scala.jdk.CollectionConverters._

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.{ChangeListManager, LocalChangeList}

case class ProjectChangeListsManager(project: Project) {

  private val manager = ChangeListManager.getInstance(project)

  def changeLists: List[LocalChangeList] = manager.getChangeLists.asScala.toList

}