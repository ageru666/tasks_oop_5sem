package Task10;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImageProcessor implements Runnable {
    private String inputImagePath;
    private String outputImagePath;

    public ImageProcessor(String inputImagePath, String outputImagePath) {
        this.inputImagePath = inputImagePath;
        this.outputImagePath = outputImagePath;
    }

    @Override
    public void run() {
        try {
            System.out.println("Processing image " + inputImagePath + " on thread " + Thread.currentThread().getName());

            BufferedImage originalImage = ImageIO.read(new File(inputImagePath));

            int newWidth = originalImage.getWidth() / 2;
            int newHeight = originalImage.getHeight() / 2;
            Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            ImageIO.write(resizedImage, "jpg", new File(outputImagePath));

            System.out.println("Image processing complete for " + inputImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
