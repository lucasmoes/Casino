package casino.chips.domain;

import casino.chips.domain.exception.NegativeNumberException;
import casino.chips.domain.exception.NotEnoughChipsException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Chips {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Long amount;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    public Chips() {
    }

    public Chips(String username, Long amount) {
        this.username = username;
        this.amount = amount;
    }

    public void withdraw(Long amountToWithdraw) {
        if (amountToWithdraw < 0) {
            throw new NegativeNumberException("Cannot withdraw a negative amount: " + amountToWithdraw);
        }

        long newAmount = this.amount - amountToWithdraw;

        if (newAmount < 0) {
            throw new NotEnoughChipsException(
                    String.format(
                            "Cannot withdraw %d chips: %d chips remaining",
                            amountToWithdraw,
                            this.amount
                    )
            );
        }

        this.amount = newAmount;
    }

    public void deposit(Long amountToDeposit) {
        if (amountToDeposit < 0) {
            throw new NegativeNumberException("Cannot deposit a negative amount: " + amountToDeposit);
        }

        this.amount = this.amount + amountToDeposit;
    }

    public String getUsername() {
        return username;
    }

    public Long getAmount() {
        return amount;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}
