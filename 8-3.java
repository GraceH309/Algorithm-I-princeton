import java.util.*;

public class TaxiNumberV2 {
    // 结构体：存储立方和及对应的数对（i<=j）
    static class PairSum implements Comparable<PairSum> {
        long sum;
        int i;
        int j;

        public PairSum(long sum, int i, int j) {
            this.sum = sum;
            this.i = i;
            this.j = j;
        }

        // 按立方和升序排序
        @Override
        public int compareTo(PairSum other) {
            return Long.compare(this.sum, other.sum);
        }
    }

    // 结果类（与版本1一致）
    public static class Result {
        private final long sum;
        private final List<int[]> pairs;

        public Result(long sum, List<int[]> pairs) {
            this.sum = sum;
            this.pairs = pairs;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(sum + " = ");
            for (int i = 0; i < pairs.size(); i++) {
                int[] p = pairs.get(i);
                sb.append(p[0]).append("³ + ").append(p[1]).append("³");
                if (i < pairs.size() - 1) sb.append(" = ");
            }
            return sb.toString();
        }
    }

    public static List<Result> findTaxiNumbers(int n) {
        if (n < 2) return Collections.emptyList();

        // 预计算立方数：辅助空间O(n)
        long[] cube = new long[n];
        for (int i = 1; i < n; i++) {
            cube[i] = (long) i * i * i;
        }

        // 生成所有数对对应的立方和结构体（中间结果，不计入辅助空间）
        List<PairSum> pairSums = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            for (int j = i; j < n; j++) {
                long s = cube[i] + cube[j];
                pairSums.add(new PairSum(s, i, j));
            }
        }

        // 堆排序：原地排序，辅助空间O(1)，时间O(n² log n)
        heapSort(pairSums);

        // 遍历排序后的列表，收集重复立方和的数对
        List<Result> results = new ArrayList<>();
        int m = pairSums.size();
        int i = 0;
        while (i < m) {
            PairSum current = pairSums.get(i);
            long currentSum = current.sum;
            List<int[]> pairs = new ArrayList<>();
            pairs.add(new int[]{current.i, current.j});

            // 收集所有连续相同立方和的数对
            int j = i + 1;
            while (j < m && pairSums.get(j).sum == currentSum) {
                pairs.add(new int[]{pairSums.get(j).i, pairSums.get(j).j});
                j++;
            }

            // 数对数量≥2则为出租车号
            if (pairs.size() >= 2) {
                results.add(new Result(currentSum, pairs));
            }

            i = j; // 跳过已处理的数对
        }

        return results;
    }

    // 堆排序：原地排序，维护大顶堆
    private static void heapSort(List<PairSum> list) {
        int size = list.size();

        // 构建大顶堆（从最后一个非叶子节点开始堆化）
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(list, size, i);
        }

        // 逐个提取堆顶（最大元素），放到列表末尾
        for (int i = size - 1; i > 0; i--) {
            Collections.swap(list, 0, i);
            heapify(list, i, 0); // 堆化剩余元素
        }
    }

    // 堆化：维护大顶堆性质
    private static void heapify(List<PairSum> list, int heapSize, int rootIndex) {
        int largest = rootIndex; // 初始化最大元素为根节点
        int leftChild = 2 * rootIndex + 1; // 左子节点索引
        int rightChild = 2 * rootIndex + 2; // 右子节点索引

        // 比较左子节点与根节点
        if (leftChild < heapSize && list.get(leftChild).compareTo(list.get(largest)) > 0) {
            largest = leftChild;
        }

        // 比较右子节点与当前最大元素
        if (rightChild < heapSize && list.get(rightChild).compareTo(list.get(largest)) > 0) {
            largest = rightChild;
        }

        // 若最大元素不是根节点，交换并递归堆化子树
        if (largest != rootIndex) {
            Collections.swap(list, rootIndex, largest);
            heapify(list, heapSize, largest);
        }
    }

    public static void main(String[] args) {
        int n = 20;
        List<Result> results = findTaxiNumbers(n);
        System.out.println("版本2 出租车号码（n=" + n + "）：");
        for (Result res : results) {
            System.out.println(res);
        }
    }
}