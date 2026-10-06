package retailiq.co5;

/**
 * CO5: Boolean Satisfiability (SAT) Solver for product configuration constraints.
 */
public class SAT {
    public static boolean isSatisfiable(int numVars, int[][] clauses) {
        int totalAssignments = 1 << numVars;
        for (int mask = 0; mask < totalAssignments; mask++) {
            boolean allSatisfied = true;
            for (int i = 0; i < clauses.length; i++) {
                boolean clauseSatisfied = false;
                for (int j = 0; j < clauses[i].length; j++) {
                    int lit = clauses[i][j];
                    int var = Math.abs(lit) - 1;
                    boolean val = ((mask >> var) & 1) == 1;
                    if (lit < 0) val = !val;
                    if (val) {
                        clauseSatisfied = true;
                        break;
                    }
                }
                if (!clauseSatisfied) {
                    allSatisfied = false;
                    break;
                }
            }
            if (allSatisfied) return true;
        }
        return false;
    }
}
