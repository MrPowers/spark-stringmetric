package com.github.mrpowers.spark.stringmetric

import com.rockymadden.stringmetric.similarity.DiceSorensenMetric
import org.apache.spark.sql.functions._

object SimilarityFunctions {

  val dice_sorensen = udf[Option[Double], String, String](diceSorensen)

  def diceSorensen(s1: String, s2: String): Option[Double] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    DiceSorensenMetric(1).compare(str1, str2)
  }

}
