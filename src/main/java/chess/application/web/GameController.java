package chess.application.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {
    private final GameService gameService;
    private final JsonTransformer jsonTransformer;

    public GameController(GameService gameService) {
        this.jsonTransformer = new JsonTransformer();
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAllAttributes(gameService.modelReady());
        return "index";
    }

    @GetMapping("/start")
    public String start(Model model) {
        gameService.start();
        return play(model);
    }

    @GetMapping("/load/{id}")
    public String load(Model model, @PathVariable int id) {
        gameService.load();
        System.out.println("방번호: " + id);
        return play(model);
    }

    @PostMapping("/move")
    public String move(Model model, @RequestParam String source, @RequestParam String target) {
        gameService.move(source, target);
        if (gameService.isGameFinished()) {
            return end(model);
        }
        return play(model);
    }

    private String play(Model model) {
        model.addAllAttributes(gameService.modelPlayingBoard());
        return "game";
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        String statusData = jsonTransformer.render(gameService.modelStatus());
        return ResponseEntity.ok().body(statusData);
    }

    @GetMapping("/save")
    public ResponseEntity<Void> save() {
        gameService.save();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/end")
    public String end(Model model) {
        model.addAllAttributes(gameService.end());
        return "result";
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("[ERROR] " + exception.getMessage());
    }
}
