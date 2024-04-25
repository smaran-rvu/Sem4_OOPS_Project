package FileOperation.Conversion;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import DBConnection.MySQLCon;
import FileType.PDFDoc;

public class PDFToImages {

    private PDFDoc pdfToConvert;
    private String outputPath;

    public void setPdfToConvert(String path) {
        this.pdfToConvert = new PDFDoc(path);
    }

    public void convertPDFToImages() {
        try {
            PDDocument document = PDDocument.load(new File(pdfToConvert.path));
            PDFRenderer renderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();
            String imagesPath = "";

            for (int i = 0; i < pageCount; i++) {
                try {
                    BufferedImage image = renderer.renderImageWithDPI(i, 300);
                    outputPath = pdfToConvert.path.substring(0, pdfToConvert.path.length()-4) + "_page_" + (i + 1) + ".png";
                    // System.out.println(outputPath);
                    ImageIO.write(image, "PNG", new File(outputPath));
                    imagesPath += outputPath + ", ";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            document.close();
            imagesPath = imagesPath.substring(0, imagesPath.length()-2);

            var con = new MySQLCon();
            String username = System. getProperty("user.name");       
            con.addRecord(username, "PDFToImages", outputPath, imagesPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    //     var converter = new PDFToImages();
    //     converter.setPdfToConvert("C:/Users/smara/OneDrive/Desktop/Comp Sc/PDFUtil/Free_Test_Data_100KB_PDF.pdf");
    //     converter.convertPDFToImages();
    // }
}