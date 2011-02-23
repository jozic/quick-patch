package ua.com.jozic.plugins

import com.intellij.openapi.components.PersistentStateComponent

trait BasePersistentStateComponent[T] extends PersistentStateComponent[T] {
  private var state = initState

  def initState: T

  def loadState(state: T) {
    this.state = state;
  }

  def getState = state
}