package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.decks.Decks;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.concurrent.ThreadLocalRandom;

public abstract class BlueCard extends Card {

    public BlueCard(String name, Decks decks) {
        super(name, decks);
    }

    public int giveOdds(){
        return ThreadLocalRandom.current().nextInt(1, 9);
    }

    @Override
    public boolean useCard(Player player){
        System.out.println("Good luck! Odds for " + this.name + " are in your favour.");
        System.out.println("--------------------------------------------");
        return true;
    }

}
