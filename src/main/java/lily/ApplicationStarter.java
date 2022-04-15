package lily;

import lily.data.Match;
import lily.data.Player;
import lily.data.Team;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ApplicationStarter {

    static Logger log = LogManager.getLogger("MAIN");

    public static void main(String[] args) {
        String basePath = "C:\\Users\\cearu\\Documents\\ShareX\\Screenshots\\2022-04\\";

        try {
            DatabaseUtil.getDatabaseVersion();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int totalPlayers = matchList.size() * 10;
        int totalKills = 0;
        int totalKillsLiya = 0;

        int totalDeaths = 0;
        int totalDeathsLiya = 0;

        int totalAssists = 0;
        int totalAssistsLiya = 0;

        long totalDealt = 0;
        long totalDealtLiya = 0;

        long totalTaken = 0;
        long totalTakenLiya = 0;

        long totalHealed = 0;
        long totalHealedLiya = 0;

        for (Match match : matchList) {
            for (Team team : match.getTeams()) {
                for (Player player : team.getPlayers()) {
                    if (player.getName().equals("Liya Massemeure")) {
                        totalKillsLiya += player.getKills();
                        totalDeathsLiya += player.getDeaths();
                        totalAssistsLiya += player.getAssists();
                        totalDealtLiya += player.getDamageDone();
                        totalTakenLiya += player.getDamageTaken();
                        totalHealedLiya += player.getDamageHealed();
                    }
                    totalKills += player.getKills();
                    totalDeaths += player.getDeaths();
                    totalAssists += player.getAssists();
                    totalDealt += player.getDamageDone();
                    totalTaken += player.getDamageTaken();
                    totalHealed += player.getDamageHealed();
                }
            }
        }

        log.info("Kill difference: " + ((totalKillsLiya / matchList.size()) - (totalKills / totalPlayers)));
        log.info("Death difference: " + ((totalDeathsLiya / matchList.size()) - (totalDeaths / totalPlayers)));
        log.info("Assist difference: " + ((totalAssistsLiya / matchList.size()) - (totalAssists / totalPlayers)));
        log.info("Dealt difference: " + ((totalDealtLiya / matchList.size()) - (totalDealt / totalPlayers)));
        log.info("Taken difference: " + ((totalTakenLiya / matchList.size()) - (totalTaken / totalPlayers)));
        log.info("Healed difference: " + ((totalHealedLiya / matchList.size()) - (totalHealed / totalPlayers)));

        log.info("Found a total of " + matchList.size() + " matches!");
    }
}
