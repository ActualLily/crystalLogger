package lily;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TesseractUtil {

    private static boolean init;
    private static final ITesseract tesseract = new Tesseract1();

    private static void initTesseractUtil() {
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        tesseract.setDatapath(tessDataFolder.getAbsolutePath());
        init = true;
    }

    private static void ensureInit() {
        if (!init) {
            initTesseractUtil();
        }
    }

    public static ITesseract getTesseract() {
        ensureInit();
        return tesseract;
    }

    public static boolean isValidMatchLog(String imageLocation) throws TesseractException, IOException {
        File image = new File(imageLocation);

        BufferedImage bi = ImageIO.read(new File(imageLocation));

        if (bi.getWidth() != 1920 && bi.getHeight() != 1080) {
            return false;
        }

        String winningTeam = TesseractUtil.getTesseract().doOCR(image, new Rectangle(841, 44, 244, 42));

        if (winningTeam.length() > 5) {
            winningTeam = winningTeam.substring(0, 5);
        }

        return winningTeam.equals("Astra") || winningTeam.equals("Umbra");
    }

    public static String sanitizeToNumbers(String input) {
        String sanitized = input.replaceAll("O", "0");
        sanitized = sanitized.replaceAll("[^0-9]", "");

        return sanitized;
    }

    public static int minuteSecondToSeconds(String mss) {
        int seconds = Integer.parseInt(mss.substring(mss.length() - 2));
        int minutes = Integer.parseInt(mss.substring(0, mss.length() - 2));

        return minutes * 60 + seconds;
    }

}
