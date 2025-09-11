package ru.nsu.g.a.vybortseva.game;

/**
 * The class for cards.
 */
public class Card {

    /**
     * The enum of suits.
     */
    public enum Suit {
        DIAMONDS("Бубны ♢"),
        HEARTS("Червы ♡"),
        CLUBS("Трефы ♧"),
        SPADES("Пики ♤");

        private final String rusName;

        Suit(String rusName) {
            this.rusName = rusName;
        }

        /**
         * The method for getting rusName.
         */
        public String getRusName() {
            return rusName;
        }

        /**
         * The method for formatting of rusName.
         */
        @Override
        public String toString() {
            return rusName;
        }
    }

    /**
     * The enum of ranks.
     */
    public enum Rank {
        TWO("Двойка", 2),
        THREE("Тройка", 3),
        FOUR("Четверка", 4),
        FIVE("Пятерка", 5),
        SIX("Шестерка", 6),
        SEVEN("Семерка", 7),
        EIGHT("Восьмерка", 8),
        NINE("Девятка", 9),
        TEN("Десятка", 10),
        JACK("Валет", 10),
        QUEEN("Королева", 10),
        KING("Король", 10),
        ACE("Туз", 11);

        private final String rusName;
        private final int baseValue;


        Rank(String rusName, int baseValue) {
            this.rusName = rusName;
            this.baseValue = baseValue;
        }

        /**
         * The method for getting rusName.
         */
        public String getRusName() {
            return rusName;
        }

        /**
         * The method for getting baseValue.
         */
        public int getBaseValue() {
            return baseValue;
        }

        /**
         * The method for formating of rusName.
         */
        @Override
        public String toString() {
            return rusName;
        }
    }

    private final Suit suit;
    private final Rank rank;
    private boolean isHidden;

    /**
     * The constructor for card.
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.isHidden = false;
    }

    /**
     * The method for getting rank.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * The method for getting suit.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * The method for checking if card is hidden.
     */
    public boolean isHidden() {
        return isHidden;
    }

    /**
     * The method for setting hidden.
     */
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    /**
     * The method for getting baseValue.
     */
    public int getBaseValue() {
        return rank.getBaseValue();
    }

    /**
     * The method for formating card.
     */
    @Override
    public String toString() {
        if (isHidden) {
            return "<закрытая карта>";
        }
        return rank.getRusName() + " " + suit.getRusName() + " (" + getBaseValue() + ")";
    }

}
