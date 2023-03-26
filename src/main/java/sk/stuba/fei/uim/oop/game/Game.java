package sk.stuba.fei.uim.oop.game;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.cards.*;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;

public class Game {
    private final Player[] players;
    private int currentPlayer;
    private Decks decks;

    public Game() {
        int numberPlayers = 0;
        while (numberPlayers < 2 || numberPlayers > 4) {
            numberPlayers = ZKlavesnice.readInt("Enter the amount of players (2-4): ");
            if (numberPlayers < 2 || numberPlayers > 4) {
                System.out.println("Wrong amount, please try again.");
            }
        }
        this.players = new Player[numberPlayers];
        for (int i = 0; i < numberPlayers; i++) {
            this.players[i] = new Player(ZKlavesnice.readString("Enter name for PLAYER " + (i + 1) + ": "));
        }

        this.decks = new Decks(this.players);
        this.startGame();
    }

    private void startGame() {
        System.out.println("The game of BANG has begun!");
        while (this.getNumberOfActivePlayers() > 1) {
            Player activePlayer = this.players[this.currentPlayer];

            if (!this.isPlayerAlive(activePlayer)) {
                this.incrementCounter();
                continue;
            }
            System.out.println("--- NEW TURN ---");
            playersInfoPrint(activePlayer);

            if (this.checkForDynamite(activePlayer)) {
                if (!this.isPlayerAlive(activePlayer)) {
                    continue;
                }
            }

            if (!this.checkForJail(activePlayer)) {
                this.incrementCounter();
                continue;
            }

            this.firstPhase();
            this.secondPhase(activePlayer);
            if (getNumberOfActivePlayers() == 1) break;
            this.thirdPhase(activePlayer);
            this.incrementCounter();
        }
        System.out.println("--------------------------------------------");
        System.out.println("The game has ended!");
        System.out.println("The winner is " + this.getWinner().getName());
    }

    private void playersInfoPrint(Player activePlayer) {
        System.out.println("--------------------------------------------");
        System.out.println(" ");
        for (int i = 0; i < this.players.length; i++) {
            if (activePlayer.equals(this.players[i])) {
                System.out.println("Player[" + (i + 1) + "] " + activePlayer.getName() + "'s turn!");
                System.out.println("HP: " + activePlayer.getLives());
                if (this.players[this.currentPlayer].getCardsOnTable().size() > 0) {
                    cardsOnTablePrint(activePlayer);
                    continue;
                }
                System.out.println(" ");
                continue;
            }
            System.out.println("Player[" + (i + 1) + "] " + this.players[i].getName());
            System.out.println("HP: " + this.players[i].getLives());
            if (this.players[i].getCardsOnTable().size() > 0) {
                cardsOnTablePrint(this.players[i]);
                continue;
            }
            System.out.println(" ");
        }
        System.out.println("--------------------------------------------");
    }

    private boolean checkForDynamite(Player player) {
        for (Card card : player.getCardsOnTable()) {
            if (card instanceof Dynamite) {
                if (card.useCard(player)) {
                    return true;
                } else {
                    players[getPreviousPlayerIndex()].getCardsOnTable().add(card);
                    player.removeCardFromTable(card);
                    return false;
                }
            }
        }
        return false;
    }

    private boolean checkForJail(Player player) {
        for (Card card : player.getCardsOnTable()) {
            if (card instanceof Jail) {
                return card.useCard(player);
            }
        }
        return true;
    }

    private void firstPhase() {
        System.out.println("PHASE 1 - Drawing cards!");
        drawCard();
        drawCard();
    }

    private void drawCard() {
        this.players[this.currentPlayer].getCards().add(this.decks.drawCard());
    }

    private void secondPhase(Player activePlayer) {
        System.out.println("--------------------------------------------");
        System.out.println("PHASE 2 - Playing cards!");
        ArrayList<Card> cards = activePlayer.getCards();
        ArrayList<Card> playableCards = activePlayer.getPlayableCards();
        int numberCard = 100;
        while (playableCards.size() > 0 && numberCard > 0 && getNumberOfActivePlayers() > 1) {
            numberCard = this.playCard(cards, activePlayer);
            playableCards = activePlayer.getPlayableCards();
            cards = activePlayer.getCards();
        }
    }

