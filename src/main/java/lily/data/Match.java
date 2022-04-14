package lily.data;

import lily.TesseractUtil;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class Match {
    long matchDate;
    int matchTime;
    File file;
    TeamName winningTeam;

    List<Team> teams = new LinkedList<>();

    public Match(File file) throws IOException, TesseractException {
        this.file = file;

        if (TesseractUtil.doOCR(file, TextLocation.WINNING_TEAM).substring(0, 5).equals(TeamName.ASTRA.stringName())) {
            winningTeam = TeamName.ASTRA;
        } else if (TesseractUtil.doOCR(file, TextLocation.WINNING_TEAM).substring(0, 5).equals(TeamName.UMBRA.stringName())) {
            winningTeam = TeamName.UMBRA;
        } else {
            throw new TesseractException();
        }

        BasicFileAttributes view = Files.getFileAttributeView(this.file.getAbsoluteFile().toPath(), BasicFileAttributeView.class).readAttributes();
        matchDate = view.creationTime().toMillis();

        teams.add(new Team(file, TeamName.ASTRA, (winningTeam == TeamName.ASTRA)));
        teams.add(new Team(file, TeamName.UMBRA, (winningTeam == TeamName.UMBRA)));

    }

    public int getMatchTime() {
        return matchTime;
    }

    public File getFile() {
        return file;
    }
}
