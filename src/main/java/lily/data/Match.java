package lily.data;

import lily.TesseractUtil;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;

public class Match {

    static Logger log = (Logger) LogManager.getLogger(Match.class.getName());

    long matchDate = 0;
    int matchTime = 0;
    File image;

    public Match(String imageLocation) throws IOException {
        this.image = new File(imageLocation);

        BasicFileAttributes view = Files.getFileAttributeView(image.getAbsoluteFile().toPath(), BasicFileAttributeView.class).readAttributes();
        matchDate = view.creationTime().toMillis();

        try {
            matchTime = minuteSecondToSeconds(
                    TesseractUtil.sanitizeToNumbers(
                            TesseractUtil.getTesseract().doOCR(image, new Rectangle(1730, 300, 120, 40))));
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }

    public int getMatchTime() {
        return matchTime;
    }

    public File getImage() {
        return image;
    }

    private int minuteSecondToSeconds(String mss) {
        int seconds = Integer.parseInt(mss.substring(mss.length() - 2));
        int minutes = Integer.parseInt(mss.substring(0, mss.length() - 2));

        return minutes * 60 + seconds;
    }

}
