package gcp4zio

import gcp4zio.utils.ApplicationLogger
import zio.Task

package object bq extends ApplicationLogger {
  type BQEnv = BQApi[Task]

  case class BQLoadException(msg: String) extends RuntimeException(msg)

  sealed trait BQInputType extends Serializable
  object BQInputType {
    final case class CSV(
        delimiter: String = ",",
        headerPresent: Boolean = true,
        parseMode: String = "FAILFAST",
        quoteChar: String = "\""
    ) extends BQInputType {
      override def toString: String =
        s"CSV with delimiter => $delimiter header_present => $headerPresent parse_mode => $parseMode"
    }
    final case class JSON(multiLine: Boolean = false) extends BQInputType {
      override def toString: String = s"Json with multiline  => $multiLine"
    }
    case object BQ      extends BQInputType
    case object PARQUET extends BQInputType
    case object ORC     extends BQInputType
  }
}
