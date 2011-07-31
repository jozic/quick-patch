package ua.com.jozic.quickpatch.core

import com.intellij.openapi.vcs.changes.LocalChangeList

trait SaveDecisionMaker {

  def saveEmpty: Boolean

  def saveDefault: Boolean

  def needToSave(list: LocalChangeList) = {
    val dontSaveDefault = list.hasDefaultName && !saveDefault
    val dontSaveEmpty = list.getChanges.isEmpty && !saveEmpty
    !(dontSaveDefault || dontSaveEmpty)
  }
}