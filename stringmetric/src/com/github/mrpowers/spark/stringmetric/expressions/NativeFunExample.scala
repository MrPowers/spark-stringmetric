package com.github.mrpowers.spark.stringmetric.expressions

import com.github.mrpowers.spark.stringmetric.unsafe.UTF8StringFunctions
import org.apache.spark.unsafe.types.UTF8String
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.expressions.codegen.{CodegenContext, ExprCode}
import org.apache.spark.sql.types.{DataType, StringType}

/*
 * Alleviates painfully long codegen strings.
 *
 * TODO: See if there is a less hacky way to inject the imports
 * into generated code.
 */
trait UTF8StringFunctionsHelper {
  val stringFuncs: String = "com.github.mrpowers.spark.stringmetric.unsafe.UTF8StringFunctions"
}

trait String2StringExpression extends ImplicitCastInputTypes with NullIntolerant with UTF8StringFunctionsHelper { self: UnaryExpression =>
  override def dataType: DataType = StringType
  override def inputTypes: Seq[DataType] = Seq(StringType)
  protected override def nullSafeEval(input: Any): Any = "hi"
}

case class Funify(child: Expression) extends UnaryExpression with String2StringExpression {

  // scalastyle:off caselocale
  def convert(v: UTF8String): UTF8String = UTF8StringFunctions.funifyString(v).asInstanceOf[UTF8String]
  // scalastyle:on caselocale

  override def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = {
    defineCodeGen(ctx, ev, c => s"$stringFuncs.funifyString($c)")
  }

}

//case class FunifyString(left: Expression, right: Expression) extends UnaryExpression with String2StringExpression {
//  override def prettyName: String = "funifyString"
//
//  override def nullSafeEval(thing: Any): Any = {
//    UTF8StringFunctions.funifyString(thing.asInstanceOf[UTF8String])
//  }
//
//  override def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = {
//    defineCodeGen(ctx, ev, input => s"$stringFuncs.funifyString($input)")
//  }
//}

