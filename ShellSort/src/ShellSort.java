import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShellSort {

    // This class should not be instantiated.
    private ShellSort() {
    }


    private static void sort(Comparable[] a) {
        int n = a.length;

        // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ...
        int h = 0;
        while (h < n / 3) h = 3 * h + 1;

        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            assert isHsorted(a, h);
            h /= 3;
        }
        assert isSorted(a);
    }


    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }


    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }


    private static boolean isHsorted(Comparable[] a, int h) {
        for (int i = h; i < a.length; i++)
            if (less(a[i], a[i - h])) return false;
        return true;
    }


    private static void show(Comparable[] a) {
        for (Comparable comparable : a) {
            StdOut.println(comparable);
        }
    }


    public static void main(String[] args) throws FileNotFoundException {

        File txt = new File("sort.txt");
        Scanner scan = new Scanner(txt);
        ArrayList<String> data = new ArrayList<>();
        while (scan.hasNextLine()) {
            data.add(scan.nextLine());
        }
        System.out.println(data);
        Comparable[] a = data.toArray(new Comparable[]{});
        ShellSort.sort(a);
        show(a);
    }

}
