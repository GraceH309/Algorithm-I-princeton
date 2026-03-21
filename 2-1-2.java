public class UnionFindMax {
    private int[] parent;       // parent[i]：节点i的父节点
    private int[] size;         // size[root]：以root为根的连通分量大小（用于按大小合并）
    private int[] maxElement;   // maxElement[root]：以root为根的连通分量的最大元素（仅根节点有效）
    private int n;              // 节点总数（节点编号默认0~n-1，若需其他编号可调整）

    /**
     * 初始化并查集：n个独立节点（编号0~n-1）
     * 每个节点的父节点是自身，最大元素是自身，分量大小为1
     */
    public UnionFindMax(int n) {
        this.n = n;
        parent = new int[n];
        size = new int[n];
        maxElement = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
            maxElement[i] = i;
        }
    }

    /**
     * 查找节点i所在连通分量的根节点（带路径压缩优化）
     * 路径压缩：让i直接指向根节点，减少后续查找次数
     */
    public int find(int i) {
        validate(i);  // 验证节点编号合法性
        // 递归路径压缩（也可实现为迭代，避免栈溢出）
        if (parent[i] != i) {
            parent[i] = find(parent[i]);  // 父节点指向根节点，完成路径压缩
        }
        return parent[i];
    }

    /**
     * 查找包含节点i的连通分量中的最大元素
     * 逻辑：先找到根节点，再返回根节点对应的maxElement
     */
    public int findMax(int i) {
        int root = find(i);  // 路径压缩后，直接获取根节点
        return maxElement[root];
    }

    /**
     * 合并节点p和q所在的连通分量（按大小合并优化）
     * 合并后，新根的maxElement为两个分量最大元素的较大值
     */
    public void union(int p, int q) {
        validate(p);
        validate(q);
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ) {
            return;  // 已在同一连通分量，无需合并
        }

        // 按大小合并：小分量合并到大分量的根节点下
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;  // rootP的父节点设为rootQ
            // 更新新根（rootQ）的最大元素
            maxElement[rootQ] = Math.max(maxElement[rootQ], maxElement[rootP]);
            size[rootQ] += size[rootP];  // 更新分量大小
        } else {
            parent[rootQ] = rootP;  // rootQ的父节点设为rootP
            // 更新新根（rootP）的最大元素
            maxElement[rootP] = Math.max(maxElement[rootP], maxElement[rootQ]);
            size[rootP] += size[rootQ];  // 更新分量大小
        }
    }

    /**
     * 判断节点p和q是否在同一连通分量
     */
    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return find(p) == find(q);
    }

    /**
     * 验证节点编号是否合法（0 <= i < n）
     */
    private void validate(int i) {
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("节点编号超出范围：" + i + "（合法范围0~" + (n-1) + "）");
        }
    }

    // 测试用例
    public static void main(String[] args) {
        // 示例：连通分量 {1,2,6,9}（假设节点编号0~9）
        UnionFindMax uf = new UnionFindMax(10);  // 节点0~9
        uf.union(1, 2);
        uf.union(2, 6);
        uf.union(6, 9);

        // 验证连通分量中所有元素的findMax返回9
        System.out.println(uf.findMax(1));  // 输出9
        System.out.println(uf.findMax(2));  // 输出9
        System.out.println(uf.findMax(6));  // 输出9
        System.out.println(uf.findMax(9));  // 输出9

        // 验证跨分量合并后的最大元素
        uf.union(9, 5);  // 合并{1,2,6,9}和{5}，最大元素仍为9
        System.out.println(uf.findMax(5));  // 输出9

        uf.union(5, 10);  // 报错：节点10超出范围（0~9）
    }
}