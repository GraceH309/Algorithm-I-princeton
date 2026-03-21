import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // 静态常量（移到实例变量前，符合声明顺序）
    private static final int[] DR = {-1, 1, 0, 0}; // 上下左右行偏移
    private static final int[] DC = {0, 0, -1, 1}; // 上下左右列偏移

    private final int n;                  // 网格大小
    private final boolean[][] isOpen;     // 标记站点是否开放（改为boolean[][]）
    private final WeightedQuickUnionUF uf; // 并查集（包含虚拟顶+底）
    private final WeightedQuickUnionUF ufNoBottom; // 并查集（仅虚拟顶，防回流）
    private final int top;                // 虚拟顶层节点
    private final int bottom;             // 虚拟底层节点
    private int openSites;                // 开放站点数量

    /**
     * 构造n×n网格，所有站点初始为封闭状态
     * @param n 网格大小（n≥1）
     * @throws IllegalArgumentException 若n≤0
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        this.n = n;
        this.isOpen = new boolean[n + 1][n + 1]; // 行/列从1到n（方便索引）
        int size = n * n;
        this.uf = new WeightedQuickUnionUF(size + 2); // 0=顶，size+1=底
        this.ufNoBottom = new WeightedQuickUnionUF(size + 1); // 仅0=顶
        this.top = 0;
        this.bottom = size + 1;
        this.openSites = 0;
    }

    /**
     * 开放指定站点（若未开放），并与相邻开放站点连通
     * @param row 行索引（1~n）
     * @param col 列索引（1~n）
     * @throws IllegalArgumentException 若索引越界
     */
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen[row][col]) return;

        isOpen[row][col] = true;
        openSites++;
        int current = xyTo1D(row, col);

        // 连接虚拟顶
        if (row == 1) {
            uf.union(current, top);
            ufNoBottom.union(current, top);
        }
        // 连接虚拟底
        if (row == n) {
            uf.union(current, bottom);
        }

        // 连接四个相邻站点（无额外循环，优化性能）
        for (int i = 0; i < DR.length; i++) {
            int nr = row + DR[i];
            int nc = col + DC[i];
            if (nr >= 1 && nr <= n && nc >= 1 && nc <= n && isOpen[nr][nc]) {
                int neighbor = xyTo1D(nr, nc);
                uf.union(current, neighbor);
                ufNoBottom.union(current, neighbor);
            }
        }
    }

    /**
     * 判断指定站点是否开放
     * @param row 行索引（1~n）
     * @param col 列索引（1~n）
     * @return 若开放则为true，否则为false
     * @throws IllegalArgumentException 若索引越界
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return isOpen[row][col];
    }

    /**
     * 判断指定站点是否为"满"（开放且连通到顶层）
     * @param row 行索引（1~n）
     * @param col 列索引（1~n）
     * @return 若满则为true，否则为false
     * @throws IllegalArgumentException 若索引越界
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen[row][col]) return false;
        int current = xyTo1D(row, col);
        return ufNoBottom.find(current) == ufNoBottom.find(top);
    }

    /**
     * 返回开放站点的数量
     * @return 开放站点数
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * 判断系统是否渗流（顶层与底层连通）
     * @return 若渗流则为true，否则为false
     */
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    /**
     * 验证行、列索引是否合法（1~n）
     * @param row 行索引
     * @param col 列索引
     * @throws IllegalArgumentException 若索引越界
     */
    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException(
                String.format("Index out of bounds: row=%d, col=%d (must be 1~%d)", row, col, n)
            );
        }
    }

    /**
     * 将二维坐标（row, col）转换为并查集的一维索引
     * @param row 行索引（1~n）
     * @param col 列索引（1~n）
     * @return 一维索引（1~n²）
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * n + col;
    }

    /**
     * 测试客户端（可选）
     */
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        System.out.println("Percolates? " + p.percolates()); // 预期true
        System.out.println("Is (3,1) full? " + p.isFull(3,1)); // 预期true
        System.out.println("Open sites: " + p.numberOfOpenSites()); // 预期3
    }
}