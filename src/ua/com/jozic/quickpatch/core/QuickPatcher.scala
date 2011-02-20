package ua.com.jozic.quickpatch.core

import com.intellij.openapi.vcs.changes.LocalChangeList
import java.io.{FileOutputStream, OutputStreamWriter, File}
import com.intellij.codeStyle.CodeStyleFacade
import com.intellij.openapi.diff.impl.patch.{UnifiedDiffWriter, IdeaTextPatchBuilder}
import com.intellij.openapi.project.Project

abstract class QuickPatcher(val project: Project) {

  val extension = ".patch"

  def location: String

  def prefix = ""

  def makePatch(list: LocalChangeList) {
    val fileName = normalizeLocation + prefix + list.getName + extension
    val file = new File(fileName).getAbsoluteFile
    val writer = new OutputStreamWriter(new FileOutputStream(fileName))
    try {
      val patches = IdeaTextPatchBuilder.buildPatch(project,
        list.getChanges, project.getBaseDir.getPresentableUrl, false)
      val lineSeparator = CodeStyleFacade.getInstance(project).getLineSeparator
      UnifiedDiffWriter.write(patches, writer, lineSeparator)
    } finally {
      writer.close
    }
  }

  def normalizeLocation = if (location.endsWith("/")) {
    location
  } else {
    location + "/"
  }
}