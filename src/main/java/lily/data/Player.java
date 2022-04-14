package lily.data;

import lily.TesseractUtil;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class Player {

    static Logger log = LogManager.getLogger("MAIN");

    String name;
    String server;
    String rank;

    Integer kills;
    Integer deaths;
    Integer assists;
    Integer damageDone;
    Integer damageTaken;
    Integer damageHealed;
    Integer objectiveTime;

    boolean isWinner = false;

    String tesseractData;

    public Player(File file, Integer playerNumber) throws TesseractException, IOException {
        name = TesseractUtil.doOCR(file, TextLocation.PLAYER_NAME, playerNumber);
        server = TesseractUtil.doOCR(file, TextLocation.PLAYER_SERVER, playerNumber);
        rank = TesseractUtil.doOCR(file, TextLocation.PLAYER_RANK, playerNumber);

        if (rank.equals("")) {
            rank = "Unranked";
            log.debug("Correcting rank to Unranked");
        }

        kills = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.PLAYER_KILLS, playerNumber));
        deaths = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.PLAYER_DEATHS, playerNumber));
        assists = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.PLAYER_ASSISTS, playerNumber));

        damageDone = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.PLAYER_DEALT, playerNumber));
        damageTaken = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.PLAYER_TAKEN, playerNumber));
        damageHealed = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.PLAYER_HEALED, playerNumber));

        objectiveTime = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.PLAYER_TIME, playerNumber));
    }

    @Override
    public String toString() {
        return name + " (" + rank + ") " + kills + "/" + deaths + "/" + assists;
    }
}
