package retailiq.co3;

/**
 * CO3: Tree Dynamic Programming (Maximum Weight Independent Set on Hierarchy).
 * Optimizes incentive or campaign bonus distribution across an organizational or
 * category hierarchy tree where adjacent parent-child nodes cannot both be activated.
 *
 * State:
 *   dp[u][0] = maximum value obtainable in subtree of u if u is EXCLUDED.
 *   dp[u][1] = maximum value obtainable in subtree of u if u is INCLUDED.
 *
 * Transitions:
 *   dp[u][0] = sum_{v in children(u)} max(dp[v][0], dp[v][1])
 *   dp[u][1] = weight[u] + sum_{v in children(u)} dp[v][0]
 *
 * Time Complexity: O(N) linear time single post-order / DFS traversal.
 * Space Complexity: O(N)
 *
 * Course restriction: Implemented without java.util.*
 */
public class TreeDP {

    public static class TreeNode {
        public int id;
        public String name;
        public double weight;
        public TreeNode[] children;
        public int childCount;

        public TreeNode(int id, String name, double weight, int maxChildren) {
            this.id = id;
            this.name = name;
            this.weight = weight;
            this.children = new TreeNode[maxChildren];
            this.childCount = 0;
        }

        public void addChild(TreeNode child) {
            if (childCount == children.length) {
                TreeNode[] newArr = new TreeNode[children.length * 2];
                for (int i = 0; i < childCount; i++) newArr[i] = children[i];
                children = newArr;
            }
            children[childCount++] = child;
        }
    }

    public static class TreeDPResult {
        public double maxBonus;
        public double[][] dp;
        public boolean[] included;
        public String rootName;

        public TreeDPResult(double maxBonus, double[][] dp, boolean[] included, String rootName) {
            this.maxBonus = maxBonus;
            this.dp = dp;
            this.included = included;
            this.rootName = rootName;
        }
    }

    public static TreeDPResult solve(TreeNode root, int totalNodes) {
        if (root == null || totalNodes <= 0) {
            return new TreeDPResult(0, new double[0][0], new boolean[0], "");
        }

        double[][] dp = new double[totalNodes][2];
        boolean[] included = new boolean[totalNodes];

        dfs(root, dp);

        double maxVal = Math.max(dp[root.id][0], dp[root.id][1]);
        reconstruct(root, dp[root.id][1] > dp[root.id][0], dp, included);

        return new TreeDPResult(maxVal, dp, included, root.name);
    }

    private static void dfs(TreeNode u, double[][] dp) {
        dp[u.id][0] = 0;
        dp[u.id][1] = u.weight;

        for (int i = 0; i < u.childCount; i++) {
            TreeNode v = u.children[i];
            dfs(v, dp);

            dp[u.id][0] += Math.max(dp[v.id][0], dp[v.id][1]);
            dp[u.id][1] += dp[v.id][0];
        }
    }

    private static void reconstruct(TreeNode u, boolean isIncluded, double[][] dp, boolean[] included) {
        included[u.id] = isIncluded;

        for (int i = 0; i < u.childCount; i++) {
            TreeNode v = u.children[i];
            if (isIncluded) {
                // If u is included, child v cannot be included
                reconstruct(v, false, dp, included);
            } else {
                // Pick whichever child state gave better value
                boolean pickV = dp[v.id][1] > dp[v.id][0];
                reconstruct(v, pickV, dp, included);
            }
        }
    }
}
