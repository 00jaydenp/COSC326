import java.util.*;

/**
 * Etude04 Filename: TentsAndTrees.java COSC326 Semester 1 7th May 2021
 * 
 * @author Dray Ambrose 9742599
 * @author Jayden Prakash 4718680
 * @author Sean Cartman 3157705
 * @author Susie Tay 5717090
 */

public class TentsAndTrees {
    private static ArrayList<ArrayList<ArrayList<Character>>> grid = new ArrayList<>(); // 2D grid representing
                                                                                        // game map
    private static LinkedList<LinkedList<Integer>> rows = new LinkedList<>();
    private static LinkedList<LinkedList<Integer>> cols = new LinkedList<>();
    private static LinkedList<Point> tentPositions = null;

    public static void main(String[] args) {
        getInput();

    }

    /**
     * Gets input from stdin and initialises arrays with values. Currently only
     * works for one iteration, will need to adjust so it works as long as there are
     * scenarios.
     */
    public static void getInput() {
        int treeNum = 0;
        Scanner line = new Scanner(System.in);
        int iteration = 0;
        boolean first = true;
        boolean row = true; // True if row array has not yet been set
        int count = 0;
        boolean error = false;
        grid.add(new ArrayList<ArrayList<Character>>());
        rows.add(new LinkedList<Integer>());
        cols.add(new LinkedList<Integer>());
        while (line.hasNextLine()) {
            
            String input = line.nextLine();
            if (input.equals("")) {
                input = line.nextLine();
                grid.add(new ArrayList<ArrayList<Character>>());
                rows.add(new LinkedList<Integer>());
                cols.add(new LinkedList<Integer>());
                if(error == false){
                    solver(iteration);
                }
                error = false;
                iteration++;
                count = 0;
                first = true;
                row = true;

            }

            Scanner token = new Scanner(input); // Used specifically to get the row/col values
            int len = input.length();
            /**
             * Used in order to initialise the grid with the right amount of row/columns
             */
            if (first) {
                for (int i = 0; i <= len - 1; i++) {
                    grid.get(iteration).add(new ArrayList<Character>());
                }
                first = false;
            }
            /**
             * Executes if input is now numbers. On first run through, will add to rows
             * array, on second will add to columns array.
             */
            if (Character.isDigit(input.charAt(0))) {
                while (token.hasNextInt()) {
                    if (row) {
                        rows.get(iteration).push(token.nextInt());
                    } else {
                        cols.get(iteration).add(token.nextInt());
                    }
                }
                row = false;
                /** sets up the grid */
            } else {

                for (int i = 0; i < len; i++) {
                    try{
                    grid.get(iteration).get(count).add(input.charAt(i));
                    }catch(IndexOutOfBoundsException e){
                        System.out.println("BAD FORMAT: Ensure rows and columns match the row/column counts");
                        System.out.println();
                        error = true;
                        break;
                    }
                }
                count++;
            }
            if (!line.hasNextLine()) {
                solver(iteration);
            }
        }
        line.close();

    }

