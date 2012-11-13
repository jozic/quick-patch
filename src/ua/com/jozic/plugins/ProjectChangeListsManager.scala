package ua.com.jozic.plugins

import com.intellij.openapi.project.Project
import scalaj.collection.Imports._
import com.intellij.openapi.vcs.changes.ChangeListManager

case class ProjectChangeListsManager(project: Project) {

  private val manager = ChangeListManager.getInstance(project)

  def changeLists = manager.getChangeLists.asScala

  def nonEmptyChangeLists = changeLists.filterNot(_.getChanges.isEmpty)

  def hasNoChangeLists = changeLists.isEmpty

  def hasChangeLists = !hasNoChangeLists

  def hasOnlyEmptyChangeLists = nonEmptyChangeLists.isEmpty

  def hasNonEmptyChangeLists = !hasOnlyEmptyChangeLists

  def hasOnlyDefaultChangeList = changeLists match {
    case defaultList :: Nil => defaultList.hasDefaultName
    case _ => false
  }

  def hasOnlyEmptyChangeListsExceptDefault = nonEmptyChangeLists.filterNot(_.hasDefaultName).isEmpty

  def hasNonEmptyChangeListsExceptDefault = !hasOnlyEmptyChangeListsExceptDefault
}