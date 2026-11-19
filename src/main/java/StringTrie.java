import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StringTrie {

    private final TrieNode root;

    public StringTrie() {
        this.root = new TrieNode(' ');
    }

    public void addStringsFromFile(String pathName) { // Add all strings from a file pathname if file is found
        try {

            Scanner scanner = new Scanner(new File(pathName));
            while (scanner.hasNextLine()) {
                String dna = scanner.nextLine().trim();
                this.addString(dna);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Trie was not loaded, file not found.");
        }
    }

    public void addString(String string) {
        TrieNode current = root;
        char[] chars = string.toCharArray();

        for (char c:chars) {
            current = current.add(c);
        }

        current.setEnd(true);
    }

    public boolean search(String string) { // SIMILAR TO ADD STRING BUT IT'S DIFFERENT
        TrieNode current = root;
        char[] chars = string.toCharArray();

        for (char c:chars) {
            current = current.getBranch(c);
            if (current == null) {
                return false;
            }
        }

        return current.isEnd();
    }

    // NODE CLASS --------------------------------------------------------------------------------------
    private static class TrieNode {

        public final char CHAR;
        public final ArrayList<TrieNode> branches;
        private boolean isEnd;

        public TrieNode(char CHAR) {
            this.CHAR = CHAR;
            this.branches = new ArrayList<>();
            this.isEnd = false;
        }

        public TrieNode getBranch(char c) {
            for (TrieNode current : branches) {
                if (current.CHAR == c) {
                    return current;
                }
            }
            return null;
        }

        public TrieNode add(char c) {
            TrieNode node = this.getBranch(c);
            if (node != null) {
                return node;
            }

            node = new TrieNode(c);
            branches.add(node);

            return node;
        }

        // GETTERS AND SETTERS
        public void setEnd(boolean bool) {
            isEnd = bool;
        }

        public boolean isEnd() {
            return isEnd;
        }
    }

}
