import java.util.*;

/**
 * Class for representing passengers in Elevator.
 * @author Jayden Prakash - 4718680
 * @author Dray Ambros = 9742599
 */
public class Person {
    Random rand = new Random();
    public int currentFloor = rand.nextInt(10)+1;
    public int desFloor; 
    public int waitTime;
    public int travelTime;
    public boolean direction; // True if up, false if down

    public Person(int desFloor){
        this.desFloor = desFloor;
        // this.direction = direction;
        this.waitTime = 0;
        this.travelTime = 0;
    }

    public int getFloor(){
        return desFloor;
    }

    public int getWait(){
        return waitTime;
    }

    public int getTravel(){
        return travelTime;
    }

    public void addWait(int x){
        this.waitTime += x;
    }
    public void addTravel(int x){
        this.travelTime += x;
    }

}
