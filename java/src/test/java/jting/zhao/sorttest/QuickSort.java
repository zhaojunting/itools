package jting.zhao.sorttest;

import java.util.Random;

/**
 * Created by zhaojt on 2018/3/8.
 *
 * 快速排序
 */
public class QuickSort {


    public static void main(String[] args) {
        for(int i = 0; i <= 1; i++) {
            int[] arr = {1,1,1,1,2,77,0,0,0,0,1};    //new int[]{49, 38, 65, 97, 76, 13, 27};
            print(arr);
            quickSort(arr, 0, arr.length - 1);
            System.out.println("-----------------------------------------------------------");
        }
    }

    /**
     *
     * @param input
     * @param left
     * @param right
     */
    public static void quickSort(int[] input, int left, int right){
        System.out.println(left + ", " + right);
        if(left >= right){
            return;
        }

        int key = left; //基准值

        int r = right;
        int l = left;

        while(l < r) {
            //从右到左迭代，寻找第一个小于基准值的元素，迭代到基准值为止
            for(; r > key ; r--){
                if(input[r] < input[key]){
                    //交换值
                    swap(input,r,key);

                    key = r;

                    print(input);
                    break;
                }
            }


            //从左到右迭代，寻找第一个大于基准值的元素，迭代到基准值为止
            for(; l < key ; l++){
                if(input[l] > input[key]){
                    //交换值
                    swap(input,l,key);

                    key = l;

                    print(input);
                    break;
                }
            }

        }
        //递归
        quickSort(input, left, key - 1);
        quickSort(input, key + 1, right);


    }

    public static void swap(int[] arr, int a, int b){
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static void print(int[] input){
        for(int i = 0; i <= input.length - 1; i++){
            System.out.print(input[i]+", ");
        }
        System.out.println();
    }

    public static int[] randomArr(){
        Random random = new Random();
        int length = random.nextInt(5) + 6;

        int[] arr = new int[length];

        for(int i = 0; i < arr.length - 1; i++){
            arr[i] = random.nextInt(100);
        }

        return arr;
    }
}
