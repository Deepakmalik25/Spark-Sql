package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class StringsCleaner(override val uid: String) extends UnaryTransformer[String, String, StringsCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteStrings(str: String): String = {
      val strings = Array("IDEA: XBRL DOCUMENT", "\\/\\* Do Not Remove This Comment \\*\\/", "v2.4.0.8")
      var str1 = str
      for(myString <- strings) {
         var pattern1 = new Regex(myString)
         str1 = pattern1.replaceAllIn(str1, " ")
      }
      str1
	}
	override protected def createTransformFunc: String => String = {
		deleteStrings _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}