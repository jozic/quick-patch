package ua.com.jozic.plugins

import com.intellij.openapi.project.Project
import com.intellij.notification.{NotificationType, Notification, Notifications => JBNotifications}

trait Notifications {
  def doNotify(notification: Notification, project: Project) {
    JBNotifications.Bus.notify(notification, project)
  }

  def doNotify(notification: Notification) {
    JBNotifications.Bus.notify(notification)
  }

  def success(groupId: String, title: String, content: String) =
    new Notification(groupId, title, content, NotificationType.INFORMATION)

  def warning(groupId: String, title: String, content: String) =
    new Notification(groupId, title, content, NotificationType.WARNING)

  def failure(groupId: String, title: String, content: String) =
    new Notification(groupId, title, content, NotificationType.ERROR)
}