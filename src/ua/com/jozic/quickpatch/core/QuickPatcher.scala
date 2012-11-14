package ua.com.jozic.quickpatch.core

import com.intellij.openapi.vcs.changes.LocalChangeList
import java.io.FileWriter
import com.intellij.codeStyle.CodeStyleFacade
import com.intellij.openapi.diff.impl.patch.{UnifiedDiffWriter, IdeaTextPatchBuilder}
import com.intellij.openapi.project.Project
import java.net.URLEncoder
import scala.util.control.Exception._

abstract class QuickPatcher(val project: Project) {

  val extension = ".patch"

  val lineSeparator = CodeStyleFacade.getInstance(project).getLineSeparator

  def location: String

  def prefix = ""

  def makePatch(list: LocalChangeList) {

    def patchFileName: String = {
      val normalizedLocation = if (location.endsWith("/")) location else location + "/"
      normalizedLocation + prefix + URLEncoder.encode(list.getName, "UTF-8") + extension
    }

    val writer = new FileWriter(patchFileName)
    ultimately(writer.close()) {
      val patches = IdeaTextPatchBuilder.buildPatch(project,
        list.getChanges, project.getBaseDir.getPresentableUrl, false)
      UnifiedDiffWriter.write(project, patches, writer, lineSeparator, null)
    }
  }
}