import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        }
        if (this.y == that.y) {
            return 0.0;
        }
        if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }

    public Comparator<Point> slopeOrder() {
        return (a, b) -> Double.compare(this.slopeTo(a), this.slopeTo(b));
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // 可选：保留单元测试，不影响编译
    public static void main(String[] args) {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(2, 2);
        System.out.println("p1 vs p2: " + p1.compareTo(p2));
    }
}