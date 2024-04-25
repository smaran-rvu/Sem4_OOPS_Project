# Getting Started

PDFUtil is a command line based application which helps to perform different operations on PDFs and Images such as:
- Merge PDFs
- Split a PDF page by page
- Convert Images to PDF
- Convert PDF to Images page by page

# Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain source codes
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

# Classes:

## FileType package:

Contains:
- class `PDFDoc` 
- class `ImageFile` 

which define the major file types used in this app

> Note: Both classes inherit from the super class `Document` (method overriding is used here) 

## FileOperations package:

Contains all classes to perform above listed file operations

### Conversion package:

Contains 
- class `ImagesToPDF` - to convert (merge) Images to a PDF  
- class `PDFToImages` - to convert (split) a PDF to Images

### PDFUtilities package:

Contains 
- class `PDFMerger` - to merge multiple PDFs to a single PDF  
- class `PDFSplitter` - to split a PDF to multiple PDFs

## DBConnection package:

Contains class `MySQLCon` to connect to a MySQL database and retrieve the history of operations performed with this app.

> Note: Method overloading is used here
