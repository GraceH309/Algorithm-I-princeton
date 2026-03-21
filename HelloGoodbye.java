public class HelloGoodbye {
    public static void main(String[] args) {
        // 假设命令行传入两个参数（题目示例明确输入两个名字）
        String name1 = args[0];
        String name2 = args[1];
        
        // 问候语：按参数顺序拼接
        System.out.printf("Hello %s and %s.%n", name1, name2);
        // 告别语：按参数倒序拼接
        System.out.printf("Goodbye %s and %s.%n", name2, name1);
    }
}