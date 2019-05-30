package com.github.mrpowers.spark.stringmetric

import com.github.mrpowers.spark.daria.sql.SparkSessionExt._
import com.github.mrpowers.spark.fast.tests.DataFrameComparer
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType}
import org.scalatest.FunSpec

class SimilarityFunctionsSpec
    extends FunSpec
    with SparkSessionTestWrapper
    with DataFrameComparer {

  describe("cosine_distance") {

    it("runs the cosine distance metric") {

      val sourceDF = spark.createDF(
        List(
          ("night", "nacht"),
          ("cat", "cat"),
          (null, "nacht"),
          (null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true)
        )
      )

      val actualDF = sourceDF.withColumn(
        "w1_w2_cosine_distance",
        SimilarityFunctions.cosine_distance(col("word1"), col("word2"))
      )

      val expectedDF = spark.createDF(
        List(
          ("night", "nacht", 1.0),
          ("cat", "cat", 0.0),
          (null, "nacht", null),
          (null, null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true),
          ("w1_w2_cosine_distance", DoubleType, true)
        )
      )

      assertSmallDataFrameEquality(actualDF, expectedDF)

    }

  }

  describe("cosineDistanceFun") {

    it("calculates the cosine distance") {

      assert(SimilarityFunctions.cosineDistanceFun("blah", "black") === Some(1.0))

    }

  }

  describe("fuzzy_score") {

    it("calculates the fuzzy score of two string") {

      val sourceDF = spark.createDF(
        List(
          ("", ""),
          ("workshop", "b"),
          ("room", "o"),
          ("workshop", "w"),
          ("workshop", "ws"),
          ("workshop", "wo"),
          ("Apache Software Foundation", "asf"),
          (null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true)
        )
      )

      val actualDF = sourceDF.withColumn(
        "w1_w2_fuzzy_score",
        SimilarityFunctions.fuzzy_score(col("word1"), col("word2"))
      )

      val expectedDF = spark.createDF(
        List(
          ("", "", 0),
          ("workshop", "b", 0),
          ("room", "o", 1),
          ("workshop", "w", 1),
          ("workshop", "ws", 2),
          ("workshop", "wo", 2),
          ("Apache Software Foundation", "asf", 3),
          (null, null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true),
          ("w1_w2_fuzzy_score", IntegerType, true)
        )
      )

    }

  }

  describe("hamming") {

    it("computes the hamming metric") {

      val sourceDF = spark.createDF(
        List(
          ("toned", "roses"),
          ("1011101", "1001001"),
          (null, "nacht"),
          (null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true)
        )
      )

      val actualDF = sourceDF.withColumn(
        "w1_w2_hamming",
        SimilarityFunctions.hamming(col("word1"), col("word2"))
      )

      val expectedDF = spark.createDF(
        List(
          ("toned", "roses", 3),
          ("1011101", "1001001", 2),
          (null, "nacht", null),
          (null, null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true),
          ("w1_w2_hamming", IntegerType, true)
        )
      )

      assertSmallDataFrameEquality(actualDF, expectedDF)

    }

  }

  describe("jaccard_similarity") {

    it("computes the jaccard similarity") {

      val sourceDF = spark.createDF(
        List(
          ("night", "nacht"),
          ("context", "contact"),
          (null, "nacht"),
          (null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true)
        )
      )

      val actualDF = sourceDF.withColumn(
        "w1_w2_jaccard",
        SimilarityFunctions.jaccard_similarity(col("word1"), col("word2"))
      )

      val expectedDF = spark.createDF(
        List(
          ("night", "nacht", 0.43),
          ("context", "contact", 0.57),
          (null, "nacht", null),
          (null, null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true),
          ("w1_w2_jaccard", DoubleType, true)
        )
      )

      assertSmallDataFrameEquality(actualDF, expectedDF)

    }

  }

  describe("jaro_winkler") {

    it("finds the Jaro Winkler Distance which indicates the similarity score between two strings") {

      val sourceDF = spark.createDF(
        List(
          ("dwayne", "duane"),
          ("jones", "johnson"),
          ("fvie", "ten"),
          (null, "nacht"),
          (null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true)
        )
      )

      val actualDF = sourceDF.withColumn(
        "w1_w2_jaro_winkler",
        SimilarityFunctions.jaro_winkler(col("word1"), col("word2"))
      )

      val expectedDF = spark.createDF(
        List(
          ("dwayne", "duane", 0.8400000000000001),
          ("jones", "johnson", 0.8323809523809523),
          ("fvie", "ten", 0.0),
          (null, "nacht", null),
          (null, null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true),
          ("w1_w2_jaro_winkler", DoubleType, true)
        )
      )

      assertSmallDataFrameEquality(actualDF, expectedDF)

    }

  }

}
