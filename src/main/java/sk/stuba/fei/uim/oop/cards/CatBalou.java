package sk.stuba.fei.uim.oop.cards;

import java.util.concurrent.ThreadLocalRandom;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;

public class CatBalou extends BrownCard{
    private static final String CARD_NAME = "CatBalou";

    public CatBalou(Decks decks){
        super(CARD_NAME, decks);
    }

    @Override
    public boolean canPlay(Player player) {
        return true;
    }

    @Override
    public void playCard(Player player, Player[] players) {
        Player playerTwo = players[getTargetedPlayer(players)];

        if(player.equals(playerTwo)){
            System.out.println("Can't play " + this.name + "on yourself!");
            return;
        }
        if(playerTwo.getCards().size() == 0 && playerTwo.getCardsOnTable().size() == 0){
            System.out.println("Can't play " + this.name + " on player with no cards in hand or on the table!");
            return;
        }

        System.out.println(player.getName() + " played " + this.name + " on player " + playerTwo.getName() + ".");

        System.out.println("Do you want to discard a card from hand or table?");
        if(player.binaryInputFromPlayer("hand", "table")==0){
            int numberOfCards = playerTwo.getCards().size();
            if(numberOfCards == 0){
                System.out.println("Can't play " + this.name + " on player with no cards in hand!");
                return;
            }
            int random = ThreadLocalRandom.current().nextInt(0, numberOfCards);
            System.out.println("Removed " + playerTwo.getName() + "'s " + playerTwo.getCards().get(random).getName() + "!");
            playerTwo.removeCardFromHand(playerTwo.getCards().get(random));

        } else {
            int numberOfCards = playerTwo.getCardsOnTable().size();
            if(numberOfCards == 0){
                System.out.println("Can't play " + this.name + " on player with no cards on the table!");
                return;
            }
            int random = ThreadLocalRandom.current().nextInt(0, numberOfCards);
            System.out.println("Removed " + playerTwo.getName() + "'s " + playerTwo.getCardsOnTable().get(random).getName() + "!");
            playerTwo.removeCardFromTable(playerTwo.getCardsOnTable().get(random));
        }
        player.removeCardFromHand(this);
    }
}
