package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

public abstract class Card {
    protected String name;
    protected Decks decks;

    public Card(String name, Decks decks) {
        this.name = name;
        this.decks = decks;
    }

    public Decks getDecks() {
        return decks;
    }

    public String getName() {
        return name;
    }

    public abstract boolean canPlay(Player player);

    public void setCanPlay(boolean b) {
    }

    public abstract void playCard(Player player, Player[] players);

    protected int getTargetedPlayer(Player[] players) {
        int numberOfPlayer;
        while (true) {
            numberOfPlayer = ZKlavesnice.readInt("Number of player you want to use card on: ");
            if (numberOfPlayer <= 0 || numberOfPlayer > players.length) {
                System.out.println("Wrong number, please try again.");
            } else {
                if (!players[numberOfPlayer - 1].isAlive()) {
                    System.out.println("Can't target a dead player!");
                } else {
                    break;
                }
            }
        }
        return numberOfPlayer - 1;
    }

    public boolean useCard(Player player) {
        return false;
    }

}
