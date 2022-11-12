public class Point implements Comparable<Point> {
    private int x; 
    private int y;

    public Point (int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "["+ x+ ","+y+"]";
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int compareTo(Point p){
        if(this.x == p.getX() ){
            return this.y - p.getY();
        }
        return this.x - p.getX();
    }
    public boolean equals(Point p){
        if(this.x == p.getX() && this.y == p.getY()){
            return true;
        }
        return false;
    }
}