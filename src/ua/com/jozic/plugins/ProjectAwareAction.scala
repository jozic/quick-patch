package ua.com.jozic.plugins

import com.intellij.openapi.actionSystem.{PlatformDataKeys, AnActionEvent, AnAction}

trait ProjectAwareAction {
  self: AnAction =>

  def project(event: AnActionEvent) = event.getData(PlatformDataKeys.PROJECT);

  def projectOpt(event: AnActionEvent) = project(event) match {
    case null => None
    case p => Some(p)
  }
}