package ua.com.jozic.plugins

import java.util.ResourceBundle

import com.intellij.CommonBundle

class MessageBundle(bundleName: String) {
  lazy val bundle = ResourceBundle.getBundle(bundleName)

  def message(key: String, params: AnyRef*) = CommonBundle.message(bundle, key, params: _*)
}