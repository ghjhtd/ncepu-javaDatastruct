package chapter02;

import static java.lang.System.out;
import static java.lang.System.exit;
import static chapter02.ErrUtil.error;
/*-------------------------------------------------------
 错误处理工具类，提供出错信息打印并退出程序的执行
--------------------------------------------------------*/
class ErrUtil {

    public static void error(String msg) {
        out.println(msg);
        exit(-1);
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


/*-------------------------------------------------
线性表各种实现的测试类，测试线性表实现类的
相关方法是否正常执行 
---------------------------------------------------*/
class TestList {

    public static void main(String[] args) {
        out.printf("初始化一个顺序列表，插入数字1-10\n");
        SeqList sl = new SeqList();
        for (int i = 0; i < 9; i++) {
            sl.insertAt(i, i + 1);
        }
        sl.insertAt(9, 10);
        sl.printList();
        out.println("删除0号之后：");
        sl.deleteAt(0);
        sl.printList();
        out.printf("列表中现在共有%d个元素\n", sl.getLength());
        out.println("删除最后一位元素：");
        sl.deleteAt(sl.getLength() - 1);
        sl.printList();
        Object e = 3;
        out.printf("%s 在列表中的位置是 %d:\n", e, sl.locateNode(e));
        e = "更新对象";
        out.printf("将最后一位元素替换为\"%s\"之后：\n", e);
        sl.updateAt(sl.getLength() - 1, e);
        sl.printList();
        int p = sl.locateNode(e);
        out.printf("%d的位置上是：\"%s\"", p, sl.getNode(p));
        out.println("删空列表：");
        while (sl.getLength() > 0) {
            sl.deleteAt(0);
        }
        out.println("现在列表中的元素数量：" + sl.getLength());
        out.println("现在创建一个不带头结点的空单链表");
        LinkList nhList = new LinkList();
        out.println("加入一些数据");
        Object[] so = new Object[]{"cat", "lion", 5, "dog", 8.5, new java.util.Date()};
        for (int i = 0; i < so.length; i++) {
            nhList.insertAt(i, so[i]);
        }
        out.println("现在列表中的元素：");
        nhList.printList();
        out.println("再插入一些数据");
        nhList.insertAt(0, "data1");
        nhList.insertAt(0, 5);
        nhList.printList();
        out.printf("现在列表中的元素总数：%d\n", nhList.getLength());
        out.println("删除第一个元素和最后一个元素后：");
        nhList.deleteAt(0);
        nhList.deleteAt(nhList.getLength() - 1);
        nhList.printList();
        p = nhList.getLength() / 2;
        out.printf("删除中间的%d位置上的元素%s后\n", p, nhList.getNode(p));
        nhList.deleteAt(p);
        nhList.printList();
        out.println("删除所有列表元素：");
        while (nhList.getLength() > 0) {
            nhList.deleteAt(0);
        }
        out.printf("现在列表的长度为%d\n", nhList.getLength());
        out.println("再初始化一个带头结点的单链表：");
        LinkList hList = LinkList.createFHList(so);
        hList.printList();
        out.printf("%s 在列表中的位置是：%d\n", so[2], hList.locateNode(so[2]));
        out.println("列表元素的总数是" + hList.getLength());
        out.println("删空所有元素");
        while (hList.getLength() > 0) {
            hList.deleteAt(0);
        }
        out.println("建立双向链表");
        DlinkList dList = new DlinkList();
        for(int i=0;i<10;i++) dList.Add(i);
        dList.printList();
        out.println("删除第一个和最后一个结点");
        dList.deleteNode(dList.head.rLink);
        dList.deleteNode(dList.head.lLink);
        dList.printList();
        p=2;
        DlistNode nodeP=dList.getNode(p);
        out.printf("现在链表中%d位置上的元素是%s，",p,nodeP.data);
        out.print("在该位置后添加一个元素\"after node\"，之前位置添加一个\"before node\"\n");
        dList.insertAfter(nodeP, "after node");
        dList.insertBefore(nodeP, "before node");
        dList.printList();
        out.println("之后删除该元素：");
        dList.deleteNode(nodeP);
        dList.printList();
        out.printf("现在的元素数量是：%d\n",dList.getLength());
        out.println("删空该链表");
        while(!dList.isEmpty()) dList.deleteNode(dList.head.rLink);
        out.printf("现在链表中的元素数量是%d",dList.getLength());
        
    }
}

class intNode {

    int value;
    intNode next;
}

/*-------------------------------------------------
     约瑟夫问题的可执行Java应用程序实现 
---------------------------------------------------*/
class Josephus {

    static intNode u;
    /*静态reader字段用于读入键盘输入*/
    static java.util.Scanner reader = new java.util.Scanner(System.in);

    static intNode makeLoop(int n) {
        intNode h, /* 链表的头指针 */
                tail, /* 链表末尾表元的指针 */
                p = null;
        int k;
        if (n <= 0) {
            return null;
        }
        h = tail = null;
        k = 1;
        while (k <= n) {
            p = new intNode();
            p.value = k++;
            if (h == null) {
                h = tail = p;
            } else {
                tail = tail.next = p;
            }
        }
        tail.next = h;
        u = tail;
        return h;
    }

    static int input(String msg) {
        System.out.print(msg);
        String r = reader.nextLine();
        return Integer.parseInt(r);
    }

    public static void main(String[] args) {
        intNode head, q = null;
        int n, m, k = 0, j = 0;
        n = input("请输入总人数：");
        m = input("请输入报数的数字：");
        head = makeLoop(n);
        if (head == null) {
            return;
        }
        for (j = 1; j <= n; j++) {
            for (k = 1; k < m; k++) {
                u = u.next;
            }
            System.out.printf("\tDELETE: %d\n",
                    u.next.value);
            q = u.next;
            u.next = u.next.next;
            if (q == head) {
                head = u.next;
            }
            if (u.next == q) {
                head = null;
            }
            q = null;
        }//for循环结束
        reader.close();
    }//main方法结束
}//Josephus类定义结束