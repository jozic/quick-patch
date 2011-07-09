package ua.com.jozic.plugins

import com.intellij.openapi.diagnostic.Logger
import scala.util.control.Exception._

trait LoggingExceptions {

  def loggerCategory: String

  lazy val logger = Logger.getInstance(loggerCategory)

  def loggingAsWarn(message: String)(body: => Unit) {
    warning(message)(body)
  }

  private def warning(message: String) = handling[Unit](classOf[Exception]) by {
    logger.warn(message, _)
  }
}