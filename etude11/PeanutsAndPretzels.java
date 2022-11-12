/**
* Etude11 Peanuts and Pretzels
* Filename: PeanutsAndPretzels.java
* COSC326 Semester 1
* 23rd March 2021
* @author Dray Ambrose 9742599
* @author Jayden Prakash 4718680
* @author Sean Cartman 3157705
* @author Susie Tay 5717090
*/

import java.util.*;
import java.util.Map.Entry;


public class PeanutsAndPretzels {

   //public static int[][] max = new int[1000][1000];
   

   //an arrayList of all the root nodes of every treen generated
   public static ArrayList<TreeNode> rootNodes = new ArrayList<TreeNode>();

   //an arraylist of all of the rules generated
   //the rules themselves are stored in hashmaps for the good search time
   public static ArrayList<TreeMap<Integer,PeanutValue>> combinations = new ArrayList<TreeMap<Integer,PeanutValue>>();

   /**
	* Method too get input from standard in
	*/
   public static void getInput(){
	   Scanner scan = new Scanner(System.in);
	   
	   
	   boolean  isReadingRules = false;
	   
	   //add a new item to the combiations ArrayList, for the first rule list to be read into
	   combinations.add(new TreeMap<Integer,PeanutValue>());
	   
	   while(scan.hasNextLine()){
		   String line = scan.nextLine();
		   
		   if(line.equals("")){// empty line means we need to read in annother start and rule set
			   combinations.add(new TreeMap<Integer,PeanutValue>());
			   
			   //we are no longer reading rules as we have to read in a new start postition
			   isReadingRules = ! isReadingRules;

		   }
		   else{
			   //check if we are reading in rules
			   if(isReadingRules){
				   //call read rules using the last hash map in combiantions (the rule set of the last read in start postion)
				   readRules(line,combinations.get(combinations.size()-1));
				   
			   }
			   else{//if we didn't read a rule we just read a start position
				   
				   readPP(line);
				   
				   //we should expect to read a rule next time
				   isReadingRules = ! isReadingRules;


				   //add the default rules ( 0 1 and 1 0)
				   //done here so it is done first and won't overwrite using map.put
				   Integer a = new Integer(1);
				   Integer b = new Integer(0);

				   combinations.get(combinations.size()-1).put(a,new PeanutValue(1));
				   combinations.get(combinations.size()-1).get(a).add_pretzels(new Integer("0"));

				   combinations.get(combinations.size()-1).put(b,new PeanutValue(0));
				   combinations.get(combinations.size()-1).get(b).add_pretzels(new Integer("1"));
			   }
		   }
	   }
   }
   /**
	* Method to read in peanut rules
	* Line : the last line from stdin
	* m : the last generdate ruleset 
	*/
   public static void readRules(String line,TreeMap<Integer,PeanutValue> m){
	   //split the read in line up by white space
	   String[] bowl = line.split("\\s+");
	   
	   try {
		   
		   //convert the peanut & preteral values from input to numbers
		   //use substring as the first item is the opperator
		   //create an Interger object as we want to use them a map keys
		   Integer peanutValue = new Integer(bowl[0].substring(1));
		   Integer pretzealValue = new Integer(bowl[1].substring(1));
		   
		   //get the opperators as we know the first item will be a char
		   char peanutOpperator = bowl[0].charAt(0);
		   char pretzeaOpperator = bowl[1].charAt(0);

	   
		   //check if our map has an item with that peanut value if so
		   // pass it into addpretzal rule
		   // if not create it and 
		   // what the peanut value is will depend of the value give in Peanut value and opperator
		   if(peanutOpperator == '='){
			   
			   PeanutValue f = m.get(peanutValue);
			   if(f == null){
				   f = new PeanutValue(peanutValue);
				   m.put(peanutValue,f);
			   }
			   addPretzalRules(f, pretzealValue, pretzeaOpperator);
		   }

		   else if(peanutOpperator == '<'){
			   
			   for(int i = peanutValue.intValue()-1; i >= 0; i--){
				   
				   Integer x = new Integer(i);
				   PeanutValue f = m.get(x);
				   if(f == null){
					   f = new PeanutValue(x);
					   m.put(x,f);
				   }
				   addPretzalRules(f, pretzealValue, pretzeaOpperator);
			   }
		   }
		   else{
			   for(int i = peanutValue.intValue()+1; i < 1000 && i < rootNodes.get(rootNodes.size()-1).getPeanut()+1; i++){
				   Integer x = new Integer(i);
				   PeanutValue f = m.get(x);
				   if(f == null){
					   f = new PeanutValue(x);
					   m.put(x,f);
				   }
				   
				   addPretzalRules(f, pretzealValue, pretzeaOpperator);
			   }
		   }
		   
	   } catch (Exception e) {
		   System.err.println("failed to read rule : "+line);
	   }
	   
   }

   public static void addPretzalRules(PeanutValue p, Integer value, char opperator ){
	   //Pretzal values are stored in sets don't need to worry about overwriting 
	   if(opperator == '='){
		   p.add_pretzels(value);
	   }
	   
	   else if(opperator == '<'){
		   for(int i = value.intValue()-1; i >= -1; i--){
			   p.add_pretzels(new Integer(i));
		   }
	   }
	   else{
		   
		   for(int i = value.intValue()+1; i < 1000 && i < rootNodes.get(rootNodes.size()-1).getPretzeals()+1; i++){
			   p.add_pretzels(new Integer(i));
		   }
	   }
   }


   //easy to get the starting node just use scanners to get the first 2 ints in the line
	public static void readPP(String line){
		Scanner scan = new Scanner(line);
		try {
			int a = scan.nextInt();
			int b = scan.nextInt();
			rootNodes.add(new TreeNode(a, b));

		} catch (Exception e) {
			System.err.println("failed too add : "+ line);
		}
	}
	public static void printItems (TreeMap<Integer,PeanutValue> peanutValues){
		for(Entry<Integer,PeanutValue> i: peanutValues.entrySet()){
			System.out.println(i);
		}
		System.out.println("\n ***********\n");

	}

    public static void main (String[] args) {
		getInput();

		for (int i =0; i < rootNodes.size(); i++) {
			//printItems(combinations.get(i));
			TreeNode top = rootNodes.get(i);
			top.resetMemo();
			top.generateChildren(combinations.get(i));
			System.out.println(top.getMove(combinations.get(i)));

		}
	}
}
