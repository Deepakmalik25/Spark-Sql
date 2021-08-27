package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class AbbrevCleaner(override val uid: String) extends UnaryTransformer[String, String, AbbrevCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteAbbrev(instr: String): String = {
	      val pattern = new Regex("[A-Z]\\.([A-Z]\\.)+")
	      val str = pattern.replaceAllIn(instr, " ")
	      str
	}
	override protected def createTransformFunc: String => String = {
		deleteAbbrev _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}