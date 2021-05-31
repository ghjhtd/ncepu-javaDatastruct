/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HomeWork.chapter02;

import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.out;
import static HomeWork.chapter02.ErrUtil.error;
/**
 *
 * @龚皓靖 120191140216 
 */
/*-------------------------------------------------------
 错误处理工具类，提供出错信息打印并退出程序的执行
--------------------------------------------------------*/
class ErrUtil {
    public static void error(String msg) {
        out.println(msg);
        exit(-1);
    }
}


public class chapter02 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        boolean state = true, shuru = true;
        int which,count;
        String zifu;
        while (state){
            try{
                System.out.println("请选择要存储的线性表的结构：（0-顺序表  1-链表 2-退出）");
                which = scanner.nextInt();
                switch (which){
                    case 0:
                        SeqList sl = new SeqList();
                        count = 0;
                        while (shuru){
                            System.out.println("请输入第" + (count+1)+ "个想要存入顺序表的数：\n(输入q退出,t倒序查看,s顺序查看)");
                            zifu = scanner1.nextLine();
                            if(zifu.toUpperCase().equals("Q")){
                                shuru = false;
                            }
                            if(zifu.toUpperCase().equals("T")){
                                for (int i = sl.getLength()-1; i >= 0; i--) {
                                    out.printf("[%d]=%s\n", i, sl.getNode(i));
                                }
                            }
                            if(zifu.toUpperCase().equals("S")){
                                sl.printList();
                            }
                            else {
                                sl.insertAt(count, Integer.parseInt(zifu));
                                count++;
                            }
                        }
                        break;
                    case 1:
                        LinkList s2 = new LinkList();
                        count = 0;
                        while (shuru){
                            System.out.println("请输入第" + (count+1)+ "个想要存入链表的数：\n(输入q退出,t倒序查看,s顺序查看)");
                            zifu = scanner1.nextLine();
                            if(zifu.toUpperCase().equals("Q")){
                                shuru = false;
                            }
                            if(zifu.toUpperCase().equals("T")){
                                for (int i = s2.getLength()-1; i >= 0; i--) {
                                    out.printf("[%d]=%s\n", i, s2.getNode(i));
                                }
                            }
                            if(zifu.toUpperCase().equals("S")){
                                s2.printList();
                            }
                            else {
                                s2.insertAt(count, Integer.parseInt(zifu));
                                count++;
                            }
                        }
                        break;
                    case 2:
                        state = false;
                        break;
                    default:
                        System.out.println("请输入0-2的整数");
                        break;
                }
            }
            catch (Exception e){
                System.out.println("请输入正确参数\n");
            }
        }
    }
}


/*-------------------------------------------------
  顺序列表的类定义 
---------------------------------------------------*/
class SeqList {

    public static int ListSize = 100;
    private Object data[] = new Object[ListSize];
    private int length;  //当前表的长度

    //操作定义 
    public int locateNode(Object x) {
        /*搜索方法：在表中从前向后顺序查找 x
          返回第一个值为x的元素的下标*/
        int i = 0;
        while (i < length && !x.equals(data[i])) {
            i++;
        }
        if (i == length) {
            return -1;
        } else {
            return i;
        }
    }

    public void insertAt(int i, Object x) {
        //在表中第 i 个位置插入新元素 x 
        if (length == ListSize) {
            error("表空间溢出\n");
        } else if (i < 0 || i > length) {
            error("非法插入位置\n");
        }

        for (int j = length - 1; j >= i; j--) {
            data[j + 1] = data[j];
        }
        data[i] = x;
        length++;
    }

    public void deleteAt(int i) {
        //删除表中第 i (i>=0)个位置元素 
        if (i < 0 || i > length - 1) {
            error("非法删除位置\n");
        }
        for (int j = i; j < length - 1; j++) {
            data[j] = data[j + 1];
        }
        length--;
    }

    public void updateAt(int i, Object x) {
        if (i < 0 || i > length - 1) {
            error("非法位置！");
        }
        data[i] = x;
    }

    public Object getNode(int i) {
        if (i < 0 || i > length - 1) {
            error("非法位置！");
        }
        return data[i];
    }

    public int getLength() {
        return length;
    }

    public void printList() {
        for (int i = 0; i < length; i++) {
            out.printf("[%d]=%s\n", i, data[i]);
        }
    }
}
/*-------------------------------------------------
单链表的结点定义和链表类定义 
---------------------------------------------------*/
class ListNode {
    Object data;
    ListNode next;
}

class LinkList {

    private ListNode head;
    private boolean isFlag; //是否为标志性头结点
    
    //操作定义
    public LinkList(Object[] data) {
        init(data); //通过调用init，利用data建表
    }

    /*init用尾插法初始化链表*/
    private void init(Object[] data) {
        ListNode r = null, s;
        for (int i = 0; i < data.length; i++) {
            s = new ListNode();
            s.data = data[i];
            if (head == null) {
                head = s;
            } else {
                r.next = s;
            }
            r = s;
        }
    }

    /* bR参数代表是否逆序*/
    public LinkList(Object[] data, boolean bR) {
        if (!bR) {
            init(data); //bR为false，调用尾插法
        } else {
            /*头插法建表，数据和data数组是逆序的*/
            for (int i = 0; i < data.length; i++) {
                ListNode s = new ListNode();
                s.data = data[i];
                s.next = head;
                head = s;
            }//end for 
        }//end 
    }


