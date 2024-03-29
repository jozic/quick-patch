package ua.com.jozic.quickpatch.core

import java.io.FileWriter
import java.net.URLEncoder

import scala.util.control.Exception._

import com.intellij.application.options.CodeStyle
import com.intellij.openapi.diff.impl.patch.{IdeaTextPatchBuilder, UnifiedDiffWriter}
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.LocalChangeList
import com.intellij.openapi.vcs.changes.patch.PatchWriter

abstract class QuickPatcher(val project: Project) {

  val extension = ".patch"

  val lineSeparator = CodeStyle.getSettings(project).getLineSeparator

  def location: String

  def prefix = ""

  def makePatch(list: LocalChangeList): Unit = {

    def patchFileName: String = {
      val normalizedLocation = if (location.endsWith("/")) location else location + "/"
      normalizedLocation + prefix + URLEncoder.encode(list.getName, "UTF-8") + extension
    }

    val writer = new FileWriter(patchFileName)
    ultimately(writer.close()) {
      val base = PatchWriter.calculateBaseDirForWritingPatch(project, list.getChanges)
      val patches = IdeaTextPatchBuilder.buildPatch(project,
        list.getChanges, base, false)
      UnifiedDiffWriter.write(project, patches, writer, lineSeparator, null)
    }
  }
}