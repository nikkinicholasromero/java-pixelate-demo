import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String sourceDirectory = System.getenv("PIXELATION_SOURCE");
        String destinationDirectory = System.getenv("PIXELATION_DESTINATION");

        Path sourcePath = Paths.get(sourceDirectory);
        Path destinationPath = Paths.get(destinationDirectory);

        if (Files.exists(sourcePath) && Files.isDirectory(sourcePath) && Files.exists(destinationPath) && Files.isDirectory(destinationPath)) {
            while (true) {
                DirectoryStream<Path> directoryStream = Files.newDirectoryStream(sourcePath);
                for (Path subPath : directoryStream) {
                    if (!Files.isDirectory(subPath)) {
                        File file = subPath.toFile();
                        BufferedImage img = ImageIO.read(file);
                        if (img != null) {
                            BufferedImage imagePixelated = ImageUtil.pixelate(img, 5);
                            ImageIO.write(imagePixelated, "jpg", new File(destinationDirectory + "\\" + subPath.getFileName()));
                            file.delete();
                        }
                    }
                }

                Thread.sleep(1000);
            }
        }
    }
}
