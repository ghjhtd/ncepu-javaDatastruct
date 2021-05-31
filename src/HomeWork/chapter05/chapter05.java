package HomeWork.chapter05;
import java.util.*;

import static HomeWork.chapter05.KeyBoard.getchar;

/**
 * @Description: 二叉链表的建立和遍历输出
 * @author: 龚皓靖 120191140216
 * @date: 2021年04月28日 10:22
 */
public class chapter05 {
    public static void main(String[] args) {
        boolean state = true;
        String a;
        char[] pno;
        char[] ino;
        BiTree tree = new BiTree();
        BiTNode root = null;
        while (true){
            a = getchar("新建一棵二叉树\n输入q退出\n按任意键继续\n");
            if(a.toUpperCase().equals("Q")) break;
            pno = getchar("请输入二叉树的先序序列:").toCharArray();
            ino = getchar("请输入二叉树的中序序列:").toCharArray();
            if(isZimu(pno) && isZimu(ino)){
                System.out.println("\n通过 先序字符串\n " + String.valueOf(pno) +" \n和中序字符串\n " + String.valueOf(ino) + " \n创建二叉树：\n");
                root = (BiTNode) tree.createCharBiTree(String.valueOf(pno), String.valueOf(ino));
                state = true;
                while (state){
                    a = getchar("请选择遍历方式：\n1--非递归先序遍历\n" +
                            "2--非递归中序遍历\n3--非递归后序遍历\n4--递归先序遍历\n" +
                            "5--递归中序遍历\n6--递归后序遍历\n" +
                            "7--层次遍历\n0--新建二叉树\n");
                    switch (a){
                        case "1":
                            System.out.println("\n**************************************");
                            System.out.println("非递归先序遍历");
                            tree.preOrder_Task(root);
                            System.out.println("\n**************************************");
                            break;
                        case "2":
                            System.out.println("\n**************************************");
                            System.out.println("非递归中序遍历");
                            tree.inOrder_Task(root);
                            System.out.println("\n**************************************");
                            break;
                        case "3":
                            System.out.println("\n**************************************");
                            System.out.println("非递归后序遍历");
                            tree.postOrder_Task(root);
                            System.out.println("\n**************************************");
                            break;
                        case "4":
                            System.out.println("\n**************************************");
                            System.out.println("递归先序遍历");
                            tree.preOrder(root);
                            System.out.println("\n**************************************");
                            break;
                        case "5":
                            System.out.println("\n**************************************");
                            System.out.println("递归中序遍历");
                            tree.inOrder(root);
                            System.out.println("\n**************************************");
                            break;
                        case "6":
                            System.out.println("\n**************************************");
                            System.out.println("递归后序遍历");
                            tree.postOrder(root);
                            System.out.println("\n**************************************");
                            break;
                        case "7":
                            System.out.println("\n**************************************");
                            System.out.println("层次遍历");
                            List list;
                            list = tree.level_Iterator(root);
                            for (Object list1:list){
                                System.out.println(list1);
                            }
                            System.out.println("\n**************************************");
                            break;
                        case "0":
                            state = false;
                            break;
                        default:
                            System.out.println("请输入正确的数字！！！！！！！！！！！！！！！！！！！！\n");
                            break;
                    }
                }
            }
            else
            System.out.println("请将输入数据类型限定为26个英文字母!!!");
        }
        System.out.println("程序已退出！");
    }
    /**
    * @Description: 判断字符数组数据是否都属于26个英文字母
    * @author: 龚皓靖
    * @date: 2021/5/23 23:11
    * @param arr: 输入的字符数组
    * @Return: boolean 为真时表示字符数组数据都属于26个英文字母
    */
    public static boolean isZimu(char[] arr){
        boolean isZM = true;
        for(char i : arr){
            if(!((i >= 'a' && i <= 'z') || (i >= 'A' && i <= 'Z'))) isZM = false;
        }
        return isZM;
    }
}

