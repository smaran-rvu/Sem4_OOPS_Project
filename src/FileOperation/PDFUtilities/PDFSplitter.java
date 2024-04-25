package FileOperation.PDFUtilities;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

// import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import DBConnection.MySQLCon;
import FileType.PDFDoc;

public class PDFSplitter {

    private PDFDoc pdfToSplit;
    private String outputFilePath;

    public void setPDFToSplit(String path) {
        this.pdfToSplit = new PDFDoc(path);
    }

    public void splitPDF() {
        Splitter splitter = new Splitter();

        PDDocument document;
        try {
            // document = Loader.loadPDF(new File(pdfToSplit.path));
            document = PDDocument.load(new File(pdfToSplit.path));
            List<PDDocument> Page = splitter.split(document); 
            
            String pdfsSplit = "";
            pdfsSplit += pdfToSplit.path; 
            
            Iterator<PDDocument> iteration = Page.listIterator(); 
            outputFilePath = pdfToSplit.path.substring(0, pdfToSplit.path.length()-4);
            
            int j = 1; 
            while (iteration.hasNext()) { 
                PDDocument pd = iteration.next(); 
                String tempPath = outputFilePath + "_page_" + j++ + ".pdf";
                pd.save(tempPath); 
                pdfsSplit += tempPath + ", ";
            } 
            pdfsSplit = pdfsSplit.substring(0, pdfsSplit.length()-2);
            
            var con = new MySQLCon();
            String username = System. getProperty("user.name");       
            con.addRecord(username, "SplitPDF", pdfsSplit);

        } catch (IOException e) {
            e.printStackTrace();
        } 
        
    }

}

// class TestSplitter {
//     public static void main(String[] args) {
//         var pdfSplitter = new PDFSplitter();
//         pdfSplitter.setPDFToSplit("C:/Users/smara/OneDrive/Desktop/Comp Sc/PDFUtil/Free_Test_Data_100KB_PDF.pdf"); 
//         pdfSplitter.splitPDF();
//     }
// }