public class InversionCountLinear {

    /**
     * 计数排序+前缀和：线性时间计算反转数（元素范围有界）
     * @param arr 输入数组（非负整数，最大值 K 为常数）
     * @return 反转数
     */
    public static long countInversionsLinear(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }

        // 步骤1：找到数组元素的最大值 K（若已知 K，可直接传入，省略此步）
        int K = 0;
        for (int num : arr) {
            if (num < 0) {
                throw new IllegalArgumentException("数组元素必须为非负整数");
            }
            if (num > K) {
                K = num;
            }
        }

        // 步骤2：初始化计数数组（记录已遍历元素的频次）
        int[] cnt = new int[K + 1];
        long inversionCount = 0;

        // 步骤3：从后往前遍历，统计反转数
        for (int i = arr.length - 1; i >= 0; i--) {
            int current = arr[i];
            // 计算已遍历元素中比 current 小的个数（前缀和：cnt[0..current-1]）
            long smallerCount = getPrefixSum(cnt, current - 1);
            inversionCount += smallerCount;
            // 更新计数数组：当前元素加入已遍历集合
            cnt[current]++;
        }

        return inversionCount;
    }

    /**
     * 计算计数数组的前缀和（0 到 x 的和）
     * @param cnt 计数数组
     * @param x 右边界（x < 0 时和为 0）
     * @return 前缀和
     */
    private static long getPrefixSum(int[] cnt, int x) {
        if (x < 0) {
            return 0;
        }
        long sum = 0;
        // 因 K 为常数，此循环为 O(1) 时间
        for (int i = 0; i <= x; i++) {
            sum += cnt[i];
        }
        return sum;
    }

    // 测试用例
    public static void main(String[] args) {
        int[] arr1 = {3, 1, 2, 4}; // K=4（常数），反转数=2
        int[] arr2 = {4, 3, 2, 1}; // K=4，反转数=6
        int[] arr3 = {1, 2, 3, 4}; // K=4，反转数=0
        int[] arr4 = {2, 0, 1, 3}; // K=3，反转数=2（(2,0),(2,1)）
        int[] arr5 = {5, 5, 5};    // 无反转数，0

        System.out.println(countInversionsLinear(arr1)); // 2
        System.out.println(countInversionsLinear(arr2)); // 6
        System.out.println(countInversionsLinear(arr3)); // 0
        System.out.println(countInversionsLinear(arr4)); // 2
        System.out.println(countInversionsLinear(arr5)); // 0
    }
}