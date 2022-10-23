package casino.chips.application;

import casino.chips.data.ChipsRepository;
import casino.chips.domain.Chips;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class ChipsService {
    private final ChipsRepository chipsRepository;

    public ChipsService(ChipsRepository chipsRepository) {
        this.chipsRepository = chipsRepository;
    }

    public Balance findBalance(String username) {
        Chips chips = this.findChipsByUsername(username);
        return this.showBalanceFor(chips);
    }

    public Balance depositChips(String username, Long amount) {
        Chips chips = this.findChipsByUsername(username);

        chips.deposit(amount);
        this.chipsRepository.save(chips);

        return this.showBalanceFor(chips);
    }

    public Balance withdrawChips(String username, Long amount) {
        Chips chips = this.findChipsByUsername(username);

        chips.withdraw(amount);
        this.chipsRepository.save(chips);

        return this.showBalanceFor(chips);
    }

    private Chips findChipsByUsername(String username) {
        return this.chipsRepository
                .findByUsername(username)
                .orElse(new Chips(username, 0L));
    }

    private Balance showBalanceFor(Chips chips) {
        return new Balance(
                chips.getUsername(),
                chips.getLastUpdate(),
                chips.getAmount()
        );
    }
}
