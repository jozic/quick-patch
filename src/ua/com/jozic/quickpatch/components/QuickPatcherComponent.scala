package ua.com.jozic.quickpatch.components

import com.intellij.openapi.project.Project
import com.intellij.openapi.components.ProjectComponent
import ua.com.jozic.quickpatch.core.{SaveDecisionMaker, QuickPatcher}
import com.intellij.openapi.vcs.changes.{ChangeListManager, LocalChangeList}
import scalaj.collection.Imports._

class QuickPatcherComponent(val project: Project) extends ProjectComponent {

  val quickPatcher = new QuickPatcher(project) {
    def location = QuickPatchSettings.patchesLocation

    override def prefix = QuickPatchSettings.addProjectName match {
      case true => project.getName + "."
      case _ => super.prefix
    }
  }

  val decisionMaker = new SaveDecisionMaker {
    def saveDefault = QuickPatchSettings.saveDefault

    def saveEmpty = QuickPatchSettings.saveEmpty
  }

  def disposeComponent {
  }

  def initComponent {
  }

  def projectClosed {
  }

  def projectOpened {
  }

  def getComponentName = "QuickPatcherComponent"

  def makePatches {
    val changeListManager = ChangeListManager.getInstance(project)
    val localChangeLists = changeListManager.getChangeLists.asScala
    localChangeLists.filter(forSave).foreach(makePatch)
  }

  def forSave(changeList: LocalChangeList) = decisionMaker.needToSave(changeList)

  def makePatch(changeList: LocalChangeList) = quickPatcher.makePatch(changeList)
}