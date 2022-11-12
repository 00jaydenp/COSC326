/**
 * Class to store possible values a pretzels can take on given a peanut
 */
import java.util.*;

public class PeanutValue {
    private int peanut;
    private TreeSet<Integer> pretzels;
    private ArrayList<Integer> a;

    public PeanutValue(int p){
        peanut = p;
        pretzels = new TreeSet<Integer>();
    }
    
    public int getPeanut() {
        return peanut;
    }
    
    public ArrayList<Integer> getPretzels(){
        if (a == null){
            a = new ArrayList<Integer>(pretzels.descendingSet());
        }
        return a;
    }

    public void add_pretzels(Integer i){
        pretzels.add(i);
    }
    public String toString(){
        String s= ""+peanut;
        s +=" : ";
        for(Integer i: pretzels){
            s += i;
            s += " ";
        }
        return s;
    }

}
