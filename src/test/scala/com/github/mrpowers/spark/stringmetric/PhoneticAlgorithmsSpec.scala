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

}
