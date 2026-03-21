import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * 支持动态中位数的数据类型：
 * - 对数时间插入元素
 * - 常数时间查找中位数（偶数个时返回较低中位数）
 * - 对数时间删除中位数
 */
public class DynamicMedian {
    // 大顶堆：存储左半部分较小元素，堆顶为较低中位数
    private final PriorityQueue<Integer> maxHeap;
    // 小顶堆：存储右半部分较大元素
    private final PriorityQueue<Integer> minHeap;

    public DynamicMedian() {
        // 大顶堆：通过逆序比较器实现（默认小顶堆）
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        // 小顶堆：默认自然顺序（从小到大）
        minHeap = new PriorityQueue<>();
    }

    /**
     * 插入元素（对数时间复杂度 O(log n)）
     * @param num 待插入的整数
     */
    public void insert(int num) {
        // 规则：小于等于大顶堆堆顶 → 插入大顶堆；否则插入小顶堆
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        // 插入后平衡两个堆的大小，确保满足平衡条件
        balanceHeaps();
    }

    /**
     * 查找中位数（常数时间复杂度 O(1)）
     * @return 较低的中位数（偶数个元素时）或唯一中位数（奇数个元素时）
     * @throws NoSuchElementException 当容器为空时抛出
     */
    public int findMedian() {
        if (isEmpty()) {
            throw new NoSuchElementException("容器为空，无法查找中位数");
        }
        // 中位数就是大顶堆的堆顶
        return maxHeap.peek();
    }

    /**
     * 删除中位数（对数时间复杂度 O(log n)）
     * @return 被删除的中位数
     * @throws NoSuchElementException 当容器为空时抛出
     */
    public int removeMedian() {
        if (isEmpty()) {
            throw new NoSuchElementException("容器为空，无法删除中位数");
        }
        // 删除大顶堆堆顶（中位数）
        int median = maxHeap.poll();
        // 删除后重新平衡堆结构
        balanceHeaps();
        return median;
    }

    /**
     * 检查容器是否为空
     * @return 空返回 true，否则返回 false
     */
    public boolean isEmpty() {
        return maxHeap.isEmpty() && minHeap.isEmpty();
    }

    /**
     * 获取容器中元素的个数
     * @return 元素总数
     */
    public int size() {
        return maxHeap.size() + minHeap.size();
    }

    /**
     * 平衡两个堆的大小：确保 maxHeap.size() == minHeap.size() 或 maxHeap.size() = minHeap.size() + 1
     */
    private void balanceHeaps() {
        // 情况1：大顶堆比小顶堆多 2 个元素 → 移一个到小顶堆
        if (maxHeap.size() - minHeap.size() > 1) {
            minHeap.offer(maxHeap.poll());
        }
        // 情况2：小顶堆比大顶堆多 1 个元素 → 移一个到大顶堆
        else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
        // 其他情况（大小相等或大顶堆多 1 个）无需平衡
    }

    // 测试用例
    public static void main(String[] args) {
        DynamicMedian dm = new DynamicMedian();

        // 测试插入和查找
        dm.insert(3);
        dm.insert(1);
        dm.insert(2);
        System.out.println("当前中位数：" + dm.findMedian()); // 预期 2（元素 [1,2,3]，奇数个）

        dm.insert(4);
        System.out.println("当前中位数：" + dm.findMedian()); // 预期 2（元素 [1,2,3,4]，偶数个，较低中位数）

        // 测试删除
        System.out.println("删除中位数：" + dm.removeMedian()); // 预期 2
        System.out.println("当前中位数：" + dm.findMedian()); // 预期 3（元素 [1,3,4]，奇数个）

        System.out.println("删除中位数：" + dm.removeMedian()); // 预期 3
        System.out.println("当前中位数：" + dm.findMedian()); // 预期 1（元素 [1,4]，偶数个，较低中位数）

        System.out.println("删除中位数：" + dm.removeMedian()); // 预期 1
        System.out.println("当前中位数：" + dm.findMedian()); // 预期 4（元素 [4]，奇数个）

        System.out.println("删除中位数：" + dm.removeMedian()); // 预期 4
        System.out.println("容器是否为空：" + dm.isEmpty()); // 预期 true

        // 测试空容器异常
        try {
            dm.findMedian();
        } catch (NoSuchElementException e) {
            System.out.println("异常捕获成功：" + e.getMessage()); // 预期输出异常信息
        }
    }
}