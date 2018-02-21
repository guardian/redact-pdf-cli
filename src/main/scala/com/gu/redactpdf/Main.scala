package com.gu.redactpdf

import org.rogach.scallop.ScallopConf

class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
  val source = opt[String](required = true)
  val destination = opt[String](required = true)
  val redactedWords = trailArg[List[String]]()
  val noExcludePronouns = opt[Boolean](default = Some(false))
  verify()
}

object Main {
  def main(args: Array[String]) {

    val pronouns = List("he", "she", "him", "her", "job")

    val conf = new Conf(args)
    PdfRedactor.redact(conf.source(), conf.destination(), conf.redactedWords() ++ pronouns.filterNot(_ => conf.noExcludePronouns()))
  }
}