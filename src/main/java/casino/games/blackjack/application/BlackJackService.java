package casino.games.blackjack.application;

import casino.games.blackjack.application.exception.GameNotFoundException;
import casino.games.blackjack.application.exception.MoveException;
import casino.games.blackjack.data.BlackJackRepository;
import casino.games.blackjack.domain.*;
import casino.chips.application.ChipsService;
import casino.chips.domain.exception.NegativeNumberException;
import casino.chips.domain.exception.NotEnoughChipsException;
import casino.games.blackjack.domain.factory.NumberOfDeckBasedFactory;
import casino.games.blackjack.dto.EndGameDto;
import casino.games.blackjack.dto.GameToRepresentInAListDto;
import casino.games.blackjack.dto.PlayingGameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BlackJackService {

    private final BlackJackRepository blackJackRepository;
    private final ChipsService chipsService;

    @Autowired
    public BlackJackService(BlackJackRepository blackJackRepository, ChipsService chipsService) {
        this.blackJackRepository = blackJackRepository;
        this.chipsService = chipsService;
    }

    public Object newGame(String username, Long bet, int numberOfDecks) {
        widthDrawChips(username, bet);

        Deck deck = new NumberOfDeckBasedFactory(numberOfDecks).createDeck();

        Hand playerHand = new Hand(new ArrayList<>());
        Dealer dealer = new Dealer(deck, new Hand(new ArrayList<>()));

        Game game = new Game(dealer, playerHand, username, bet);
        game.startGame();

        return returnGame(game);
    }

    public List<GameToRepresentInAListDto> getGames(String username) {
        List<Game> listOfGames = blackJackRepository.findAllByUsername(username);
        List<GameToRepresentInAListDto> newListOfGames = new ArrayList<>();

        for (Game game : listOfGames) {
            GameToRepresentInAListDto gameInList = new GameToRepresentInAListDto(game);
            newListOfGames.add(gameInList);
        }
        return newListOfGames;
    }

    public Object getGame(Long id, String username) {
        Game game = blackJackRepository.findGameByIdAndUsername(id, username);
        return returnGame(game);
    }

    public Object continueOldGameOrStartNewGame(String username, Long bet, int numberOfDecks) {

        Game game = this.blackJackRepository.findGamesByUsernameAndGameState(username, GameState.PLAYING);
        if (game == null) {
            widthDrawChips(username, bet);

            Deck deck = new NumberOfDeckBasedFactory(numberOfDecks).createDeck();

            Hand playerHand = new Hand(new ArrayList<>());
            Dealer dealer = new Dealer(deck, new Hand(new ArrayList<>()));

            game = new Game(dealer, playerHand, username, bet);
            game.startGame();

        }
        return returnGame(game);
    }

    public Object move(String username, String moveType) {
        Game game = this.blackJackRepository.findGamesByUsernameAndGameState(username, GameState.PLAYING);
        if (game == null) {
            throw new MoveException("You need to start a new game to be able to make a move");
        }
        if (moveType.equals("double down")) {
            widthDrawChips(username, game.getBet());
        }

        game.move(moveType);

        return returnGame(game);
    }

    public Object move(String username, Long id, String moveType) {
        Game game = blackJackRepository.findGameByIdAndUsername(id, username);
        if (game == null) {
            throw new GameNotFoundException("Game not found with id: " + id);
        }

        if (game.getGameState() != GameState.PLAYING) {
            throw new MoveException("You can't make a move on a game that is not playing, please start a new game");
        }

        if (moveType.equals("double down")) {
            widthDrawChips(username, game.getBet());
        }

        game.move(moveType);

        return returnGame(game);
    }


    public void widthDrawChips(String username, Long amount) {
        try {
            chipsService.withdrawChips(username, amount);
        } catch (NotEnoughChipsException exception) {
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, exception.getMessage());
        } catch (NegativeNumberException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    public Object returnGame(Game game) {
        if (!game.getGameState().equals(GameState.PLAYING)) {
            chipsService.depositChips(game.getUsername(), game.getBet());

            this.blackJackRepository.save(game);
            return new EndGameDto(game);
        }
        this.blackJackRepository.save(game);
        return new PlayingGameDto(game);
    }
}