package ua.com.jozic.plugins

import com.intellij.openapi.diagnostic.Logger
import scala.util.control.Exception._

trait LoggingExceptions {

  def loggerCategory: String

  lazy val logger = Logger.getInstance(loggerCategory)

  def logExceptionAsWarn[E <: Exception](message: String)(body: => Unit)(implicit m: scala.reflect.ClassManifest[E]) {
    val handler = handling(m.erasure) by {
      logger.warn(message, _)
    }
    handler(body)
  }
}