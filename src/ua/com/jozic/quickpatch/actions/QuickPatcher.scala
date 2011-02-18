package ua.com.jozic.quickpatch.actions

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.{LocalChangeList, ChangeListManager}
import java.io.{FileOutputStream, OutputStreamWriter, File}
import com.intellij.codeStyle.CodeStyleFacade
import com.intellij.openapi.diff.impl.patch.{UnifiedDiffWriter, IdeaTextPatchBuilder}
import scalaj.collection.Implicits._

class QuickPatcher(val project: Project) {

  val LOCATION = "C:/patches/"
  val saveEmpty = false;
  val saveDefault = false;
  val decisionMaker = new SaveDecisionMaker(saveEmpty, saveDefault)

  def patchCurrentChanges {
    val changeListManager = ChangeListManager.getInstance(project)
    val localChangeLists = changeListManager.getChangeLists.asScala
    localChangeLists.filter(forSave).foreach(makePatch)
  }

  def forSave(list: LocalChangeList) = decisionMaker.needToSave(list)

  def makePatch(list: LocalChangeList) = {
    val fileName = LOCATION + list.getName + ".patch"
    val file = new File(fileName).getAbsoluteFile()
    val writer = new OutputStreamWriter(new FileOutputStream(fileName))
    try {
      val patches = IdeaTextPatchBuilder.buildPatch(project,
        list.getChanges, project.getBaseDir().getPresentableUrl(), false)
      val lineSeparator = CodeStyleFacade.getInstance(project).getLineSeparator
      UnifiedDiffWriter.write(patches, writer, lineSeparator);
    } finally {
      writer.close();
    }
  }

  class SaveDecisionMaker(val saveEmpty: Boolean, val saveDefault: Boolean) {

    def needToSave(list: LocalChangeList): Boolean = {
      if (list.hasDefaultName && !saveDefault) {
        return false
      }
      val isEmpty = list.getChanges.size == 0
      if (isEmpty && !saveEmpty) {
        return false
      }
      return true
    }
  }

}