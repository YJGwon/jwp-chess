package chess.application.web;

import chess.domain.Room;
import chess.dto.ScoreDto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameRestController {
    private final GameService gameService;

    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public ResponseEntity<List<Room>> list() {
        return ResponseEntity.ok().body(gameService.list());
    }

    @GetMapping("/score/{gameNo}")
    public ResponseEntity<ScoreDto> status(@PathVariable int gameNo) {
        return ResponseEntity.ok().body(gameService.scoresOf(gameNo));
    }
}
