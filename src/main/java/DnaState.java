public class DnaState implements Comparable<DnaState>{
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
