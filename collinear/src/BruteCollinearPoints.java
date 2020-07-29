import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        Point[] copy = new Point[points.length];
        segments = new ArrayList<>();

        // avoid modifying the original array, you need to copy down
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            copy[i] = points[i];
        }

        Arrays.sort(copy);
        for (int i = 0; i < copy.length - 1; i++) {
            if (copy[i].compareTo(copy[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        run(copy);
    }


    public int numberOfSegments() {
        return segments.size();
    }


    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegment segment : segments) {
            res[i++] = segment;
        }
        return res;
    }

    private void run(Point[] points) {
        int n = points.length;
        // traverse directly quadruple
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                double s1 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < n - 1; k++) {
                    double s2 = points[i].slopeTo(points[k]);
                    if (s1 == s2) {
                        for (int m = k + 1; m < n; m++) {
                            double s3 = points[i].slopeTo(points[m]);
                            if (s1 == s3) {
                                LineSegment temp = new LineSegment(points[i], points[m]);
                                segments.add(temp);
                            }
                        }
                    }
                }
            }
        }
    }
}
