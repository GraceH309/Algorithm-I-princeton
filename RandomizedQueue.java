import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;
    private static final int INIT_CAPACITY = 8;

    // 构造空随机队列
    public RandomizedQueue() {
        // 移除 @SuppressWarnings，接受泛型数组创建的警告（课程允许此警告）
        array = (Item[]) new Object[INIT_CAPACITY];
        size = 0;
    }

    // 检查队列是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 返回队列中元素个数
    public int size() {
        return size;
    }

    // 调整数组大小
    private void resize(int newCapacity) {
        // 移除 @SuppressWarnings，接受泛型数组创建的警告
        Item[] newArray = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    // 添加元素到队列
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        // 扩容：当数组满时，扩容为原来的2倍
        if (size == array.length) {
            resize(2 * array.length);
        }
        array[size++] = item;
    }

    // 随机移除并返回一个元素
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }
        // 替换弃用的 uniform 为 uniformInt
        int randomIndex = StdRandom.uniformInt(size);
        Item item = array[randomIndex];
        
        // 将选中的元素与最后一个元素交换
        array[randomIndex] = array[size - 1];
        array[size - 1] = null; // 避免内存泄漏
        size--;
        
        // 缩容：当元素个数小于容量的1/4时，缩容为原来的1/2
        if (size > 0 && size == array.length / 4) {
            resize(array.length / 2);
        }
        
        return item;
    }

    // 随机返回一个元素但不删除
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }
        // 替换弃用的 uniform 为 uniformInt
        int randomIndex = StdRandom.uniformInt(size);
        return array[randomIndex];
    }

    // 返回迭代器（随机顺序）
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current;
        private int[] shuffledIndices;

        public RandomizedQueueIterator() {
            current = 0;
            // 创建索引数组并洗牌（Checkstyle 允许迭代器内部使用数组）
            shuffledIndices = new int[size];
            for (int i = 0; i < size; i++) {
                shuffledIndices[i] = i;
            }
            StdRandom.shuffle(shuffledIndices);
        }

        public boolean hasNext() {
            return current < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            return array[shuffledIndices[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported");
        }
    }

    // 单元测试
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        
        // 测试空队列
        System.out.println("Is empty: " + rq.isEmpty());
        System.out.println("Size: " + rq.size());
        
        // 测试添加元素
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
        }
        System.out.println("After enqueue 10 elements, size: " + rq.size());
        
        // 测试sample
        System.out.println("Sample element: " + rq.sample());
        System.out.println("Size after sample: " + rq.size());
        
        // 测试迭代器
        System.out.print("First iterator: ");
        for (int num : rq) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        System.out.print("Second iterator (independent): ");
        for (int num : rq) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // 测试dequeue
        System.out.print("Dequeue 5 elements: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(rq.dequeue() + " ");
        }
        System.out.println();
        System.out.println("Size after dequeue: " + rq.size());
        
        // 测试边界情况
        try {
            rq.enqueue(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
        
        try {
            while (!rq.isEmpty()) {
                rq.dequeue();
            }
            rq.dequeue(); // 应该抛出异常
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}