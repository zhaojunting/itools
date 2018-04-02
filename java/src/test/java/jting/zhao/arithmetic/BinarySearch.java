package jting.zhao.arithmetic;

import jting.zhao.sorttest.QuickSort;

/**
 * @ClassName: BinarySearch
 * @Description: (二分法查找)
 * @Author: zhaojt
 * @Date: 2018/3/12 16:12
 * Inc.All rights reserved.
 */
public class BinarySearch {

    static int[] arr = new int[]{1,2,3,4,5,7,8,9,0};

    public static void main(String[] args) {
        insert(arr, 0,arr.length - 1, 6);
        QuickSort.print(arr);

//        System.out.println(search(arr,0,arr.length - 2, 6));
    }


    public static int search(int[] arr, int begin, int end, int val){
        int left = 0;
        int right = end;
        int mid = (left + right) / 2 ;
        while(left <= right){
            if(arr[mid] > val){
                right = mid - 1;
                mid = (left + right) / 2 ;
            }else if(arr[mid] < val){
                left = mid + 1;
                mid = (left + right) / 2;
            }else{
                return mid;
            }
        }
        return -1;
    }


    public static void insert(int[] arr , int begin, int end, int val){
        int left = begin;
        int right = end;
        int mid = (left + right) / 2 ;

        while(left <= right){
            if(arr[mid] > val){
                right = mid - 1;
                mid = (left + right) / 2;
            }else if(arr[mid] < val){
                left = mid + 1;
                mid = (left + right) / 2;
            }else{
                break;
            }
        }
        int idx = arr[mid] > val ? mid : mid + 1;
        for(int i = end - 1; i >= idx; i--){
            arr[i + 1] = arr[i];
        }
        arr[idx] = val;

    }
}
