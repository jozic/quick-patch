package ua.com.jozic.plugins

import com.intellij.CommonBundle
import java.util.ResourceBundle

trait MessageBundle {
  lazy val bundle = ResourceBundle.getBundle(bundleName)

  def message(key: String, params: Any*) = CommonBundle.message(bundle, key, params)

  def bundleName: String
}


