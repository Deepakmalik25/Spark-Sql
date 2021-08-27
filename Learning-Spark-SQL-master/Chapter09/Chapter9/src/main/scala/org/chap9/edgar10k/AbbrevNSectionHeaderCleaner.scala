package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class AbbrevNSectionHeaderCleaner(override val uid: String) extends UnaryTransformer[String, String, AbbrevNSectionHeaderCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteAbbrevNSectionHeaders(instr: String): String = {
      //println("Input string length="+ instr.length())
      val pattern1 = new Regex("[A-Z]\\.([A-Z]\\.)+")
      val str1 = pattern1.replaceAllIn(instr, " ")
      val pattern2 = new Regex("[0-9]+[a-zA-Z]*\\.([0-9])+")
      val str2 = pattern2.replaceAllIn(str1, " ")
      //println("Output string length ="+ str2.length())
      //println("String length reduced by="+ (instr.length - str2.length()))
      str2
	}
	override protected def createTransformFunc: String => String = {
		deleteAbbrevNSectionHeaders _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}