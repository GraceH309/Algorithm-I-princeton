import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        // 边界条件：数组长度小于3，无法构成三元组
        if (n < 3) {
            return result;
        }

        // 排序：O(n log n)，满足题目要求
        Arrays.sort(nums);

        // 遍历固定第一个元素 a = nums[i]
        for (int i = 0; i < n - 2; i++) { // i 最大为 n-3，保证右侧有两个元素
            // 剪枝：排序后 a > 0，右侧均为正数，和不可能为 0
            if (nums[i] > 0) {
                break;
            }

            // 去重：跳过重复的 a（避免重复三元组）
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int target = -nums[i]; // 目标：b + c = target
            int left = i + 1;      // 左指针（b 的起始位置）
            int right = n - 1;     // 右指针（c 的起始位置）

            // 双指针搜索 b 和 c
            while (left < right) {
                int currentSum = nums[left] + nums[right];

                if (currentSum == target) {
                    // 找到有效三元组，加入结果集
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // 去重：跳过所有与当前 b 相同的元素
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // 去重：跳过所有与当前 c 相同的元素
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    // 移动双指针，继续搜索下一组可能
                    left++;
                    right--;
                } else if (currentSum < target) {
                    // 和偏小，需要增大 b，左指针右移
                    left++;
                } else {
                    // 和偏大，需要减小 c，右指针左移
                    right--;
                }
            }
        }

        return result;
    }

    // 测试用例
    public static void main(String[] args) {
        // 测试用例 1：常规情况（含重复元素）
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        System.out.println("测试用例 1 结果：" + threeSum(nums1));
        // 预期输出：[[-1, -1, 2], [-1, 0, 1]]

        // 测试用例 2：全 0 数组
        int[] nums2 = {0, 0, 0, 0};
        System.out.println("测试用例 2 结果：" + threeSum(nums2));
        // 预期输出：[[0, 0, 0]]

        // 测试用例 3：无有效三元组
        int[] nums3 = {1, -1, -1, 0, 2};
        System.out.println("测试用例 3 结果：" + threeSum(nums3));
        // 预期输出：[[-1, 0, 1]]

        // 测试用例 4：边界情况（长度为 3）
        int[] nums4 = {1, -2, 1};
        System.out.println("测试用例 4 结果：" + threeSum(nums4));
        // 预期输出：[[-2, 1, 1]]

        // 测试用例 5：空数组/长度不足 3
        int[] nums5 = {};
        int[] nums6 = {0};
        System.out.println("测试用例 5 结果：" + threeSum(nums5)); // 预期：[]
        System.out.println("测试用例 6 结果：" + threeSum(nums6)); // 预期：[]
    }
}