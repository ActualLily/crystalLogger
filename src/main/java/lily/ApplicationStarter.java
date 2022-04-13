package lily;

import lily.data.Match;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ApplicationStarter {

    public static void main(String args[]) {
        String basePath = "C:\\Users\\cearu\\Documents\\ShareX\\Screenshots\\2022-04\\";
        String imagePath = "ffxiv_dx11_UffIXhrnVX.png";

        try {
            Match currentMatch = new Match(basePath + imagePath);
            System.out.println(currentMatch.getMatchTime());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
