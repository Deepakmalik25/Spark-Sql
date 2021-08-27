package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class SectionHeadersCleaner(override val uid: String) extends UnaryTransformer[String, String, SectionHeadersCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteSectionHeaders(instr: String): String = {
      val pattern = new Regex("[0-9]+[a-zA-Z]*\\.([0-9])*")
      val str = pattern.replaceAllIn(instr, " ")
      str
    }
	override protected def createTransformFunc: String => String = {
		deleteSectionHeaders _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}