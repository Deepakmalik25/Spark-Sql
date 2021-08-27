package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class OnlyAlphasCleaner(override val uid: String) extends UnaryTransformer[String, String, OnlyAlphasCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def keepOnlyAlphas(instr: String): String = {
      val pattern1 = new Regex("[^a-zA-Z|]")
      val str1 = pattern1.replaceAllIn(instr, " ")
      val str2 = str1.replaceAll("[\\s]+", " ")
      str2
	}
	override protected def createTransformFunc: String => String = {
		keepOnlyAlphas _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}