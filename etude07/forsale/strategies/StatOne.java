package forsale.strategies;
import forsale.*;
import java.lang.Math;
import java.util.*;

public class StatOne implements Strategy {
    Helper help;
    public StatOne(){
        help = new Helper();
    }

    public int bid(PlayerRecord p, AuctionState a){
        //list<Card> ca = a.getCardsInAuction();
        double stand = help.sd(a.getCardsInAuction());
        int money = p.getCash();
        if(stand < 2){
            return 0;
        }
        else{
            return 1;
        }

    }
    public Card chooseCard(PlayerRecord p, SaleState s){
        TreeSet<Card> t = new TreeSet<Card>(p.getCards()); 
        double stand = help.sd1(s.getChequesAvailable());
        if(stand < 4){
            return t.pollFirst();
        }
        else{
            return t.pollLast();
        }
    }
}
