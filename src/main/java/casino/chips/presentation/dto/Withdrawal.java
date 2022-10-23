package casino.chips.presentation.dto;

import javax.validation.constraints.Positive;

public class Withdrawal {
    @Positive
    public Long amount;
}
