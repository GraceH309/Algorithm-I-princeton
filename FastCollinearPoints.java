import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        // 输入校验
        if (points == null) {
            throw new IllegalArgumentException("Points array cannot be null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Array contains null point");
            }
        }
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicate points");
                }
            }
        }

        segments = new ArrayList<>();
        if (n < 4) return;

        // 对每个点作为原点处理
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            // 复制数组并按斜率排序
            Point[] sortedBySlope = Arrays.copyOf(points, n);
            Arrays.sort(sortedBySlope, p.slopeOrder());

            int j = 1;
            while (j < n) {
                double currentSlope = p.slopeTo(sortedBySlope[j]);
                int k = j;
                // 找到所有相同斜率的点
                while (k < n && p.slopeTo(sortedBySlope[k]) == currentSlope) {
                    k++;
                }
                // 至少3个点（加p共4个）且p是最小点（避免重复线段）
                if (k - j >= 3) {
                    Point min = p;
                    Point max = p;
                    for (int m = j; m < k; m++) {
                        if (sortedBySlope[m].compareTo(min) < 0) min = sortedBySlope[m];
                        if (sortedBySlope[m].compareTo(max) > 0) max = sortedBySlope[m];
                    }
                    if (p.compareTo(min) == 0) {
                        segments.add(new LineSegment(min, max));
                    }
                }
                j = k;
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    // 示例客户端（可选保留）
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) p.draw();
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment seg : collinear.segments()) {
            StdOut.println(seg);
            seg.draw();
        }
        StdDraw.show();
    }
}