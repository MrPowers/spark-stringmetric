package com.github.mrpowers.spark.stringmetric

import com.github.mrpowers.spark.stringmetric.expressions._
import org.apache.spark.sql.Column
import org.apache.spark.sql.catalyst.expressions.Expression

object FunExamples {

  private def withExpr(expr: Expression): Column = new Column(expr)

  def funify_string(col: Column): Column = withExpr {
    Funify(col.expr)
  }

}
