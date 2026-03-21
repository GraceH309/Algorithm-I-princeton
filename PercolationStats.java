import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // 常量：95%置信区间的Z值（解决重复字面量警告）
    private static final double CONFIDENCE_95 = 1.96;

    private final double[] thresholds; // 每次实验的渗流阈值
    private final int trials;          // 实验次数

    /**
     * 执行T次独立实验（n×n网格）
     * @param n 网格大小（n≥1）
     * @param trials 实验次数（trials≥1）
     * @throws IllegalArgumentException 若n≤0或trials≤0
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be positive");
        }
        this.trials = trials;
        this.thresholds = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                // 随机选择未开放的站点
                int row, col;
                do {
                    row = StdRandom.uniformInt(1, n + 1);
                    col = StdRandom.uniformInt(1, n + 1);
                } while (p.isOpen(row, col));
                p.open(row, col);
            }
            thresholds[t] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    /**
     * 返回渗流阈值的样本均值
     * @return 样本均值
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * 返回渗流阈值的样本标准差
     * @return 样本标准差（若实验次数=1则返回0.0）
     */
    public double stddev() {
        if (trials == 1) return 0.0;
        return StdStats.stddev(thresholds);
    }

    /**
     * 返回95%置信区间的下界
     * @return 置信区间下界
     */
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    /**
     * 返回95%置信区间的上界
     * @return 置信区间上界
     */
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    /**
     * 测试客户端：读取命令行参数n和T，输出统计结果
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java-algs4 PercolationStats <n> <trials>");
            return; // 替换System.exit(1)，避免错误
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        // 格式化输出（保留10位小数）
        System.out.printf("mean                    = %.10f%n", stats.mean());
        System.out.printf("stddev                  = %.10f%n", stats.stddev());
        System.out.printf("95%% confidence interval = [%.10f, %.10f]%n",
                          stats.confidenceLo(), stats.confidenceHi());
    }
}