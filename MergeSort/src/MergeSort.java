import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MergeSort {

    private static void Sort(Comparable[] a, int lo, int hi) {

        Comparable[] aux = new Comparable[a.length];

        if (hi <= lo) {
            return;
        }
        int mid = (lo + hi) / 2;
        Sort(a, lo, mid);
        Sort(a, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }


    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);

        System.arraycopy(a, 0, aux, 0, a.length);

        int k = lo;
        int j = mid + 1;

        for (int i = 0; i <= hi; i++) {
            if (k > mid) a[i] = aux[j++];
            if (j > hi) a[i] = aux[k++];
            if (less(aux[j], aux[k])) a[i] = aux[j++];
            else a[i] = aux[k++];
        }
        assert isSorted(a, lo, hi);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] a, int p, int q) {
        for (int i = p; i < q; i++) {
            if (less(a[i + 1], a[i])) {
                return false;
            }
        }
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
        Sort(a, 0, a.length - 1);
        show(a);
    }

}
