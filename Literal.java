import java.util.ArrayList;

/**
 * A data structure to hold literals
 */
public class Literal extends Proposition {

    public char letter;

    public Literal(boolean isNegative, char letter) {
        super(isNegative);
        this.letter = letter;
    }

    /**
     * Adds the literal to the clause given through the clause parameter
     */
    public void collectClause(ArrayList<String> clause) {
        String clauseString = (this.isNegative ? "~" : "") + this.letter;
        String oppositeString = (!this.isNegative ? "~" : "") + this.letter;
        if (clause.contains(oppositeString)) {
            clause.add("!");
        }
        else if (!clause.contains(clauseString)) {
            clause.add(clauseString);
        }
    }
    
    /**
     * Adds a new clause containing only this literal to the list of clauses
     */
    public void collectClauses(ArrayList<ArrayList<String>> clauses) {
        ArrayList<String> clause = new ArrayList<String>();
        this.collectClause(clause);
        clauses.add(clause);
    }

    /**
     * Creates a new Literal in memory based on an existing Literal
     */
    public Proposition copy() {
        return new Literal(this.isNegative, this.letter);
    }

    /**
     * Returns false- required by the Proposition
     */
    public boolean isStatement() {
        return false;
    }

    @Override
    public String toString() {
        return (this.isNegative ? "~" : "") + this.letter;
    }
    
}
