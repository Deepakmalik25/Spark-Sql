package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class TablesNHTMLElemCleaner(override val uid: String) extends UnaryTransformer[String, String, TablesNHTMLElemCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteTablesNHTMLElem(instr: String): String = {
      val pattern1 = new Regex("(?s)(?i)<Table.*?</Table>")
      val str1 = pattern1.replaceAllIn(instr, " ")
      val pattern2 = new Regex("(?s)<[^>]*>")
      val str2 = pattern2.replaceAllIn(str1, " ")
      str2
	}
	override protected def createTransformFunc: String => String = {
		deleteTablesNHTMLElem _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}