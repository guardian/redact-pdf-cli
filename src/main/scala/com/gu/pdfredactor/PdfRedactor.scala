package com.gu.pdfredactor

import java.io.File
import org.apache.pdfbox.pdmodel.{PDDocument, PDPageContentStream}
import java.awt.Color

object PdfRedactor {

  def redact(source: String, destination: String, names: List[String]): Unit = {
    val input = new File(source)
    val document = PDDocument.load(input)

    for (name <- names) {
      val foundText = TextFinder.findString(document, name)
      redactFoundText(document, foundText)
    }

    val destinationFile = new File(destination)
    document.save(destinationFile)
    document.close()
  }

  def redactFoundText(document: PDDocument, blocks: List[FoundText]): Unit = {
    val allPages = document.getDocumentCatalog.getPages

    blocks.groupBy(_.pageIndex).foreach { case (index, pageData) =>
      val page = allPages.get(index)
      val contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, false)
      pageData.foreach({ case FoundText(_, x1, y1, x2, y2, _) =>
        contentStream.setNonStrokingColor(Color.BLACK)
        contentStream.addRect(x1, page.getBBox.getHeight - y1, x2 - x1, y2 - y1)
        contentStream.fill()
      })
      contentStream.close()
    }
  }
}

