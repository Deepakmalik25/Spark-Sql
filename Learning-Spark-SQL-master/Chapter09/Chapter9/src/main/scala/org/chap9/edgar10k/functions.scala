package org.chap9.edgar10k

import org.apache.spark.sql.types._
import scala.util.matching.Regex
object functions {
	def deleteAbbrev(instr: String): String = {
	      println("Input string length="+ instr.length())
	      val pattern = new Regex("[A-Z]\\.([A-Z]\\.)+")
	      val str = pattern.replaceAllIn(instr, " ")
	      println("Output string length ="+ str.length())
	      println("String length reduced by="+ (instr.length - str.length()))
	      str
	}

	//Removes some Decimal Numbers as well but that is alright in textual analysis we are doing.
	def deleteSectionHeaders(instr: String): String = {
	      println("Input string length="+ instr.length())
	      val pattern = new Regex("[0-9]+[a-zA-Z]*\\.([0-9])*")
	      val str = pattern.replaceAllIn(instr, " ")
	      println("Output string length ="+ str.length())
	      println("String length reduced by="+ (instr.length - str.length()))
	      str
	}

	def deleteDocTypes(instr: String): String = {
	      println("Input string length="+ instr.length())
	      val pattern = new Regex("(?s)<TYPE>(GRAPHIC|EXCEL|PDF|ZIP|COVER|CORRESP|EX-10[01].INS|EX-99.SDR [KL].INS|EX-10[01].SCH|EX-99.SDR [KL].SCH|EX-10[01].CAL|EX-99.SDR [KL].CAL|EX-10[01].DEF|EX-99.SDR [KL].LAB|EX-10[01].LAB|EX-99.SDR [KL].LAB|EX-10[01].PRE|EX-99.SDR [KL].PRE|EX-10[01].PRE|EX-99.SDR [KL].PRE).*?</TEXT>")   
	      val str = pattern.replaceAllIn(instr, " ")
	      println("Output string length ="+ str.length())
	      println("String length reduced by="+ (instr.length - str.length()))
	      str
	}

	def deleteMetaData(instr: String): String = {
	      val pattern1 = new Regex("<HEAD>.*?</HEAD>")
	      val str1 = pattern1.replaceAllIn(instr, " ")
	      val pattern2 = new Regex("(?s)<TYPE>.*?<SEQUENCE>.*?<FILENAME>.*?<DESCRIPTION>.*?")
	      val str2 = pattern2.replaceAllIn(str1, " ")
	      str2
	}

	def deleteTablesNHTMLElem(instr: String): String = {
	      val pattern1 = new Regex("(?s)(?i)<Table.*?</Table>")
	      val str1 = pattern1.replaceAllIn(instr, " ")
	      val pattern2 = new Regex("(?s)<[^>]*>")
	      val str2 = pattern2.replaceAllIn(str1, " ")
	      str2
	}


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

	def deleteExcessLFCRWS(instr: String): String = {
	      val pattern1 = new Regex("[\n\r]+")
	      val str1 = pattern1.replaceAllIn(instr, "\n")
	      val pattern2 = new Regex("[\t]+")
	      val str2 = pattern2.replaceAllIn(str1, " ")
	      val pattern3 = new Regex("\\s+")
	      val str3 = pattern3.replaceAllIn(str2, " ")
	      str3
	}

	def deleteAllURLsFileNamesDigitsPunctuationExceptPeriod(instr: String): String = {
	      val pattern1 = new Regex("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
	      val str1 = pattern1.replaceAllIn(instr, "")
	      val pattern2 = new Regex("[_a-zA-Z0-9\\-\\.]+.(txt|sgml|xml|xsd|htm|html)")
	      val str2 = pattern2.replaceAllIn(str1, " ")
	      val pattern3 = new Regex("[^a-zA-Z|^.]")
	      val str3 = pattern3.replaceAllIn(str2, " ")
	      str3
	}

	def keepOnlyAlphas(instr: String): String = {
	      val pattern1 = new Regex("[^a-zA-Z|]")
	      val str1 = pattern1.replaceAllIn(instr, " ")
	      val str2 = str1.replaceAll("[\\s]+", " ")
	      str2
	}
}