package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;

public class Dynamite extends BlueCard {
    private static final String CARD_NAME = "Dynamite";

    public Dynamite(Decks decks){
        super(CARD_NAME, decks);
    }

    @Override
    public boolean canPlay(Player player) {
        return !player.isCardOnTable(this);
    }

    @Override
    public void playCard(Player player, Player[] players) {
        if (player.isCardOnTable(this)) {
            System.out.println("You already have Dynamite on the table!");
            return;
        }
        System.out.println(player.getName() + " played " + this.name + "!");
        player.getCardsOnTable().add(this);
        player.removeCardFromHand(this);
    }

    @Override
    public boolean useCard(Player player) {
        int odds = giveOdds();
        if (odds == 1) {
            System.out.println("Bad luck! Dynamite exploded.");
            System.out.println("--------------------------------------------");
            player.removeLife(3);
            player.removeCardFromTable(this);
            return true;
        }
        super.useCard(player);
        return false;
    }
}
