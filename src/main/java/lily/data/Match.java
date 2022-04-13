package lily.data;

import lily.TesseractUtil;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class Match {
    long matchDate = 0;
    int matchTime = 0;
    File image;
    String winner;

    List<Player> participants = new LinkedList<>();

    public Match(String imageLocation) throws IOException {
        this.image = new File(imageLocation);

        BasicFileAttributes view = Files.getFileAttributeView(image.getAbsoluteFile().toPath(), BasicFileAttributeView.class).readAttributes();
        matchDate = view.creationTime().toMillis();

        try {
            matchTime = TesseractUtil.minuteSecondToSeconds(
                    TesseractUtil.sanitizeToNumbers(
                            TesseractUtil.getTesseract().doOCR(image, new Rectangle(1730, 300, 120, 40))));

            for (int i = 1; i <= 10; i++) {
                participants.add(new Player(TesseractUtil.getTesseract().doOCR(image, new Rectangle(90, 405 + ((i - 1) * 51), 1730, 51))));
            }

            System.out.println(winner);
            // First 5 participants are in the winning team
            for (int i = 0; i < 5; i++) {
                participants.get(i).setWinner(true);
            }

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

}
