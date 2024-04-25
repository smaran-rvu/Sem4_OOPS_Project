package FileType;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFDoc extends Document{

    public PDDocument document;
    private int noOfPages;

    private void setNoOfPages(){
        this.noOfPages = this.document.getNumberOfPages();
    }

    public int getNumberOfPages(){
        return this.noOfPages;
    }

    public PDFDoc(String path){
        this.path = path;
        open();
        setNoOfPages();
        close();
    }
    
    @Override
    public void open(){
        try  {
            this.document = PDDocument.load(new File(this.path));
            // System.out.println("PDF Document : " + path  + " opened successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(){
        try {
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println("PDF Document : " + path  + " closed successfully.");
    }

}
