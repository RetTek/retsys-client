/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.main;
import javafx.scene.shape.Circle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import javafx.print.PrinterJob;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Muthu
 */
public class PrintHandler {
   // WritableImage snapshot = quotes.getScene().snapshot(null);
    public void Print() throws Exception {
    PDDocument doc = null;
    PDPage page = null;
    PDXObjectImage ximg = null;
    //BufferedImage bufferedImg = SwingFXUtils.fromFXImage(snapshot, null);

   try{
       doc = new PDDocument();
       page = new PDPage();

       doc.addPage(page);
       PDFont font = PDType1Font.HELVETICA_BOLD;

       PDPageContentStream content = new PDPageContentStream(doc, page);
       content.beginText();
     //content.setFont(font, 12 );
       content.moveTextPositionByAmount( 100, 700 );
       content.drawString("Hello World");

       content.endText();
       content.close();
      doc.save("C:/Users/Muthu/Desktop/DFWithText.pdf");
      doc.close();
    } catch (Exception e){
    System.out.println(e);
    }
}
    
    
    
}
