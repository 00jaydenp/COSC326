package etude02;
/**
 * Etude02 WordChains
 * Filename: WordChains.java
 * COSC326 Semester 1
 * 19th March 2021
 * @author Dray Ambrose 9742599
 * @author Jayden Prakash 4718680 
*/

import java.util.*;

public class WordChains {
  private static HashMap<String, ArrayList<String>> adjacentWords = new HashMap<>();
  private static HashSet<String> dictionary = new HashSet<String>();
  private static ArrayList<String> problems = new ArrayList<>();

  public static void main(String[] args) {
    getInput();
    /**
     * Block for dealing with input, determining whether it is fixed length or
     * shortest path version
     */

    for (String x : problems) {
      String first = "";
      String second = "";
      int steps = 0;
      boolean err = false;

      try {
        if (x.split(" ").length == 3) {
          steps = Integer.parseInt(x.split(" ")[2]);
        }
      } catch (NumberFormatException e) {
        err = true;
        System.out.println(x + " INVALID - Third token must be an integer");
      }

      try {
        first = x.split(" ")[0];
        second = x.split(" ")[1];
      } catch (ArrayIndexOutOfBoundsException e) {
        err = true;
        System.out.println(x + " INVALID - There must be at least two words in input");
      }

      if (!err) {
        if (steps == 0) {
          System.out.println(shortestPath(first, second));
        } else {
          Stack<String> s = new Stack<>();
          if (fixedPath(s, first, second, steps)) {
            for (int i = 0; i < s.size(); i++) {
              System.out.print(s.get(i) + " ");
            }
            System.out.println();
          } else {
            System.out.println(first + " " + second + " " + steps + " impossible");
          }
        }
      }
    }
  }

  /**
   * getInput method which gets the input from stdin and then puts input words
   * into dictionary, and an adjacentwords list which stores each word and the
   * adjacent words of each word.
   */
  private static void getInput() {
    Scanner input = new Scanner(System.in);
    while (input.hasNextLine()) {
      String prob = input.nextLine();
      if (prob.trim().equals("")) {
        break;
      }
      problems.add(prob);
    }

    while (input.hasNextLine()) {
      String word = input.nextLine();
      dictionary.add(word);
      adjacentWords.put(word, new ArrayList<String>());

    }
    input.close();
    for (String key : adjacentWords.keySet()) {
      for (int i = 0; i < key.length(); i++) {
        char[] keys = key.toCharArray();
        for (char c = 'a'; c <= 'z'; c++) {
          keys[i] = c;
          String word = String.valueOf(keys);
          if (dictionary.contains(word)) {
            adjacentWords.get(key).add(word);
          }
        }
      }
    }
  }

  /**
   * shortestPath method that calculates the shortest path between two words.
   * 
   * @param word1 the word you start from.
   * @param word2 the word that you need to get to.
   * @return String the result of the search.
   */
  public static String shortestPath(String word1, String word2) {
    HashMap<String, Boolean> usedWords = new HashMap<>();
    if (word1.equals(word2)) {
      return word1 + " " + word2;
    }

    ArrayList<Set<String>> adjacencies = new ArrayList<>();
    if (dictionary.contains(word1) && dictionary.contains(word2)) {
      if (adjacentWords.get(word1).size() == 0 || adjacentWords.get(word2).size() == 0) {
        return word1 + " " + word2 + " impossible";
      }
      adjacencies.add(new TreeSet<String>());
      adjacencies.get(0).add(word1);
      boolean found = false;
      int usedWordsCount = -1;

      while (!found) {
        if (usedWords.size() == usedWordsCount) {
          return word1 + " " + word2 + " impossible";
        } else {
          usedWordsCount = usedWords.size();
        }
        Set<String> nextLevel = new TreeSet<>(); // handles next level of the tree
        for (Set<String> level : adjacencies) {
          for (String w : level) {
            if (!usedWords.containsKey(w)) {
              usedWords.put(w, true);
              for (String k : adjacentWords.get(w)) {
                nextLevel.add(k);
              }
            }
          }
        }
        if (nextLevel.contains(word2)) {
          found = true;
          adjacencies.add(nextLevel);
        } else {
          adjacencies.add(nextLevel);
        }
      }

      ArrayList<String> wordChain = new ArrayList<>();
      String currentWord = word2;
      for (int i = adjacencies.size() - 2; i >= 0; i--) {
        wordChain.add(currentWord);
        for (String s : adjacencies.get(i)) {
          if (adjacentWords.get(s).contains(currentWord)) {
            currentWord = s;
            break;
          }
        }
      }
      wordChain.add(word1);
      String chain = "";
      for (int i = wordChain.size() - 1; i >= 0; i--) {
        chain += wordChain.get(i) + " ";
      }
      return chain;
    } else {
      return word1 + " " + word2 + " impossible";
    }
  }

  /**
   * fixedPath method that calculates word chains of a fixed length using
   * recursion.
   * 
   * @param s     stack to store the current path on
   * @param word1 word to start from
   * @param word2 word to get to
   * @param steps the length of the word chain
   * @return boolean false if chain is not found; true otherwise.
   */
  public static boolean fixedPath(Stack<String> s, String word1, String word2, int steps) {
    s.push(word1);
    if (word1.equals(word2)) {
      return true;
    }
    if (s.size() > steps - 1) {
      return false;
    }
    for (String w : adjacentWords.get(word1)) {
      if (!s.contains(w)) {
        if (fixedPath(s, w, word2, steps)) {
          return true;
        }
        s.pop();
      }
    }
    return false;
  }
}