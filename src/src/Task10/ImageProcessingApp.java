package Task10;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;



public class ImageProcessingApp {
    public static void main(String[] args) {
        clearOutputFolder("src/src/Task10/OutputImg/");

        CustomThreadPool threadPool = new CustomThreadPool(2);

        String[] imagePaths = {"src/src/Task10/InputImg/1.jpg", "src/src/Task10/InputImg/2.png", "src/src/Task10/InputImg/3.jpg", "src/src/Task10/InputImg/4.jpg", "src/src/Task10/InputImg/5.jpg"};

        for (String inputImagePath : imagePaths) {
            String outputImagePath = "src/src/Task10/OutputImg/processed_" + new File(inputImagePath).getName();

            Runnable task = new ImageProcessor(inputImagePath, outputImagePath);
            try {
                threadPool.execute(task);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        threadPool.shutdown();
    }

    private static void clearOutputFolder(String outputFolder) {
        File folder = new File(outputFolder);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        } else {
            folder.mkdirs();
        }
    }
}
