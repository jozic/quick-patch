package ua.com.jozic.quickpatch.components

import com.intellij.openapi.components.ApplicationComponent
import org.jetbrains.annotations.NotNull
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import ua.com.jozic.plugins.ProjectComponentsAware

class QuickPatchConfigurationComponent extends ApplicationComponent with Configurable
with ProjectComponentsAware {

  def initComponent {}

  def disposeComponent {}

  @NotNull def getComponentName = "QuickPatchConfigurationComponent"

  def getHelpTopic = ""

  def getIcon = null

  def getDisplayName = "Quick Patch"

  def disposeUIResources {}

  def reset {}

  def apply {}

  def isModified = false

  def createComponent = QuickPatchSettings.peer

  def savePatchesFor(project: Project) {
    val quickPatcherComponent = projectComponent[QuickPatcherComponent](project)
    quickPatcherComponent makePatches
  }
}