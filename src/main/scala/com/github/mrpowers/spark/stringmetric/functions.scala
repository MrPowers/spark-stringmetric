package com.github.mrpowers.spark.stringmetric

import com.github.mrpowers.spark.stringmetric.expressions._
import org.apache.spark.sql.Column
import org.apache.spark.sql.catalyst.expressions.Expression

object functions {
  private def withExpr(expr: Expression): Column = new Column(expr)

  /** Calculates the hamming distance between two strings.
   *
   * @param left the first string
   * @param right the second string
   * @return null if either string is null or they are of unequal length,
   * otherwise returns an integer distance
   */
  def hamming(left: Column, right: Column): Column = withExpr {
    HammingDistance(left.expr, right.expr)
  }
}
