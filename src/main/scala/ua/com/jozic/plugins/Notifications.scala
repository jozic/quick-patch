package ua.com.jozic.plugins

import com.intellij.openapi.project.Project
import com.intellij.notification.{NotificationType, Notification, Notifications => JBNotifications}

trait Notifications {
  def doNotify(notification: Notification, project: Project) {
    JBNotifications.Bus.notify(notification, project)
  }

  def success(groupId: String, title: String, content: String): Notification =
    new Notification(groupId, title, content, NotificationType.INFORMATION)

  def warning(groupId: String, title: String, content: String): Notification =
    new Notification(groupId, title, content, NotificationType.WARNING)

  def failure(groupId: String, title: String, content: String): Notification =
    new Notification(groupId, title, content, NotificationType.ERROR)
}