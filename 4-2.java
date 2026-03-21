import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * 带最大值的堆栈：支持 push、pop、getMax 三种 O(1) 操作
 */
public class MaxStack {
    // 主栈：存储所有元素
    private final Deque<Double> dataStack;
    // 辅助栈：存储阶段性最大值，栈顶始终是当前最大值
    private final Deque<Double> maxStack;

    // 构造函数：初始化双栈
    public MaxStack() {
        dataStack = new LinkedList<>();
        maxStack = new LinkedList<>();
    }

    /**
     * 压栈操作：同时维护辅助栈的最大值状态
     * @param x 待压入的实数（支持 double 类型，满足“元素是实数”要求）
     */
    public void push(double x) {
        // 主栈压入元素
        dataStack.push(x);
        // 辅助栈：空栈或当前元素 >= 栈顶最大值时，压入当前元素
        if (maxStack.isEmpty() || x >= maxStack.peek()) {
            maxStack.push(x);
        }
    }

    /**
     * 弹栈操作：同步更新辅助栈的最大值状态
     * @return 弹出的元素
     * @throws NoSuchElementException 栈为空时抛出
     */
    public double pop() {
        if (dataStack.isEmpty()) {
            throw new NoSuchElementException("栈为空，无法执行 pop 操作");
        }
        // 主栈弹出元素
        double popped = dataStack.pop();
        // 若弹出的是当前最大值，辅助栈同步弹出
        if (popped == maxStack.peek()) {
            maxStack.pop();
        }
        return popped;
    }

    /**
     * 获取当前栈的最大值
     * @return 栈中最大值
     * @throws NoSuchElementException 栈为空时抛出
     */
    public double getMax() {
        if (maxStack.isEmpty()) {
            throw new NoSuchElementException("栈为空，无最大值");
        }
        // 辅助栈顶即为当前最大值
        return maxStack.peek();
    }

    /**
     * 辅助方法：判断栈是否为空（可选，方便测试）
     */
    public boolean isEmpty() {
        return dataStack.isEmpty();
    }

    // 测试用例
    public static void main(String[] args) {
        MaxStack stack = new MaxStack();

        // 测试 1：常规压栈 + 获取最大值
        stack.push(3.5);
        stack.push(1.2);
        stack.push(4.8);
        stack.push(4.8); // 重复最大值
        System.out.println("当前最大值：" + stack.getMax()); // 预期：4.8

        // 测试 2：弹栈 + 验证最大值更新
        System.out.println("弹出元素：" + stack.pop()); // 弹出 4.8
        System.out.println("当前最大值：" + stack.getMax()); // 预期：4.8（剩余一个 4.8）
        System.out.println("弹出元素：" + stack.pop()); // 弹出 4.8
        System.out.println("当前最大值：" + stack.getMax()); // 预期：3.5

        // 测试 3：继续弹栈至空
        System.out.println("弹出元素：" + stack.pop()); // 弹出 1.2
        System.out.println("弹出元素：" + stack.pop()); // 弹出 3.5
        System.out.println("栈是否为空：" + stack.isEmpty()); // 预期：true

        // 测试 4：空栈操作（触发异常）
        try {
            stack.pop();
        } catch (NoSuchElementException e) {
            System.out.println("异常捕获：" + e.getMessage()); // 预期：栈为空，无法执行 pop 操作
        }
        try {
            stack.getMax();
        } catch (NoSuchElementException e) {
            System.out.println("异常捕获：" + e.getMessage()); // 预期：栈为空，无最大值
        }
    }
}