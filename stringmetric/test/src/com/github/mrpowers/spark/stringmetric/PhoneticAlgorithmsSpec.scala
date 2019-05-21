package com.github.mrpowers.spark.stringmetric

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StringType
import com.github.mrpowers.spark.daria.sql.SparkSessionExt._
import com.github.mrpowers.spark.fast.tests.DataFrameComparer
import org.scalatest.FunSpec

class PhoneticAlgorithmsSpec
    extends FunSpec
    with SparkSessionTestWrapper
    with DataFrameComparer {

  describe("double_metaphone") {

    it("encodes a string with the double metaphone algorithm") {

      val sourceDF = spark.createDF(
        List(
          ("night"),
          ("cat"),
          (""),
          (null)
        ), List(
          ("word1", StringType, true)
        )
      )

      val actualDF = sourceDF.withColumn(
        "word1_double_metaphone",
        PhoneticAlgorithms.double_metaphone(col("word1"))
      )

      val expectedDF = spark.createDF(
        List(
          ("night", "NT"),
          ("cat", "KT"),
          ("", null),
          (null, null)
        ), List(
          ("word1", StringType, true),
          ("word1_double_metaphone", StringType, true)
        )
      )

      assertSmallDataFrameEquality(actualDF, expectedDF)

    }

  }

  describe("nysiis") {

    it("encodes a string using the nysiis algorithm") {

      val sourceDF = spark.createDF(
        List(
          ("night"),
          ("cat"),
          (""),
          (null)
        ), List(
          ("word1", StringType, true)
        )
      )

      val actualDF = sourceDF.withColumn(
        "word1_nysiis",
        PhoneticAlgorithms.nysiis(col("word1"))
      )

      val expectedDF = spark.createDF(
        List(
          ("night", "NAGT"),
          ("cat", "CAT"),
          ("", ""),
          (null, null)
        ), List(
          ("word1", StringType, true),
          ("word1_nysiis", StringType, true)
        )
      )

      assertSmallDataFrameEquality(actualDF, expectedDF)

    }

  }

  describe("refined_soundex") {

    it("encodes a string with the refined soundex algorithm") {

      val sourceDF = spark.createDF(
        List(
          ("night"),
          ("cat"),
          (""),
          (null)
        ), List(
          ("word1", StringType, true)
        )
      )

      val actualDF = sourceDF.withColumn(
        "word1_refined_soundex",
        PhoneticAlgorithms.refined_soundex(col("word1"))
      )

      val expectedDF = spark.createDF(
        List(
          ("night", "N80406"),
          ("cat", "C306"),
          ("", ""),
          (null, null)
        ), List(
          ("word1", StringType, true),
          ("word1_refined_soundex", StringType, true)
        )
      )

      assertSmallDataFrameEquality(actualDF, expectedDF)

    }

  }

}
