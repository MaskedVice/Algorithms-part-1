import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class PointSET {
    private final Set<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> res = new ArrayList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                res.add(p);
            }
        }
        return res;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }
        double minDis = Double.POSITIVE_INFINITY;
        Point2D min = null;

        for (Point2D point : points) {
            double dis = p.distanceSquaredTo(point);
            if (dis < minDis) {
                minDis = dis;
                min = point;
            }
        }
        return min;
    }
}
