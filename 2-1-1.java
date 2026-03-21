import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// 并查集数据结构（带路径压缩和按大小合并）
class UnionFind {
    private int[] parent;   // parent[i]：节点i的父节点
    private int[] size;     // size[i]：以i为根的连通分量大小
    private int componentCount;  // 当前连通分量数量

    // 初始化：n个独立节点
    public UnionFind(int n) {
        componentCount = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;   // 每个节点的父节点是自己
            size[i] = 1;     // 初始每个连通分量大小为1
        }
    }

    // 查找节点x的根节点（带路径压缩）
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // 路径压缩：让x直接指向根节点
        }
        return parent[x];
    }

    // 合并x和y所在的连通分量（按大小合并）
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return;  // 已在同一连通分量，无需合并

        // 小分量合并到大分量，避免树过高
        if (size[rootX] < size[rootY]) {
            parent[rootX] = rootY;
            size[rootY] += size[rootX];
        } else {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
        }
        componentCount--;  // 合并后连通分量数量减1
    }

    // 获取当前连通分量数量
    public int getComponentCount() {
        return componentCount;
    }
}

// 主程序：求解社交网络全连接最早时间
public class SocialNetworkConnectivity {
    public static void main(String[] args) {
        // 输入格式（按顺序）：
        // 第一行：成员数量n（成员编号0~n-1，或需映射为整数）
        // 后续每行：时间戳 t + 成员a + 成员b（日志已按t升序排列）
        int n = StdIn.readInt();
        UnionFind uf = new UnionFind(n);

        while (!StdIn.isEmpty()) {
            String timestamp = StdIn.readString();  // 时间戳（可是字符串或数字）
            int a = StdIn.readInt();                // 成员a（整数编号）
            int b = StdIn.readInt();                // 成员b（整数编号）

            uf.union(a, b);
            // 当所有成员连通时，输出当前时间戳并退出
            if (uf.getComponentCount() == 1) {
                StdOut.println("所有成员最早连通的时间：" + timestamp);
                return;
            }
        }

        // 遍历完所有日志仍未连通
        StdOut.println("所有成员无法完全连通");
    }
}