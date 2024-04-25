package FileType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageFile extends Document {
    private BufferedImage imageFile;
    private int[] resolution = new int[2];

    public ImageFile(String path) {
        this.path = path;
    }

    @Override
    public void open() {
        try {
            File file = new File(path);
            imageFile = ImageIO.read(file);
            // System.out.println("Image: " + path + " opened successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        imageFile.flush();
        // System.out.println("Image: " + path + " closed successfully.");
    }

    public void setResolution() {
        resolution[0] = imageFile.getWidth();
        resolution[1] = imageFile.getHeight();
    }

    public int[] getResolution() {
        return resolution;
    }
}