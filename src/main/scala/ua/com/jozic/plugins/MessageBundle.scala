package ua.com.jozic.plugins

import java.util.ResourceBundle

import com.intellij.AbstractBundle

class MessageBundle(bundleName: String) {
  lazy val bundle = ResourceBundle.getBundle(bundleName)

  def message(key: String, params: AnyRef*): String = AbstractBundle.message(bundle, key, params: _*)
}