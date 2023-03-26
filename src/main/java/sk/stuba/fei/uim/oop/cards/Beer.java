package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;

public class Beer extends BrownCard{
    private static final String CARD_NAME = "Beer";

    public Beer(Decks decks){
        super(CARD_NAME, decks);
    }

    @Override
    public boolean canPlay(Player player) {
        return true;
    }

    @Override
    public void playCard(Player player, Player[] players){
        System.out.println(player.getName() + " played " + this.name + "!");
        player.addLife();
        player.removeCardFromHand(this);
    }
}
