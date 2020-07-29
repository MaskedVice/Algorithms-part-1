import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static final boolean VER = true;
    private static final boolean HOR = false;

    private Node root;
    private int size;

    public KdTree() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        root = insert(root, VER, p, 0, 0, 1, 1);
    }

    private Node insert(Node x, boolean direction, Point2D p, double xmin, double ymin, double xmax,
                        double ymax) {
        if (x == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }

        // special cases, when the same point is not already inserted
        if (p.equals(x.point)) {
            return x;
        }

        // must save each recursive rectangular area corresponding
        if (direction == VER) {
            if (p.x() < x.point.x()) {
                x.left = insert(x.left, HOR, p, xmin, ymin, x.point.x(), ymax);
            }
            else {
                x.right = insert(x.right, HOR, p, x.point.x(), ymin, xmax, ymax);
            }
        }
        else {
            if (p.y() < x.point.y()) {
                x.left = insert(x.left, VER, p, xmin, ymin, xmax, x.point.y());
            }
            else {
                x.right = insert(x.right, VER, p, xmin, x.point.y(), xmax, ymax);
            }
        }

        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node x = root;
        boolean direction = VER;
        while (x != null) {
            if (p.equals(x.point)) {
                return true;
            }

            if ((direction == VER && p.x() < x.point.x())
                    || (direction == HOR && p.y() < x.point.y())) {
                x = x.left;
            }
            else {
                x = x.right;
            }
            direction = !direction;
        }
        return false;
    }

    public void draw() {
        draw(root, VER);
    }

    // draw attention to points and lines when color, thickness differences
    private void draw(Node x, boolean direction) {
        if (x == null) {
            return;
        }

        // draw points
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        x.point.draw();

        // draw a line
        StdDraw.setPenRadius();
        if (direction == VER) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.point.x(), x.rect.ymin(), x.point.x(), x.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.point.y(), x.rect.xmax(), x.point.y());
        }

        draw(x.left, !direction);
        draw(x.right, !direction);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        List<Point2D> list = new ArrayList<>();
        // Only the root is not empty when called, call the function because there is no judgment on the recursive when null
        if (!isEmpty()) {
            range(root, rect, list);
        }
        return list;
    }

    private void range(Node x, RectHV rect, List<Point2D> list) {
        if (rect.contains(x.point)) {
            list.add(x.point);
        }

        // Only when the left and right subtrees corresponding to the rectangular area intersect the query region, it may fall a bit in the query area
        if (x.left != null && x.left.rect.intersects(rect)) {
            range(x.left, rect, list);
        }
        if (x.right != null && x.right.rect.intersects(rect)) {
            range(x.right, rect, list);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        // special case to be excluded
        if (isEmpty()) {
            return null;
        }
        return nearest(root, VER, p, root.point);
    }

    private Point2D nearest(Node x, boolean direction, Point2D p, Point2D neighbor) {
        if (x == null) {
            return neighbor;
        }
        if (p.equals(x.point)) {
            return x.point;
        }

        // before the current point to judge and update the nearest neighbor
        if (p.distanceSquaredTo(x.point) < p.distanceSquaredTo(neighbor)) {
            neighbor = x.point;
        }

        if ((direction == VER && p.x() < x.point.x())
                || (direction == HOR && p.y() < x.point.y())) {
            neighbor = nearest(x.left, !direction, p, neighbor);
            if (x.right != null
                    && x.right.rect.distanceSquaredTo(p) < p.distanceSquaredTo(neighbor)) {
                neighbor = nearest(x.right, !direction, p, neighbor);
            }
        }
        else {
            neighbor = nearest(x.right, !direction, p, neighbor);
            if (x.left != null
                    && x.left.rect.distanceSquaredTo(p) < p.distanceSquaredTo(neighbor)) {
                neighbor = nearest(x.left, !direction, p, neighbor);
            }
        }

        return neighbor;
    }

    private class Node {
        Point2D point;
        RectHV rect;
        Node left, right;

        Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }
}