/**
* @Description: 二叉树的二叉链表表示法
* @author: 龚皓靖
* @date: 2021/4/28 10:42
*/
class BiTNode {
    /* 结点结构*/
    Object data;
    BiTNode lchild, rchild;
    /*左右孩子指针*/
}

/**
* @Description: 二叉树定义
* @author: 龚皓靖
* @date: 2021/4/28 10:42
*/
class BiTree {

    public BiTNode root;  //根结点
    /**
    * @Description: 先序递归遍历二叉树
    * @author: 龚皓靖
    * @date: 2021/4/28 10:52
    * @param T: 根结点
    * @Return: void
    */
    public void preOrder(BiTNode T) {

        if (T != null) {
            visit(T.data);
            /*访问结点*/
            BiTree.this.preOrder(T.lchild);
            /*遍历左子树*/
            BiTree.this.preOrder(T.rchild);
            /*遍历右子树*/
        }
    }

    /**
    * @Description: 后续递归遍历二叉树
    * @author: 龚皓靖
    * @date: 2021/4/28 10:53
    * @param root: 根结点
    * @Return: void
    */
    public void  postOrder(BiTNode root){
        if(root==null)  return;
        postOrder(root.lchild);
        postOrder(root.rchild);
        visit(root.data);
    }

    /**
    * @Description: 中序递归遍历二叉树
    * @author: 龚皓靖
    * @date: 2021/4/28 10:54
    * @param T: 根结点
    * @Return: void
    */
    public void inOrder(BiTNode T) {
        if (T == null) {
            return;
        }
        inOrder(T.lchild);
        this.visit(T.data);
        inOrder(T.rchild);
    }

    /**
    * @Description: 用于访问Visit的操作实现
    * @author: 龚皓靖
    * @date: 2021/4/28 10:54
    * @param data: 数据
    * @Return: void
    */
    private void visit(Object data) {
        System.out.print(data);
    }

    /**
    * @Description: 用于任务书法非递归遍历二叉树时的结点任务类型
    * @author: 龚皓靖
    * @date: 2021/4/28 10:54
    * @para Visit :访问 Travel :遍历
    * @Return:
    */
    private enum Task {
        Visit, Travel
    }

    /**
    * @Description: 任务书法存入到栈中的元素类型定义
    * @author: 龚皓靖
    * @date: 2021/4/28 10:55
    * @Return:
    */
    private class ElemType {

        ElemType(Task task) {
            this.task = task;
        }
        /*指向根结点的指针*/
        BiTNode ptr;
        /*任务性质*/
        Task task;
    }

    /**
    * @Description: 任务书法非递归遍历二叉树，利用栈实现先序遍历，BT为根结点
    * @author: 龚皓靖
    * @date: 2021/5/26 20:27
    * @param BT: 树的根结点
    * @Return: void
    */
    public void preOrder_Task(BiTNode BT){
        Stack S = new Stack();
        ElemType e = new ElemType(Task.Travel);
        e.ptr = BT;
        if (BT != null) {
            S.push(e);
            /*布置初始任务*/
        }
        while (!S.isEmpty()) {
            e = (ElemType) S.pop();
            /*每次处理一项任务*/
            if (e.task == Task.Visit) {
                visit(e.ptr.data);
                /* 访问任务*/
            } else if (e.ptr != null) {
                /*处理非空树的遍历任务*/
                BiTNode p = e.ptr;
                e = new ElemType(Task.Travel);
                e.ptr = p.rchild;
                S.push(e);
                e = new ElemType(Task.Travel);
                e.ptr = p.lchild;
                S.push(e);
                e = new ElemType(Task.Visit);
                e.ptr = p;
                S.push(e);
            }
            /*处理非空树遍历任务结束*/
        }
    }

