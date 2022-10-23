package casino.games.blackjack.data;

import casino.games.blackjack.domain.Game;
import casino.games.blackjack.domain.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlackJackRepository extends JpaRepository<Game, Long> {

    Game findGamesByUsernameAndGameState(String username, GameState gameState);

    List<Game> findAllByUsername(String username);

    Game findGameByIdAndUsername(Long id, String username);

}