    /**
     * Turns all tiles non-adjacent to a tree into an X.
     * 
     * @param iteration the number puzzle we are currently on.
     */
    public static void nonAdjacent(int iteration) {
        for (int row = 0; row <= grid.get(iteration).size() - 1; row++) {
            for (int col = 0; col <= grid.get(iteration).get(row).size() - 1; col++) {
                try {
                    if (grid.get(iteration).get(row).get(col) == 'T') {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {
                    if (grid.get(iteration).get(row).get(col + 1) == 'T') {
                        continue;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
                try {
                    if (grid.get(iteration).get(row + 1).get(col) == 'T') {
                        continue;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
                try {
                    if (grid.get(iteration).get(row - 1).get(col) == 'T') {
                        continue;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
                try {
                    if (grid.get(iteration).get(row).get(col - 1) == 'T') {
                        continue;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
                if (grid.get(iteration).get(row).get(col) == '.') {
                    grid.get(iteration).get(row).set(col, 'X');
                }
            }
        }
    }

    /**
     * Turns all empty spaces in a column/row with count 0 into X.
     * 
     * @param iteration the number puzzle we are on.
     */
    public static void zeroLine(int iteration) {
        int numRows = rows.get(iteration).size();
        for (int i = 0; i < rows.get(iteration).size(); i++) {
            if (rows.get(iteration).get(i) == 0) {
                Collections.replaceAll(grid.get(iteration).get(i), '.', 'X');
            }
            if (cols.get(iteration).get(i) == 0) {
                int count = 0;
                while (count < numRows) {
                    if (grid.get(iteration).get(count).get(i) == '.') {
                        grid.get(iteration).get(count).set(i, 'X');
                    }
                    count++;
                }
            }
        }
    }

    /**
     * Prints out solution for current puzzle.
     * 
     * @param iteration the number puzzle we are on.
     */
    public static void printSolution(int iteration) {
        for (int row = 0; row < grid.get(iteration).size(); row++) {
            for (int col = 0; col < grid.get(iteration).get(row).size(); col++) {
                System.out.print(grid.get(iteration).get(row).get(col));

            }
            System.out.println();
        }
        System.out.println();

    }

    /**
     * Calls all methods needed to solve puzzle.
     * 
     * @param iteration number puzzle we are on.
     */
    public static void solver(int iteration) {
        tentPositions = null;
        try{
        getTrees(iteration);
        nonAdjacent(iteration);
        zeroLine(iteration);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("BAD FORMAT: Ensure rows and columns match the row/column counts");
            System.out.println();
            return;
        }
        Point[] trees = getTrees(iteration);
        LinkedList<Point> tents = new LinkedList<Point>();
        matchTentsAndTree(tents, 0, trees, iteration);
        if(tentPositions != null){
        for (int i = 0; i < tentPositions.size(); i++) {
            int x = tentPositions.get(i).getX();
            int y = tentPositions.get(i).getY();
            grid.get(iteration).get(x).set(y, 'C');
        }
        for (int i = 0; i < grid.get(iteration).size(); i++) {
            Collections.replaceAll(grid.get(iteration).get(i), 'X', '.');
        }
        printSolution(iteration);
    }else{
        System.out.println("No Solution");
    }

    }

    /**
     * Gets the position of trees and returns them in an array.
     * 
     * @param iteration number puzzle we are on.
     * @return array of points holding positional data on trees.
     */
    public static Point[] getTrees(int iteration) {
        int tcount = 0;
        Point[] trees = new Point[reqTrees(iteration)];
        for (int row1 = 0; row1 < grid.get(iteration).size(); row1++) {
            for (int col = 0; col < grid.get(iteration).get(row1).size(); col++) {
                if (grid.get(iteration).get(row1).get(col) == 'T') {
                    trees[tcount] = new Point(row1, col);
                    tcount++;
                }
            }
        }
        // System.out.println((int) trees[1].getX() + " " + (int) trees[1].getY());
        return trees;
    }

    /**
     * Counts the number of required tents in a game
     * 
     * @param iteration the number puzzle we are on
     * @return int the number of tents required in a game
     */
    public static int reqTrees(int iteration) {
        int trees = 0;
        for (int x = 0; x < rows.get(iteration).size(); x++) {
            trees += rows.get(iteration).get(x);
        }
        // System.out.println(trees);
        return trees;
    }

    /**
     * Checks to see whether placing a tent in a specific spot is a valid move.
     * 
     * @param p     the point to check
     * @param tents linked list holding all positional data on currently placed
     *              tents
     * @param int   iteration the number puzzle we are on.
     */
    public static boolean valid(Point p, LinkedList<Point> tents, int iteration) {
        int x = (int) p.getX();
        int y = (int) p.getY();
        int cmpY = grid.get(iteration).get(0).size();
        int cmpX = grid.get(iteration).size();
        if (x < 0 || y < 0 || y >= cmpY  || x >= cmpX) {
            return false;
        }

        if (grid.get(iteration).get(x).get(y) == 'X' || grid.get(iteration).get(x).get(y) == 'T') {
            return false;
        }

        // System.out.println(x + " " + y);

        

        // // System.out.println(p.getX() + " " + p.getY());
        
        
        Point uPoint = new Point(x-1, y);
        // System.out.println(uPoint.getX() + " " + uPoint.getY());
        Point dPoint = new Point(x + 1, y);
        Point lPoint = new Point(x, y - 1);
        Point rPoint = new Point(x, y + 1);
        Point uLPoint = new Point(x - 1, y - 1);
        Point uRPoint = new Point(x - 1, y + 1);
        Point lLPoint = new Point(x + 1, y - 1);
        Point lRPoint = new Point(x + 1, y + 1);
        for(Point t : tents){
            if(t.equals(p) || t.equals(uPoint) || t.equals(dPoint) || t.equals(lPoint) || t.equals(rPoint) ||
            t.equals(uLPoint) || t.equals(uRPoint) || t.equals(lLPoint) || t.equals(lRPoint)){
                return false;
            }
        }
        // Checks to see that no tents are adjacent orthogonally or diagonally
        if (tents.contains(uPoint) || tents.contains(dPoint) ||
        tents.contains(lPoint) || tents.contains(rPoint) ){
        return false;
        }
        
        int rowCount = rows.get(iteration).get(x);
        int colCount = cols.get(iteration).get(y);
        // System.out.println("Row: " +  rowCount + " Col: " + colCount);
        int tentRowCount = 0;
        int tentColCount = 0;
        for(Point t: tents){
            int tx = (int) t.getX();
            int ty = (int) t.getY();
            if(tx ==x){
                tentRowCount = tentRowCount + 1;
            }
            if(ty == y){
                tentColCount = tentColCount +1;
            }
        }
        /** If you change this to  > from == it will give you solutions, and the first one is 
         * right except for the last two lines where the number of tents exceed the number allowed
         */
        
        if (tentRowCount >= rowCount || tentColCount >= colCount) { // If the amount of tents in a row or colum exceeds
                                                                    // the allowed amount
            return false;
        }

        return true;
    }

    /**
     * Recursive Method to find the positions of tents in the puzzle.
     * 
     * @param tents     linked list that holds position of tents
     * @param depth     depth of recursion
     * @param trees     array of points that hold position of trees
     * @param iteration what puzzle we are currently one
     */
    public static void matchTentsAndTree(LinkedList<Point> tents, int depth, Point[] trees, int iteration) {

        if (tents.size() == trees.length) {
            // System.out.println(count);
            // System.out.println(tents.size());
            tentPositions = tents;
            return;
        }
        //System.out.println(tents.size() + " out of" + trees.length);
        //System.out.println(tents);
        Point tree = trees[depth];

        Point upTent = new Point(tree.getX() - 1,  tree.getY());
        Point downTent = new Point( tree.getX() + 1, tree.getY());
        Point leftTent = new Point( tree.getX(),  tree.getY() - 1);
        Point rightTent = new Point( tree.getX(), tree.getY() + 1);

        if (valid(upTent, tents, iteration)) {
            LinkedList<Point> newTents = new LinkedList<Point>(tents);
            newTents.add(upTent);
            matchTentsAndTree(newTents, depth + 1, trees, iteration);
        }
        if (valid(downTent, tents, iteration)) {
            LinkedList<Point> newTents = new LinkedList<Point>(tents);
            newTents.add(downTent);
            matchTentsAndTree(newTents, depth + 1, trees, iteration);
        }
        if (valid(leftTent, tents, iteration)) {
            LinkedList<Point> newTents = new LinkedList<Point>(tents);
            newTents.add(leftTent);
            matchTentsAndTree(newTents, depth + 1, trees, iteration);
        }
        if (valid(rightTent, tents, iteration)) {
            LinkedList<Point> newTents = new LinkedList<Point>(tents);
            newTents.add(rightTent);
            matchTentsAndTree(newTents, depth + 1, trees, iteration);
        }
        return;
    }
}
