package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class SectionHeaderCleaner(override val uid: String) extends UnaryTransformer[String, String, SectionHeaderCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteSectionHeaders(instr: String): String = {
      //println("Input string length="+ instr.length())
      val pattern = new Regex("Item  .|[A-Z].")
      val str = pattern.replaceAllIn(instr, " ")
      //println("Output string length ="+ str.length())
      //println("String length reduced by="+ (instr.length - str.length()))
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