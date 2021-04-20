package HomeWork.chapter02;


/**
 * @author 龚皓靖 120191140216 信管1901
 * 约瑟夫问题
 */

class intNode {
    int value;
    intNode next;
}

public class chapter02 {
    static intNode u;
    /*静态reader字段用于读入键盘输入*/
    static java.util.Scanner reader = new java.util.Scanner(System.in);
    static int input(String msg) {
        System.out.print(msg);
        String r = reader.nextLine();
        return Integer.parseInt(r);
    }

    public static void main(String[] args) {
        intNode head = null, q = null;
        int n, m, k = 0, j = 0;
        n = input("请输入总人数：");
        m = input("请输入报数的数字：");
        head = makeLoop(n);   //新建个数为n的循环链表
        if (head == null) {
            return;
        }
        for (j = 1; j <= n; j++) {
            for (k = 1; k < m; k++) {
                u = u.next;
            }
            System.out.println("第" + j + "个报数的人编号：" + u.next.value);
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
        System.out.println("报数时的初始安全位置：" + u.next.value);
        reader.close();
    }//main方法结束

    static intNode makeLoop(int n) {
        intNode h = null,tail,p = null;
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
}
