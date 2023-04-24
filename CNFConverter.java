import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CNFConverter {

    public static void main(String args[]) {
        // Parse the inputs
        Scanner sc = new Scanner(System.in);
        String sentence = sc.nextLine();
        sc.close();
        
        //Remove whitespace, create a tree using resultant string
        sentence = sentence.replaceAll("\\s", "");
        Tree tree = new Tree(sentence);

        // Apply the steps of CNF Conversion
        tree.removeImplications();
        tree.applyDeMorgans();
        tree.applyDistributions();

        // Collect the clauses ito an unordered lit of unordered strings
        // I tried using priority queues to prevent unneeded sorting,
        // but for some reason that gave me problems that sorting didn't
        ArrayList<ArrayList<String>> clauses = tree.collectClauses();

        // Each line (clause) will be added to this arraylit after sorting
        ArrayList<String> clauseStrings = new ArrayList<String>();

        // Sort each clause internally, then ad unique clauses
        for (int i = 0; i < clauses.size(); i++) {
            ArrayList<String> clause = clauses.get(i);
            Collections.sort(clause);
            String clauseString = "";

            for (String literal: clause) {
                clauseString += "," + literal;
            }
            clauseString = clauseString.substring(1);
            if (!clauseStrings.contains(clauseString)) clauseStrings.add(clauseString);
        }

        // Sort the clauses, then print them
        Collections.sort(clauseStrings);
        for (String s: clauseStrings) {
            if (s.charAt(0) != '!') {
                System.out.println(s);
            }
        }
    }
}

class Tree {
    Statement root;

    /**
     * Does a BFS and removes all >'s and ='s as it goes
     */
    public void removeImplications() {
        Queue<Statement> unchecked = new LinkedList<Statement>();
        unchecked.offer(this.root);

        while (!unchecked.isEmpty()) {
            Statement current = unchecked.poll();
            current.removeImplications();

            if(current.leftChild.isStatement()) {
                unchecked.offer((Statement) current.leftChild);
            }
            if(current.rightChild.isStatement()) {
                unchecked.offer((Statement) current.rightChild);
            }
        }
    }

    /**
     * Does a BFS and applies DeMorgan's law down as it goes
     */
    public void applyDeMorgans() {
        Queue<Statement> unchecked = new LinkedList<Statement>();
        unchecked.offer(this.root);

        while (!unchecked.isEmpty()) {
            Statement current = unchecked.poll();
            current.applyDeMorgans();

            if(current.leftChild.isStatement()) {
                unchecked.offer((Statement) current.leftChild);
            }
            if(current.rightChild.isStatement()) {
                unchecked.offer((Statement) current.rightChild);
            }
        }
    }

    /**
     * Does a BFS and applies the distributive law anytime an OR is
     * seen above an AND. Each time this happens, the search restarts until 
     * no ORs are seen underneath any ANDs. After this, the tree should
     * be in CNF.
     */
    public void applyDistributions() {
        Queue<Statement> unchecked = new LinkedList<Statement>();
        unchecked.offer(this.root);

        while (!unchecked.isEmpty()) {
            Statement current = unchecked.poll();
            if (current.applyDistributions()) {
                unchecked = new LinkedList<Statement>();
                unchecked.offer(this.root);
                continue;
            }

            if(current.leftChild.isStatement()) {
                unchecked.offer((Statement) current.leftChild);
            }
            if(current.rightChild.isStatement()) {
                unchecked.offer((Statement) current.rightChild);
            }
        }
    }

    /**
     * Collects the clauses of the tree
     * @return
     */
    public ArrayList<ArrayList<String>> collectClauses() {
        ArrayList<ArrayList<String>> clauses = new ArrayList<ArrayList<String>>();
        this.root.collectClauses(clauses);

        return clauses;
    }

    @Override
    public String toString() {
        if (this.root == null) return "null";
        else return this.root.toString();
    }
    
    /**
     * Generate a tree from an input string
     * @param input the input string, with no whitespace
     */
    public Tree(String input) {
        boolean negateNext = false;
        Statement current = null;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            switch(currentChar) {
                case Proposition.NOT:
                    negateNext = true;
                break;


                case Proposition.OPEN:
                    Statement newStatement = new Statement(negateNext);
                    negateNext = false;
                    if (this.root == null) {
                        this.root = newStatement;
                    }
                    else {
                        current.addChild(newStatement);
                    }
                    current = newStatement;
                break;


                case Proposition.CLOSE:
                    current = current.parent;
                break;


                case Proposition.AND:
                case Proposition.OR:
                case Proposition.IMPLIES:
                case Proposition.EQUALS:
                    current.operator = currentChar;
                break;


                default:
                    current.addChild(new Literal(negateNext, currentChar));
                    negateNext = false;
                break;
            }
        }
    }
}