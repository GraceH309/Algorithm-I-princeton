import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        int count = 0;

        while (!StdIn.isEmpty()) {
            String currentWord = StdIn.readString();
            count++;

            // 核心修改：用 StdRandom.bernoulli(1.0 / count) 实现 1/count 概率
            // bernoulli(p) 以概率 p 返回 true，完全等价原逻辑，且满足课程要求
            if (StdRandom.bernoulli(1.0 / count)) {
                champion = currentWord;
            }
        }

        // 修复 System.exit() 错误：无输入时直接打印提示（不终止程序）
        if (champion != null) {
            System.out.println(champion);
        } else {
            System.out.println("未输入任何单词");
        }
    }
}