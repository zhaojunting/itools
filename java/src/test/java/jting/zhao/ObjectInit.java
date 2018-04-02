package jting.zhao;
/**
 * @ClassName: ObjectInit
 * @Description: (对象初始化过程)
 * @Author: tianxin
 * @Date: 2018/2/9 14:56 www.gopay.com.cn Inc.All rights reserved.
 */
public class ObjectInit {
    public static int k = 0;
    public static ObjectInit t1 = new ObjectInit("t1");
    public static ObjectInit t2 = new ObjectInit("t2");
    public static int i = print("i");
    public static int n = 99;
    public int j = print("j");

    {
        print("构造块");
    }
    static {
        print("静态块");
    }

    public ObjectInit(String str) {
        System.out.println((++k) + ":" + str + "   i=" + i + "    n=" + n);
        ++i;
        ++n;
    }

    public static int print(String str) {
        System.out.println((++k) + ":" + str + "   i=" + i + "    n=" + n);
        ++n;
        return ++i;
    }

    public static void main(String args[]) {
        ObjectInit t = new ObjectInit("init");


    }
}
