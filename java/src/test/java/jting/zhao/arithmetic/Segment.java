package jting.zhao.arithmetic;

import java.util.*;

/**
 * @ClassName: Segment
 * @Description:  线段重叠，交集，并集，最大交集（TODO没想好怎么玩）
 * @Author: zhaojt
 * @Date: 2018/3/10 10:16
 * Inc.All rights reserved.
 */
public class Segment implements Comparable<Segment>{

    private int left;
    private int right;
    Segment next;

    public Segment(int left, int right) {
        if(left > right){
            throw new RuntimeException("参数有误!");
        }
        this.left = left;
        this.right = right;
    }

    public static void main(String[] args) {

        List<Segment> list1 = randomSegments(5);
        List<Segment> list2 = randomSegments(6);
        System.out.println(list1);
        System.out.println(list2);

        Collections.sort(list1);
        Collections.sort(list2);
        System.out.println(list1);
        System.out.println(list2);


//        List<Segment> result = union(segments.get(0), segments.get(1));
        List<Segment> result = intersection(list1, list2);

        System.out.println(result);
    }

    public static List<Segment> randomSegments(int num){
        ArrayList<Segment> segments = new ArrayList<Segment>();
        Random random = new Random();
        for(int i = 0;i < num; i++){
            int left = random.nextInt(100);
            int right = random.nextInt(20) + left;

            Segment cur = new Segment(left, right);
            if(i >= 1){
                segments.get(i-1).next = cur;
            }
            segments.add(cur);
        }
        return segments;
    }


    /**
     * 两排序集合交集
     * @param list1
     * @param list2
     * @return
     */
    public static List<Segment> intersection(List<Segment> list1, List<Segment> list2){
        return null;
    }

    public static Segment intersection2(Segment list1, Segment list2){


        return null;
    }
    /**
     * 两条线段交集
     * @param s1
     * @param s2
     * @return
     */
    public static Segment intersection(Segment s1, Segment s2){
        if(s1.left > s2.right || s1.right < s2.left){
            return null;
        }
        if(s1.left >= s2.left){
            return new Segment(s1.left, s2.right >= s1.right ? s1.right : s2.right);
        }
        if(s1.right <= s2.right){
            return new Segment(s2.left >= s1.left ? s2.left : s1.left, s1.right);
        }
        return s2;
    }

    /**
     * 两条线段并集
     * @param s1
     * @param s2
     * @return
     */
    public static List<Segment> union(Segment s1, Segment s2){
        List<Segment> segments = new ArrayList<Segment>();
        if(s1.left > s2.right || s1.right < s2.left){
            segments.add(s1);
            segments.add(s2);
            return segments;
        }
        if(s1.left >= s2.left){
            segments.add(new Segment(s2.left, s2.right >= s1.right ? s2.right : s1.right));
            return segments;
        }
        if(s1.right <= s2.right){
            segments.add(new Segment(s2.left >= s1.left ? s1.left : s2.left, s2.right));
            return segments;
        }
        segments.add(s1);
        return segments;
    }

    @Override
    public int compareTo(Segment o) {
        if(o == null){
            throw new NullPointerException();
        }
        if(this.left > o.left){
            return 1;
        }
        if(this.left < o.left){
            return -1;
        }
        if(this.left == o.left){
            if(this.right > o.right){
                return 1;
            }
            if(this.right == o.right){
                return 0;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return "[" + left + ", " + right +  ']';
    }
}
