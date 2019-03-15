package ua.com.jozic.quickpatch.components

import org.jetbrains.annotations.NotNull
import com.intellij.openapi.options.Configurable
import ua.com.jozic.plugins.ProjectComponentsAware
import com.intellij.openapi.components.{Storage, PersistentStateComponent, State, ApplicationComponent}
import org.jdom.Element
import com.intellij.openapi.util.IconLoader

@State(name = "QuickPatchSettings$MODULE",
  storages = Array(new Storage(id = "default", file = "$APP_CONFIG$/qp.xml")))
class QuickPatchSettingsComponent extends ApplicationComponent with Configurable
with ProjectComponentsAware with PersistentStateComponent[Element] {

  var settings: QuickPatchSettings = QuickPatchSettings()

  val settingsUI = new QuickPatchSettingsUI

  def getState = QuickPatchSettings.getState(settings.options)

  def loadState(state: Element) {
    for (qps <- QuickPatchSettings.loadState(state, "Can't load settings for Quick Patch"))
      settings = qps
  }

  def initComponent() {}

  def disposeComponent() {}

  @NotNull def getComponentName = "QuickPatchSettingsComponent"

  def getHelpTopic = ""

  def getIcon = IconLoader.getIcon("/ua/com/jozic/quickpatch/icons/quickpatch.png")

  def getDisplayName = "Quick Patch"

  def disposeUIResources() {}

  def reset() {
    settingsUI.updateUI(settings)
  }

  def apply() {
    settings = settingsUI.currentSettings
    settingsUI.checkIfReadyToUse(settings)
  }

  def isModified = settingsUI.isModified(settings)

  def createComponent = settingsUI.jComponent
}