package casino.chips.data;

import casino.chips.domain.Chips;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This is a magic interface, which is automatically implemented
 * by Spring based on the chosen data storage configuration
 * when the application is started.
 */
public interface ChipsRepository extends JpaRepository<Chips, Long> {
    Optional<Chips> findByUsername(String username);
}
