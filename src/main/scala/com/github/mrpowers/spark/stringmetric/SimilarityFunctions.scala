package com.github.mrpowers.spark.stringmetric

//import com.github.mrpowers.spark.stringmetric.expressions.HammingDistance
import org.apache.spark.sql.Column
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.functions._

import java.util.Locale

import org.apache.commons.text.similarity.{
  CosineDistance,
  JaccardSimilarity,
  JaroWinklerDistance,
  FuzzyScore,
  HammingDistance
}


object SimilarityFunctions {
  private def withExpr(expr: Expression): Column = new Column(expr)

  val cosine_distance = udf[Option[Double], String, String](cosineDistanceFun)

  private[stringmetric] def cosineDistanceFun(s1: String, s2: String): Option[Double] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    val cd = new CosineDistance()
    Some(cd(s1, s2))
  }

  val fuzzy_score = udf[Option[Integer], String, String](fuzzyScoreFun)

  private[stringmetric] def fuzzyScoreFun(s1: String, s2: String): Option[Integer] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    val f = new FuzzyScore(Locale.ENGLISH)
    Some(f.fuzzyScore(str1, str2))
  }

  val hamming = udf[Option[Integer], String, String](hammingFun)

  private[stringmetric] def hammingFun(s1: String, s2: String): Option[Integer] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    val h = new HammingDistance()
    Some(h.apply(str1, str2))
  }

  val jaccard_similarity = udf[Option[Double], String, String](jaccardSimilarityFun)

  private[stringmetric] def jaccardSimilarityFun(s1: String, s2: String): Option[Double] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    val j = new JaccardSimilarity()
    Some(j.apply(str1, str2))
  }

  val jaro_winkler = udf[Option[Double], String, String](jaroWinlkerFun)

  private[stringmetric] def jaroWinlkerFun(s1: String, s2: String): Option[Double] = {
    val str1 = Option(s1).getOrElse(return None)
    val str2 = Option(s2).getOrElse(return None)
    val j = new JaroWinklerDistance()
    Some(j.apply(str1, str2))
  }

}
