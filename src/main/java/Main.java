/*                            - General Info -
File: GenEquipCode
Description: To simulates all possible equipment codes in WoM
Date: 07/25/25-08/10/25
Author: LeviSeven
*/
/*
 * = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * - Globals -
 * = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 */

/*
 * - Import Statements -
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

  /*
   * - Controllers -
   */

  // Wether to print the list in full, default 0
  private static final byte printAllListElements = 0;
  // Size of the permutation model array, default 5
  private static final int PERM_SIZE = 5;

  // Wether to test a particular quality, default 0
  private static final byte testParticularQuality = 0;
  // The quality to be tested
  private static final int SPEC_QUAL = 60;

  // Names of the output files
  private static final String PATH = "output/",
      CODES_FILE = PATH + "codes.txt", STAT_FILE = PATH + "stat.csv";

  /*
   * - Executives Variables -
   */

  // * - Equipment Codes - *

  // Models of all possible permutations
  private static List<char[]> permuts = new ArrayList<char[]>();
  // Basic rules for all rarities
  private static Setup[] setups = new Setup[4];

  // Statistics during the generation
  private static int[] numCodes = new int[90], numSumCombs = new int[90];

  // * - Boolean Converters - *

  private static final boolean SHOW_FULL_LIST = printAllListElements == 1;
  private static final boolean TEST_QUALITY = testParticularQuality == 1;

  /*
   * = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
   * - Main -
   * = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
   */

  public static void main(String[] args) {

    /*
     * - Initialization -
     */

    // * - Debug Prompts - *

    // Prints the controllers
    System.out.println("SHOW_FULL_LIST = " + SHOW_FULL_LIST);
    System.out.print("TEST_QUALITY = " + TEST_QUALITY);
    System.out.println(TEST_QUALITY ? ", SPEC_QUAL = " + SPEC_QUAL : "");
    System.out.println();

    // * - Permutation Model - *

    // Initializes the model
    char[] permModel = new char[PERM_SIZE];
    for (int i = 0; i < permModel.length; i++)
      permModel[i] = (char) ('A' + i);

    // Gets all permutations
    permuts = getPermut(permModel);
    System.out.println(permuts.size() +
        " permutations recorded as the model.");
    // Displays all permutations
    if (SHOW_FULL_LIST) {
      for (char[] permut : permuts) {
        String msg = "";
        for (char ch : permut)
          msg += ch;
        System.out.print(msg + " ");
      } // End of for loop
      System.out.println();
    } // End of if

    // * - File Inits - *

    // Inits stat arrays
    for (int i = 0; i < numCodes.length; i++) {
      numCodes[i] = 0;
      numSumCombs[i] = 0;
    } // End of for loop

    // Overrides the files
    try {
      FileWriter codesFile = new FileWriter(CODES_FILE),
          statFile = new FileWriter(STAT_FILE);

      codesFile.write("");
      // Inits stat file with the header
      statFile.write("qual, numSumCombs, numCodes" + "\n");

      codesFile.close();
      statFile.close();
      System.out.println("Files initialized.");
    } catch (IOException e) {
      printExcept(e);
    } // End of try-catch

    // * - Rarity Setups - *

    startNewSection();
    System.out.println("Rarities" + "\n");
    System.out.println("Basic Setups for All Rarities - " + "\n");
    // Assigns the rarity setups
    int cnt = 0;
    for (Rarity rarity : Rarity.values()) {
      setups[cnt] = rarity.getSetup();
      System.out.print(setups[cnt]);
      cnt++;
    } // End of for loop

    /*
     * - Quality Loop -
     */

    int rar = 0;
    // Loops through all qualities
    for (int qual = 10; qual <= 99; qual++) {

      // * - Loop Initialization - *

      // Locates current rarity
      if (qual > setups[rar].getMaxQual())
        rar++;
      // Skips to the specified quality during the test
      if (TEST_QUALITY && qual != SPEC_QUAL)
        continue;
      // Separates the section for every 5% qualities
      if (qual % 5 == 0) {
        startNewSection();
        System.out.println();
      } // End of if

      // Assigns property variables
      int[] propAmt = setups[rar].getPropAmtRange();
      System.out.println("\t - " + qual + "% - \n");
      // Inits the output strings
      String codeLine = "", statRow = "";

      /*
       * - Loop Assigning -
       */

      // Loops for the amount variations
      for (int amt = propAmt[0]; amt <= propAmt[1]; amt++) {
        // Inits list of the amt loop
        TreeSet<Equip> codesPerAmt = new TreeSet<Equip>();

        // * - Sum Combination - *

        // Assigns calculation for all sum combinations
        List<int[]> sumCombs = getSumComb(setups[rar], amt, qual);
        // Scores the number
        numSumCombs[qual - 10] += sumCombs.size();
        System.out.println(sumCombs.size() +
            " sum combinations found with " + amt + " properties.");
        // Records the number of sum combinations

        // Displays the list
        if (SHOW_FULL_LIST)
          for (int[] comb : sumCombs)
            System.out.print(Arrays.toString(comb) + "\n");

        // * - Convertion - *

        // Assigns permutation for every combination results
        for (int[] comb : sumCombs) {
          for (char[] perm : permuts) {

            int[] properties = new int[PERM_SIZE];
            // Plugs in properties into the permutation
            for (int i = 0; i < properties.length; i++)
              properties[i] = comb[(int) perm[i] - 65];

            // Skips the code for the exclusives being the one in 2 properties
            if (amt == 2 && properties[4] != 0)
              continue;
            // Records the equipment code without duplicates
            codesPerAmt.add(new Equip(properties));
          } // End of perm for loop
        } // End of comb for loop

        /*
         * - Recording -
         */

        System.out.println(codesPerAmt.size() + " codes found with " +
            amt + " properties.");

        // * - Quality Amounts - *

        // Prints and records the list
        for (Equip code : codesPerAmt) {
          if (SHOW_FULL_LIST)
            System.out.print(code + " ");
          codeLine += code + " ";
        } // End of for loop
        if (SHOW_FULL_LIST)
          System.out.println();

        // Scores the number of codes
        numCodes[qual - 10] += codesPerAmt.size();
        System.out.println();
      } // End of amt for loop

      // * - Stat Row - *

      // Records the stat row
      statRow = qual + ", " + numSumCombs[qual - 10] +
          ", " + numCodes[qual - 10];

      System.out.println(numSumCombs[qual - 10] + " sum combinations with " +
          numCodes[qual - 10] + " codes found in total for " +
          qual + "% equipment." + "\n");

      // * - File Writing - *

      try {
        FileWriter codesFile = new FileWriter(CODES_FILE, true),
            statFile = new FileWriter(STAT_FILE, true);

        // Adds the line to files
        codesFile.write(codeLine + "\n");
        statFile.write(statRow + "\n");

        codesFile.close();
        statFile.close();
      } catch (IOException e) {
        printExcept(e);
      } // End of try-catch

    } // End of qual for loop

    /*
     * - Total Summaries -
     */

    startNewSection();
    System.out.println("All equipment codes generated.");

    int totalCodes = 0, totalSumCombs = 0;
    // Sums up the total numbers
    for (int i = 0; i < numCodes.length; i++) {
      totalCodes += numCodes[i];
      totalSumCombs += numSumCombs[i];
    } // End of for loop

    try {
      FileWriter statFile = new FileWriter(STAT_FILE, true);
      // Writes the total row
      statFile.write("Total, " + totalSumCombs + ", " + totalCodes + "\n");

      statFile.close();
    } catch (IOException e) {
      printExcept(e);
    } // End of try-catch

    System.out.println(totalSumCombs + " sum combinations with " +
        totalCodes + " codes found for all 10%-99% equipment.");

  } // End of main

  /*
   * = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
   * - Algorithm Methods -
   * This part is done with the help of ChatGPT.
   * = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
   */

  /*
   * - Permutation Generator -
   */

  // Recursively permutates the model
  private static void loopPermut(char[] arr, int idx, List<char[]> result) {

    // * - Base Case - *

    // Records the permutation if one of the loops reaches the end
    if (idx == arr.length - 1) {
      result.add(arr.clone());
      return;
    } // End of if

    // * - Recursive Case - *

    for (int i = idx; i < arr.length; i++) {
      // Swaps the elements at two indexes
      swap(arr, i, idx);
      // Recursively permutates the rest of the model
      loopPermut(arr, idx + 1, result);
      // Swaps the elements back
      swap(arr, i, idx);
    } // End of for loop
  } // End of void method

  // * - Return - *

  // Returns a list of all permutations of the model
  private static List<char[]> getPermut(char[] emts) {
    List<char[]> result = new ArrayList<char[]>();
    loopPermut(emts, 0, result);
    return result;
  } // End of List<char[]> method

  /*
   * - Sum Combination -
   */

  // Recursively finds all combinations of numbers that sums up to a target
  private static void loopComb(int n, int min, int max, int sum,
      List<Integer> curr, int idx, List<List<Integer>> result) {

    // * - Base Case - *

    // Records the combination if the list reaches the size
    if (curr.size() == n) {
      // Checks if the sum is correct
      if (sum == 0)
        result.add(new ArrayList<Integer>(curr));
      return;
    } // End of if

    // * - Recursive Case - *

    // Loops through all possible numbers
    for (int i = Math.max(min, idx); i <= max; i++) {
      // Breaks the loop if the sum is exceeded
      if (i > sum)
        break;
      // Adds the current number to the list
      curr.add(i);
      // Recursively finds the combinations
      loopComb(n, min, max, sum - i, curr, i, result);
      // Removes the last number from the list
      curr.remove(curr.size() - 1);
    } // End of for loop

  } // End of void method

  // * - Return - *

  // Returns a list of all sum combinations
  private static List<int[]> getSumComb(Setup setup, int amt, int qual) {
    // Inits the list
    List<List<Integer>> lstRes = new ArrayList<List<Integer>>();
    List<Integer> curr = new ArrayList<Integer>();
    int[] propVal = setup.getPropValRange();
    // Recursively finds all sum combinations
    loopComb(amt, propVal[0], propVal[1], qual, curr, 0, lstRes);

    // Converts the list
    List<int[]> arrRes = new ArrayList<int[]>();
    for (List<Integer> list : lstRes) {
      int[] arr = new int[PERM_SIZE];
      for (int i = 0; i < PERM_SIZE; i++)
        arr[i] = (i < list.size() ? list.get(i) : 0);
      arrRes.add(arr); // Add the array to the result list
    } // End of for loop
    return arrRes;
  } // End of List<int[]> method

  /*
   * = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
   * - Other Methods -
   * = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
   */

  // * - Swap - *

  // Overloadings that swaps chars
  private static void swap(char[] arr, int i, int j) {
    char temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  } // End of void method

  // * - Separator - *

  // Prints the separator
  public static void startNewSection() {
    System.out.println();
    for (int i = 0; i < 40; i++)
      System.out.print("= ");
    System.out.println();
  } // End of void method

  // * - IO Exception - *

  // Prints the error message
  public static void printExcept(IOException e) {
    System.out.println("Error: " + e.getMessage());
    e.printStackTrace();
  } // End of void method

} // * - End of class -