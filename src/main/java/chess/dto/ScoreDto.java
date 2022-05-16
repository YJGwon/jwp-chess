package chess.dto;

import chess.domain.Camp;
import chess.domain.Score;
import java.util.Map;

public class ScoreDto {
    private final double whiteScore;
    private final double blackScore;

    public ScoreDto(double whiteScore, double blackScore) {
        this.whiteScore = whiteScore;
        this.blackScore = blackScore;
    }

    public static ScoreDto from(Map<Camp, Score> scores) {
        Score whiteScore = scores.get(Camp.WHITE);
        Score blackScore = scores.get(Camp.BLACK);
        return new ScoreDto(whiteScore.getValue(), blackScore.getValue());
    }

    public double getWhiteScore() {
        return whiteScore;
    }

    public double getBlackScore() {
        return blackScore;
    }
}
