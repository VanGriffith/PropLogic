import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Resolution {

    static ArrayList<ArrayList<Literal>> clauses;

    public static void main(String[] args) {
        clauses = new ArrayList<ArrayList<Literal>>();
        Scanner sc = new Scanner(System.in);

        // Read input lines
        while (sc.hasNextLine()) {
            // Turn input line into array of literal-strings
            String[] line = sc.nextLine().split(",");

            // Create arraylist of literals from the string array
            ArrayList<Literal> literals = new ArrayList<>();
            for (String str: line) {
                literals.add(new Literal(str));
            }
            clauses.add(literals);
        }
        sc.close();

        /**
         * The following several nested for loops perform the resolution, aborting 
         * and printing "Contradiction" the moment one is found. If it reaches the end
         * of the loops, the list of clauses is printed.
         */
        // For each clause (c1)
        for (int c1Index = 0; c1Index < clauses.size() - 1; c1Index++) {
            ArrayList<Literal> clauseOne = clauses.get(c1Index);

            // For each literal in c1...
            for (int l1Index = 0; l1Index < clauseOne.size(); l1Index++) {
                Literal literal = clauseOne.get(l1Index);
                Literal negatedLiteral = (Literal) literal.copy().negate();

                // Check if any clauses after c1 contain the negation of the literal.
                // If so, create a new clause and add it to the list of clauses
                for (int c2Index = c1Index + 1; c2Index < clauses.size(); c2Index++) {
                    ArrayList<Literal> clauseTwo = clauses.get(c2Index);
                    if (clauseTwo.contains(negatedLiteral)) {
                        ArrayList<Literal> newClause = new ArrayList<Literal>();
                        newClause.addAll(clauseOne);
                        newClause.remove(literal);

                        boolean tautologyFound = false;
                        for (Literal extraLiteral: clauseTwo) {
                            if (newClause.contains(extraLiteral.copy().negate())) {
                                tautologyFound = true;
                            }
                            else if (!newClause.contains(extraLiteral)){
                                newClause.add(extraLiteral);
                            } 
                        }
                        newClause.remove(negatedLiteral);
                        if (newClause.size() == 0) {
                            System.out.println("Contradiction");
                            return;
                        }

                        if (!tautologyFound) {
                            clauses.add(newClause);
                        }
                    }
                }
            }
        }

        // Print the found clauses
        printClauses();
    }

    /**
     * Prints the clauses
     */
    public static void printClauses() {

        PriorityQueue<String> clausePQ = new PriorityQueue<>();

        for (ArrayList<Literal> clause: clauses) {
            PriorityQueue<String> literalPQ = new PriorityQueue<>();
            for (Literal literal: clause) {
                literalPQ.add(literal.toString());
            }
            String literalString = "";
            while (!literalPQ.isEmpty()) {
                literalString += "," + literalPQ.poll();
            }
            literalString = literalString.substring(1);
            if (!clausePQ.contains(literalString)) {
                clausePQ.offer(literalString);
            }
        }

        while (!clausePQ.isEmpty()) {
            System.out.println(clausePQ.poll());   
        }
    }
}
