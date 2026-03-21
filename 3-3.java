import java.util.Random;
import java.util.Scanner;

/**
 * 鸡蛋掉落问题 - 版本3：2枚鸡蛋，~2√n次抛掷
 * 核心策略：分块试探（先通过第1枚鸡蛋找块边界，再用第2枚鸡蛋逐层试块内楼层）
 */
public class EggDropVersion3 {

    // 模拟鸡蛋抛掷：返回是否破碎（楼层≥T则破碎）
    private static boolean dropEgg(int floor, int criticalFloor) {
        return floor >= criticalFloor;
    }

    // 核心逻辑：2枚鸡蛋找临界楼层T
    public static Result findCriticalFloor(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("楼层数n必须为正整数");
        }

        int blockSize = (int) Math.sqrt(n); // 分块大小：√n（核心参数）
        blockSize = Math.max(blockSize, 1); // 边界处理：n=1时块大小为1
        int egg1Count = 0; // 第1枚鸡蛋的抛掷次数
        int egg2Count = 0; // 第2枚鸡蛋的抛掷次数
        int currentBlockBoundary = blockSize; // 第1枚鸡蛋当前测试的块边界
        boolean egg1Broken = false; // 第1枚鸡蛋是否破碎

        // 步骤1：第1枚鸡蛋试块边界（找T所在的块）
        while (true) {
            egg1Count++;
            System.out.printf("第1枚鸡蛋 - 第%d次抛掷：第%d层 -> ", egg1Count, currentBlockBoundary);

            if (dropEgg(currentBlockBoundary, Result.criticalFloor)) {
                System.out.println("破碎");
                egg1Broken = true;
                break; // 破碎，确定T在当前块内
            } else {
                System.out.println("未破碎");
            }

            // 若当前边界已超过n层，说明T>n（即T=n+1）
            if (currentBlockBoundary >= n) {
                break;
            }

            // 移动到下一个块边界（避免超过n层）
            currentBlockBoundary = Math.min(currentBlockBoundary + blockSize, n);
        }

        // 情况1：第1枚鸡蛋未破碎（所有楼层都试完，T=n+1）
        if (!egg1Broken) {
            return new Result(n + 1, egg1Count + egg2Count);
        }

        // 情况2：第1枚鸡蛋破碎，确定块范围：[startFloor, currentBlockBoundary]
        int startFloor = currentBlockBoundary - blockSize + 1;
        System.out.printf("\n确定T在区间 [%d, %d] 内，用第2枚鸡蛋逐层试探\n", startFloor, currentBlockBoundary);

        // 步骤2：第2枚鸡蛋在块内逐层试探
        for (int floor = startFloor; floor <= currentBlockBoundary; floor++) {
            egg2Count++;
            System.out.printf("第2枚鸡蛋 - 第%d次抛掷：第%d层 -> ", egg2Count, floor);

            if (dropEgg(floor, Result.criticalFloor)) {
                System.out.println("破碎");
                return new Result(floor, egg1Count + egg2Count); // 找到T
            } else {
                System.out.println("未破碎");
            }
        }

        // 理论上不会走到这里（第1枚鸡蛋破碎时T一定在当前块内）
        return new Result(n + 1, egg1Count + egg2Count);
    }

    // 存储结果：临界楼层T和总抛掷次数
    static class Result {
        static int criticalFloor; // 全局临界楼层（模拟用）
        int t; // 找到的临界楼层
        int totalDrops; // 总抛掷次数

        public Result(int t, int totalDrops) {
            this.t = t;
            this.totalDrops = totalDrops;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // 1. 输入楼层数n
        System.out.print("请输入楼房的楼层数n：");
        int n = scanner.nextInt();
        if (n <= 0) {
            System.out.println("楼层数必须为正整数！");
            return;
        }

        // 2. 随机生成临界楼层T（1 ≤ T ≤ n+1）
        // T=n+1表示鸡蛋从n层掉落也不会破碎
        Result.criticalFloor = random.nextInt(n + 1) + 1;
        System.out.printf("\n模拟：临界楼层T = %d（程序内部随机生成，用户不可见）\n", Result.criticalFloor);
        System.out.println("----------------------------------------");

        // 3. 执行找临界楼层的逻辑
        Result result = findCriticalFloor(n);

        // 4. 输出结果
        System.out.println("----------------------------------------");
        System.out.printf("最终结果：\n");
        System.out.printf("临界楼层T = %d\n", result.t);
        System.out.printf("总抛掷次数 = %d\n", result.totalDrops);
        System.out.printf("理论最坏次数（2√n）≈ %d\n", (int) (2 * Math.sqrt(n)));

        scanner.close();
    }
}