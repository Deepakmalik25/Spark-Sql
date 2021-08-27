package org.chap9.edgar10k

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.ml.util.Identifiable
import org.apache.spark.sql.types.{DataType, DataTypes, StringType}
import org.apache.spark.ml.feature.{HashingTF, IDF, RegexTokenizer, Tokenizer, NGram, StopWordsRemover}
import org.apache.spark.ml.linalg.{DenseVector, SparseVector, Vector, Vectors}

class Cleaner(override val uid: String) extends UnaryTransformer[String, String, Cleaner] {
	def this() = this(Identifiable.randomUID("cleaner"))
	def cleanerff(s: String): String = {
		s.replaceAll("(]]d|,|:|;|\\?|!)", "")
	}
	override protected def createTransformFunc: String => String = {
		cleanerff _
	}
	override protected def validateInputType(inputType: DataType): Unit = {
		require(inputType == StringType)
	}
	override protected def outputDataType: DataType = DataTypes.StringType
}