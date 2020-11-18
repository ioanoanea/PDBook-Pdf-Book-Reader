package com.ioanoanea.pdbook.pdf;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.tom_roush.pdfbox.cos.COSName;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDResources;
import com.tom_roush.pdfbox.pdmodel.graphics.PDXObject;
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class PdfEditor  {

    private Context context;
    private PDDocument pdDocument = null;
    private PDFTextStripper pdfTextStripper = null;

    public PdfEditor(Context context){
        this.context = context;
        PDFBoxResourceLoader.init(context);
    }


    /** extracting text from a pdf document
     *  extracting text page by page
     *  return text content in ArrayList with pages */
    public ArrayList<String> getTextFrom(Uri uri){
        // initializing file adress
        //final File file = new File(path);
        final ArrayList<String> textContent = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(context);


        try {
            // get file input stream from uri
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            // initializing pdf text stripper
            pdfTextStripper = new PDFTextStripper();

            // initializing new pdf document
            assert inputStream != null;
            pdDocument = PDDocument.load(inputStream);

            // get number of pages
            int length = pdDocument.getNumberOfPages();

            for (int i = 1; i <= length; i++){
                // select page
                pdfTextStripper.setStartPage(i);
                pdfTextStripper.setEndPage(i);

                // extract text
                textContent.add(pdfTextStripper.getText(pdDocument));
            }

        } catch (Exception e) {
            // display error
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            try {
                if(pdDocument != null)
                    pdDocument.close();
            } catch (Exception e1) {
                // display error
                Toast.makeText(context, e1.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        return textContent;
    }


    /** get images from a pdf document
     *  save images to external storage
     *  return images in ArrayList string format with image path
     *  this function is in developing stadium */
    public ArrayList<String> getImageFrom(String path){
        // initializing image list
        ArrayList<String> images = new ArrayList<>();
        try {
            // initializing pdf document
            PDDocument pdDocument = PDDocument.load(new File(path));

            // extract images from any page
            int pageNumber = 0;
            PDPage page = pdDocument.getPage(pageNumber);
            // get page resources
            PDResources pdResources = page.getResources();

            int imageNumber = 1;

            for (COSName objectName : pdResources.getXObjectNames()){
                // get page objects from page resources
                PDXObject object = pdResources.getXObject(objectName);

                // if object is image save extract and save image
                if(object instanceof PDImageXObject){
                    // get image object
                    PDImageXObject pdImageXObject = (PDImageXObject) object;
                    Bitmap image = pdImageXObject.getImage();
                    saveImage(new File(path), pageNumber, imageNumber, image);

                    // add image to images list
                    images.add(getImagePath(new File(path), pageNumber, imageNumber));
                }
                imageNumber++;
            }

        } catch (Exception e) {
            // display error
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

        return images;

    }


    /** save image to external storage
     *  images are saved in PDBook folder
     *  images are saved in png format */
    private void saveImage(File pdfFile, int page, int nr, Bitmap image){

        try {
            // create output directory
            File outputDirectory = new File(Environment.getExternalStorageDirectory(), "PDBook");
            if(!outputDirectory.exists())
                outputDirectory.mkdirs();

            // create output file
            File file = new File(Environment.getExternalStorageDirectory(), getImagePath(pdfFile,page,nr));
            // save image in external storage
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private String getImagePath(File pdfFile, int page, int nr){
        return "/PDBook/" + pdfFile.getName() + String.valueOf(page) + "_" + String.valueOf(nr) + ".png";
    }


}
