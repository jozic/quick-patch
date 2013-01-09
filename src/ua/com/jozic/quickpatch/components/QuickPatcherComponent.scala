package ua.com.jozic.quickpatch.components

import com.intellij.openapi.project.Project
import ua.com.jozic.quickpatch.core.QuickPatcher
import com.intellij.openapi.vcs.changes.LocalChangeList
import ua.com.jozic.plugins.ProjectChangeListsManager

class QuickPatcherComponent(val project: Project) extends BaseQuickPatchComponent {

  private val changeListsManager = ProjectChangeListsManager(project)

  val quickPatcher = new QuickPatcher(project) {
    def location = settings.location

    override def prefix = if (settings.addProjectName) this.project.getName + "." else super.prefix
  }

  val needToSave = (list: LocalChangeList) => {
    val dontSaveDefault = list.hasDefaultName && !settings.saveDefault
    val dontSaveEmpty = list.getChanges.isEmpty && !settings.saveEmpty
    val ignoreByPattern = settings.ignorePattern map (_.r.pattern.matcher(list.getName).matches) getOrElse false
    !(dontSaveDefault || dontSaveEmpty || ignoreByPattern)
  }

  def getComponentName = "QuickPatcherComponent"

  def makePatches() {
    for (list <- changeListsManager.changeLists if needToSave(list))
      quickPatcher.makePatch(list)
  }
}