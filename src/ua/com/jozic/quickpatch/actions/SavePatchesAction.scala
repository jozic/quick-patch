package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.AnActionEvent

class SavePatchesAction extends BasePatchesAction {

  def doAction(event: AnActionEvent) {
    val currentProject = project(event)
    patcherComponent(currentProject).makePatches()
  }
}