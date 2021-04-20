/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HomeWork.chapter04;
import static HomeWork.chapter04.ErrUtil.error;
/**
 *
 * @龚皓靖 120191140216 
 */

class ErrUtil {
    public static void error(String msg) {
        System.out.println(msg);
        System.exit(-1);
    }
}

public class chapter04 {
    public static void main(String[] args){
        Person[] persons = new Person[50];
        for(int i=0;i<persons.length;i++){
            persons[i]=new Person();
            double rnd=Math.random();
            int fm=(int)(2*rnd);
            if(fm==0){
                persons[i].sex='女';
                persons[i].name=(i+1) + "号女舞者";
            }else {
                persons[i].sex='男';
                persons[i].name=(i+1) + "号男舞者";
            }      
        }
        DancersPair.dancePartners(persons);        
    }
    
}

class Person{
    public String name;
    public char sex;
    public Person parterner;
    
    public Person(){
    }
    
    public Person(String name, char sex, Person parterner){
        this.name = name;
        this.sex = sex;
        this.parterner = parterner;
    }
    
    public Person(String name, char sex){
        this.name = name;
        this.sex = sex;
    }
    
    void setName(String name){
        this.name = name;
    }
    
    String getName(){
        return this.name;
    }
    
    void setSex(char sex){
        this.sex = sex;
    }
    
    char getSex(){
        return this.sex;
    }
    
    void setParterner(Person parterner){
        this.parterner = parterner;
    }
    
    Person getParterner(){
        return this.parterner;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", sex=" + sex + ", parterner=" + parterner + '}';
    }

   
       
}

class DancersPair {

    static void dancePartners(Person dancer[]) {
        int i = 0, num = dancer.length;
        Person P = new Person();
        Person PF = new Person();
        Person PM = new Person();
        Queue Mdancers = new CirQueue(),
                Fdancers = new LinkQueue();
        for (i = 0; i < num; i++) {
            P = dancer[i];
            if (P.sex == '女') {
                Fdancers.enQueue(P);
            } else {
                Mdancers.enQueue(P);
            }
        }
        System.out.printf("配对情况如下:\n");
        while (!Fdancers.queueEmpty()
                && !Mdancers.queueEmpty()) {
            PF = (Person) Fdancers.deQueue();
            System.out.printf("%s   ", PF.name);
            PM = (Person) Mdancers.deQueue();
            System.out.printf("%s   \n ", PM.name);
            PF.parterner = PM;
            PM.parterner = PF;
        }
        if (!Fdancers.queueEmpty()) {
            System.out.printf("女队还有%d人等待\n",
                    Fdancers.length());
            P = (Person) Fdancers.queueFront();
            System.out.printf("女队中第一个等待的是%s\n",
                    P.name);
        }
        if (!Mdancers.queueEmpty()) {
            System.out.printf("男队还有%d人等待\n",
                    Mdancers.length());
            P = (Person) Mdancers.queueFront();
            System.out.printf(
                    "男队中第一个等待的是%s\n", P.name);
        }
    }
}

/*栈基本运算的接口功能描述*/
interface Stack {

    boolean stackEmpty();  // 判栈空

    boolean stackFull();  // 判栈满

    void push(Object x);  //进栈

    Object pop();  //退栈

    Object stackTop(); // 访问栈顶元素
}

/*-------------------------------------------------------------
顺序栈类定义及对Stack接口中栈基本运算的功能实现
----------------------------------------------------------------*/
class SeqStack implements Stack {

    public static final int StackSize = 100;
    Object[] data = new Object[StackSize];
    int top;

    //栈基本运算实现定义
    public SeqStack() {
        //置空栈
        top = -1;
    }

    public boolean stackEmpty() {
        return top == -1;
    }

    public boolean stackFull() {
        return top == StackSize - 1;
    }

    public void push(Object x) {
        if (stackFull()) {
            error("栈满");
        }
        ++top;
        data[top] = x;
    }

