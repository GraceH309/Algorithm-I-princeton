import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机优先队列（基于二进制大顶堆）
 * 支持：insert()、delMax()、sample()、delRandom()
 * 时间复杂度：
 * - sample()：O(1)
 * - delRandom()：O(log n)
 * - insert()/delMax()：O(log n)
 */
public class RandomPriorityQueue<T extends Comparable<T>> {
    private T[] heap;       // 底层数组存储堆元素
    private int size;       // 当前元素个数
    private static final int DEFAULT_CAPACITY = 10; // 默认初始容量

    // 无参构造（默认容量）
    @SuppressWarnings("unchecked")
    public RandomPriorityQueue() {
        heap = (T[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }

    // 带初始容量的构造
    @SuppressWarnings("unchecked")
    public RandomPriorityQueue(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("初始容量必须大于0");
        }
        heap = (T[]) new Comparable[initialCapacity];
        size = 0;
    }

    /**
     * 插入元素（大顶堆）
     * 时间复杂度：O(log n)
     */
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException("插入元素不能为null");
        }
        // 题目要求不考虑数组扩容，直接插入（超出容量会抛ArrayIndexOutOfBoundsException）
        heap[size] = item;
        swim(size); // 上浮调整堆
        size++;
    }

    /**
     * 删除并返回最大值（堆顶）
     * 时间复杂度：O(log n)
     */
    public T delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列为空，无法删除最大值");
        }
        T max = heap[0];
        swap(0, size - 1); // 堆顶与最后一个元素交换
        heap[size - 1] = null; // 避免内存泄漏
        size--;
        sink(0); // 下沉调整堆
        return max;
    }

    /**
     * 随机采样（返回任意一个剩余元素）
     * 时间复杂度：O(1)
     */
    public T sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列为空，无法采样");
        }
        // 生成 [0, size-1] 的随机索引
        int randomIndex = ThreadLocalRandom.current().nextInt(size);
        return heap[randomIndex];
    }

    /**
     * 随机删除并返回一个元素
     * 时间复杂度：O(log n)
     */
    public T delRandom() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列为空，无法删除随机元素");
        }
        // 1. 生成随机索引
        int randomIndex = ThreadLocalRandom.current().nextInt(size);
        // 2. 保存要删除的元素
        T deletedItem = heap[randomIndex];
        // 3. 交换随机索引与最后一个元素（避免破坏堆结构）
        swap(randomIndex, size - 1);
        // 4. 清空最后一个位置，减小size
        heap[size - 1] = null;
        size--;
        // 5. 调整堆：交换后的元素可能需要上浮或下沉（仅当随机索引不是原最后一个元素时）
        if (randomIndex < size) {
            swim(randomIndex);
            sink(randomIndex);
        }
        return deletedItem;
    }

    /**
     * 检查队列是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 返回队列元素个数
     */
    public int size() {
        return size;
    }

    // ------------------------------ 堆核心辅助方法 ------------------------------
    /**
     * 上浮：将索引k的元素向上调整到正确位置（大顶堆）
     * 当元素 > 父节点时，交换父子节点
     */
    private void swim(int k) {
        while (k > 0) {
            int parent = (k - 1) / 2; // 父节点索引
            if (compare(k, parent) <= 0) {
                break; // 元素 <= 父节点，满足大顶堆，停止上浮
            }
            swap(k, parent);
            k = parent; // 继续向上检查
        }
    }

    /**
     * 下沉：将索引k的元素向下调整到正确位置（大顶堆）
     * 与较大的子节点交换，直到元素 >= 所有子节点
     */
    private void sink(int k) {
        while (true) {
            int left = 2 * k + 1;  // 左子节点索引
            int right = 2 * k + 2; // 右子节点索引
            int largest = k;       // 初始化最大元素为当前节点

            // 找到左、右子节点中较大的那个
            if (left < size && compare(left, largest) > 0) {
                largest = left;
            }
            if (right < size && compare(right, largest) > 0) {
                largest = right;
            }

            if (largest == k) {
                break; // 没有比当前节点大的子节点，满足大顶堆，停止下沉
            }
            swap(k, largest);
            k = largest; // 继续向下检查
        }
    }

    /**
     * 比较索引i和j的元素大小
     * 返回正数：heap[i] > heap[j]；负数：heap[i] < heap[j]；0：相等
     */
    private int compare(int i, int j) {
        return heap[i].compareTo(heap[j]);
    }

    /**
     * 交换索引i和j的元素
     */
    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // ------------------------------ 测试用例 ------------------------------
    public static void main(String[] args) {
        RandomPriorityQueue<Integer> rpQueue = new RandomPriorityQueue<>();

        // 1. 插入元素
        rpQueue.insert(5);
        rpQueue.insert(3);
        rpQueue.insert(8);
        rpQueue.insert(1);
        rpQueue.insert(10);
        rpQueue.insert(7);
        System.out.println("初始队列大小：" + rpQueue.size()); // 预期6

        // 2. 测试sample()（随机返回，结果可能不同）
        System.out.println("\n=== 测试sample() ===");
        System.out.println("采样1：" + rpQueue.sample());
        System.out.println("采样2：" + rpQueue.sample());
        System.out.println("采样3：" + rpQueue.sample());

        // 3. 测试delRandom()（删除随机元素，保持堆结构）
        System.out.println("\n=== 测试delRandom() ===");
        System.out.println("删除随机元素：" + rpQueue.delRandom());
        System.out.println("删除后大小：" + rpQueue.size()); // 预期5
        System.out.println("删除随机元素：" + rpQueue.delRandom());
        System.out.println("删除后大小：" + rpQueue.size()); // 预期4

        // 4. 验证堆结构（通过delMax()检查最大值是否正确）
        System.out.println("\n=== 验证堆结构（delMax()） ===");
        System.out.println("删除最大值：" + rpQueue.delMax()); // 预期10（若未被随机删除）
        System.out.println("删除最大值：" + rpQueue.delMax()); // 预期8（若未被随机删除）
        System.out.println("删除最大值：" + rpQueue.delMax()); // 预期7/5（取决于随机删除结果）
        System.out.println("删除最大值：" + rpQueue.delMax()); // 预期剩余最大值
        System.out.println("队列是否为空：" + rpQueue.isEmpty()); // 预期true

        // 5. 测试空队列异常
        System.out.println("\n=== 测试空队列异常 ===");
        try {
            rpQueue.sample();
        } catch (NoSuchElementException e) {
            System.out.println("sample()异常捕获：" + e.getMessage());
        }
        try {
            rpQueue.delRandom();
        } catch (NoSuchElementException e) {
            System.out.println("delRandom()异常捕获：" + e.getMessage());
        }
    }
}