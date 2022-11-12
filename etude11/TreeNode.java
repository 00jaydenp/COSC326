
import java.util.*;
import java.util.Map.Entry;

/**
 * Class to generate a Tree for Minimax to be called on
 */
public class TreeNode {
    
    

    //if this is a winning move (1) or losing(-1) 
    private int scoreValue =0;

    //my parent in the tree
    private TreeNode parent;

    //my children
    private HashSet<TreeNode> children = new HashSet<TreeNode>();
    
    //peatnut sum at this node is located in position one while pretzeal sum is locationed in position 2
    private int peanutValue;
    
    private int pretzelValue;
    
    //store nodes in an array that have all ready been visited
    public static TreeNode[][] memosied;


    private boolean hasChildren;

    /**
     * Constructer to genertate the root node
     * @param peanut the number of peanuts at the root node
     * @param pretzels the number of pretzels at the root node
     * 
     */


    public static void resetMemo(){
        memosied = new TreeNode[1001][1001];
    }

    public int getPretzeals(){
        return pretzelValue;
    }

    public int getPeanut(){
        return peanutValue;
    }

    public boolean shouldGen(){
        if(!hasChildren && scoreValue == 0){
            return true;
        }
        return false;
    }

    public HashSet<TreeNode> getChildren(){
        return children;
    }

    public TreeNode(int peanut, int pretzels) {
        peanutValue = peanut;
        pretzelValue = pretzels;
        if(peanut ==0  && pretzels == 0){
            scoreValue = 1;
        }
        memosied = new TreeNode[1001][1001];
        memosied[peanut][pretzels] = this;

    }

    public TreeNode(int peanut, int pretzels, TreeNode inParent){
        parent = inParent;
        peanutValue = peanut;
        pretzelValue = pretzels;
        memosied[peanut][pretzels] = this;
        if(peanut ==0  && pretzels == 0){
            scoreValue = 1;
        }
    }
    public void setChildren(HashSet<TreeNode> c){
        children = c;
    }
    
    /**
     * Generate a tree to dertimind if this tree node is a wining node
     * we don't need keep track of our children we only care about our values
     * steps to speed up
     * memoisation -> don't generate nodes with all ready generated peant and pretzaks values and point to the existing nodes
     * don't generate nodes if we can determind our parent's nodes value( i.e our child value is one)
     * Sort our Peanut and pretzals values so we check the biggest first. 
     * This works because if there are lots of rules leading to a large branching factor for our tree
     * There will porbally be a vaild path so it is found fast and we don't need to generate a large tree 
     * Generate using recursion as it's fast and easier to understand
     * @param peanutValues
     */
    public void generateChildren(TreeMap<Integer,PeanutValue> peanutValues){
        hasChildren = true;
        boolean breaker = false;
        boolean set = true;
        for(Entry<Integer,PeanutValue> i: peanutValues.descendingMap().entrySet()){
            Integer x = peanutValue - i.getValue().getPeanut();
           
            for (Integer j :i.getValue().getPretzels()) {
                Integer y = pretzelValue - j;
                //if this combaion of peanuts and pretzelss is not less than 0
                if (x >= 0 && y >= 0) {
                    //check if there is a memosied version
                    
                    if(memosied[x][y] == null){
                        TreeNode a = new TreeNode(x,y, this);
                        if(a.getScoreValue() == 0){
                            a.generateChildren(peanutValues);
                        }
                    }
                    children.add(memosied[x][y]);
                    //System.out.println(memosied[x][y]);
                    if(memosied[x][y].getScoreValue() == 1){
                        set = false;
                        breaker = true;
                        break;
                    }
                        
                }
            }
            if(breaker){
                break;
            }
        }
        if(set){
            scoreValue = 1;
        }
        else{
            scoreValue = -1;
        }
        //System.out.println(peanutValue +"  "+pretzelValue+" "+scoreValue);
    }
    

    //like generate tree but just generates the first layer and returns the move to make 
    public String getMove(TreeMap<Integer,PeanutValue> peanutValues){
        hasChildren = true;
        
        for(Entry<Integer,PeanutValue> i: peanutValues.descendingMap().entrySet()){
            Integer x = peanutValue - i.getValue().getPeanut();
           
            for (Integer j :i.getValue().getPretzels()) {
                Integer y = pretzelValue - j;
                if (x >= 0 && y >= 0) {
                    if(memosied[x][y].getScoreValue() == 1){
                        return i.getValue().getPeanut() + " " + j;
                    }
                }
            }
        }
        return "0 0";
    }
    
    public int getScoreValue() {
        return scoreValue;
    }
    
    public void setScoreValue(int i) {
        scoreValue = i;
    }
    
    public String toString() {
        return peanutValue+ " " + pretzelValue + " : "+scoreValue;
    }

    public int pretzeal(){
        return pretzelValue;
    }
}
