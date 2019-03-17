package ua.com.jozic.quickpatch.components

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.LocalChangeList
import ua.com.jozic.plugins.ProjectChangeListsManager
import ua.com.jozic.quickpatch.core.QuickPatcher

class QuickPatcherComponent(val project: Project) extends ProjectComponent {

  private val changeListsManager = ProjectChangeListsManager(project)

  def settings = QuickPatchSettings(project)

  val quickPatcher = new QuickPatcher(project) {

    override def location: String = settings.location

    override def prefix: String = if (settings.addProjectName) project.getName + "." else super.prefix
  }

  val needToSave = (list: LocalChangeList) => {
    val dontSaveDefault = list.hasDefaultName && !settings.saveDefault
    val dontSaveEmpty = list.getChanges.isEmpty && !settings.saveEmpty
    val ignoreByPattern = settings.maybeIgnorePattern.exists(_.r.pattern.matcher(list.getName).matches)
    !(dontSaveDefault || dontSaveEmpty || ignoreByPattern)
  }

  override def getComponentName = "QuickPatcherComponent"

  def makePatches(): Unit = {
    changeListsManager.changeLists.filter(needToSave).foreach(quickPatcher.makePatch)
  }
}