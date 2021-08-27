package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class ExtCharsetCleaner(override val uid: String) extends UnaryTransformer[String, String, ExtCharsetCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteExtCharset(instr: String): String = {
      val pattern1 = new Regex("(?s)(&#32;|&nbsp;|&#x(A|a)0;)")
      val str1 = pattern1.replaceAllIn(instr, " ")
      val pattern2 = new Regex("(&#146;|&#x2019;)")
      val str2 = pattern2.replaceAllIn(str1, "'")
      val pattern3 = new Regex("&#120;")
      val str3 = pattern3.replaceAllIn(str2, "x")
      val pattern4 = new Regex("(&#168;|&#167;|&reg;|&#153;|&copy;)")
      val str4 = pattern4.replaceAllIn(str3, " ")
      val pattern5 = new Regex("(&#147;|&#148;|&#x201C;|&#x201D;)")
      val str5 = pattern5.replaceAllIn(str4, "\"")
      val pattern6 = new Regex("&amp;")
      val str6 = pattern6.replaceAllIn(str5, "&")
      val pattern7 = new Regex("(&#150;|&#151;|&#x2013;)")
      val str7 = pattern7.replaceAllIn(str6, "-")
      val pattern8 = new Regex("&#8260;")
      val str8 = pattern8.replaceAllIn(str7, "/")
      str8
	}
	override protected def createTransformFunc: String => String = {
		deleteExtCharset _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}