package ua.com.jozic.plugins

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.util.xmlb.XmlSerializerUtil

trait SelfPersistentStateComponent[T] extends PersistentStateComponent[T] {
  self: T =>
  def loadState(state: T) {
    XmlSerializerUtil.copyBean(state, this);
  }

  def getState = this
}