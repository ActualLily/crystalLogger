package lily;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.File;

public class TesseractUtil {

    private static boolean init;
    private static ITesseract tesseract = new Tesseract1();

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
