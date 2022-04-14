package lily.data;

import lily.TesseractUtil;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Team {

    List<Player> players = new LinkedList<>();
    TeamName teamName;
    File file;

    Integer teamKills;
    Integer teamDeaths;
    Integer teamAssists;

    boolean isWinner;

    public Team(File file, TeamName teamName, boolean isWinner) throws TesseractException, IOException {
        this.file = file;
        this.teamName = teamName;
        this.isWinner = isWinner;

        for (int i = 1; i <= 10; i++) {
            players.add(new Player(file, i));
        }

        switch (teamName) {
            case ASTRA -> {
                teamKills = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.ASTRA_KILLS));
                teamDeaths = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.ASTRA_DEATHS));
                teamAssists = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.ASTRA_ASSISTS));
            }
            case UMBRA -> {
                teamKills = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.UMBRA_KILLS));
                teamDeaths = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.UMBRA_DEATHS));
                teamAssists = Integer.valueOf(TesseractUtil.doOCR(file, TextLocation.UMBRA_ASSISTS));
            }
        }

    }

    public List<Player> getPlayers() {
        return players;
    }
}
