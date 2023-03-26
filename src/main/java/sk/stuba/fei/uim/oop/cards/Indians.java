package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;

public class Indians extends BrownCard{
    private static final String CARD_NAME = "Indians";

    public Indians(Decks decks){
        super(CARD_NAME, decks);
    }

    @Override
    public boolean canPlay(Player player) {
        return true;
    }

    @Override
    public void playCard(Player player, Player[] players) {
        System.out.println(player.getName() + " played " + this.name + " on everyone.");
        for(Player p : players){
            if(p == player){
                continue;
            }
            if(p.hasBang()){
                continue;
            }
            p.removeLife(1);
        }
        player.removeCardFromHand(this);
    }
}
