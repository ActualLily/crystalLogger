package Data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.time.LocalDateTime;

public class Match {

        static Logger log = (Logger) LogManager.getLogger(Match.class.getName());

        LocalDateTime matchTime = LocalDateTime.now();
        String tesseractData;

        public Match(String tesseractData) {
            this.tesseractData = tesseractData;

        }
}
