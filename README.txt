The class CNFConverter.java contains the source code for part 1.
It contains a main method, which reads input from the terminal, 
most easily piped in from an external file. It outputs across several 
lines, where each line is a clause, and each clause comprised of a comma
separated list of literals with "simple" tautologies removed (i.e. 
clauses contains A and ~A). My outputs don't always exactly match the 
outputs found in the given test cases, but I'm fairly confident that my 
outputs correctly follow the CNF conversion algorithm given by
our textbook.

The class Resolution.java contains the source code for part 2.
It contains a main method, which reads input from the terminal, most
easily piped in from an external file or from the output of the
CNFConverter.java class. It outputs either the word "Contradiction" or 
a list of clauses found through resolution. This class also removes
"simple" tautologies as it goes, meaning any clause reached through 
resolution containing A and ~A is deemed a tautology and not added to 
the list of clauses to check next. This gets my outputs to match up 
correctly with the test outputs, and discarding them as I go seems to 
prevent mishaps.


