package ua.com.jozic.plugins

import org.scalatest.FunSuite
import scala.IllegalArgumentException
import com.intellij.openapi.diagnostic.{DefaultLogger, Logger}
import collection.mutable.ArrayBuffer

class LoggingExceptionsTest extends FunSuite {


  trait Fixture {
    val messages = ArrayBuffer[(String, Throwable)]()

    val logging = new LoggingExceptions[Unit] {
      def loggerCategory: String = "test"

      override protected def getLogger: Logger = new DefaultLogger(loggerCategory) {
        override def warn(message: String, t: Throwable) {
          messages += (message -> t)
        }
      }
    }
  }

  test("log specified exceptions") {

    new Fixture {
      assert(messages.size === 0)

      logging.logExceptionAsWarn[NullPointerException]("Log all NPEs as warnings :)") {
        throw new NullPointerException
      }

      assert(messages.size === 1)
      assert(messages(0)._1 === "Log all NPEs as warnings :)")
      assert(messages(0)._2.getClass === classOf[NullPointerException])
    }
  }

  test("don't log unspecified exceptions") {
    new Fixture {
      assert(messages.size === 0)

      intercept[IllegalArgumentException] {
        logging.logExceptionAsWarn[NullPointerException]("Log all NPEs as warnings :)") {
          throw new IllegalArgumentException
        }
      }

      assert(messages.size === 0)
    }
  }
}