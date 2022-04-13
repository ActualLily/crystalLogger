package lily.data;

import lily.TesseractUtil;

import java.util.LinkedList;
import java.util.List;

public class Player {

    String name = "Liya Massemeure@Mateus";
    String rank = null;

    Integer kills, deaths, assists = null;
    Integer damageDone, damageTaken, damageHealed = null;
    Integer objectiveTime = null;

    String tesseractData;

    public Player(String tesseractData) {
        this.tesseractData = tesseractData;
        int currentWord = 0;

        name = getWordList().get(currentWord++) + " " + getWordList().get(currentWord++) + "@" + getWordList().get(currentWord++);
        if (getWordList().get(currentWord).matches("\\d")) {
            rank = "Unranked";
        } else {
            rank = getWordList().get(currentWord++);
        }
        kills = Integer.parseInt(TesseractUtil.sanitizeToNumbers(getWordList().get(currentWord++)));
        deaths = Integer.parseInt(TesseractUtil.sanitizeToNumbers(getWordList().get(currentWord++)));
        assists = Integer.parseInt(TesseractUtil.sanitizeToNumbers(getWordList().get(currentWord++)));
        damageDone = Integer.parseInt(TesseractUtil.sanitizeToNumbers(getWordList().get(currentWord++)));
        damageTaken = Integer.parseInt(TesseractUtil.sanitizeToNumbers(getWordList().get(currentWord++)));
        damageHealed = Integer.parseInt(TesseractUtil.sanitizeToNumbers(getWordList().get(currentWord++)));
        objectiveTime = TesseractUtil.minuteSecondToSeconds(TesseractUtil.sanitizeToNumbers(getWordList().get(currentWord++)));
    }

    private LinkedList<String> getWordList() {
        int currentWord = 0;
        List<StringBuilder> words = new LinkedList<>();
        words.add(currentWord, new StringBuilder(""));

        for (int i = 0; i < tesseractData.length(); i++) {
            if (tesseractData.charAt(i) == ' ') {
                currentWord++;
                words.add(currentWord, new StringBuilder(""));
            } else {
                words.get(currentWord).append(tesseractData.charAt(i));
            }
        }

        LinkedList<String> stringList = new LinkedList<>();

        for(StringBuilder stringBuilder : words) {
            stringList.add(stringBuilder.toString().replaceAll("!", "l"));
        }

        return stringList;
    }
}
