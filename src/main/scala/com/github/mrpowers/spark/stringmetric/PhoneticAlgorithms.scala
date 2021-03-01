package com.github.mrpowers.spark.stringmetric

import org.apache.spark.sql.functions._
import org.apache.commons.codec.language.{DoubleMetaphone, Nysiis, RefinedSoundex}

object PhoneticAlgorithms {

  val double_metaphone = udf[Option[String], String](doubleMetaphoneFun)

  private[stringmetric] def doubleMetaphoneFun(s1: String): Option[String] = {
    val str1 = Option(s1).getOrElse(return None)
    if (str1.equals("")) return None
    val dm = new DoubleMetaphone()
    Some(dm.encode(str1))
  }

  val nysiis = udf[Option[String], String](nysiisFun)

  private[stringmetric] def nysiisFun(s1: String): Option[String] = {
    val str1 = Option(s1).getOrElse(return None)
    val ny = new Nysiis()
    Some(ny.encode(str1))
  }

  val refined_soundex = udf[Option[String], String](refinedSoundexFun)

  private[stringmetric] def refinedSoundexFun(s1: String): Option[String] = {
    val str1 = Option(s1).getOrElse(return None)
    val rs = new RefinedSoundex()
    Some(rs.encode(str1))
  }

}
