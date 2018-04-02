package jting.zhao.sorttest;

/**
 * Created by tianxin on 2018/3/8.
 */
public class QuickSort2 {

    public static int[] sort(int[] arr) {
        if (arr != null && arr.length > 1) {
            quickSort(arr, 0, arr.length - 1);
        }

        return arr;
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int l = left;
        int r = right;
        int target = l;
        int temp;
        while (l < r) {
            while (r > left && arr[target] <= arr[r]) {
                r--;
            }
            if (arr[target] > arr[r]) {
                temp = arr[target];
                arr[target] = arr[r];
                arr[r] = temp;
                target = r;
                l++;
            }
            while (l < right && arr[l] <= arr[target]) {
                l++;
            }
            if (arr[target] < arr[l]) {
                temp = arr[target];
                arr[target] = arr[l];
                arr[l] = temp;
                target = l;
                r--;
            }
        }
        quickSort(arr, left, --l);
        quickSort(arr, ++r, right);
    }

}
