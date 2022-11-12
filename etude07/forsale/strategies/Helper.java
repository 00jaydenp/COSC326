package forsale.strategies;
import forsale.*;
import java.util.*;
import java.lang.Math;
public class Helper{
    public double sd(List<Card> cards){
        int sum = 0;
        for(Card c :cards){
            sum += c.getQuality();
        }
        int mean = sum/cards.size();
        sum =0;
        for(Card c :cards){
            sum += Math.pow(c.getQuality()-mean, 2);
        }
        mean = sum/(cards.size()-1);
        double dev =Math.sqrt(mean);
        return dev;
    
    }
    public double sd1(List<Integer> cards){
        int sum = 0;
        for(Integer c :cards){
            sum += c;
        }
        int mean = sum/cards.size();
        sum =0;
        for(Integer c :cards){
            sum += Math.pow(c-mean, 2);
        }
        mean = sum/(cards.size()-1);
        double dev =Math.sqrt(mean);
        return dev;
    
    }

    public double differnce(List<Card> cards){
        int max = 0;
        int min = 100;
        for(Card c :cards){
            int i= c.getQuality();
            if( i > max){
                max = i;
            }
            if( i < min){
                min = i;
            }
        }
        return max - min;
       
        
    }
    public double differnce1(List<Integer> cards){
        int max = 0;
        int min = 100;
        for(Integer c :cards){
            int i= c;
            if( i > max){
                max = i;
            }
            if( i < min){
                min = i;
            }
        }
        return max - min;
        
    }
    
}
