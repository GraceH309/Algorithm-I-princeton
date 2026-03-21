import java.util.Random;

// 螺母类：封装尺寸，支持与螺栓的比较
class Nut {
    private final int size;

    public Nut(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    // 比较当前螺母与螺栓：-1(螺母小)、0(匹配)、1(螺母大)
    public int compare(Bolt bolt) {
        return Integer.compare(this.size, bolt.getSize());
    }

    @Override
    public String toString() {
        return String.valueOf(size);
    }
}

// 螺栓类：封装尺寸，支持与螺母的比较
class Bolt {
    private final int size;

    public Bolt(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    // 比较当前螺栓与螺母：-1(螺栓小)、0(匹配)、1(螺栓大)
    public int compare(Nut nut) {
        return Integer.compare(this.size, nut.getSize());
    }

    @Override
    public String toString() {
        return String.valueOf(size);
    }
}

public class NutBoltMatcher {
    private static final Random RANDOM = new Random();

    /**
     * 入口方法：实现螺母和螺栓的配对
     * @param nuts 螺母数组
     * @param bolts 螺栓数组
     * @throws IllegalArgumentException 输入不合法时抛出
     */
    public static void match(Nut[] nuts, Bolt[] bolts) {
        // 输入校验
        if (nuts == null || bolts == null) {
            throw new IllegalArgumentException("数组不能为 null");
        }
        if (nuts.length != bolts.length) {
            throw new IllegalArgumentException("螺母和螺栓数量必须相等");
        }
        if (nuts.length == 0) {
            return; // 空数组直接返回
        }

        // 递归处理整个数组
        matchRecursive(nuts, bolts, 0, nuts.length - 1);
    }

    /**
     * 递归分治函数：处理 [low, high] 范围内的螺母和螺栓配对
     * @param nuts 螺母数组
     * @param bolts 螺栓数组
     * @param low 起始索引
     * @param high 结束索引
     */
    private static void matchRecursive(Nut[] nuts, Bolt[] bolts, int low, int high) {
        if (low >= high) {
            return; // 子数组长度为1或0，已配对
        }

        // 步骤1：随机选择当前范围内的一个螺母作为基准
        int pivotNutIdx = low + RANDOM.nextInt(high - low + 1);
        Nut pivotNut = nuts[pivotNutIdx];

        // 步骤2：用基准螺母划分螺栓数组，找到匹配的螺栓索引
        int pivotBoltIdx = partitionBolts(bolts, low, high, pivotNut);

        // 步骤3：用匹配的螺栓划分螺母数组，确定基准螺母的最终位置
        int pivotNutFinalIdx = partitionNuts(nuts, low, high, bolts[pivotBoltIdx]);

        // 步骤4：递归处理左右子数组
        matchRecursive(nuts, bolts, low, pivotNutFinalIdx - 1);  // 左子数组（小于基准）
        matchRecursive(nuts, bolts, pivotNutFinalIdx + 1, high); // 右子数组（大于基准）
    }

    /**
     * 用基准螺母划分螺栓数组：左<基准，中=基准，右>基准
     * @param bolts 螺栓数组
     * @param low 起始索引
     * @param high 结束索引
     * @param pivotNut 基准螺母
     * @return 匹配基准螺母的螺栓索引
     */
    private static int partitionBolts(Bolt[] bolts, int low, int high, Nut pivotNut) {
        int left = low;
        int right = high;

        while (left < right) {
            // 螺栓比基准螺母小，移到左半部分
            if (bolts[left].compare(pivotNut) < 0) {
                left++;
            }
            // 螺栓比基准螺母大，移到右半部分
            else if (bolts[right].compare(pivotNut) > 0) {
                right--;
            }
            // 交换左右螺栓，继续划分
            else {
                swap(bolts, left, right);
            }
        }
        // 循环结束时，left==right，对应匹配的螺栓
        return left;
    }

    /**
     * 用基准螺栓划分螺母数组：左<基准，中=基准，右>基准
     * @param nuts 螺母数组
     * @param low 起始索引
     * @param high 结束索引
     * @param pivotBolt 基准螺栓
     * @return 匹配基准螺栓的螺母索引
     */
    private static int partitionNuts(Nut[] nuts, int low, int high, Bolt pivotBolt) {
        int left = low;
        int right = high;

        while (left < right) {
            // 螺母比基准螺栓小，移到左半部分
            if (nuts[left].compare(pivotBolt) < 0) {
                left++;
            }
            // 螺母比基准螺栓大，移到右半部分
            else if (nuts[right].compare(pivotBolt) > 0) {
                right--;
            }
            // 交换左右螺母，继续划分
            else {
                swap(nuts, left, right);
            }
        }
        // 循环结束时，left==right，对应匹配的螺母
        return left;
    }

    // 交换螺栓数组中两个索引的元素
    private static void swap(Bolt[] arr, int i, int j) {
        Bolt temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 交换螺母数组中两个索引的元素
    private static void swap(Nut[] arr, int i, int j) {
        Nut temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 数组转字符串（便于输出）
    private static <T> String arrayToString(T[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    // 测试方法
    public static void main(String[] args) {
        // 测试用例：打乱的配对螺母和螺栓
        Nut[] nuts = {new Nut(3), new Nut(1), new Nut(2), new Nut(5), new Nut(4)};
        Bolt[] bolts = {new Bolt(2), new Bolt(5), new Bolt(1), new Bolt(3), new Bolt(4)};

        System.out.println("配对前：");
        System.out.println("螺母：" + arrayToString(nuts));
        System.out.println("螺栓：" + arrayToString(bolts));

        // 执行配对
        match(nuts, bolts);

        System.out.println("\n配对后（索引对应匹配）：");
        System.out.println("螺母：" + arrayToString(nuts));
        System.out.println("螺栓：" + arrayToString(bolts));

        // 验证配对结果
        System.out.println("\n配对验证：");
        for (int i = 0; i < nuts.length; i++) {
            System.out.printf("螺母%d ↔ 螺栓%d（%s）%n",
                    nuts[i].getSize(), bolts[i].getSize(),
                    nuts[i].compare(bolts[i]) == 0 ? "匹配" : "不匹配");
        }
    }
}