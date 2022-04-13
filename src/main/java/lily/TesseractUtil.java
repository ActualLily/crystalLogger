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
        return input.replaceAll("[^0-9]", "");
    }
}
