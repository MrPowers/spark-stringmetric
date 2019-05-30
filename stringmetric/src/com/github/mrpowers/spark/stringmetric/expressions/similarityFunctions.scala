package com.github.mrpowers.spark.stringmetric.expressions

import com.github.mrpowers.spark.stringmetric.unsafe.UTF8StringFunctions
import org.apache.commons.text.similarity.CosineDistance
import org.apache.spark.unsafe.types.UTF8String
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.expressions.codegen.{
  CodegenContext,
  ExprCode
}
import org.apache.spark.sql.types.{ DataType, IntegerType, StringType }

/*
 * Alleviates painfully long codegen strings.
 *
 * TODO: See if there is a less hacky way to inject the imports
 * into generated code.
 */
trait UTF8StringFunctionsHelper {
  val stringFuncs: String = "com.github.mrpowers.spark.stringmetric.unsafe.UTF8StringFunctions"
}

trait StringString2IntegerExpression
extends ImplicitCastInputTypes
with NullIntolerant
with UTF8StringFunctionsHelper { self: BinaryExpression =>
  override def dataType: DataType = IntegerType
  override def inputTypes: Seq[DataType] = Seq(StringType, StringType)

  protected override def nullSafeEval(left: Any, right: Any): Any = -1
}

case class HammingDistance(left: Expression, right: Expression)
extends BinaryExpression with StringString2IntegerExpression {
  override def prettyName: String = "hamming"

  override def nullSafeEval(leftVal: Any, righValt: Any): Any = {
    val leftStr = left.asInstanceOf[UTF8String]
    val rightStr = right.asInstanceOf[UTF8String]
    UTF8StringFunctions.hammingDistance(leftStr, rightStr)
  }

  override def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = {
    defineCodeGen(ctx, ev, (s1, s2) => s"$stringFuncs.hammingDistance($s1, $s2)")
  }
}
