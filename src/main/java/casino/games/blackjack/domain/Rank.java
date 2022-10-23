package casino.games.blackjack.domain;

public enum Rank {
    ACE(11),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10);

    private final int rank;

    Rank(int n) {
        this.rank = n;
    }

    public int getRankValue() {
        return rank;
    }
}