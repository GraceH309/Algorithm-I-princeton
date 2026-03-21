import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    // 双向链表节点
    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    // 构造空双端队列
    public Deque() {
        first = null;
        last = null;
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

    // 向队列前端添加元素
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = null;
        newNode.next = first;

        if (isEmpty()) {
            last = newNode;
        } else {
            first.prev = newNode;
        }
        first = newNode;
        size++;
    }

    // 向队列后端添加元素
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        newNode.prev = last;

        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    // 从队列前端移除并返回元素
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = first.item;
        first = first.next;
        size--;

        if (isEmpty()) {
            last = null; // 避免孤儿节点
        } else {
            first.prev = null;
        }
        return item;
    }

    // 从队列后端移除并返回元素
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = last.item;
        last = last.prev;
        size--;

        if (isEmpty()) {
            first = null; // 避免孤儿节点
        } else {
            last.next = null;
        }
        return item;
    }

    // 返回迭代器（从前端到后端）
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported");
        }
    }

    // 单元测试
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        
        // 测试空队列
        System.out.println("Is empty: " + deque.isEmpty());
        System.out.println("Size: " + deque.size());
        
        // 测试添加元素
        deque.addFirst("A");
        deque.addLast("B");
        deque.addFirst("C");
        deque.addLast("D");
        System.out.println("After adding elements, size: " + deque.size());
        
        // 测试迭代器
        System.out.print("Elements (front to back): ");
        for (String s : deque) {
            System.out.print(s + " ");
        }
        System.out.println();
        
        // 测试移除元素
        System.out.println("Remove first: " + deque.removeFirst());
        System.out.println("Remove last: " + deque.removeLast());
        System.out.println("Size after removal: " + deque.size());
        
        // 测试迭代器
        System.out.print("Remaining elements: ");
        for (String s : deque) {
            System.out.print(s + " ");
        }
        System.out.println();
        
        // 测试边界情况
        try {
            deque.addFirst(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
        
        try {
            deque.removeFirst();
            deque.removeFirst();
            deque.removeFirst(); // 应该抛出异常
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}