package com.github.mrpowers.spark.stringmetric

import com.rockymadden.stringmetric.similarity._
import org.apache.spark.sql.functions._

object SimilarityFunctions {

  val dice_sorensen = udf[Option[Double], String, String, Int](diceSorensen)

  def diceSorensen(s1: String, s2: String, nGram: Int): Option[Double] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    DiceSorensenMetric(nGram).compare(str1, str2)
  }

  val hamming = udf[Option[Int], String, String](hammingFun)

  def hammingFun(s1: String, s2: String): Option[Int] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    HammingMetric.compare(str1, str2)
  }

}
