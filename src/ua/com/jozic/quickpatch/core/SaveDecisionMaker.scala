package ua.com.jozic.quickpatch.core

import com.intellij.openapi.vcs.changes.LocalChangeList

trait SaveDecisionMaker {

  def saveEmpty: Boolean

  def saveDefault: Boolean

  def needToSave(list: LocalChangeList): Boolean = {
    if (list.hasDefaultName && !saveDefault) {
      return false
    }
    val isEmpty = list.getChanges.size == 0
    if (isEmpty && !saveEmpty) {
      return false
    }
    true
  }
}