package casino.chips.application;

import java.util.Date;

public class Balance {
    private final String username;
    private final Date lastUpdate;
    private final Long chips;

    public Balance(String username, Date lastUpdate, Long chips) {
        this.username = username;
        this.lastUpdate = lastUpdate;
        this.chips = chips;
    }

    public String getUsername() {
        return username;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public Long getChips() {
        return chips;
    }
}
