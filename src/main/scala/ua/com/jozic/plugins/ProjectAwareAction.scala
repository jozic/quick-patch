package ua.com.jozic.plugins

import com.intellij.openapi.actionSystem.{PlatformDataKeys, AnActionEvent, AnAction}

trait ProjectAwareAction {
  self: AnAction =>

  final def project(event: AnActionEvent) = event.getData(PlatformDataKeys.PROJECT)

  final def projectOpt(event: AnActionEvent) = Option(project(event))
}