    /**
    * @Description: 任务书法非递归遍历二叉树，利用栈实现后序遍历，BT为根结点
    * @author: 龚皓靖
    * @date: 2021/5/26 20:27
    * @param BT: 树的根结点
    * @Return: void
    */
    public void postOrder_Task(BiTNode BT){
        Stack S = new Stack();
        ElemType e = new ElemType(Task.Travel);
        e.ptr = BT;
        if (BT != null) {
            S.push(e);
            /*布置初始任务*/
        }
        while (!S.isEmpty()) {
            e = (ElemType) S.pop();
            /*每次处理一项任务*/
            if (e.task == Task.Visit) {
                visit(e.ptr.data);
                /* 访问任务*/
            } else if (e.ptr != null) {
                /*处理非空树的遍历任务*/
                BiTNode p = e.ptr;
                e = new ElemType(Task.Visit);
                e.ptr = p;
                S.push(e);
                e = new ElemType(Task.Travel);
                e.ptr = p.rchild;
                S.push(e);
                e = new ElemType(Task.Travel);
                e.ptr = p.lchild;
                S.push(e);
            }
            /*处理非空树遍历任务结束*/
        }
    }

    /**
    * @Description: 任务书法非递归遍历二叉树，利用栈实现中序遍历，BT为根结点
    * @author: 龚皓靖
    * @date: 2021/4/28 10:56
    * @para BT:树的根结点
    * @Return: void
    */
    public void inOrder_Task(BiTNode BT1) {
        Stack S = new Stack();
        ElemType e = new ElemType(Task.Travel);
        e.ptr = BT1;
        if (BT1 != null) {
            S.push(e);
            /*布置初始任务*/
        }
        while (!S.isEmpty()) {
            e = (ElemType) S.pop();
            /*每次处理一项任务*/
            if (e.task == Task.Visit) {
                visit(e.ptr.data);
                /* 访问任务*/
            } else if (e.ptr != null) {
                /*处理非空树的遍历任务*/
                BiTNode p = e.ptr;
                System.out.print("");
                /*依次将右子树遍历、根元素访问、左子树遍历任务入栈*/
                e = new ElemType(Task.Travel);
                e.ptr = p.rchild;
                S.push(e);
                e = new ElemType(Task.Visit);
                e.ptr = p;
                S.push(e);
                e = new ElemType(Task.Travel);
                e.ptr = p.lchild;
                S.push(e);
            }
            /*处理非空树遍历任务结束*/
        }
        /*while循环结束*/
    }

    /**
    * @Description: 层次遍历二叉树
    * @author: 龚皓靖
    * @date: 2021/5/26 21:56
    * @param root: 树的根结点
    * @Return: 包含每行元素集合的一个集合
    */
    public List<List> level_Iterator(BiTNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List> res = new ArrayList<>();
        Queue<BiTNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List list = new ArrayList();
            int count = queue.size();
            while (count > 0) {
                BiTNode node = queue.poll();
                list.add(node.data);

                if (node.lchild != null) {
                    queue.add(node.lchild);
                }
                if (node.rchild != null) {
                    queue.add(node.rchild);
                }
                count--;

            }
            res.add(list);
        }

