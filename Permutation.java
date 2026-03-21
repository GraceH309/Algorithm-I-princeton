import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please provide exactly one command line argument");
        }
        
        int k = Integer.parseInt(args[0]);
        if (k == 0) {
            return; // 无需输出
        }
        
        // 只创建一个 RandomizedQueue 对象（满足 Checkstyle 要求）
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int count = 0;
        
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            count++;
            
            if (count <= k) {
                // 前k个元素直接加入队列
                rq.enqueue(s);
            } else {
                // 替换弃用的 uniform 为 uniformInt
                int random = StdRandom.uniformInt(count);
                if (random < k) {
                    rq.dequeue();
                    rq.enqueue(s);
                }
            }
        }
        
        // 输出k个随机元素（不使用数组，直接迭代输出）
        int outputCount = 0;
        for (String s : rq) {
            if (outputCount >= k) {
                break;
            }
            StdOut.println(s);
            outputCount++;
        }
    }
}