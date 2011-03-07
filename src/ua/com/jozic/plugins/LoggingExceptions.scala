package ua.com.jozic.plugins

import com.intellij.openapi.diagnostic.Logger
import scala.util.control.Exception._

trait LoggingExceptions {

  def loggerCategory: String

  lazy val logger = Logger.getInstance(loggerCategory)

  def warn(message: String, e: Exception) = logger.warn(message, e)

  def loggingAsWarn(warnMessage: String)(body: => Unit) {
    val handle = handling(classOf[Exception]) by {
      case e => logger.warn(warnMessage, e)
    }
    handle {
      body
    }
  }
}