    private void thirdPhase(Player activePlayer) {
        if (activePlayer.getCards().size() == 0) {
            return;
        }
        System.out.println("PHASE 3 - Throwing cards away!");
        if (activePlayer.getCards().size() <= activePlayer.getLives()) {
            System.out.println("Do you want to throw cards away?");
            if (activePlayer.binaryInputFromPlayer("no", "yes") == 0) {
                return;
            }
        }
        int numberCard;
        while (activePlayer.getCards().size() > activePlayer.getLives() || activePlayer.getCards().size() > 0) {
            numberCard = chooseCard("throw away");
            if (numberCard == -1) {
                if (activePlayer.getCards().size() <= activePlayer.getLives()) {
                    break;
                }
                System.out.println("Can't end phase yet, you have too many cards!");
                System.out.println("Lives: " + activePlayer.getLives());

                continue;
            }
            activePlayer.removeCardFromHand(activePlayer.getCards().get(numberCard));
        }
    }

    private int playCard(ArrayList<Card> playableCards, Player activePlayer) {
        playersInfoPrint(activePlayer);
        if (activePlayer.getCardsOnTable().size() > 0) {
            cardsOnTablePrint(activePlayer);
        }

        System.out.println("Playable cards on hand:");
        int numberCard = chooseCard("play");
        if (numberCard == -1) {
            return 0;
        }
        playableCards.get(numberCard).playCard(activePlayer, players);
        return 1;
    }

    private void cardsOnTablePrint(Player player) {
        System.out.println("Cards on the table:");
        for (int i = 0; i < player.getCardsOnTable().size(); i++) {
            System.out.println(player.getCardsOnTable().get(i).getName());
        }
        System.out.println(" ");
    }

    private int chooseCard(String verb) {
        System.out.println("0. End phase");
        ArrayList<Card> cards = new ArrayList<>(this.players[currentPlayer].getCards());
        for (int i = 0; i < cards.size(); i++) {
            System.out.println(i + 1 + ". " + cards.get(i).getName());
        }
        int numberOfCard;
        while (true) {
            numberOfCard = ZKlavesnice.readInt("Number of card you want to " + verb + ": ");
            if (numberOfCard == 0) return -1;
            if (numberOfCard < 0 || numberOfCard > cards.size()) {
                System.out.println("Wrong number, please try again.");
            } else {
                break;
            }
        }
        return numberOfCard - 1;
    }

    private int getNumberOfActivePlayers() {
        int count = 0;
        for (Player player : this.players) {
            if (player.isAlive()) {
                count++;
            }
        }
        return count;
    }

    private void incrementCounter() {
        this.currentPlayer++;
        this.currentPlayer %= this.players.length;
    }

    private int getPreviousPlayerIndex() {
        int numPlayers = this.players.length;
        int prevIndex = (this.currentPlayer - 1 + numPlayers) % numPlayers;

        while (!players[prevIndex].isAlive()) {
            prevIndex = (prevIndex - 1 + numPlayers) % numPlayers;
        }
        return prevIndex;
    }

    private boolean isPlayerAlive(Player player) {
        if (!player.isAlive()) {
            ArrayList<Card> cardsToDeck = player.removeCardsFromHand();
            ArrayList<Card> cardsToDeckTwo = player.removeCardsFromTable();
            for (Card card : cardsToDeck) {
                this.decks.addToDiscardDeck(card);
            }
            for (Card card : cardsToDeckTwo) {
                this.decks.addToDiscardDeck(card);
            }
            return false;
        }
        return true;
    }

    private Player getWinner() {
        for (Player player : this.players) {
            if (player.isAlive()) {
                return player;
            }
        }
        return null;
    }


}
