package sk.stuba.fei.uim.oop.decks;

import sk.stuba.fei.uim.oop.cards.*;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Decks {
    private ArrayList<Card> deck;
    private ArrayList<Card> discardDeck;

    public Decks(Player[] players){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Barrel(this));
        cards.add(new Barrel(this));
        cards.add(new Dynamite(this));
        cards.add(new Jail(this));
        cards.add(new Jail(this));
        cards.add(new Jail(this));
        for (int i = 0; i < 30; i++) {
            cards.add(new Bang(this));
        }
        for (int i = 0; i < 15; i++) {
            cards.add(new Miss(this));
        }
        for (int i = 0; i < 8; i++) {
            cards.add(new Beer(this));
        }
        for (int i = 0; i < 6; i++) {
            cards.add(new CatBalou(this));
        }
        for (int i = 0; i < 4; i++) {
            cards.add(new Stagecoach(this));
        }
        cards.add(new Indians(this));
        cards.add(new Indians(this));

        Collections.shuffle(cards);
        this.deck = new ArrayList<>();
        this.deck.addAll(cards);
        this.discardDeck = new ArrayList<>();
        for(Player player : players){
            for (int i = 0; i < 4 ; i++) {
                Card card = this.deck.get(0);
                player.getCards().add(card);
                this.deck.remove(0);
            }
        }
    }

    public ArrayList<Card> getDeck(){
        return this.deck;
    }

    public ArrayList<Card> getDiscardDeck(){
        return this.discardDeck;
    }

    public Card drawCard(){
        if(this.deck.isEmpty()){
            refreshDeck();
            if(this.deck.isEmpty()){
                System.out.println("Not enough cards in deck!");
                return null;
            }
        }
        System.out.println("Drew a " + this.deck.get(0).getName() + "!");
        return this.deck.remove(0);
    }

    public void refreshDeck(){
        this.deck.addAll(this.discardDeck);
        Collections.shuffle(this.deck);
        this.discardDeck.clear();
    }

    public void addToDiscardDeck(Card card){
        this.discardDeck.add(card);
    }
}
