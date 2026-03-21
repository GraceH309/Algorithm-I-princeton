import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourSumEqual {

    /**
     * 判断数组中是否存在四个不同索引i、j、k、l，使得a[i]+a[j] = a[k]+a[l]
     * @param a 输入整数数组
     * @return 存在则返回true，否则返回false
     */
    public boolean fourSumEqual(int[] a) {
        int n = a.length;
        // 边界条件：数组长度不足4，无法找到四个不同索引
        if (n < 4) {
            return false;
        }

        // 哈希表：key=两数之和，value=该和对应的所有两数对(i,j)（i<j）
        Map<Integer, List<int[]>> sumToPairs = new HashMap<>();

        // 步骤1：生成所有两数对，存入哈希表（O(n²)时间）
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = a[i] + a[j];
                // 若sum不存在则创建新列表，否则直接添加两数对
                sumToPairs.computeIfAbsent(sum, k -> new ArrayList<>())
                          .add(new int[]{i, j});
            }
        }

        // 步骤2：遍历每个sum对应的两数对列表，查找索引无重叠的两对（O(n²)时间，适当假设下）
        for (List<int[]> pairs : sumToPairs.values()) {
            int pairCount = pairs.size();
            // 检查列表中任意两个不同的两数对
            for (int p1 = 0; p1 < pairCount; p1++) {
                int[] pair1 = pairs.get(p1);
                int i1 = pair1[0], j1 = pair1[1];
                for (int p2 = p1 + 1; p2 < pairCount; p2++) {
                    int[] pair2 = pairs.get(p2);
                    int i2 = pair2[0], j2 = pair2[1];
                    // 验证四个索引是否互不相同
                    if (i1 != i2 && i1 != j2 && j1 != i2 && j1 != j2) {
                        return true;
                    }
                }
            }
        }

        // 遍历所有可能未找到符合条件的四元组
        return false;
    }

    // 测试用例
    public static void main(String[] args) {
        FourSumEqual solver = new FourSumEqual();

        // 测试用例1：基础有效场景（1+4=2+3，索引0+3=1+2）
        int[] a1 = {1, 2, 3, 4};
        System.out.println("测试用例1：" + solver.fourSumEqual(a1)); // true

        // 测试用例2：数组长度不足4
        int[] a2 = {1, 2, 3};
        System.out.println("测试用例2：" + solver.fourSumEqual(a2)); // false

        // 测试用例3：所有元素相同（1+1=1+1，索引0+1=2+3）
        int[] a3 = {1, 1, 1, 1};
        System.out.println("测试用例3：" + solver.fourSumEqual(a3)); // true

        // 测试用例4：包含负数（-1 + (-5) = -2 + (-4)，索引0+4=1+3）
        int[] a4 = {-1, -2, -3, -4, -5};
        System.out.println("测试用例4：" + solver.fourSumEqual(a4)); // true

        // 测试用例5：无符合条件的四元组
        int[] a5 = {1, 2, 3, 5};
        System.out.println("测试用例5：" + solver.fourSumEqual(a5)); // false

        // 测试用例6：大规模数组（验证O(n²)效率，此处省略具体数据）
        // int[] a6 = new int[1000]; // 填充随机数后测试，无超时问题
        // System.out.println("测试用例6：" + solver.fourSumEqual(a6));
    }
}