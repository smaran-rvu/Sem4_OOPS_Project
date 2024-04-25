package FileOperation.Conversion;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import DBConnection.MySQLCon;

import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;

import FileType.ImageFile;

public class ImagesToPDF {

    private List<ImageFile> ImageList;

    public ImagesToPDF() {
        this.ImageList = new ArrayList<>();
    }

    public void clearImageList() {
        ImageList.clear();
    }

    public void addImage(String imagePath) {
        File imageFile = new File(imagePath);

        // Check if the image file exists
        if (!imageFile.exists()) {
            System.out.println("Image file not found: " + imagePath);
            return; // Skip to the next image
        }

        // Read the image using ImageIO
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(imageFile);
                    // Check if the image is null
            if (bufferedImage == null) {
                System.out.println("Failed to read image: " + imagePath);
                return; // Skip to the next image
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ImageList.add(new ImageFile(imagePath));
    }

    public void displayImageList() {
        System.out.println("Image Paths:");
        for (var image : ImageList) {
            System.out.println(image.path);
        }
    }

    public void convertImagesToPDF(String outputPath) {
        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();
            String imagesPath = "";

            for (var image_ : ImageList) {
                // Load each image
                File imageFile = new File(image_.path);
                imagesPath += image_.path + ", ";
                
                PDImageXObject image = JPEGFactory.createFromImage(document, ImageIO.read(imageFile));

                // Create a new page
                PDPage page = new PDPage();
                document.addPage(page);

                // Calculate the scaling factor to fit the width of the page
                float scalingFactor = page.getMediaBox().getWidth() / image.getWidth();
                
                // Calculate the scaled height of the image
                float scaledHeight = image.getHeight() * scalingFactor;

                // Create a new content stream and draw the scaled image onto the page
                PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true);
                contentStream.drawImage(image, 0, 0, page.getMediaBox().getWidth(), scaledHeight);
 
                contentStream.close();
            }

            imagesPath = imagesPath.substring(0, imagesPath.length()-2);

            // Save the PDF document
            document.save(outputPath);
            document.close();

            var con = new MySQLCon();
            String username = System. getProperty("user.name");       
            con.addRecord(username, "ImageToPDF", outputPath, imagesPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    //     ImagesToPDF converter = new ImagesToPDF();
    //     converter.addImage("C:/Users/smara/OneDrive/Desktop/Comp Sc/PDFUtil/image1.jpg");
    //     converter.addImage("C:/Users/smara/OneDrive/Desktop/Comp Sc/PDFUtil/image4.jpg");
    //     converter.addImage("C:/Users/smara/OneDrive/Desktop/Comp Sc/PDFUtil/image3.jpg");

    //     converter.displayImageList();
    //     String outputPath = "output.pdf";
    //     converter.convertImagesToPDF(outputPath);
    // }
}
