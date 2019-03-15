package ua.com.jozic.plugins

import com.intellij.openapi.project.Project
import collection.JavaConverters._
import com.intellij.openapi.vcs.changes.{LocalChangeList, ChangeListManager}

case class ProjectChangeListsManager(project: Project) {

  private val manager = ChangeListManager.getInstance(project)

  def changeLists: List[LocalChangeList] = manager.getChangeLists.asScala.toList

}