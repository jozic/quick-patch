package ua.com.jozic.quickpatch.core

import com.intellij.openapi.vcs.changes.LocalChangeList
import java.io.{FileOutputStream, OutputStreamWriter}
import com.intellij.codeStyle.CodeStyleFacade
import com.intellij.openapi.diff.impl.patch.{UnifiedDiffWriter, IdeaTextPatchBuilder}
import com.intellij.openapi.project.Project
import java.net.URLEncoder

abstract class QuickPatcher(val project: Project) {

  val extension = ".patch"

  val lineSeparator = CodeStyleFacade.getInstance(project).getLineSeparator

  def location: String

  def prefix = ""

  def makePatch(list: LocalChangeList) {
    // use more scala-ish way
    val writer = new OutputStreamWriter(new FileOutputStream(patchFileName(list)))
    try {
      val patches = IdeaTextPatchBuilder.buildPatch(project,
        list.getChanges, project.getBaseDir.getPresentableUrl, false)
      UnifiedDiffWriter.write(project, patches, writer, lineSeparator, null)
    } finally {
      writer.close()
    }
  }

  def patchFileName(list: LocalChangeList): String = {
    val normalizedLocation = if (location.endsWith("/")) location else location + "/"
    normalizedLocation + prefix + URLEncoder.encode(list.getName, "UTF-8") + extension
  }
}