    public Object pop() {
        if (stackEmpty()) {
            error("栈空");
        }
        return data[top--];
    }

    public Object stackTop() {
        if (stackEmpty()) {
            error("栈空");
        }
        return data[top];
    }
}
/*链式栈和链式队列的链表结点定义*/
class ListNode {
    Object data;
    ListNode next;
}

/*-----------------------------------------------------
链式栈的定义及对Stack接口中栈基本运算功能的实现
-------------------------------------------------------*/
class LinkStack implements Stack {

    ListNode top;

    //栈基本操作实现定义
    public LinkStack() {
        top = null;
    }

    public boolean stackEmpty() {
        return top == null;
    }

    public void push(Object x) {
        ListNode p = new ListNode();
        p.data = x;
        p.next = top;
        top = p; //新结点链入top之前, 并成为新栈顶
    }

    public Object pop() {
        Object x = null;
        ListNode p = top;
        if (stackEmpty()) {
            error("栈空");
        }
        x = p.data;
        top = p.next;
        p = null;
        return x;
    }

    public Object stackTop() {
        if (stackEmpty()) {
            error("栈空");
        }
        return top.data;
    }

    public boolean stackFull() {
        return false;  //链式栈无栈满，所以总返回false
    }

}
/*队列基本运算的接口功能描述*/
interface Queue {

    boolean queueEmpty();  // 判队空

    boolean queueFull();  // 判队满

    void enQueue(Object x);  //入栈

    Object deQueue();  //出队

    Object queueFront(); // 访问队头元素

    int length(); //求队长
}


/*--------------------------------------------------
循环顺序队列类定义及对Queue接口的功能实现定义
-----------------------------------------------------*/
class CirQueue implements Queue {
    //队列总长定义 

    public static final int QueueSize = 100;

    /*成员定义*/
    int front;
    int rear;
    int count;
    Object[] data = new Object[QueueSize];

    //操作定义
    public CirQueue() {
        front = rear = 0;
        count = 0;
    }

    public boolean queueEmpty() {
        return count == 0;
    }

    public boolean queueFull() {
        return count == QueueSize;
    }

    public void enQueue(Object x) {
        if (queueFull()) {
            error("队满");
        }
        count++;
        rear = (rear + 1) % QueueSize;
        data[rear] = x;
    }

    public Object deQueue() {
        Object temp;
        if (queueEmpty()) {
            error("队空");
        }
        front = (front + 1) % QueueSize;
        temp = data[front];
        count--;
        return temp;
    }

    public Object queueFront() {
        if (queueEmpty()) {
            error("队空");
        }
        return data[front + 1];
    }

    @Override
    public int length() {
        return count;
    }

}

/*----------------------------------------------
链式队列类定义及对队列Queue接口的功能实现
-------------------------------------------------*/
class LinkQueue implements Queue {
   //ListNode是前面链式栈中的链表结点
    ListNode front;
    ListNode rear;
    //操作定义

    public LinkQueue() {
        front = rear = null;
    }

    public boolean queueEmpty() {
        return front == null && rear == null;
    }

    public void enQueue(Object x) {
        ListNode p = new ListNode();
        p.data = x;;
        if (queueEmpty()) {
            front = rear = p;
        } else {
            rear = rear.next = p;
        }
    }

    public Object deQueue() {
        if (queueEmpty()) {
            error("队空");
        }
        ListNode p = front;
        Object x = p.data;
        front = p.next;
        if (rear == p) {
            rear = null;
        }
        p = null;
        return x;
    }

    public Object queueFront() {
        if (queueEmpty()) {
            error("队空");
        }
        return front.data;
    }

    public boolean queueFull() {
        return false;  //链式队列无堆满，总返回false
    }

    public int length() {
        int j = 0;
        ListNode p = front;
        while (p != null) {
            j++;
            p = p.next;
        }
        return j;
    }

}