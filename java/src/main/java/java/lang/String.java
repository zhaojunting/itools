package java.lang;

/**
 * @ClassName: String
 * @Description: (自定义String不会被加载)
 * @Author: zhaojt
 * @Date: 2018/3/29 20:57
 * Inc.All rights reserved.
 */
public class String {

    //https://www.cnblogs.com/liuheng0315/p/7160794.html
    public static void main(String[] args) {
        new String().t1();
    }

    public void t1(){
        System.out.println("Hello world!");
    }
}
