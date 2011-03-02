package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * @author jozic
 * @since 2/28/11
 */
class RestorePatchesAction extends BasePatchesAction {
  def doAction(event: AnActionEvent) {
    val currentProject = project(event)
    patcherComponent(currentProject).restorePatches()
  }
}
