package lily.data;

import lily.TesseractUtil;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;

public class Player {

    String name = "Liya Massemeure@Mateus";
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

        kills = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.PLAYER_KILLS, playerNumber));
        deaths = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.PLAYER_DEATHS, playerNumber));
        assists = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.PLAYER_ASSISTS, playerNumber));
    }

    @Override
    public String toString() {
        return name + " (" + rank + ") " + kills + "/" + deaths + "/" + assists;
    }
}
