package ua.com.jozic.quickpatch.components

import org.jetbrains.annotations.NotNull
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import ua.com.jozic.plugins.ProjectComponentsAware
import com.intellij.openapi.components.{Storage, PersistentStateComponent, State, ApplicationComponent}
import org.jdom.Element
import com.intellij.openapi.util.IconLoader

@State(name = "QuickPatchSettings",
  storages = Array(new Storage(id = "default", file = "$APP_CONFIG$/qp.xml")))
class QuickPatchSettingsComponent extends ApplicationComponent with Configurable
with ProjectComponentsAware with PersistentStateComponent[Element] {

  var settings = new QuickPatchSettings
  val settingsUI = new QuickPatchSettingsUI(settings)

  def getState = settings.getState

  def loadState(state: Element) {
    settings.loadState(state)
  }

  def initComponent() {}

  def disposeComponent() {}

  @NotNull def getComponentName = "QuickPatchSettingsComponent"

  def getHelpTopic = ""

  def getIcon = IconLoader.getIcon("/ua/com/jozic/quickpatch/icons/quickpatch.png")

  def getDisplayName = "Quick Patch"

  def disposeUIResources() {}

  def reset() {
    settingsUI.updateUI()
  }

  def apply() {
    settingsUI.updateModel()
  }

  def isModified = settingsUI.isModified

  def createComponent = settingsUI.jComponent

  def savePatchesFor(project: Project) {
    val quickPatcherComponent = projectComponent[QuickPatcherComponent](project)
    quickPatcherComponent.makePatches()
  }
}