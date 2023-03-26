package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;

public class Barrel extends BlueCard {
    private static final String CARD_NAME = "Barrel";

    public Barrel(Decks decks){
        super(CARD_NAME, decks);
    }

    @Override
    public boolean canPlay(Player player) {
        return !player.isCardOnTable(this);
    }

    @Override
    public void playCard(Player player, Player[] players) {
        if (player.isCardOnTable(this)) {
            System.out.println("You already have Barrel on the table!");
            return;
        }
        System.out.println(player.getName() + " played " + this.name + "!");
        player.getCardsOnTable().add(this);
        player.removeCardFromHand(this);
    }

    @Override
    public boolean useCard(Player player) {
        int odds = giveOdds();
        if (odds > 4) {
            odds = odds % 4;
        }
        if (odds == 1) {
            super.useCard(player);
            return true;
        }
        System.out.println("Bad luck! Barrel wasn't used.");
        return false;
    }

}