        return res;
    }

    /**
     * 利用先序和中序遍历的字符串序列创建字符二叉树
     * @param pre   先根序字符串
     * @param ino   中跟序字符串
     * @return   创建之后的二叉树根结点
     */
    public BiTNode createCharBiTree(String pre, String ino) {
        return crtCBT(pre, ino, 0, 0, pre.length());
    }

    /*已知pre[ps..ps+n-1]为二叉树的先序序列，
     ino[is..is+n-1]为二叉树的中序序列，本算
    法由此两个序列递归构造二叉链表*/
    private BiTNode crtCBT(String pre, String ino, int ps, int is, int n) {
        if (n == 0) {
            return null;
        } else {
            /*k为中序序列中的根结点的位置*/
            int k = ino.indexOf(pre.charAt(ps));
            if (k == (-1)) {
                return null;
            } else {
                BiTNode T = new BiTNode();
                T.data = pre.charAt(ps);
                if (k == is) {
                    T.lchild = null;
                } else {
                    T.lchild = crtCBT(pre, ino,
                            ps + 1, is, k - is);
                }
                if (k == (is + n - 1)) {
                    T.rchild = null;
                } else {
                    T.rchild = crtCBT(pre, ino,
                            ps + 1 + (k - is), k + 1, n - (k - is) - 1);
                }
                return T;

            }
        }
    }/* end crtCBT */

    /*获取一个二叉树结点的辅助方法*/
    private BiTNode getBiTNode(Object item,
                               BiTNode lptr, BiTNode rptr) {
        BiTNode T = new BiTNode();
        T.data = item;
        T.lchild = lptr;
        T.rchild = rptr;
        return T;
    }

    /*复制一个二叉树*/
    public BiTNode copyTree(BiTNode T) {
        BiTNode newlptr, newrptr, newT;
        if (T == null) {
            return null;
        }
        /*复制左子树和右子树*/
        if (T.lchild != null) {
            newlptr = copyTree(T.lchild);
        } else {
            newlptr = null;
        }
        if (T.rchild != null) {
            newrptr = copyTree(T.rchild);
        } else {
            newrptr = null;
        }
        newT = getBiTNode(T.data, newlptr, newrptr);
        return newT;
    }

    /*若二叉树中存在和 x 相同的元素，则返回指向该结点,否则返回 null */
    public BiTNode locate(BiTNode T, Object x) {
        if (T != null) {
            if (x.equals(T.data)) {
                return T;
            } else {
                if (locate(T.lchild, x) != null) {
                    return T.lchild;
                } else if (locate(T.rchild, x) != null) {
                    return T.rchild;
                }
            }/* end if(x.equals(T.data)) */
        }
        return null;
    }

    //用于二叉树叶子结点计数的辅助字段变量
    private int count;

    /**
     * 统计叶子数量
     @param T 待统计叶子数量的树根结点
     */
    public int countLeaf1(BiTNode T) {
        count = 0;
        cntLeaf(T);
        return count;
    }

    private void cntLeaf(BiTNode T) {
        if (T != null) {
            /*对叶子结点计数*/
            if (T.lchild == null && T.rchild == null) {
                this.count++;
            }
            cntLeaf(T.lchild);
            cntLeaf(T.rchild);
        }
    }

    /**
     * 统计叶子数量
     @param T 待统计叶子数量的树根结点
     */
    public int countLeaf2(BiTNode T) {
        if (T == null) {
            return 0;
        }
        if (T.lchild == null && T.rchild == null) {
            return 1;
        } else {
            int m = countLeaf2(T.lchild);
            int n = countLeaf2(T.rchild);
            return (m + n);
        }
    }

    /**
     * 统计二叉树所有的结点数量
     * @param T  待统计的二叉树根结点
     * @return    二叉树结点总数
     */
    public int count(BiTNode T) {
        if (T == null) {
            return 0;
        }
        if (T.lchild == null && T.rchild == null) {
            return 1;
        } else {
            int m = count(T.lchild);
            int n = count(T.rchild);
            return (m + n + 1);
            /*左右子树叶子结点数+1*/
        }
    }

    /**
     * 统计二叉树的深度
     @param  T  待统计深度的二叉树根结点
     */
    public int depth(BiTNode T) {
        /*返回二叉树深度*/
        int depthval;
        if (T == null) {
            depthval = 0;
        } else {
            int depthLeft = depth(T.lchild);
            int depthRight = depth(T.rchild);
            depthval = 1 + (depthLeft > depthRight
                    ? depthLeft : depthRight);
        }
        return depthval;
    }

}

/**
* @Description: 工具类
* @author: 龚皓靖
* @date: 2021/4/28 10:43
*/
class KeyBoard {

    private static Scanner reader = new Scanner(System.in);
    static String getchar(String msg) {
        System.out.print(msg);
        return reader.nextLine();
    }
    static  void error(String msg){
        System.err.println(msg);
        System.exit(-1);
    }
}