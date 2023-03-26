package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;

public class Bang extends BrownCard{
    private static final String CARD_NAME = "Bang";

    public Bang(Decks decks){
        super(CARD_NAME, decks);
    }

    @Override
    public boolean canPlay(Player player) {
        return true;
    }

    @Override
    public void playCard(Player player, Player[] players){
        Player playerTwo = players[getTargetedPlayer(players)];

        if(player.equals(playerTwo)){
            System.out.println("Can't play Bang on yourself!");
            return;
        }

        System.out.println(player.getName() + " played " + this.name + " on player " + playerTwo.getName() + ".");

        if(playerTwo.useBarrel(playerTwo)){
            player.removeCardFromHand(this);
            return;
        }
        Card card = playerTwo.hasMiss();
        if(card != null){
            card.setCanPlay(true);
            card.playCard(playerTwo, players);
            player.removeCardFromHand(this);
            return;
        }
        playerTwo.removeLife(1);
        player.removeCardFromHand(this);
    }


}


