package lily;

import lily.data.TeamName;
import lily.data.TextLocation;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TesseractUtil {

    static Logger log = LogManager.getLogger("MAIN");

    private static final Rectangle EXPECTED_IMAGE = new Rectangle(0, 0, 1920, 1080);
    private static boolean init;
    private static final ITesseract tesseract = new Tesseract1();

    private static void initTesseractUtil() {
        log.info("Initializing Tesseract...");
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        tesseract.setDatapath(tessDataFolder.getAbsolutePath());

        init = true;
    }

    private static void ensureInit() {
        if (!init) {
            initTesseractUtil();
        }
    }

    public static String doOCR(File file, TextLocation data, Integer playerNumber) throws TesseractException, IOException {
        ensureInit();
        String ocrData = "";

        Rectangle playerAdjustedBounds = new Rectangle(data.bounds().x, data.bounds().y + ((playerNumber - 1) * TextLocation.PLAYER_DATA.bounds().height / 10), data.bounds().width, data.bounds().height);

        if (Integer.class.equals(data.type())) {
            tesseract.setVariable("tessedit_char_whitelist", "0123456789");
            ocrData = tesseract.doOCR(file, playerAdjustedBounds).trim();
            tesseract.setVariable("tessedit_char_whitelist", "");

            if (ocrData.equals("")) {
                log.warn("Did not find data in " + file.getName() + " for " + data.name());
                ocrData = "0";
            }

        } else {
            ocrData = tesseract.doOCR(file, playerAdjustedBounds).trim();
        }

        log.debug("OCR " + data.name() + ": " + ocrData);
        return ocrData;
    }

    public static String doOCR(File file, TextLocation data) throws TesseractException, IOException {
        return doOCR(file, data, 1);
    }

    public static boolean isValidMatch(File file) throws TesseractException, IOException {
        if (expectedSize(file)) {
            String teamName = doOCR(file, TextLocation.WINNING_TEAM);
            return teamName.startsWith(TeamName.UMBRA.stringName()) || teamName.startsWith(TeamName.ASTRA.stringName());
        }

        return false;
    }

    public static boolean expectedSize(File file) throws IOException {
        log.trace("Checking bounds of " + file.getAbsolutePath());

        BufferedImage bufferedImage = ImageIO.read(file);

        if (bufferedImage.getWidth() != EXPECTED_IMAGE.width && bufferedImage.getHeight() != EXPECTED_IMAGE.height) {
            log.warn(("Expected " + EXPECTED_IMAGE.width + "x" + EXPECTED_IMAGE.height + ", got " + bufferedImage.getWidth() + "x" + bufferedImage.getHeight()));
            return false;
        } else {
            return true;
        }

    }

}
