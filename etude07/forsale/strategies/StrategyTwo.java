
package forsale.strategies;
import forsale.*;
import java.util.*;

public class StrategyTwo implements Strategy {
   public int bid(PlayerRecord p, AuctionState a) {
       
    int bidMin = 1 + a.getCurrentBid();
    int lowcard = 30;
    int highcard = 0;
    int cardQuality = 0;
    List<Card> cardsInAuction = a.getCardsInAuction();

    if (a.getCardsInDeck().size() == 0) {
      return p.getCash();
    }
    for (int i = 0; i < cardsInAuction.size(); i++) {
      cardQuality = cardsInAuction.get(i).getQuality();

      if (cardQuality > highcard) {
        highcard = cardQuality;
      }
      
      if (cardQuality < lowcard) {
        lowcard = cardQuality;
      }
    }
    if (bidMin > p.getCash()/2) {
      return 0;
    }
    if ( bidMin == 1) {
      return 2;
    }
    
    if (lowcard >= 15 && highcard <= 27) {
      if (bidMin <= 4) {
        return bidMin;
      }
    }

    //if (lowcard == 0 || highcard <= 28) {
    //  return bidMin;
    //}
    
    if (bidMin <= 4) {
      return bidMin;
    }
    return 0;
   }


  public Card chooseCard(PlayerRecord p, SaleState s) {

      List<Integer> cardsInSale = s.getChequesAvailable();
      List<Integer> cardsInDeck = s.getChequesRemaining();

      List<Card> hand = p.getCards();

      double avgDeck = 0;
      double avgSale = 0;

      int deckSize = cardsInDeck.size();

      // sort the cards in hands
      Collections.sort(hand);

      for (int i = 0; i < deckSize;i++) {
          avgDeck+= cardsInDeck.get(i)/deckSize;
      }

      for (int i = 0; i < 5; i++) {
          avgSale+= cardsInSale.get(i) / 5;
	  
          if (cardsInSale.get(i) == 15) {
              return hand.get(hand.size() - 1);
          }

          if (cardsInSale.get(i) == 0) {
              return hand.get(hand.size() / 2);
          }
      }

      if (avgSale > avgDeck) {
          return hand.get(hand.size() - 1);
      } else {
          return hand.get(0);
      }

  }
  
}
