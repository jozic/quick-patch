package ua.com.jozic.plugins

import com.intellij.openapi.components.PersistentStateComponent

trait BasePersistentStateComponent[T] extends PersistentStateComponent[T] {
  var state: T

  def loadState(state: T) {
    this.state = state;
  }

  def getState = state
}