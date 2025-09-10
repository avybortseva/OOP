package ru.nsu.g.a.vybortseva.blackjack;

public class Card {

    public Suit getSuit() {
        return suit;
    }

    public enum Suit {
        DIAMONDS ("Бубны ♢"),
        HEARTS("Червы ♡"),
        CLUBS("Трефы ♧"),
        SPADES("Пики ♤");

        private final String rusName;

        Suit(String rusName){
            this.rusName = rusName;
        }

        public String getRusName() {
            return rusName;
        }

        @Override
        public String toString() {
            return rusName;
        }
    }

    public enum Rank{
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

        public String getRusName() {
            return rusName;
        }

        public int getBaseValue() {
            return baseValue;
        }

        @Override
        public String toString() {
            return rusName;
        }
    }

    private final Suit suit;
    private final Rank rank;
    private boolean isHidden;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.isHidden = false;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isHidden() {
        return isHidden;
    }
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public int getBaseValue(){
        return rank.getBaseValue();
    }

    @Override
    public String toString() {
        if (isHidden) {
            return "<закрытая карта>";
        }
        return rank.getRusName() + " " + suit.getRusName() + " (" + getBaseValue() + ")";
    }

}
