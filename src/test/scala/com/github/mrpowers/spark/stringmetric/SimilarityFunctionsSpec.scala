package com.github.mrpowers.spark.stringmetric

import com.github.mrpowers.spark.daria.sql.SparkSessionExt._
import com.github.mrpowers.spark.fast.tests.DataFrameComparer
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, StringType}
import org.scalatest.FunSpec

class SimilarityFunctionsSpec
    extends FunSpec
    with SparkSessionTestWrapper
    with DataFrameComparer {

  describe("dice_sorensen") {

    it("runs the dice sorensen metric") {

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
        "w1_w2_dice_sorensen",
        SimilarityFunctions.dice_sorensen(col("word1"), col("word2"))
      )

      val expectedDF = spark.createDF(
        List(
          ("night", "nacht", 0.6),
          ("cat", "cat", 1.0),
          (null, "nacht", null),
          (null, null, null)
        ), List(
          ("word1", StringType, true),
          ("word2", StringType, true),
          ("w1_w2_dice_sorensen", DoubleType, true)
        )
      )

      assertSmallDataFrameEquality(actualDF, expectedDF)

    }

  }

}
