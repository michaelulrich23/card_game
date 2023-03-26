package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;

public class Jail extends BlueCard {
    private static final String CARD_NAME = "Jail";

    public Jail(Decks decks){
        super(CARD_NAME, decks);
    }

    @Override
    public boolean canPlay(Player player) {
        return !player.isCardOnTable(this);
    }

    @Override
    public void playCard(Player player, Player[] players) {
        Player playerTwo = players[getTargetedPlayer(players)];

        if (player.equals(playerTwo)) {
            System.out.println("Can't play " + this.name + " on yourself!");
            return;
        }
        if (playerTwo.isCardOnTable(this)) {
            System.out.println(playerTwo.getName() + " already has " + this.name + " on the table!");
            return;
        }
        System.out.println(player.getName() + " played " + this.name + " on player " + playerTwo.getName() + ".");
        playerTwo.getCardsOnTable().add(this);
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
            player.removeCardFromTable(this);
            return true;
        }
        System.out.println("Bad luck! You're staying in Jail.");
        System.out.println("--------------------------------------------");
        player.removeCardFromTable(this);
        return false;
    }
}
