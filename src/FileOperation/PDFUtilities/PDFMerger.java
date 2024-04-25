package FileOperation.PDFUtilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import FileType.PDFDoc;
import DBConnection.MySQLCon;

public class PDFMerger {
    private List<PDFDoc> pdfsToMerge;

    public PDFMerger() {
        pdfsToMerge = new ArrayList<>();
    }

    public void addPDF(String path) {
        pdfsToMerge.add(new PDFDoc(path));
    }

    public void clearPDFList() {
        pdfsToMerge.clear();
    }

    public void printPDFPaths() {
        for (PDFDoc pdf : pdfsToMerge) {
            System.out.println("PDF Path: " + pdf.getPath());
        }
    }

    public void mergePDFs(String outputFilePath) {
        PDFMergerUtility merger = new PDFMergerUtility();
        for (PDFDoc pdf : pdfsToMerge) {
            try {
                merger.addSource(new File(pdf.path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        merger.setDestinationFileName(outputFilePath);
        try {
            merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            var con = new MySQLCon();
            String username = System. getProperty("user.name");       
            String pdfsMerged = "";
            for (PDFDoc pdfDoc : pdfsToMerge) {
                pdfsMerged += pdfDoc.path + ", ";
            }    
            pdfsMerged += outputFilePath;
            con.addRecord(username, "MergePDF", pdfsMerged);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// class Test {
//     public static void main(String[] args) {
//         var merger = new PDFMerger();
//         merger.addPDF("C:/Users/smara/OneDrive/Desktop/Comp Sc/PDFUtil/Dree_Test_Data_100KB_PDF_1.pdf");
//         merger.addPDF("C:/Users/smara/OneDrive/Desktop/Comp Sc/PDFUtil/Dree_Test_Data_100KB_PDF_2.pdf");
//         merger.addPDF("C:/Users/smara/OneDrive/Desktop/Comp Sc/PDFUtil/Dree_Test_Data_100KB_PDF_3.pdf");
        
//         merger.printPDFPaths();
//         merger.mergePDFs("Tree_Test_Data_100KB_PDF.pdf");
//     }
// }