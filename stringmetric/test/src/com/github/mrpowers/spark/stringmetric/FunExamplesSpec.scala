package com.github.mrpowers.spark.stringmetric

import com.github.mrpowers.spark.daria.sql.SparkSessionExt._
import com.github.mrpowers.spark.fast.tests.DataFrameComparer
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType}
import org.scalatest.FunSpec

class FunExamplesSpec
    extends FunSpec
    with SparkSessionTestWrapper
    with DataFrameComparer{


  describe("funify_string") {

    it("makes a string fun!") {

      val df = spark.createDF(
        List(
          ("night"),
          ("cat")
        ), List(
          ("word", StringType, true)
        )
      ).withColumn("fun_word", FunExamples.funify_string(col("word")))

      df.show()

    }

  }

}
