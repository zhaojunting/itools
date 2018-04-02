package jting.zhao;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: LamdaTest
 * @Description: (java8 lamda表达式测试)
 * @Author: zhaojt
 * @Date: 2018/3/22 13:04
 * Inc.All rights reserved.
 *
 * http://blog.csdn.net/zougen/article/details/78127531
 */
public class LamdaTest {

    /**
     * IDEA java 环境配置
     * http://blog.csdn.net/u010598360/article/details/73162734?locationNum=9&fps=1
     * @param args
     */
    public static void main(String[] args) {

    }

    /**
     * lamda： map reduce
     */
    public static void t1(){
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double bill = costBeforeTax.stream().map((cost) -> cost + 0.12*cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total : " + bill);
    }
}
