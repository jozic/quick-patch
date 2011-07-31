package ua.com.jozic.plugins

import com.intellij.openapi.project.Project
import scalaj.collection.Imports._
import com.intellij.openapi.vcs.changes.{ChangeList, ChangeListManager}

class ProjectChangeListsManager(project: Project) {

  private val manager = ChangeListManager.getInstance(project)

  private def emptyChangeList(list: ChangeList) = list.getChanges.isEmpty

  def changeLists = manager.getChangeLists.asScala

  def nonEmptyChangeLists = changeLists.filterNot(emptyChangeList)

  def hasNoChangeLists = changeLists.isEmpty

  def hasChangeLists = !hasNoChangeLists

  def hasNonEmptyChangeLists = !hasOnlyEmptyChangeLists

  def hasOnlyEmptyChangeLists = nonEmptyChangeLists.isEmpty

  def hasOnlyDefaultChangeList = changeLists match {
    case defaultList :: Nil => defaultList.hasDefaultName
    case _ => false
  }

  def hasNonEmptyChangeListsExceptDefault = !hasOnlyEmptyChangeListsExceptDefault

  def hasOnlyEmptyChangeListsExceptDefault = nonEmptyChangeLists.filterNot(_.hasDefaultName).isEmpty
}

object ProjectChangeListsManager {
  def apply(project: Project) = new ProjectChangeListsManager(project)
}