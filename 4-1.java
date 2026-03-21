import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * 双栈实现队列（摊销O(1)复杂度）
 * 核心：懒加载转移元素，确保每次操作的摊销栈操作次数为常数
 * @param <E> 队列元素类型
 */
public class DoubleStackQueue<E> {
    // 入队栈：存储待入队的元素（队尾方向）
    private final Deque<E> stackIn;
    // 出队栈：存储待出队的元素（队首方向）
    private final Deque<E> stackOut;

    public DoubleStackQueue() {
        // 用ArrayDeque实现栈（效率高于Java遗留类Stack，推荐使用Deque接口）
        stackIn = new ArrayDeque<>();
        stackOut = new ArrayDeque<>();
    }

    /**
     * 入队操作：直接压入stackIn（O(1)栈操作）
     * @param element 待入队元素
     */
    public void enqueue(E element) {
        stackIn.push(element);
    }

    /**
     * 出队操作：摊销O(1)栈操作
     * 仅当stackOut为空时，转移stackIn所有元素到stackOut（一次性O(k)，但每个元素仅转移1次）
     * @return 队首元素
     * @throws NoSuchElementException 队列为空时抛出
     */
    public E dequeue() {
        transferIfNeeded(); // 懒加载转移
        if (stackOut.isEmpty()) {
            throw new NoSuchElementException("队列已空，无法出队");
        }
        return stackOut.pop(); // 直接弹出stackOut顶部（队首）
    }

    /**
     * 查看队首元素：摊销O(1)栈操作
     * @return 队首元素（不弹出）
     * @throws NoSuchElementException 队列为空时抛出
     */
    public E peek() {
        transferIfNeeded(); // 懒加载转移
        if (stackOut.isEmpty()) {
            throw new NoSuchElementException("队列为空，无队首元素");
        }
        return stackOut.peek(); // 查看stackOut顶部（队首）
    }

    /**
     * 判断队列是否为空（O(1)）
     * @return true=空，false=非空
     */
    public boolean isEmpty() {
        return stackIn.isEmpty() && stackOut.isEmpty();
    }

    /**
     * 获取队列大小（O(1)）
     * @return 队列中元素个数
     */
    public int size() {
        return stackIn.size() + stackOut.size();
    }

    /**
     * 辅助方法：当stackOut为空时，将stackIn所有元素转移到stackOut
     * 转移后，stackIn为空，stackOut元素顺序与原队列一致（先进先出）
     */
    private void transferIfNeeded() {
        if (stackOut.isEmpty()) {
            // 一次性转移所有元素（每个元素仅转移1次）
            while (!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
    }
}