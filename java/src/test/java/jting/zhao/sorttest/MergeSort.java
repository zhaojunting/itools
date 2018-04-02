package jting.zhao.sorttest;

/**
 * @ClassName: MergeSort
 * @Description: (这里用一句话描述这个类的作用)
 * @Author: zhaojt
 * @Date: 2018/3/9 15:26
 * Inc.All rights reserved.
 */
public class MergeSort {

    int maxDeep = 3;//定义最大深度

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            int[] arr = QuickSort.randomArr();//

            QuickSort.print(arr);

            mergeSort(arr, 0, arr.length - 1, 0);

            QuickSort.print(arr);

            System.out.println("--------------------------------------------------------");
        }

    }

    public static void mergeSort(int[] arr, int left, int right, int curDeep){
        //排序
        if((right - left) == 0){
            return;
        }
        if((right - left) == 1){
            if(arr[left] > arr[right]) {
                QuickSort.swap(arr, left, right);
                QuickSort.print(arr);
            }
            return;
        }

        //分治
        int i = (left + right) / 2;
        int j = left;
        int k = i + 1;
        mergeSort(arr, left, i , curDeep + 1 );
        mergeSort(arr, i + 1, right, curDeep + 1 );

        //合并
        int tmp[] = new int[right - left + 1];
        int tmpIdx = 0;
        for(;j <= i;){
            if(k > right){
                tmp[tmpIdx++] = arr[j++];
                continue;
            }
            if(arr[j] > arr[k]){
                tmp[tmpIdx++] = arr[k++];
            }else if(arr[j] == arr[k]){
                tmp[tmpIdx++] = arr[k++];
                tmp[tmpIdx++] = arr[j++];
            }else{
                tmp[tmpIdx++] = arr[j++];
            }
        }
        for(;k <= right;){
            tmp[tmpIdx++] = arr[k++];
        }

        //交换
        for(int a = 0; a < tmp.length; a++){
            arr[left++] = tmp[a];
        }
        QuickSort.print(arr);
    }
}
