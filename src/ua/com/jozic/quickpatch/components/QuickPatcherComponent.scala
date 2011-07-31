package ua.com.jozic.quickpatch.components

import com.intellij.openapi.project.Project
import ua.com.jozic.quickpatch.core.{SaveDecisionMaker, QuickPatcher}
import com.intellij.openapi.vcs.changes.LocalChangeList
import ua.com.jozic.plugins.ProjectChangeListsManager

class QuickPatcherComponent(val project: Project) extends BaseQuickPatchComponent {

  private val changeListsManager = ProjectChangeListsManager(project)

  val quickPatcher = new QuickPatcher(project) {
    def location = settings.location

    override def prefix = if (settings.addProjectName) project.getName + "." else super.prefix
  }

  val decisionMaker = new SaveDecisionMaker {
    def saveDefault = settings.saveDefault

    def saveEmpty = settings.saveEmpty
  }

  def getComponentName = "QuickPatcherComponent"

  def makePatches() {
    val localChangeLists = changeListsManager.changeLists
    localChangeLists.filter(forSave).foreach(makePatch)
  }

  def forSave(changeList: LocalChangeList) = decisionMaker needToSave changeList

  def makePatch(changeList: LocalChangeList) {
    quickPatcher.makePatch(changeList)
  }
}