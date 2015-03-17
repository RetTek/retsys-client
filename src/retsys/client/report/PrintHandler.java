/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.report;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Muthu
 */
public class PrintHandler {
   // WritableImage snapshot = quotes.getScene().snapshot(null);
    
    private String reportName;
    private Map dataMap;
    
    
    public PrintHandler(String reportName, Map dataMap) {
    this.reportName=reportName;
    this.dataMap=dataMap;
}
    public String generatePrintData(){
        VelocityBuildReq vm = new VelocityBuildReq();
               
    try {
    String k = vm.generateReport(dataMap, reportName);//"<html><body> This is my Project </body></html>";
    OutputStream file = new FileOutputStream(new File("C:\\Temp\\Test.pdf"));
    Document document = new Document();
    PdfWriter writer = PdfWriter.getInstance(document, file);
    document.open();
    InputStream is = new ByteArrayInputStream(k.getBytes());
    XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
    document.close();
    file.close();
} catch (Exception e) {
    e.printStackTrace();
}
        
    return vm.generateReport(dataMap, reportName); 
    }
    

    public void print() throws Exception {
        
        
    }
    
    
    
}
