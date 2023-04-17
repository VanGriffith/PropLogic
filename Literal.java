import java.util.ArrayList;
import java.util.PriorityQueue;

public class Literal extends Proposition {

    public char letter;

    public Literal(boolean isNegative, char letter) {
        super(isNegative);
        this.letter = letter;
    }

    /**
     * Adds the literal to the clause given through the clause parameter
     */
    public void collectClause(PriorityQueue<String> clause) {
        clause.add((this.isNegative ? "~" : "") + this.letter);
    }
    
    /**
     * Adds a new clause containing only this literal to the list of clauses
     */
    public void collectClauses(ArrayList<PriorityQueue<String>> clauses) {
        PriorityQueue<String> clause = new PriorityQueue<String>();
        this.collectClause(clause);
        clauses.add(clause);
    }

    public Proposition copy() {
        return new Literal(this.isNegative, this.letter);
    }

    public boolean isStatement() {
        return false;
    }
    
}
