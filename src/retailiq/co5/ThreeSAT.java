package retailiq.co5;

/**
 * CO5: 3-SAT problem solver for 3-literal clauses.
 */
public class ThreeSAT {
    public static boolean solve(int numVars, int[][] clauses) {
        return SAT.isSatisfiable(numVars, clauses);
    }
}
