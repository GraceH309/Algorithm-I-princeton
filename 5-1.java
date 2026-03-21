import java.util.HashSet;
import java.util.Set;

public class IntersectionCalculator {
    // （方案一的countIntersectionSort方法保留）

    /**
     * 方法2：哈希表计算交集个数
     * 时间复杂度：O(n)（哈希表操作摊销O(1)）
     * 空间复杂度：O(n)（存储第一个数组的点）
     */
    public static int countIntersectionHash(Point[] a, Point[] b) {
        if (a == null || b == null || a.length == 0 || b.length == 0) {
            return 0;
        }

        // 存储第一个数组的所有点到哈希集合
        Set<Point> pointSet = new HashSet<>();
        for (Point p : a) {
            pointSet.add(p);
        }

        // 遍历第二个数组，计数交集点
        int count = 0;
        for (Point p : b) {
            if (pointSet.contains(p)) {
                count++;
            }
        }
        return count;
    }

    // 单元测试（补充哈希表方法）
    public static void main(String[] args) {
        Point[] a = {new Point(1,2), new Point(3,4), new Point(5,6), new Point(7,8)};
        Point[] b = {new Point(3,4), new Point(7,8), new Point(9,10), new Point(1,1)};
        System.out.println("哈希表法交集个数：" + countIntersectionHash(a, b)); // 预期2

        Point[] c = {new Point(1,1), new Point(2,2), new Point(3,3)};
        Point[] d = {new Point(3,3), new Point(1,1), new Point(2,2)};
        System.out.println("哈希表法交集个数：" + countIntersectionHash(c, d)); // 预期3
    }
}