import java.util.TreeSet;

/**
 * 带删除的继承数据类型：支持删除元素和查找≥x的最小继承者
 * 底层基于红黑树（TreeSet），所有操作最坏时间复杂度O(log n)
 */
public class SuccessorWithDeletion {
    private final TreeSet<Integer> set;  // 存储当前存在的元素（红黑树实现）
    private final int n;                 // 初始集合的大小（元素范围0~n-1）

    /**
     * 构造函数：初始化集合 S = {0, 1, ..., n-1}
     * @param n 初始集合的元素个数（元素范围0到n-1）
     * @throws IllegalArgumentException 若n≤0，抛出非法参数异常
     */
    public SuccessorWithDeletion(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("初始集合大小n必须为正整数：" + n);
        }
        this.n = n;
        this.set = new TreeSet<>();
        // 初始化集合：添加0到n-1的所有整数
        for (int i = 0; i < n; i++) {
            set.add(i);
        }
    }

    /**
     * 从集合中删除元素x（若x不存在，忽略操作）
     * @param x 要删除的元素
     * @throws IllegalArgumentException 若x超出0~n-1范围，抛出异常
     */
    public void delete(int x) {
        validate(x);  // 验证x的合法性
        set.remove(x); // TreeSet删除操作：O(log n)
    }

    /**
     * 查找x的继承者：集合中最小的y ≥ x
     * @param x 目标元素
     * @return 继承者y；若不存在（所有元素都小于x），返回-1
     * @throws IllegalArgumentException 若x超出0~n-1范围，抛出异常
     */
    public int findSuccessor(int x) {
        validate(x);  // 验证x的合法性
        // ceiling(x)：返回≥x的最小元素，不存在则返回null（O(log n)）
        Integer successor = set.ceiling(x);
        return successor != null ? successor : -1;
    }

    /**
     * 验证元素x的合法性（必须在0~n-1范围内）
     * @param x 待验证的元素
     */
    private void validate(int x) {
        if (x < 0 || x >= n) {
            throw new IllegalArgumentException("元素x超出合法范围0~" + (n-1) + "：" + x);
        }
    }

    // 测试用例
    public static void main(String[] args) {
        // 初始化集合S = {0,1,2,3,4,5,6,7,8,9}（n=10）
        SuccessorWithDeletion swd = new SuccessorWithDeletion(10);

        // 测试1：查找存在的继承者
        System.out.println(swd.findSuccessor(3));  // 输出3（3存在，且是≥3的最小元素）
        System.out.println(swd.findSuccessor(5));  // 输出5

        // 测试2：删除元素后查找继承者
        swd.delete(3);
        System.out.println(swd.findSuccessor(3));  // 输出4（3已删除，≥3的最小元素是4）
        swd.delete(4);
        System.out.println(swd.findSuccessor(3));  // 输出5（3、4已删除，≥3的最小元素是5）

        // 测试3：查找无继承者的情况
        swd.delete(5);
        swd.delete(6);
        swd.delete(7);
        swd.delete(8);
        swd.delete(9);
        System.out.println(swd.findSuccessor(5));  // 输出-1（所有≥5的元素都已删除）

        // 测试4：删除不存在的元素（无效果）
        swd.delete(3);  // 3已删除，再次删除无影响
        System.out.println(swd.findSuccessor(3));  // 仍输出5

        // 测试5：非法元素（抛出异常）
        // swd.delete(10);  // 抛出异常：x=10超出0~9范围
        // swd.findSuccessor(-1);  // 抛出异常：x=-1非法
    }
}