package casino.games.blackjack.presentation;

import casino.games.blackjack.application.BlackJackService;
import casino.games.blackjack.domain.exception.WrongMoveException;
import casino.games.blackjack.dto.GameDto;
import casino.games.blackjack.dto.GameToRepresentInAListDto;
import casino.games.blackjack.dto.MoveDto;
import casino.security.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/blackjack")
public class BlackJackController {

    @Autowired
    private final BlackJackService service;

    public BlackJackController(BlackJackService service) {
        this.service = service;
    }

    @GetMapping("/get-all-games")
    public List<GameToRepresentInAListDto> getGames(Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return service.getGames(profile.getUsername());
    }

    @GetMapping("/game/{id}")
    public Object getGame(Authentication authentication, @PathVariable Long id) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return service.getGame(id, profile.getUsername());
    }

    @PostMapping("/new-game")
    public Object newGame(Authentication authentication, @Validated @RequestBody GameDto gameDto) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return service.newGame(profile.getUsername(), gameDto.bet, gameDto.numberOfDecks);
    }

    @PostMapping("/move/{id}")
    public Object moveById(Authentication authentication, @Validated @RequestBody MoveDto move, @PathVariable Long id) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        try {
            return this.service.move(profile.getUsername(), id, move.move);
        } catch (WrongMoveException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
    
    @PostMapping("/start-game")
    public Object startGame(Authentication authentication, @Validated @RequestBody GameDto gameDto) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return this.service.continueOldGameOrStartNewGame(profile.getUsername(), gameDto.bet, gameDto.numberOfDecks);
    }

    @PostMapping("/move")
    public Object move(Authentication authentication, @Validated @RequestBody MoveDto move) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        try {
            return this.service.move(profile.getUsername(), move.move);
        } catch (WrongMoveException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
