package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.cards.*;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;

public class Player {
    private int lives;
    private final String name;
    private ArrayList<Card> cards;
    private ArrayList<Card> cardsOnTable;

    public Player(String name) {
        this.name = name;
        this.lives = 4;
        this.cards = new ArrayList<>();
        this.cardsOnTable = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public ArrayList<Card> getCardsOnTable() {
        return this.cardsOnTable;
    }

    public void removeLife(int numberOfLives) {
        this.lives = this.lives - numberOfLives;
        if (this.lives <= 0) {
            System.out.println(this.name + " lost " + numberOfLives + " HP and has died.");
            return;
        }
        System.out.println(this.name + " lost " + numberOfLives + " HP and has " + this.lives + " HP left.");
    }

    public void addLife() {
        System.out.println("You gained a life!");
        this.lives++;
    }

    public boolean isAlive() {
        return this.lives > 0;
    }

    public void removeCardFromHand(Card card) {
        card.getDecks().addToDiscardDeck(card);
        this.cards.remove(card);
    }

    public void removeCardFromTable(Card card) {
        card.getDecks().addToDiscardDeck(card);
        this.cardsOnTable.remove(card);
    }

    public ArrayList<Card> removeCardsFromHand() {
        ArrayList<Card> removedCards = new ArrayList<>(this.cards);
        this.cards.clear();
        return removedCards;
    }

    public ArrayList<Card> removeCardsFromTable() {
        ArrayList<Card> removedCards = new ArrayList<>(this.cards);
        this.cards.clear();
        return removedCards;
    }

    public boolean isCardOnTable(BlueCard cardType) {
        for (Card card : cardsOnTable) {
            if (card.getClass().isInstance(cardType)) {
                return true;
            }
        }
        return false;
    }

    public Card hasMiss() {
        for (Card card : this.cards) {
            if (card instanceof Miss) {
                return card;
            }
        }
        return null;
    }

    public boolean hasBang() {
        for (Card card : this.cards) {
            if (card instanceof Bang) {
                System.out.println(this.name + " defended against Indians.");
                this.removeCardFromHand(card);
                return true;
            }
        }
        return false;
    }

    public void drawCard(Decks decks) {
        this.cards.add(decks.drawCard());
    }

    public int binaryInputFromPlayer(String firstWord, String secondWord) {
        int input;
        do {
            System.out.println("0 - " + firstWord);
            input = ZKlavesnice.readInt("1 - " + secondWord);
            if (input != 0 && input != 1) {
                System.out.println("Wrong number, please try again.");
            }
        } while (input != 0 && input != 1);
        return input;
    }

    public ArrayList<Card> getPlayableCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (Card card : this.cards) {
            if (card.canPlay(this)) {
                cards.add(card);
            }
        }
        return cards;
    }

    public boolean useBarrel(Player playerTwo) {
        for (Card card : this.cardsOnTable) {
            if (card instanceof Barrel) {
                System.out.println(playerTwo.getName() + " do you wish to try to use Barrel?");
                if (binaryInputFromPlayer("no", "yes") == 1) {
                    return card.useCard(this);
                }
            }
        }
        return false;
    }
}
