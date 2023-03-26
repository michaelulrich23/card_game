package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;

public class Stagecoach extends BrownCard{
    private static final String CARD_NAME = "Stagecoach";

    public Stagecoach(Decks decks){
        super(CARD_NAME, decks);
    }

    @Override
    public boolean canPlay(Player player) {
        return this.getDecks().getDeck().size() + this.getDecks().getDiscardDeck().size() >= 2;
    }

    @Override
    public void playCard(Player player, Player[] players) {
        if(this.getDecks().getDeck().size() + this.getDecks().getDiscardDeck().size() < 2){
            System.out.println("Not enough cards in deck!");
            return;
        }
        System.out.println(player.getName() + " played " + this.name + "!");

        player.drawCard(this.decks);
        player.drawCard(this.decks);

        player.removeCardFromHand(this);
    }
}
