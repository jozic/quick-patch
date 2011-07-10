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

  def normalizeLocation = if (location.endsWith("/")) location else location + "/"

  def makePatch(list: LocalChangeList) {
    val fileName = normalizeLocation + prefix + URLEncoder.encode(list.getName) + extension
    val writer = new OutputStreamWriter(new FileOutputStream(fileName))
    try {
      val patches = IdeaTextPatchBuilder.buildPatch(project,
        list.getChanges, project.getBaseDir.getPresentableUrl, false)
      UnifiedDiffWriter.write(patches, writer, lineSeparator)
    } finally {
      writer.close()
    }
  }
}