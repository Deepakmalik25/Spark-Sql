package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class DocTypesCleaner(override val uid: String) extends UnaryTransformer[String, String, DocTypesCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteDocTypes(instr: String): String = {
      val pattern = new Regex("(?s)<TYPE>(GRAPHIC|EXCEL|PDF|ZIP|COVER|CORRESP|EX-10[01].INS|EX-99.SDR [KL].INS|EX-10[01].SCH|EX-99.SDR [KL].SCH|EX-10[01].CAL|EX-99.SDR [KL].CAL|EX-10[01].DEF|EX-99.SDR [KL].LAB|EX-10[01].LAB|EX-99.SDR [KL].LAB|EX-10[01].PRE|EX-99.SDR [KL].PRE|EX-10[01].PRE|EX-99.SDR [KL].PRE).*?</TEXT>")   
      val str = pattern.replaceAllIn(instr, " ")
      str
	}
	override protected def createTransformFunc: String => String = {
		deleteDocTypes _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}