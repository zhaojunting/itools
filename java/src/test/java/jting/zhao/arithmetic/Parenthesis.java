package jting.zhao.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Parenthesis
 * @Description: 打印所有括号匹配排列方式
 * @Author: zhaojt
 * @Date: 2018/3/27 21:30
 * Inc.All rights reserved.
 */
public class Parenthesis {

    static void printParenthesis(int pos , int n , int open ,int close ,char[] buffer){
        //System.out.println("step"+pos+" open is : "+ open + "close is :" + close);
        //System.out.println(new String(buffer));
        if(close == n){
            //System.out.println("over");
            System.out.println(new String(buffer));

            return;
        }
        if(open >close){
            buffer[pos]='}';
            printParenthesis(pos+1, n, open, close+1, buffer);

        }

        if(open <n){
            buffer[pos] = '{';
            printParenthesis(pos+1, n, open+1, close, buffer);
        }

    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //System.out.println("012142");
        int  n = 4;
        char[] cs = new char[2 * n];
        printParenthesis(0, n, 0, 0, cs);  //copy

        //System.out.println("012143");
//        XTree xTree = buildXTree(n);
//        xTree.print(xTree.root);
        int i = 0;
    }



    static class Node{
        Node pre;
        Node n1;
        Node n2;
        char value ;
        int l ,r;


    }

    static class XTree{
        Node root;
        int n;
        int deep;
        Node end;

        List<Node> ends;

        public XTree(int n) {
            this.n = n;
            deep = 2 * n;

            end = new Node();
            end.value = ')';
            end.l = n;
            end.r = n;

            root = new Node();
            root.value = '(';
            root.l = 1;

            ends = new ArrayList<Node>();
            build(root);
        }

        public void build(Node foo){
            if(foo.l == n && foo.r == n - 1){
                Node end = new Node();
                end.value = ')';
                end.l = n;
                end.r = n;

                foo.n1 = end;
                end.pre = foo;
                ends.add(end);
                return;
            }
            if(foo.r == foo.l){
                Node node = new Node();
                node.value = '(';
                node.l = foo.l + 1;
                node.r = foo.r;
                foo.n1 = node;
                node.pre = foo;

                build(node);
            }else if(foo.l == n){
                Node node = new Node();
                node.value = ')';
                node.l = foo.l;
                node.r = foo.r + 1;
                foo.n1 = node;
                node.pre = foo;

                build(node);
            }else{
                Node n1 = new Node();
                n1.value =')';
                n1.l = foo.l;
                n1.r = foo.r + 1;
                foo.n1 = n1;
                n1.pre = foo;

                Node n2 = new Node();
                n2.value = '(';
                n2.l = foo.l + 1;
                n2.r = foo.r;
                foo.n2 = n2;
                n2.pre = foo;

                build(n1);
                build(n2);

            }
        }

        public void print(Node node){
            for(int i = 0; i < ends.size(); i++){
                printNode(ends.get(i));
                print("\n");
            }
        }

        private void printNode(Node node){
            if(node.pre != null){
                printNode(node.pre);
            }
            print(node.value);
        }

        private void print(Object obj){
            System.out.print(obj);
        }

    }

    public static XTree buildXTree(int n){
        return new XTree(n);
    }




}
