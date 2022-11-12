package forsale.strategies;

import forsale.*;
import java.util.*;

/**
 * New implementation attempting to use strategy guide:
 * https://boardgamegeek.com/thread/89221/sale-strategy-article
 * Not yet finished!
 */

public class StrategyThree implements Strategy {
    double averageRemaining;
    List<Card> cards;
    List<Card> cardvalue;
    Helper help;
    final int MAX_BET = 8;

    public StrategyThree() {
        help = new Helper();
    }

    public int bid(PlayerRecord p, AuctionState a) {
        double stand = help.sd(a.getCardsInAuction());
        int bidMin = 1 + a.getCurrentBid();
        int lowcard = 30;
        int highcard = 0;
        int cardQuality = 0;
        int money = p.getCash();
        int currentBid = a.getCurrentBid();
        cards = a.getCardsInAuction();
        Collections.sort(cards);
        System.out.println("Player Money: " + money);

        for (int i = 0; i < cards.size(); i++) {
            cardQuality = cards.get(i).getQuality();
      
            if (cardQuality > highcard) {
              highcard = cardQuality;
            }
            
            if (cardQuality < lowcard) {
              lowcard = cardQuality;
            }
        }
        if (bidMin > money / 2) {
            return 0;
        }
        if (bidMin == 1) {
            return 2;
        }
        if(stand < a.getPlayersInAuction().size() + 2){ //If round is somewhat even, bet a little more than usual
            if(highcard > 20 && currentBid < money/(3 + 2)){
                return bidMin;
            }
            else if(lowcard >= 10 && currentBid < money/(4 + 2)){ // bet less if the lowcard if more than 10, and the high card
                return bidMin;
            }
        }

        if(highcard > 20 && currentBid < money/3){ // if high card is more than 20, bid up to half your money
            return bidMin;
        }
        else if(currentBid < money/4){ // bet less if the lowcard if more than 10, and the high card is less than 20
            return bidMin;
        }

        if(lowcard >= 15){ //if the low card is 15 or greater, take it without bidding further.
            return 0;
        }
        if (bidMin <= 4) {
            return bidMin;
          }
         
        return bidMin;
    }

    public Card chooseCard(PlayerRecord p, SaleState s) {
    TreeSet<Card> myCards = new TreeSet<Card>(p.getCards()); 
        for(PlayerRecord other : s.getPlayers()){
            TreeSet<Card> otherCards = new TreeSet<Card>(other.getCards());
            System.out.println(other.getName() + " " + otherCards.last());
            System.out.println("My cards: " + myCards);
            if(myCards.last().getQuality() < otherCards.last().getQuality()){
                 System.out.println("No certain win playing: " + myCards.first());
                 return myCards.first();
             }
        }
        double stand = help.differnce1(s.getChequesAvailable());
        if(stand < 3){
            return myCards.last();
        }
        return myCards.first();
    }
}