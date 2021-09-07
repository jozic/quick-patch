package ua.com.jozic.quickpatch.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.Service.Level
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.LocalChangeList
import ua.com.jozic.plugins.ProjectChangeListsManager
import ua.com.jozic.quickpatch.core.QuickPatcher

@Service(Array(Level.PROJECT))
final class QuickPatcherService(val project: Project) {

  private val changeListsManager = ProjectChangeListsManager(project)

  def settings: QuickPatchSettings = QuickPatchSettings(project)

  val quickPatcher: QuickPatcher = new QuickPatcher(project) {

    override def location: String = settings.location

    override def prefix: String = if (settings.addProjectName) project.getName + "." else super.prefix
  }

  val needToSave = (list: LocalChangeList) => {
    val dontSaveDefault = list.hasDefaultName && !settings.saveDefault
    val dontSaveEmpty = list.getChanges.isEmpty && !settings.saveEmpty
    val ignoreByPattern = settings.maybeIgnorePattern.exists(_.r.pattern.matcher(list.getName).matches)
    !(dontSaveDefault || dontSaveEmpty || ignoreByPattern)
  }

  def makePatches(): Unit = {
    changeListsManager.changeLists.filter(needToSave).foreach(quickPatcher.makePatch)
  }
}