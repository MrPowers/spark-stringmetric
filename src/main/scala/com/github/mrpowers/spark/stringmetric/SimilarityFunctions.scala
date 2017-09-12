package com.github.mrpowers.spark.stringmetric

import java.util.Locale

import com.rockymadden.stringmetric.similarity._
import org.apache.spark.sql.functions._
import org.apache.commons.text.similarity.{CosineDistance, FuzzyScore, HammingDistance, JaccardSimilarity}

object SimilarityFunctions {

  val cosine_distance = udf[Option[Double], String, String](cosineDistanceFun)

  def cosineDistanceFun(s1: String, s2: String): Option[Double] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    val cd = new CosineDistance()
    Some(cd(s1, s2))
  }

  val dice_sorensen = udf[Option[Double], String, String, Int](diceSorensenFun)

  def diceSorensenFun(s1: String, s2: String, nGram: Int): Option[Double] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    DiceSorensenMetric(nGram).compare(str1, str2)
  }

  val fuzzy_score = udf[Option[Integer], String, String](fuzzyScoreFun)

  def fuzzyScoreFun(s1: String, s2: String): Option[Integer] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    val f = new FuzzyScore(Locale.ENGLISH)
    Some(f.fuzzyScore(str1, str2))
  }

  val hamming = udf[Option[Int], String, String](hammingFun)

  def hammingFun(s1: String, s2: String): Option[Int] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    val h = new HammingDistance()
    Some(h.apply(str1, str2))
  }

  val jaccard_similarity = udf[Option[Double], String, String](jaccardSimilarityFun)

  def jaccardSimilarityFun(s1: String, s2: String): Option[Double] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    val j = new JaccardSimilarity()
    Some(j.apply(str1, str2))
  }

}
