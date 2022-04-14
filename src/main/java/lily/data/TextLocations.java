package lily.data;

import java.awt.*;

public enum TextLocations {
    WINNING_TEAM(new Rectangle(859, 49, 205, 35), String.class),
    TEAM_KDA(new Rectangle(540, 190, 1200, 28), String.class),
    KDA_BOUNDS(new Rectangle(160, 0, 81, 38), Integer.class),

    PLAYER_DATA(new Rectangle(96, 410, 1700, 500), String.class),
    PLAYER_NAME(new Rectangle(PLAYER_DATA.bounds.x, PLAYER_DATA.bounds.y, 290, PLAYER_DATA.bounds.height / 10), String.class),
    PLAYER_SERVER(new Rectangle(408, PLAYER_DATA.bounds.y, 190, PLAYER_DATA.bounds.height / 10), String.class),
    PLAYER_RANK(new Rectangle(644, PLAYER_DATA.bounds.y, 190, PLAYER_DATA.bounds.height / 10), String.class),

    ASTRA_KILLS(new Rectangle(TEAM_KDA.bounds.x, TEAM_KDA.bounds.y, KDA_BOUNDS.bounds.width, KDA_BOUNDS.bounds.height), Integer.class),
    ASTRA_DEATHS(new Rectangle(ASTRA_KILLS.bounds.x + KDA_BOUNDS.bounds.x, TEAM_KDA.bounds.y, KDA_BOUNDS.bounds.width, KDA_BOUNDS.bounds.height), Integer.class),
    ASTRA_ASSISTS(new Rectangle(ASTRA_DEATHS.bounds.x + KDA_BOUNDS.bounds.x, TEAM_KDA.bounds.y, KDA_BOUNDS.bounds.width, KDA_BOUNDS.bounds.height), Integer.class),

    UMBRA_KILLS(new Rectangle(1330, TEAM_KDA.bounds.y, KDA_BOUNDS.bounds.width, KDA_BOUNDS.bounds.height), Integer.class),
    UMBRA_DEATHS(new Rectangle(UMBRA_KILLS.bounds.x + KDA_BOUNDS.bounds.x, TEAM_KDA.bounds.y, KDA_BOUNDS.bounds.width, KDA_BOUNDS.bounds.height), Integer.class),
    UMBRA_ASSISTS(new Rectangle(UMBRA_DEATHS.bounds.x + KDA_BOUNDS.bounds.x, TEAM_KDA.bounds.y, KDA_BOUNDS.bounds.width, KDA_BOUNDS.bounds.height), Integer.class);

    Rectangle bounds;
    Class<?> returnType;

    TextLocations(Rectangle bounds, Class<?> returnType) {
        this.bounds = bounds;
        this.returnType = returnType;
    }

    public Rectangle bounds() {
        return bounds;
    }

    public Class<?> type() {
        return returnType;
    }

    public Integer playerX(Integer playerNumber) {
        return PLAYER_DATA.bounds.x + ((PLAYER_DATA.bounds.height / 10) * (playerNumber - 1));
    }
}
