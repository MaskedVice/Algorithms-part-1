import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        Point[] copy = new Point[points.length];
        segments = new ArrayList<>();

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
        Point[] bases = Arrays.copyOf(points, n);    // set the reference points
        int current = 0;

        while (current < n) {
            Point base = bases[current++];        // Select the next reference point
            Point min = base;        // collinear small segment endpoint
            Point max = base;        // greater contribution to the segment endpoint
            int count = 2;            // line there will be at least two points
            Arrays.sort(points, base.slopeOrder());        // sorted by the slope of the reference point
            for (int i = 0; i < n - 1; i++) {
                double s1 = base.slopeTo(points[i]);
                double s2 = base.slopeTo(points[i + 1]);
                if (s1 == s2) {
                    count++;
                    // Update the small end and big end in the case of new entrants collinear points
                    if (max.compareTo(points[i + 1]) < 0) {
                        max = points[i + 1];
                    } else if (min.compareTo(points[i + 1]) > 0) {
                        min = points[i + 1];
                    }
                    // When point i, i + 1 as the last two points need to be determined
                    // save to base only a small segment endpoint
                    if (i == n - 2 && count >= 4 && base.compareTo(min) == 0) {
                        LineSegment temp = new LineSegment(min, max);
                        segments.add(temp);
                    }
                } else {
                    // interrupt sequence when the same slope, it is necessary to judge
                    // save to base only a small segment endpoint
                    if (count >= 4 && base.compareTo(min) == 0) {
                        LineSegment temp = new LineSegment(min, max);
                        segments.add(temp);
                    }
                    // slope when starting a new sequence, the base needs to be compared with the first slope of the new point, the reset min and max
                    if (base.compareTo(points[i + 1]) > 0) {
                        min = points[i + 1];
                        max = base;
                    } else {
                        min = base;
                        max = points[i + 1];
                    }
                    count = 2;
                }
            }
        }
    }


}
