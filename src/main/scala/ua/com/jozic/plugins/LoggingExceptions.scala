package ua.com.jozic.plugins

import com.intellij.openapi.diagnostic.Logger

import scala.reflect.{ClassTag, classTag}
import scala.util.control.Exception._

trait LoggingExceptions[R] {

  def loggerCategory: String

  lazy val logger = getLogger

  protected def getLogger: Logger = Logger.getInstance(loggerCategory)

  def logExceptionAsWarn[E <: Exception : ClassTag](message: String)(body: => R): Option[R] =
    catching(classTag[E].runtimeClass).either(body) match {
      case Left(t) => logger.warn(message, t); None
      case Right(result) => Some(result)
    }
}