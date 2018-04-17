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

//    错误: 在类 java.lang.String 中找不到 main 方法, 请将 main 方法定义为:
//    public static void main(String[] args)
//    否则 JavaFX 应用程序类必须扩展javafx.application.Application

    public void t1(){
        System.out.println("Hello world!");
    }
}
