package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class AllURLsFileNamesDigitsPunctuationExceptPeriodCleaner(override val uid: String) extends UnaryTransformer[String, String, AllURLsFileNamesDigitsPunctuationExceptPeriodCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteAllURLsFileNamesDigitsPunctuationExceptPeriod(instr: String): String = {
      val pattern1 = new Regex("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
      val str1 = pattern1.replaceAllIn(instr, "")
      val pattern2 = new Regex("[_a-zA-Z0-9\\-\\.]+.(txt|sgml|xml|xsd|htm|html)")
      val str2 = pattern2.replaceAllIn(str1, " ")
      val pattern3 = new Regex("[^a-zA-Z|^.]")
      val str3 = pattern3.replaceAllIn(str2, " ")
      str3
	}
	override protected def createTransformFunc: String => String = {
		deleteAllURLsFileNamesDigitsPunctuationExceptPeriod _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}