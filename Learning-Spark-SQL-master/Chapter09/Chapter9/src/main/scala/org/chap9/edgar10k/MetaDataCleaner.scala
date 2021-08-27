package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class MetaDataCleaner(override val uid: String) extends UnaryTransformer[String, String, MetaDataCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteMetaData(instr: String): String = {
      val pattern1 = new Regex("<HEAD>.*?</HEAD>")
      val str1 = pattern1.replaceAllIn(instr, " ")
      val pattern2 = new Regex("(?s)<TYPE>.*?<SEQUENCE>.*?<FILENAME>.*?<DESCRIPTION>.*?")
      val str2 = pattern2.replaceAllIn(str1, " ")
      str2
	}
	override protected def createTransformFunc: String => String = {
		deleteMetaData _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}