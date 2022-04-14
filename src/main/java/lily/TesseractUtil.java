package lily;

import lily.data.ImageSizeException;
import lily.data.TeamName;
import lily.data.TextLocation;
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

    private static final Rectangle EXPECTED_IMAGE = new Rectangle(0, 0, 1920, 1080);
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

    public static String doOCR(File file, TextLocation data) throws TesseractException, IOException, ImageSizeException {
        ensureInit();

        // Make sure image doesn't exceed TextLocation bounds
        BufferedImage bufferedImage = ImageIO.read(file);
        String ocrData;

        if (bufferedImage.getWidth() != EXPECTED_IMAGE.width && bufferedImage.getHeight() != EXPECTED_IMAGE.height) {
            throw new ImageSizeException(EXPECTED_IMAGE.width, EXPECTED_IMAGE.height, bufferedImage.getWidth(), bufferedImage.getHeight());
        }

        if (Integer.class.equals(data.type())) {
            tesseract.setVariable("tessedit_char_whitelist", "0123456789");
            ocrData = tesseract.doOCR(file, data.bounds());
            tesseract.setVariable("tessedit_char_whitelist", "");

            if (ocrData.equals("")) {
                ocrData = "0";
            }
        } else {
            ocrData = tesseract.doOCR(file, data.bounds());
        }

        return ocrData.trim();
    }

    public static boolean isValidMatch(File file) throws TesseractException, ImageSizeException, IOException {
        String teamName = doOCR(file, TextLocation.WINNING_TEAM);

        return teamName.startsWith(TeamName.UMBRA.stringName()) || teamName.startsWith(TeamName.ASTRA.stringName());
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
