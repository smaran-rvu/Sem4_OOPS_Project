import java.util.Scanner;

import DBConnection.*;
import FileOperation.PDFUtilities.*;
import FileOperation.Conversion.*;

public class App {
    public static void main(String[] args) throws Exception {
        var scanner = new Scanner(System.in);
        var con = new MySQLCon();
        int choice;
        System.out.println("Welcome to PDFUtil!!");
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1 -> Split a PDF");
            System.out.println("2 -> Merge PDFs");      
            System.out.println("3 -> Convert Images to PDF");      
            System.out.println("4 -> Convert PDF to Images");    
            System.out.println("5 -> Show History of conversions");    
            System.out.println("6 -> Exit Application");    
            System.out.print("Enter your option here: ");
            
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        var pdfSplitter = new PDFSplitter();
                        System.out.print("\nEnter the path of the PDF to split: ");
                        pdfSplitter.setPDFToSplit(scanner.nextLine()); 
                        pdfSplitter.splitPDF();
                        System.out.println("\nPDF Splitted successfully!!");
                        break;
                
                    case 2:
                        var pdfMerger = new PDFMerger();
                        System.out.print("\nEnter the number of PDFs to merge: ");
                        int nos_pdf = scanner.nextInt();
                        scanner.nextLine();
                        for (int i = 0; i < nos_pdf; i++) {
                            System.out.print("Enter the path of PDF no " + (i+1) + " : ");                            
                            pdfMerger.addPDF(scanner.nextLine());
                        }

                        // scanner.nextLine();
                        System.out.print("Enter the full path of the output PDF file: ");
                        String outputPath = scanner.nextLine();
                        
                        System.out.println("Enter");
                        System.out.println("    1 -> You want to review the files to merge, then merge");
                        System.out.println("    2 -> You want to Clear the list and start over again");
                        System.out.print("  Enter choice here: ");
                        int ch = scanner.nextInt();
                        if(ch == 1){
                            System.out.println("\nPDFs to be merged: ");
                            pdfMerger.printPDFPaths();
                            pdfMerger.mergePDFs(outputPath);
                            System.out.println("\nPDFs Merged successfully!!");
                        }
                        else{
                            pdfMerger.clearPDFList();
                        }                        
                        break;

                    case 3:
                        var imageToPDFConverter = new ImagesToPDF();
                        System.out.print("\nEnter the number of images to merge into a PDF: ");
                        int nos_imgs = scanner.nextInt();
                        scanner.nextLine();
                        for (int i = 0; i < nos_imgs; i++) {
                            System.out.print("Enter the path of image no " + (i+1) + " : ");
                            imageToPDFConverter.addImage(scanner.nextLine());
                        }

                        System.out.print("Enter the full path of the output PDF file: ");
                        String outputPath2 = scanner.nextLine();
                        
                        System.out.println("Enter");
                        System.out.println("    1 -> You want to review the files to merge, then merge");
                        System.out.println("    2 -> You want to Clear the list and start over again");
                        System.out.print("  Enter choice here: ");
                        int ch2 = scanner.nextInt();
                        if(ch2 == 1){
                            System.out.println("\nImages to be merged: ");
                            imageToPDFConverter.displayImageList();
                            imageToPDFConverter.convertImagesToPDF(outputPath2);
                            System.out.println("\nImages merged to a PDF successfully!!");
                        }
                        else{
                            imageToPDFConverter.clearImageList();
                        }
                        break;
                    
                    case 4:
                        var pdfToImageConverter = new PDFToImages();
                        System.out.print("\nEnter the path of the PDF to split into images: ");
                        pdfToImageConverter.setPdfToConvert(scanner.nextLine());
                        pdfToImageConverter.convertPDFToImages();
                        System.out.println("\nPDF splitted into images successfully!!");
                        break;

                    case 5:
                        System.out.println("\nHistory:");
                        con.printHistory();
                        break;

                    case 6:
                        System.out.println("\nThanks for using PDFUtil! Have a good day :)\n");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Enter a valid choice please :)");
                        break;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println("\nEnter a valid choice please :)");
            }
        }
    }
}
