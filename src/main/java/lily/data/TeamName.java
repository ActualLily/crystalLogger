package lily.data;

public enum TeamName {
    ASTRA("Astra"),
    UMBRA("Umbra");

    String teamName;

    TeamName(String teamName) {
        this.teamName = teamName;
    }

    public String stringName() {
        return teamName;
    }
}
