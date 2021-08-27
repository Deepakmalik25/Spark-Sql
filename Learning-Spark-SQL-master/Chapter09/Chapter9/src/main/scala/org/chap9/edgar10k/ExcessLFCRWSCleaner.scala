package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import scala.util.matching.Regex
import org.apache.spark.ml.util.Identifiable

class ExcessLFCRWSCleaner(override val uid: String) extends UnaryTransformer[String, String, ExcessLFCRWSCleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def deleteExcessLFCRWS(instr: String): String = {
            val pattern1 = new Regex("[\n\r]+")
            val str1 = pattern1.replaceAllIn(instr, "\n")
            val pattern2 = new Regex("[\t]+")
            val str2 = pattern2.replaceAllIn(str1, " ")
            val pattern3 = new Regex("\\s+")
            val str3 = pattern3.replaceAllIn(str2, " ")
            str3
      }
	override protected def createTransformFunc: String => String = {
		deleteExcessLFCRWS _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}