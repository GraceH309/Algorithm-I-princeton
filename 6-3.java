import java.util.Random;

// 单链表节点定义
class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

public class LinkedListShuffler {
    private final Random random;

    public LinkedListShuffler() {
        this.random = new Random(); // 随机数生成器（保证均匀性）
    }

    /**
     * 主函数：洗牌单链表
     * @param head 链表头节点
     * @return 洗牌后的链表头节点
     */
    public ListNode shuffle(ListNode head) {
        if (head == null || head.next == null) {
            return head; // 空链表或长度1，无需洗牌
        }
        int totalLength = getListLength(head); // 计算总长度（仅需一次）
        return shuffleRecursive(head, totalLength);
    }

    /**
     * 递归分治洗牌
     * @param head 当前链表头节点
     * @param length 当前链表长度
     * @return 洗牌后的链表头节点
     */
    private ListNode shuffleRecursive(ListNode head, int length) {
        // Base Case：长度≤1，直接返回（已有序/无需洗牌）
        if (length <= 1) {
            return head;
        }

        // 1. 拆分：左半部分长度leftLen，右半部分rightLen
        int leftLen = length / 2;
        int rightLen = length - leftLen;
        ListNode rightHead = splitList(head, leftLen); // 拆分后head为左链表，rightHead为右链表

        // 2. 递归洗牌左右两半
        ListNode shuffledLeft = shuffleRecursive(head, leftLen);
        ListNode shuffledRight = shuffleRecursive(rightHead, rightLen);

        // 3. 概率合并两个已洗牌的链表
        return mergeRandomly(shuffledLeft, leftLen, shuffledRight, rightLen);
    }

    /**
     * 拆分链表：将链表从第leftLen个节点后断开，返回右链表头节点
     * @param head 原链表头节点
     * @param leftLen 左链表长度
     * @return 右链表头节点
     */
    private ListNode splitList(ListNode head, int leftLen) {
        ListNode leftTail = head;
        // 遍历到左链表的尾节点（第leftLen个节点）
        for (int i = 1; i < leftLen; i++) {
            leftTail = leftTail.next;
        }
        ListNode rightHead = leftTail.next;
        leftTail.next = null; // 断开左右链表
        return rightHead;
    }

    /**
     * 概率合并两个已洗牌的链表（核心：按剩余长度比例随机选择）
     * @param left 左链表头节点
     * @param leftLen 左链表剩余长度
     * @param right 右链表头节点
     * @param rightLen 右链表剩余长度
     * @return 合并后的链表头节点
     */
    private ListNode mergeRandomly(ListNode left, int leftLen, ListNode right, int rightLen) {
        ListNode dummy = new ListNode(-1); // 虚拟头节点（简化合并）
        ListNode curr = dummy;

        while (left != null && right != null) {
            // 生成[0, leftLen+rightLen-1]的均匀随机数
            int randomIdx = random.nextInt(leftLen + rightLen);

            if (randomIdx < leftLen) {
                // 选左链表的节点：剩余长度比例为leftLen/(leftLen+rightLen)
                curr.next = left;
                left = left.next;
                leftLen--; // 左链表剩余长度减1
            } else {
                // 选右链表的节点：剩余长度比例为rightLen/(leftLen+rightLen)
                curr.next = right;
                right = right.next;
                rightLen--; // 右链表剩余长度减1
            }
            curr = curr.next;
        }

        // 拼接剩余节点（其中一个链表已空）
        curr.next = (left != null) ? left : right;
        return dummy.next;
    }

    /**
     * 辅助函数：计算链表长度
     * @param head 链表头节点
     * @return 链表长度
     */
    private int getListLength(ListNode head) {
        int len = 0;
        ListNode curr = head;
        while (curr != null) {
            len++;
            curr = curr.next;
        }
        return len;
    }

    /**
     * 辅助函数：打印链表（测试用）
     * @param head 链表头节点
     */
    public static void printList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    // 测试用例
    public static void main(String[] args) {
        LinkedListShuffler shuffler = new LinkedListShuffler();

        // 构建测试链表：1->2->3->4->5（长度5）
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.println("原链表：");
        printList(head);

        // 洗牌3次，验证随机性（每次结果应不同，且元素全包含）
        for (int i = 1; i <= 3; i++) {
            // 注意：拆分时会修改原链表next指针，需重新构建原链表
            ListNode newHead = buildTestList(new int[]{1,2,3,4,5});
            ListNode shuffled = shuffler.shuffle(newHead);
            System.out.printf("洗牌第%d次：", i);
            printList(shuffled);
        }
    }

    /**
     * 辅助函数：根据数组构建链表（测试用）
     * @param arr 输入数组
     * @return 链表头节点
     */
    private static ListNode buildTestList(int[] arr) {
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        for (int num : arr) {
            curr.next = new ListNode(num);
            curr = curr.next;
        }
        return dummy.next;
    }
}