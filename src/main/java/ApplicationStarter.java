import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ApplicationStarter {

    public static void main(String args[]) {
        ITesseract tessInstance = new Tesseract1();
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");

        tessInstance.setVariable("tessedit_char_whitelist", "0123456789");
        tessInstance.setDatapath(tessDataFolder.getAbsolutePath());

        String basePath = "C:\\Users\\cearu\\Documents\\ShareX\\Screenshots\\2022-04\\";
        String imagePath = "ffxiv_dx11_6k7oiv3nmw.png";

        try {
            System.out.println(tessInstance.doOCR(new File(basePath + imagePath)) );
        } catch (TesseractException e) {
            e.printStackTrace();
        }

    }
}
