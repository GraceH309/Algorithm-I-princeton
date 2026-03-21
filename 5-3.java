/**
 * 颜色检查接口：模拟 color(i) 操作，返回i桶的颜色（0=红，1=白，2=蓝）
 */
@FunctionalInterface
interface ColorChecker {
    int check(int index);
}

/**
 * 交换操作接口：模拟 swap(i,j) 操作，交换i桶和j桶的鹅卵石
 */
@FunctionalInterface
interface Swapper {
    void swap(int i, int j);
}
public class DutchNationalFlag {

    /**
     * 荷兰国旗排序：满足所有性能要求
     * @param n 桶数组长度
     * @param colorChecker 颜色检查器（对应 color(i)）
     * @param swapper 交换器（对应 swap(i,j)）
     */
    public static void sortColors(int n, ColorChecker colorChecker, Swapper swapper) {
        if (n <= 1) {
            return; // 长度0或1无需排序
        }

        int left = 0;       // 红色区域右边界
        int current = 0;    // 当前遍历指针
        int right = n - 1;  // 蓝色区域左边界

        while (current <= right) {
            int color = colorChecker.check(current); // 最多n次调用
            switch (color) {
                case 0: // 红色：移到左侧红色区域
                    swapper.swap(current, left); // 最多n次调用
                    left++;
                    current++; // 交换后current位置是原left的元素（已检查）
                    break;
                case 1: // 白色：留在中间
                    current++;
                    break;
                case 2: // 蓝色：移到右侧蓝色区域
                    swapper.swap(current, right); // 最多n次调用
                    right--; // 交换后current位置是原right的元素（未检查，不++）
                    break;
                default:
                    throw new IllegalArgumentException("无效颜色值：" + color);
            }
        }
    }

    // 测试用例
    public static void main(String[] args) {
        // 测试1：普通情况 [2,0,2,1,1,0] → 预期 [0,0,1,1,2,2]
        testSort(new int[]{2, 0, 2, 1, 1, 0});
        
        // 测试2：全同色 [1,1,1] → 预期 [1,1,1]
        testSort(new int[]{1, 1, 1});
        
        // 测试3：已排序 [0,0,1,1,2,2] → 预期不变
        testSort(new int[]{0, 0, 1, 1, 2, 2});
        
        // 测试4：边界情况 [2,0] → 预期 [0,2]
        testSort(new int[]{2, 0});
        
        // 测试5：空数组
        testSort(new int[]{});
    }

    /**
     * 测试辅助方法：维护真实数组，模拟 color 和 swap 操作
     */
    private static void testSort(int[] original) {
        int[] arr = Arrays.copyOf(original, original.length);
        int n = arr.length;

        // 实现 ColorChecker：返回数组对应索引的颜色
        ColorChecker colorChecker = index -> arr[index];

        // 实现 Swapper：交换数组对应索引的元素
        Swapper swapper = (i, j) -> {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        };

        // 执行排序
        sortColors(n, colorChecker, swapper);

        // 输出结果
        System.out.printf("原数组：%s → 排序后：%s → 验证：%s%n",
                Arrays.toString(original),
                Arrays.toString(arr),
                isSorted(arr) ? "✅ 正确" : "❌ 错误");
    }

    /**
     * 验证数组是否按 [0,0,...,1,1,...,2,2...] 排序
     */
    private static boolean isSorted(int[] arr) {
        // 检查是否有 0 在 1 或 2 之后，1 在 2 之后
        boolean hasSeen1 = false;
        boolean hasSeen2 = false;
        for (int num : arr) {
            if (num == 0) {
                if (hasSeen1 || hasSeen2) return false;
            } else if (num == 1) {
                hasSeen1 = true;
                if (hasSeen2) return false;
            } else if (num == 2) {
                hasSeen2 = true;
            }
        }
        return true;
    }
}