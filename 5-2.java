import java.util.HashMap;
import java.util.Map;

public class ArrayPermutationChecker {

    /**
     * 哈希频次统计法：判断两个数组是否为排列
     * 时间复杂度：O(n)（两次遍历数组 + 一次遍历哈希表，均为线性时间）
     * 空间复杂度：O(n)（哈希表存储元素频次，最坏情况存储所有不同元素）
     * @param a 数组1
     * @param b 数组2
     * @return 是否为排列
     */
    public static boolean isPermutationByHash(int[] a, int[] b) {
        // 边界处理：null 情况
        if (a == null || b == null) {
            return a == b;
        }
        // 长度不同直接返回false
        if (a.length != b.length) {
            return false;
        }

        Map<Integer, Integer> frequencyMap = new HashMap<>();

        // 第一步：统计数组a的元素频次
        for (int num : a) {
            // 若元素存在，频次+1；否则初始化为1
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // 第二步：校验数组b的元素频次
        for (int num : b) {
            // 若元素不存在或频次已为0，说明b有多余元素，返回false
            int count = frequencyMap.getOrDefault(num, 0) - 1;
            if (count < 0) {
                return false;
            }
            frequencyMap.put(num, count);
        }

        // 第三步：确保所有元素频次为0（避免a有多余元素，因长度相同，此步可省略但更严谨）
        for (int count : frequencyMap.values()) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    // 测试用例
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {4, 3, 2, 1};
        int[] arr3 = {1, 2, 3, 5};
        int[] arr4 = null;
        int[] arr5 = {1, 2, 2, 3};
        int[] arr6 = {2, 1, 3, 2};

        System.out.println(isPermutationByHash(arr1, arr2)); // true
        System.out.println(isPermutationByHash(arr1, arr3)); // false
        System.out.println(isPermutationByHash(arr4, null)); // true
        System.out.println(isPermutationByHash(arr5, arr6)); // true
    }
}