    /*静态工厂方法，创建带头结点的LinkList实例 */
    public static LinkList createFHList(Object[] data) {
        /*调用构造方法构造LinkList实例*/
        LinkList list = new LinkList(data);
        /*生成标志头结点*/
        ListNode flagHead = new ListNode();
        /*将生成的头结点的next指针指向原链表的头结点*/
        flagHead.next = list.head;
        /*将原头结点设置为生成的标记头结点*/
        list.head = flagHead;
        list.isFlag = true;//将头结点标记设置为真值
        return list;
    }

    /*不带参的构造方法，用于构造空的不带头结点的单链表*/
    public LinkList() {
    }

    /*静态工厂类方法，用于构造空的带头结点的单链表*/
    public static LinkList createFHList() {
        return createFHList(new Object[]{});
    }

    public Object getNode(int i) {
        int j = 0;
        ListNode p;
        //带头结点链表需要给定数据结点
        p = isFlag ? head.next : head;
        while (p != null && j < i) {
            p = p.next;
            j++;
        }
        if (p == null || i < 0) {
            error("错误的查找位置");
        }
        return p.data;
    }

    public int getLength() {
        int j = 0;
        ListNode p;
        //带头结点链表需要给定数据结点
        p = isFlag ? head.next : head;
        while (p != null) {
            p = p.next;
            j++;
        }
        return j;
    }

    public int locateNode(Object key) {
        //带头结点链表需要取数据结点
        ListNode p = isFlag ? head.next : head;
        int j = 0;
        while (p != null && !key.equals(p.data)) {
            p = p.next;
            j++;
        }
        if (p != null) {
            return j;
        } else {
            return -1;
        }
    }

    public void insertAt(int i, Object x) {
        ListNode p = head, q = p;
        if (isFlag) {
            q = p.next;
        }
        int j = 0;
        while (j < i && q != null) {
            j++;
            p = q;
            q = q.next;
        }
        if (j < i || i < 0) {
            error("位置错误");
        }
        ListNode s = new ListNode();
        s.data = x;
        s.next = q;
        if (q == head) {
            head = s;
        } else {
            p.next = s;
        }
    }

    void deleteAt(int i) {
        ListNode p = head, q = p;
        if (isFlag) {
            q = p.next;
        }
        if (q == null) {
            error("空表不能删除");
        }
        for (int j = 0; j < i && q != null; j++) {
            p = q;
            q = q.next;
        }
        if (q == null || i < 0) {
            error("位置错误");
        }
        if (q == head) {
            head = q.next;
        }
        p.next = q.next;
        q = null;

    }

    public void updateAt(int i, Object x) {
        int j = 0;
        ListNode p;
        //带头结点链表需要给定数据结点
        p = isFlag ? head.next : head;
        while (p != null && j < i) {
            p = p.next;
            j++;
        }
        if (p == null || i < 0) {
            error("错误的更新位置");
        }
        p.data = x;
    }

    public void printList() {
        ListNode node = isFlag ? head.next : head;
        int j = 0;
        while (node != null) {
            out.printf("[%d]=%s\n", j++, node.data);
            node = node.next;
        }
    }
}


/*-------------------------------------------------
双向链表的结点定义和链表类定义 
---------------------------------------------------*/
class DlistNode {

    Object data;
    DlistNode lLink, rLink;
}

class DlinkList {

    DlistNode head;

    //操作定义
    /*构造方法建立头结点*/
    public DlinkList() {
        head = new DlistNode();
        head.lLink = head;  //左指针指向自己
        head.rLink = head;  //右指针也指向自己
    }

    /*头结点左右指针都指向自己为空*/
    public boolean isEmpty() {
        return head.lLink == head
                && head.rLink == head;
    }

    /*在current之后插入新结点，返回新结点*/
    public DlistNode insertAfter(DlistNode current,
            Object x) {
        DlistNode p = new DlistNode();
        p.data = x;
        p.lLink = current;
        p.rLink = current.rLink;
        current.rLink = p;
        current = p.rLink;
        current.lLink = p;
        return p;
    }

    /*在current之前插入新结点，返回新结点*/
    public DlistNode insertBefore(DlistNode current,
            Object x) {
        DlistNode s = new DlistNode();
        s.data = x;
        s.lLink = current.lLink;
        s.rLink = current;
        current.lLink.rLink = s;
        current.lLink = s;
        return s;
    }

    public DlistNode Add(Object x) {
        if (isEmpty()) //表为空在头节点后插入新结点
        {
            return insertAfter(head, x);
        } else //不为空，则在头结点之后插入新结点
        {
            return insertBefore(head, x);
        }
    }

    /*删去结点current*/
    public void deleteNode(DlistNode current) {
        //头节点不能删除
        if (current == head) {
            return;
        }
        current.rLink.lLink = current.lLink;
        current.lLink.rLink = current.rLink;
        current = null;
    }

    public DlistNode getNode(int i) {
        int j = 0;
        DlistNode p = head.rLink;
        while (p != head && j < i) {
            p = p.rLink;
            j++;
        }
        if (p == head || i < 0) {
            error("错误的查找位置");
        }
        return p;
    }

    public int locateNode(Object key) {
        DlistNode p = head.rLink;
        int j = 0;
        while (p != head && !key.equals(p.data)) {
            p = p.rLink;
            j++;
        }
        if (p != head) {
            return j;
        } else {
            return -1;
        }
    }

    public int getLength() {
        DlistNode p = head.rLink;
        int j = 0;
        while (p != head) {
            p = p.rLink;
            j++;
        }
        return j;
    }

    public void printList() {
        DlistNode p = head.rLink;
        int j=0;
        while (p != head) {
            out.printf("[%d]=%s\n",j++,p.data);
            p = p.rLink;
        }
    }

}