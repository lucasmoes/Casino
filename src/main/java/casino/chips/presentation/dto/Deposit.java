package casino.chips.presentation.dto;

import javax.validation.constraints.Positive;

public class Deposit {
    @Positive
    public Long amount;
}
