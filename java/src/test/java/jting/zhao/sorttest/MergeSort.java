package jting.zhao.sorttest;

/**
 * Created by tianxin on 2018/3/8.
 */
public class MergeSort {
    public static int[] sort(int[] arr) {
        if (arr != null && arr.length > 0) {
            spilt(arr, 0, arr.length - 1);
        }
        return arr;
    }

    public static void spilt(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (right - left + 1) / 2 + left;
            if (mid == right) {
                int temp;
                if (arr[left]>arr[right]) {
                    temp = arr[left];
                    arr[left] = arr[right];
                    arr[right] = temp;
                }
                return;
            }
            spilt(arr, left, mid);
            spilt(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }

    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int l = mid - left + 1;
        int r = right - mid;
        int[] L = new int[l];
        int[] R = new int[r];
        int temp = left;
        int i = 0;
        int j = 0;

        for (int k = 0; k < l; k++) {
            L[k] = arr[left + k];
        }
        for (int k = 0; k < r; k++) {
            R[k] = arr[mid + 1 + k];
        }

        while (i < l && j < r) {
            if (L[i] < R[j]) {
                arr[temp] = L[i];
                temp++;
                i++;
            } else {
                arr[temp] = R[j];
                temp++;
                j++;
            }

        }


        while (i < l) {
            arr[temp] = L[i];
            temp++;
            i++;
        }


        while (j < r) {
            arr[temp] = R[j];
            temp++;
            j++;

        }

    }
}
