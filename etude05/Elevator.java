import java.util.*;

/**
 * Elevator simulator for Etude05.
 * 
 * @author Jayden Prakash - 4718680
 * @author Dray Ambrose - 9742599
 */
public class Elevator {
    public static final int MAX_PASSENGERS = 4;
    public static final int NUM_FLOORS = 10;
    public static final int DOOR_TIME = 5;
    public static final int TRAVEL_TIME = 10;
    public static LinkedList<Person> passengers = new LinkedList<Person>();
    public static int currFloor = 1;
    // public static int totalTime = 0;
    // public static int waitTime = 0;
    public static LinkedList<Person> passengersWaiting = new LinkedList<Person>();
    public static LinkedList<Person> passengersArrived = new LinkedList<Person>();

    public static void main(String[] args) {
        longestQueue(5);
        longestQueue(15);
        longestQueue(30);
    }


    /**
     * Calculates the difference between two floors.
     * @param a floor number 
     * @param b floor number    
     * @return the difference between the two floors
     */
    public static int floorDiff(int a, int b){
        if(a>b){
            return a - b;
        }
        else{
            return b - a;
        }
    }

    /**
     * Strategy that prioritises people that have been in the elevator the longest.
     * Goes towards first button pressed and drops off/picks up people on the way
     * @param n the number of passengers queueing for the elevator.
     */
    public static void longestQueue(int n) {
        int destination = 0;
        loadPassengers(n);
        boolean done = true;
        while (done) {
            if (passengersWaiting.isEmpty() && passengers.isEmpty()) { 
                done = false;
            }
            LinkedList<Person> pWaitingCopy = new LinkedList<Person>(passengersWaiting);
            LinkedList<Person> pCopy = new LinkedList<Person>(passengers);
            boolean door = false;
            for (Person p : passengers) {
                if (currFloor == p.desFloor) {
                    pCopy.remove(p);
                    passengersArrived.add(p);
                    // System.out.println("Passenger getting off at floor " + currFloor);
                    door = true;

                }
            }
            passengers = pCopy;
            for (Person p : passengersWaiting) {
                if (passengers.size() == 4) {
                    break;
                }
                if (p.currentFloor == currFloor) {
                    // System.out.println("Passenger getting on at floor " + currFloor);
                    pWaitingCopy.remove(p);
                    passengers.add(p);
                    door = true;
                }
            }
            passengersWaiting = pWaitingCopy;
            if (door == true) {
                addDoorTimes();
            }
            if(!passengers.isEmpty()){
            destination = passengers.get(0).desFloor; // Destination of first passenger
            }else if(!passengersWaiting.isEmpty()){
                destination = passengersWaiting.get(0).currentFloor;
            }
            if (currFloor < destination) {
                currFloor++;
                addTravelTimes();
            } else if(currFloor > destination) {
                currFloor--;
                addTravelTimes();
            }

        }
        currFloor = 1;
        printTimes(n);
    }

    /**
     * Collective Control Strategy. Go to the top floor, then down to the bottom
     * floor picking up and dropping off passengers as needed on the way, until
     * there are no passengers left waiting.
     * 
     * @param n number of passengers
     */
    public static void collectiveControl(int n) {
        loadPassengers(n);
        boolean top = false;

        while (true) {
            // System.out.println("Looping");
            if (passengersWaiting.isEmpty() && passengers.isEmpty()) {
                break;
            }
            LinkedList<Person> pWaitingCopy = new LinkedList<Person>(passengersWaiting);
            LinkedList<Person> pCopy = new LinkedList<Person>(passengers);
            boolean door = false;
            for (Person p : passengers) {
                if (currFloor == p.desFloor) {
                    pCopy.remove(p);
                    passengersArrived.add(p);
                    // System.out.println("Passenger getting off at floor " + currFloor);
                    door = true;

                }
            }
            passengers = pCopy;
            for (Person p : passengersWaiting) {
                if (passengers.size() == 4) {
                    break;
                }
                if (p.currentFloor == currFloor) {
                    // System.out.println("Passenger getting on at floor " + currFloor);
                    pWaitingCopy.remove(p);
                    passengers.add(p);
                    door = true;
                }
            }
            
            passengersWaiting = pWaitingCopy;
            if (door == true) {
                addDoorTimes();
            }
            if (currFloor < NUM_FLOORS && top == false) {
                currFloor++;
                addTravelTimes();
                if (currFloor == NUM_FLOORS) {
                    top = true;
                }
            } else {
                currFloor--;
                addTravelTimes();
                if (currFloor == 1) {
                    top = false;
                }
            }

        }
        currFloor = 1;
        printTimes(n);
    }

    public static void printTimes(int n) {
        if(passengersArrived.size() == n){
        System.out.println("Average Elevator Travel Time for " + n + " Passengers: " + averageTravel() + "s");
        System.out.println("Average Wait Time for " + n + " Passengers: " + averageWait() + "s");
        }
        System.out.println();
        passengersArrived.clear();
    }

    /**
     * Returns the average time spent waiting for the elevator for all passengers.
     * 
     * @return int average wait time of all passengers.
     */
    public static int averageWait() {
        int avg = 0;
        int total = 0;
        for (Person p : passengersArrived) {
            total += p.getWait();
        }
        avg = total / passengersArrived.size();

        return avg;
    }

    /**
     * Returns the average travel time spent in the elevator..
     * 
     * @return int average travel time for all passengers.
     */
    public static int averageTravel() {
        int avg = 0;
        int total = 0;
        for (Person p : passengersArrived) {
            total += p.getTravel();
        }
        avg = total / passengersArrived.size();

        return avg;
    }

    /**
     * Adds the door time to respective time fields. For all passengers waiting for
     * the elevator will add to the wait time. For all passengers currently in the
     * elevator will add to the travel time.s
     */
    public static void addDoorTimes() {
        for (Person p : passengers) {
            p.addTravel(DOOR_TIME);
        }
        for (Person p : passengersWaiting) {
            p.addWait(DOOR_TIME);
        }
    }

    /**
     * Adds the travel time to respective time fields. For all passengers waiting
     * for the elevator will add to the wait time. For all passengers currently in
     * the elevator will add to the travel time.
     */
    public static void addTravelTimes() {
        for (Person p : passengersWaiting) {
            p.addWait(TRAVEL_TIME);
        }
        for (Person p : passengers) {
            p.addTravel(TRAVEL_TIME);
        }
    }

    /**
     * Loads passengers into the elevator and randomizes the floor they want to go
     * to.
     * 
     * @param n number of passengers.
     */
    public static void loadPassengers(int n) {
        Random rand = new Random();
        int count = 0;
        while (count < n) {
            passengersWaiting.add(new Person(rand.nextInt(10) + 1));
            count++;
        }
    }

}