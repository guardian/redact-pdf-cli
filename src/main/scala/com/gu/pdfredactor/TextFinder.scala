package com.gu.pdfredactor

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.{PDFTextStripper, TextPosition}

import scala.collection.mutable.ListBuffer

case class FoundText(pageIndex: Int, x1: Float, y1: Float, x2: Float, y2: Float, text: String)

object TextFinder {
  def findString(document: PDDocument, needle: String): List[FoundText] = {
    val textFinder = new TextFinder(needle)
    textFinder.getText(document)
    textFinder.locations.result()
  }

  def findMultiple(needle: String, haystack: String): List[Int] =
    s"\\b$needle\\b".r.findAllMatchIn(haystack).toList.map(_.start)
}

class TextFinder(val needle: String) extends PDFTextStripper {

  val locations: ListBuffer[FoundText] = new ListBuffer

  super.setSortByPosition(true)

  override protected def writeString(text: String, textPositions: java.util.List[TextPosition]): Unit = {
    TextFinder.findMultiple(needle, text.toLowerCase).foreach { index =>
      val first = textPositions.get(index)
      val last = textPositions.get(index + needle.length - 1)
      locations.append(
        FoundText(
          pageIndex = getCurrentPageNo - 1,
          x1 = first.getX,
          y1 = first.getY,
          x2 = last.getX + last.getWidth,
          y2 = last.getY + last.getHeight,
          text
        )
      )
    }
  }
}