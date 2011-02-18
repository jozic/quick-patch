package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.{PlatformDataKeys, AnActionEvent, AnAction}

class SavePatchesAction extends AnAction {
  def actionPerformed(event: AnActionEvent) = {
    val project = event.getData(PlatformDataKeys.PROJECT)
    new QuickPatcher(project).patchCurrentChanges
  }
}
