package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;

public class Miss extends BrownCard{
    private static final String CARD_NAME = "Miss";
    private boolean canPlay;

    public Miss(Decks decks){
        super(CARD_NAME, decks);
        canPlay = false;
    }

    @Override
    public boolean canPlay(Player player) {
        return this.canPlay;
    }

    @Override
    public void setCanPlay(boolean bool) {
        this.canPlay = bool;
    }

    @Override
    public void playCard(Player player, Player[] players){
        if(!canPlay(player)){
            System.out.println("Can't play " + this.name + "!");
            return;
        }
        System.out.println(player.getName() + " played " + this.name + "!");
        this.setCanPlay(false);
        player.removeCardFromHand(this);
    }
}
