public class BitonicSearch {

    // ==================== 标准版本（~3lg n 比较）====================
    public static boolean standardSearch(int[] nums, int target) {
        int n = nums.length;
        if (n < 1) {
            return false;
        }

        // 步骤1：二分查找峰值（O(lg n)）
        int peakIndex = findPeak(nums);

        // 步骤2：搜索左侧递增段（O(lg n)）
        boolean foundInLeft = binarySearchIncr(nums, target, 0, peakIndex);
        if (foundInLeft) {
            return true;
        }

        // 步骤3：搜索右侧递减段（O(lg n)）
        return binarySearchDecr(nums, target, peakIndex, n - 1);
    }

    // 辅助方法：二分查找峰值（严格递增后严格递减，唯一峰值）
    private static int findPeak(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2; // 避免溢出
            if (nums[mid] < nums[mid + 1]) {
                // 仍在递增，峰值在右侧
                low = mid + 1;
            } else {
                // 开始递减，峰值在左侧（含mid）
                high = mid;
            }
        }
        return low; // 循环结束时 low == high，即峰值索引
    }

    // 辅助方法：递增数组二分查找
    private static boolean binarySearchIncr(int[] nums, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    // 辅助方法：递减数组二分查找
    private static boolean binarySearchDecr(int[] nums, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] > target) {
                // 递减段：target更小，向右找
                left = mid + 1;
            } else {
                // target更大，向左找
                right = mid - 1;
            }
        }
        return false;
    }

    // ==================== 签名版本（~2lg n 比较）====================
    public static boolean signatureSearch(int[] nums, int target) {
        int n = nums.length;
        if (n < 1) {
            return false;
        }

        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            // 找到目标，直接返回
            if (nums[mid] == target) {
                return true;
            }

            // 判断 mid 所在段：递增段（mid < high 避免 mid+1 越界）
            if (mid < high && nums[mid] < nums[mid + 1]) {
                if (target < nums[mid]) {
                    // 可能在左侧递增段，直接二分搜索
                    if (binarySearchIncr(nums, target, low, mid)) {
                        return true;
                    }
                    // 左侧未找到，继续找峰值（缩小到右侧）
                    low = mid + 1;
                } else {
                    // target > nums[mid]，仅可能在右侧，继续找峰值
                    low = mid + 1;
                }
            } else {
                // mid 所在段：递减段（右侧[mid, high]递减）
                if (target < nums[mid]) {
                    // 可能在右侧递减段，直接二分搜索
                    if (binarySearchDecr(nums, target, mid, high)) {
                        return true;
                    }
                    // 右侧未找到，继续找峰值（缩小到左侧）
                    high = mid - 1;
                } else {
                    // target > nums[mid]，仅可能在左侧，继续找峰值
                    high = mid - 1;
                }
            }
        }

        // 遍历结束未找到
        return false;
    }

    // ==================== 测试用例 ====================
    public static void main(String[] args) {
        // 测试用例1：target在递增段
        int[] nums1 = {1, 3, 5, 4, 2};
        System.out.println("标准版-递增段：" + standardSearch(nums1, 3)); // true
        System.out.println("签名版-递增段：" + signatureSearch(nums1, 3)); // true

        // 测试用例2：target在递减段
        int[] nums2 = {2, 4, 6, 5, 3, 1};
        System.out.println("标准版-递减段：" + standardSearch(nums2, 3)); // true
        System.out.println("签名版-递减段：" + signatureSearch(nums2, 3)); // true

        // 测试用例3：target是峰值
        int[] nums3 = {1, 5, 3};
        System.out.println("标准版-峰值：" + standardSearch(nums3, 5)); // true
        System.out.println("签名版-峰值：" + signatureSearch(nums3, 5)); // true

        // 测试用例4：target不存在
        int[] nums4 = {1, 4, 6, 5, 2};
        System.out.println("标准版-不存在：" + standardSearch(nums4, 7)); // false
        System.out.println("签名版-不存在：" + signatureSearch(nums4, 7)); // false

        // 测试用例5：边界情况（数组长度=2，递增）
        int[] nums5 = {1, 2};
        System.out.println("标准版-长度2递增：" + standardSearch(nums5, 2)); // true
        System.out.println("签名版-长度2递增：" + signatureSearch(nums5, 2)); // true

        // 测试用例6：边界情况（数组长度=2，递减）
        int[] nums6 = {3, 1};
        System.out.println("标准版-长度2递减：" + standardSearch(nums6, 1)); // true
        System.out.println("签名版-长度2递减：" + signatureSearch(nums6, 1)); // true
    }
}