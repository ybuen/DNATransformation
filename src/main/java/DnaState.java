import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DnaState implements Comparable<DnaState>{

    final static private StringTrie TRIE = new StringTrie();
    final static private char[] POSSIBLE_CHARS = {'A', 'C', 'T', 'G'};

    final DnaState previous;
    final public String dnaString;
    final public int length, numDifferentChars, steps;

    public DnaState(String dnaString, DnaState previous, int numDifferentChars) {
        this.dnaString = dnaString;
        this.previous = previous;
        this.length = dnaString.length();

        if (previous != null) {
            this.steps = previous.steps + 1;
        } else {
            this.steps = 0;
        }
        this.numDifferentChars = numDifferentChars;
    }

    public static void initializeStringTrie() throws FileNotFoundException { // Add all possible strings to the Trie
        Scanner scanner = new Scanner(new File("DNAsequences.txt"));

        while (scanner.hasNextLine()) {
            String dna = scanner.nextLine().trim();
            TRIE.addString(dna);
        }
        scanner.close();
    }

    public ArrayList<String> getPossibleAdjacents() {
        ArrayList<String> possibleAdjacents = new ArrayList<>();
        String newString;
        char replacement;

        for (int i = 0; i < length; i ++) {

            for (int ci = 0; ci < 4; ci ++) {
                replacement = POSSIBLE_CHARS[ci];
                if (replacement == dnaString.charAt(i)) {
                    continue;
                }

                newString = dnaString.substring(0, i) + replacement + dnaString.substring(i + 1, length);

                if (TRIE.search(newString)) { // IF the dna string is known
                    possibleAdjacents.add(newString);
                }
            }
        }

        return possibleAdjacents;
    }

    public static int getNumDifferentChars(String goalString, String currentString) {
        int count = 0;
        for (int i = 0; i < currentString.length(); i ++) {
            if (goalString.charAt(i) != currentString.charAt(i)) {
                count ++;
            }
        }
        return count;
    }

    @Override
    public int compareTo(DnaState o) {
        return Integer.compare(this.steps + this.numDifferentChars, o.steps + o.numDifferentChars);
    }

    @Override
    public String toString() {
        return dnaString;
    }
}
