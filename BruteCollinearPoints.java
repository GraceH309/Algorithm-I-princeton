import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
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

        // 暴力枚举所有4点组合
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            for (int j = i + 1; j < n; j++) {
                Point q = points[j];
                double slopePQ = p.slopeTo(q);
                for (int k = j + 1; k < n; k++) {
                    Point r = points[k];
                    double slopePR = p.slopeTo(r);
                    if (slopePQ != slopePR) continue;
                    for (int l = k + 1; l < n; l++) {
                        Point s = points[l];
                        double slopePS = p.slopeTo(s);
                        if (slopePQ == slopePS) {
                            // 排序获取线段端点（避免重复）
                            Point[] collinear = {p, q, r, s};
                            Arrays.sort(collinear);
                            segments.add(new LineSegment(collinear[0], collinear[3]));
                        }
                    }
                }
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

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment seg : collinear.segments()) {
            StdOut.println(seg);
            seg.draw();
        }
        StdDraw.show();
    }
}