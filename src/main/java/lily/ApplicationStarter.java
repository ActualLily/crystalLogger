package lily;

import lily.data.Match;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ApplicationStarter {

    static Logger log = LogManager.getLogger("MAIN");

    public static void main(String[] args) {
        String basePath = "C:\\Users\\cearu\\Documents\\ShareX\\Screenshots\\2022-04\\";

        List<Match> matchList = new LinkedList<>();

        try {
            for (File file : Objects.requireNonNull(new File(basePath).listFiles())) {
                try {
                    if (TesseractUtil.isValidMatch(file)) {
                        log.info("Parsing " + file.getAbsolutePath());
                        matchList.add(new Match(file));
                    }
                } catch (TesseractException e) {
                    e.printStackTrace();

                    System.out.println(matchList.size());

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
