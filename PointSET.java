import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle is null");
        ArrayList<Point2D> result = new ArrayList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");
        if (isEmpty()) return null;
        Point2D nearest = null;
        double minDistSq = Double.POSITIVE_INFINITY;
        for (Point2D q : points) {
            double distSq = p.distanceSquaredTo(q);
            if (distSq < minDistSq) {
                minDistSq = distSq;
                nearest = q;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        PointSET set = new PointSET();
        set.insert(new Point2D(0.5, 0.5));
        set.insert(new Point2D(0.3, 0.7));
        set.insert(new Point2D(0.5, 0.5)); // Duplicate
        System.out.println("Size: " + set.size()); // Expected: 2
        System.out.println("Contains (0.3, 0.7): " + set.contains(new Point2D(0.3, 0.7))); // Expected: true
        System.out.println("Contains (0.1, 0.1): " + set.contains(new Point2D(0.1, 0.1))); // Expected: false
        RectHV rect = new RectHV(0.2, 0.6, 0.6, 0.8);
        System.out.println("Points in range:");
        for (Point2D p : set.range(rect)) {
            System.out.println(p); // Expected: (0.3, 0.7)
        }
        Point2D nearest = set.nearest(new Point2D(0.4, 0.6));
        System.out.println("Nearest to (0.4, 0.6): " + nearest); // Expected: (0.3, 0.7) or (0.5, 0.5)
    }
}