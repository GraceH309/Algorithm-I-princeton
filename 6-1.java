public class MergeTwoSubarrays {

    /**
     * 合并两个已排序的子数组：a[0..n-1] 和 a[n..2n-1]
     * 仅使用长度为n的辅助数组
     * @param a 待合并的数组（长度必须为2n）
     * @param n 每个子数组的长度
     * @throws IllegalArgumentException 若数组为空或长度不等于2n
     */
    public static void merge(int[] a, int n) {
        // 输入合法性检查
        if (a == null) {
            throw new IllegalArgumentException("数组不能为空");
        }
        if (a.length != 2 * n) {
            throw new IllegalArgumentException("数组长度必须为 2n（当前长度：" + a.length + "，n：" + n + "）");
        }
        if (n == 0) {
            return; // 无元素需合并，直接返回
        }

        // 辅助数组：存储前半部分有序子数组 a[0..n-1]
        int[] temp = new int[n];
        System.arraycopy(a, 0, temp, 0, n); // 复制前n个元素到辅助数组

        int i = 0; // 辅助数组（前子数组）的指针
        int j = n; // 后半子数组的指针（a[n..2n-1]）
        int k = 0; // 原数组的填充指针（从0开始）

        // 合并两个有序子数组
        while (i < n && j < 2 * n) {
            if (temp[i] <= a[j]) {
                // 前子数组元素更小，放入原数组
                a[k++] = temp[i++];
            } else {
                // 后半子数组元素更小，放入原数组
                a[k++] = a[j++];
            }
        }

        // 处理前子数组剩余的元素（后半子数组剩余元素已在正确位置）
        while (i < n) {
            a[k++] = temp[i++];
        }
    }

    // 单元测试
    public static void main(String[] args) {
        // 测试用例1：常规情况
        int[] a1 = {1, 3, 5, 2, 4, 6};
        int n1 = 3;
        merge(a1, n1);
        System.out.println("测试用例1结果：" + arrayToString(a1)); // 预期：[1, 2, 3, 4, 5, 6]

        // 测试用例2：前子数组元素均大于后半子数组
        int[] a2 = {5, 6, 7, 8, 1, 2, 3, 4};
        int n2 = 4;
        merge(a2, n2);
        System.out.println("测试用例2结果：" + arrayToString(a2)); // 预期：[1, 2, 3, 4, 5, 6, 7, 8]

        // 测试用例3：后半子数组元素均大于前子数组
        int[] a3 = {1, 2, 3, 4, 5, 6, 7, 8};
        int n3 = 4;
        merge(a3, n3);
        System.out.println("测试用例3结果：" + arrayToString(a3)); // 预期：[1, 2, 3, 4, 5, 6, 7, 8]

        // 测试用例4：n=1（最小情况）
        int[] a4 = {3, 1};
        int n4 = 1;
        merge(a4, n4);
        System.out.println("测试用例4结果：" + arrayToString(a4)); // 预期：[1, 3]

        // 测试用例5：n=2（交叉大小）
        int[] a5 = {2, 4, 1, 3};
        int n5 = 2;
        merge(a5, n5);
        System.out.println("测试用例5结果：" + arrayToString(a5)); // 预期：[1, 2, 3, 4]
    }

    // 辅助方法：将数组转为字符串（便于打印）
    private static String arrayToString(int[] a) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]);
            if (i != a.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}