package forsale.strategies;
import forsale.*;

import java.lang.Math;
import java.util.*;
public class StrategryDiff implements Strategy {
    Helper help;
    public StrategryDiff(){
        help = new Helper();
    }


    public int bid(PlayerRecord p, AuctionState a){
        double diff = help.differnce(a.getCardsInAuction());
        double sd = help.sd(a.getCardsInAuction());
        int money = p.getCash();
        int cb = a.getCurrentBid();

        if( cb < 7){
            if(money > 10){
                if(diff > 3){
                    return 1;
                }
            }

            else if(money > 7){
                if(diff > 4){
                    return 1;
                }
            }

            else if(money > 4){
                if(diff> 5){
                    return 1;
                }

            }   
            else{
                if(diff> 6){
                    return 1;
                }
            }
        }
        return 0;

    }
    //bid our best card if I know I can Win
    public Card chooseCard(PlayerRecord p, SaleState s){
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
