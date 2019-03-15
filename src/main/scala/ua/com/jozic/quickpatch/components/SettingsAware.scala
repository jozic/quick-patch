package ua.com.jozic.quickpatch.components

import ua.com.jozic.plugins.ApplicationComponentsAware

trait SettingsAware extends ApplicationComponentsAware {
  def settings = applicationComponent[QuickPatchSettingsComponent].settings
}