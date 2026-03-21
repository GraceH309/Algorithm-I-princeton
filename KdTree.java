import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {
    private static class Node {
        private final Point2D p;
        private final RectHV rect;
        private Node left;
        private Node right;
        private final boolean isVertical;

        public Node(Point2D p, RectHV rect, boolean isVertical) {
            this.p = p;
            this.rect = rect;
            this.isVertical = isVertical;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");
        root = insert(root, p, new RectHV(0.0, 0.0, 1.0, 1.0), true);
    }

    private Node insert(Node node, Point2D p, RectHV rect, boolean isVertical) {
        if (node == null) {
            size++;
            return new Node(p, rect, isVertical);
        }
        if (node.p.equals(p)) {
            return node;
        }
        if (isVertical) {
            double cmp = Double.compare(p.x(), node.p.x());
            RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax());
            RectHV rightRect = new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            if (cmp < 0) {
                node.left = insert(node.left, p, leftRect, !isVertical);
            } else {
                node.right = insert(node.right, p, rightRect, !isVertical);
            }
        } else {
            double cmp = Double.compare(p.y(), node.p.y());
            RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y());
            RectHV rightRect = new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax());
            if (cmp < 0) {
                node.left = insert(node.left, p, leftRect, !isVertical);
            } else {
                node.right = insert(node.right, p, rightRect, !isVertical);
            }
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");
        return contains(root, p);
    }

    private boolean contains(Node node, Point2D p) {
        if (node == null) return false;
        if (node.p.equals(p)) return true;
        if (node.isVertical) {
            double cmp = Double.compare(p.x(), node.p.x());
            return cmp < 0 ? contains(node.left, p) : contains(node.right, p);
        } else {
            double cmp = Double.compare(p.y(), node.p.y());
            return cmp < 0 ? contains(node.left, p) : contains(node.right, p);
        }
    }

    public void draw() {
        StdDraw.setPenRadius();
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        StdDraw.setPenRadius();
        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle is null");
        ArrayList<Point2D> result = new ArrayList<>();
        range(root, rect, result);
        return result;
    }

    private void range(Node node, RectHV queryRect, ArrayList<Point2D> result) {
        if (node == null) return;
        if (!node.rect.intersects(queryRect)) return;
        if (queryRect.contains(node.p)) {
            result.add(node.p);
        }
        range(node.left, queryRect, result);
        range(node.right, queryRect, result);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");
        if (isEmpty()) return null;
        Point2D best = root.p;
        double bestDistSq = p.distanceSquaredTo(root.p);
        Object[] result = nearest(root, p, best, bestDistSq);
        return (Point2D) result[0];
    }

    private Object[] nearest(Node node, Point2D p, Point2D best, double bestDistSq) {
        if (node == null) {
            return new Object[]{best, bestDistSq};
        }
        double rectDistSq = node.rect.distanceSquaredTo(p);
        if (rectDistSq >= bestDistSq) {
            return new Object[]{best, bestDistSq};
        }
        double distSq = p.distanceSquaredTo(node.p);
        if (distSq < bestDistSq) {
            best = node.p;
            bestDistSq = distSq;
        }
        Node first, second;
        if (node.isVertical) {
            double cmp = Double.compare(p.x(), node.p.x());
            if (cmp < 0) {
                first = node.left;
                second = node.right;
            } else {
                first = node.right;
                second = node.left;
            }
        } else {
            double cmp = Double.compare(p.y(), node.p.y());
            if (cmp < 0) {
                first = node.left;
                second = node.right;
            } else {
                first = node.right;
                second = node.left;
            }
        }
        Object[] temp = nearest(first, p, best, bestDistSq);
        best = (Point2D) temp[0];
        bestDistSq = (double) temp[1];
        temp = nearest(second, p, best, bestDistSq);
        best = (Point2D) temp[0];
        bestDistSq = (double) temp[1];
        return new Object[]{best, bestDistSq};
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));
        System.out.println("Size: " + tree.size()); // Expected: 5
        System.out.println("Contains (0.5, 0.4): " + tree.contains(new Point2D(0.5, 0.4))); // Expected: true
        System.out.println("Contains (0.1, 0.1): " + tree.contains(new Point2D(0.1, 0.1))); // Expected: false
        RectHV rect = new RectHV(0.1, 0.2, 0.6, 0.8);
        System.out.println("Points in range:");
        for (Point2D p : tree.range(rect)) {
            System.out.println(p); // Expected: (0.5, 0.4), (0.2, 0.3), (0.4, 0.7)
        }
        Point2D nearest = tree.nearest(new Point2D(0.6, 0.5));
        System.out.println("Nearest to (0.6, 0.5): " + nearest); // Expected: (0.5, 0.4)
    }
}