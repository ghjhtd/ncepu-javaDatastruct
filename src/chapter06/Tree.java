package chapter06;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import static chapter06.KeyBoard.getchar;
import static chapter06.KeyBoard.error;

class KeyBoard {

    static Scanner reader = new Scanner(System.in);
    static String getchar() {
        return getchar("");
    }
    static String getchar(String msg) {
        System.out.print(msg);
        return reader.nextLine();
    }
    static  void error(String msg){
        System.err.println(msg);
        System.exit(-1);
    }
}

/*-------------------------------
顺序二叉树表示法
---------------------------------*/
class SqBiTree {

    public static int MAX_TREE_SIZE = 100;
    /*二叉树的最大结点数*/
    Object[] sqBiTree = new Object[MAX_TREE_SIZE];
    /* 0号单元存储根结点*/
}

/*----------------------------------------------
二叉树的二叉链表表示法
------------------------------------------------*/
class BiTNode {

    /* 结点结构*/
    Object data;
    BiTNode lchild, rchild;
    /*左右孩子指针*/
}

/*线索二叉树指针标志
   Link:指针，Thread:线索
 */
enum PT {
    Link, Thread
}
/*线索二叉树结点*/
class BiThrNode {

    Object data;
    BiThrNode lchild, rchild;  // 左右指针
    PT LTag, RTag;    // 左右标志
}

/* 二叉树定义*/
class BiTree {

    public BiTNode root;  //根结点

    //<editor-fold desc="二叉树操作实现定义" defaultstate="collapsed">
    //操作
    
    /*先序递归遍历二叉树 */
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
   
    /*后续递归遍历二叉树*/
    public void  postOrder(BiTNode root){
       if(root==null)  return;
      postOrder(root.lchild);
      postOrder(root.rchild);
      visit(root.data);
    }
    
    /*中序递归调用二叉树*/
    public void inOrder(BiTNode T) {
        if (T == null) {
            return;
        }
        inOrder(T.lchild);
        this.visit(T.data);
        inOrder(T.rchild);
    }
    
    /*用于访问Visit的操作实现*/
    private void visit(Object data) {
        System.out.print(data);
    }

    /**
     * 用于任务书法非递归遍历二叉树时的结点任务类型 
     * Visit :访问 Travel :遍历
     */
    private enum Task {
        Visit, Travel
    }

    /*任务书法存入到栈中的元素类型定义 */
    private class ElemType {

        ElemType(Task task) {
            this.task = task;
        } 
        
        /*指向根结点的指针*/
        BiTNode ptr;
       
        /*任务性质*/
        Task task;
        
    }

    /*任务书法非递归遍历二叉树，利用栈实现中序遍历，BT为根结点*/
    public void inOrder_Task(BiTNode BT) {
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

    /*路径分析法非递归遍历二叉树*/
    public void inOrder_Path(BiTNode T) {
        Stack S = new Stack();
        /*找到最左下的结点*/
        BiTNode t = goFarLeft(T, S);
        while (t != null) {
            visit(t.data);
            if (t.rchild != null) {
                t = goFarLeft(t.rchild, S);
            } else if (!S.isEmpty()) {
                t = (BiTNode) S.pop();/*退栈*/
            } else {
                t = null; /*栈空表明遍历结束*/
            }
        } /*end while*/

    }
    
    /*找到最左下的结点，同时让寻找路径中的其他结点入栈*/
    private BiTNode goFarLeft(BiTNode T, Stack S) {
        if (T == null) {
            return null;
        }
        while (T.lchild != null) {
            S.push(T);
            T = T.lchild;
        }
        return T;
    }

    
   /**
             * 使用完全先序序列创建字符二叉树
             */
            public BiTNode createPreOrderCharBiTree() {
                data = getchar("请输入完全先序字符串:").toCharArray();
                i = 0;
                return crtPOCharBT();
            }

            char[] data = {'A', 'B', ' ', 'C', ' ', ' ', 'D', ' ', ' '};

            //私有辅助字段i，用于先序创建二叉树createBiTree方法递归调用时数组的位置指针
            private int i;
            /*递归调用创建先序的二叉树*/
            private BiTNode crtPOCharBT() {
                BiTNode T;
                char ch = data[i++];
                if (ch == ' ') {
                    return null;
        } else {
            T = new BiTNode();
            /*生成根结点*/
            T.data = ch;
            /*构造左子树*/  
            T.lchild = crtPOCharBT();
            /*构造右子树*/
            T.rchild = crtPOCharBT();
          
        }
        return T;
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
            if (k == -1) {
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
                if (k == is + n - 1) {
                    T.rchild = null;
                } else {
                    T.rchild = crtCBT(pre, ino,
                            ps + 1 + (k - is), k + 1, n - (k - is) - 1);
                }
                return T;

            }
        }
    }/* end crtCBT */
   
    /*从中缀表达式创建表达式二叉树*/
    public BiTNode createExpTree(String exp) {
        Stack<Character> S = new Stack();
        Stack<BiTNode> PTR = new Stack();
        int i = 1;
        S.push('#');
        char ch = exp.charAt(i), c;
        BiTNode T = null;
        while (!(S.peek() == '#' && ch == '#')) {
            /*如果是操作数，则建叶子结点并入栈*/
            if ('0' <= ch && ch <= '9') {
                T = crtDataLeaf(PTR, ch);
            } else {
                switch (ch) {
                    case '(':
                        S.push(ch);
                        break;
                    case ')':
                        c =  S.pop();
                        while (c != '(') {
                            T = crtOpSubTree(PTR, c);  // 建运算符子树并入栈
                            c =  S.pop();
                        }
                        break;
                    default:
                      
                        while ((c= S.peek())!=ch&&precede(c,ch)) {
                            T = crtOpSubTree(PTR, c);
                            c =  S.pop();
                        }
                        if (ch != '#') {
                            S.push(ch);
                        }
                        break;

                } // switch

            }
            if (ch != '#') {
                i++;
                ch = exp.charAt(i);
            }
        }
        /*end while*/
        return  PTR.pop();
    }

    /*创建表达式二叉树的辅助方法，用于得到二叉树叶子结点*/
    private BiTNode crtDataLeaf(Stack PTR, char ch) {
        BiTNode T = new BiTNode();
        T.data = ch;
        PTR.push(T);
        return T;
    }
   /*创建表达式二叉树的辅助方法，用于创建二叉树子树*/
    private BiTNode crtOpSubTree(Stack<BiTNode> PTR, char c) {
        BiTNode T = new BiTNode();
        T.data = c;
        BiTNode rc = PTR.pop();
        T.rchild = rc;
        BiTNode lc = PTR.pop();
        T.lchild = lc;
        PTR.push(T);
        return T;
    }
      /**
       * 比较中缀表达式中的当前运算符ch和前一运算符c之间的优先级别的辅助方法
       @param  c 前一运算符，在算法中位于栈顶
       @param ch 当前正在处理的运算符
       @return  ch运算符优先级高于c返回false，低于c返回true
       */
    private  boolean precede(char c, char ch) {
     char[][] opTable={
        //该二维数组的第0行字符代表正在处理的运算符，
        //第0列中的字符代表处于栈顶的前一运算符
        {' ',  '+',  '-',   '*',   '/',  '%',  '(',   ')',  '#'},
        {'+', '>',  '>',  '<', '<', '<',   '<', '>', '>'},
        {'-',  '>',  '>',  '<', '<', '<',   '<', '>',  '>'},
        {'*',  '>',  '>',  '>', '>', '>',   '<', '>',  '>'},
        {'/',  '>',  '>',  '>',  '>', '>',  '<', '>',  '>'},
        {'%', '>', '>',  '>',  '>', '>',  '<',  '>',  '>'},
        {'(',   '<', '<',  '<',  '<', '<',  '<',  '=',  'e'},
        {'#',  '<', '<',  '<',  '<', '<',  '<',  'e',  '='}
    };
        int i,j;
       for(i=0;i<opTable.length;i++)
            if(opTable[i][0]==c) break;
       for(j=0;j<opTable[0].length;j++)
            if(opTable[0][j]==ch) break;
       if(i==opTable.length) 
           error("不被支持的栈顶运算符！"+c);
       if(j==opTable[0].length) 
           error("不被支持的表达式运算符"+ch);
       char result=opTable[i][j];
       return  result=='>';
    }
 
    
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

    /**
     * 中序遍历二叉线索树
     * @param  Thrt  线索二叉树的头结点
     */
    public void inOrder_ThrTree(BiThrNode Thrt) {
        /*p指向根结点，Thrt是头结点的指针*/
        BiThrNode p = Thrt.lchild;
        while (p != Thrt) {
            /*空树或遍历结束时，p==Thrt*/
            while (p.LTag == PT.Link) {
                p = p.lchild;
                /*第一个结点*/
            }
            visit(p.data);
            while (p.RTag == PT.Thread && p.rchild != Thrt) {
                p = p.rchild;
                visit(p.data);
                /*访问后继结点*/
            }
            p = p.rchild;
            /*p进至其右子树根*/
        }
    }

    /**
     * 由普通的二叉树获取一个尚未线索化的二叉树根结点
     *@param  T  普通二叉树的根结点
     * @return   返回未线索化的线索二叉树的根结点
     */
    public BiThrNode getUnThreadBiTree(BiTNode T) {
        if (T == null) {
            return null;
        }
        BiThrNode root = new BiThrNode();
        root.lchild = getUnThreadBiTree(T.lchild);
        root.rchild = getUnThreadBiTree(T.rchild);
        root.data = T.data;
        root.RTag = root.LTag = PT.Link;
        return root;
    }

    /**
     * 按先序遍历未线索化二叉树，采用递归实现
     *@param  root  未线索化的二叉树根结点
     */
    public void preOrder(BiThrNode root) {
        if (root == null) {
            return;
        }
        visit(root.data);
        preOrder(root.lchild);
        preOrder(root.rchild);
    }

    private BiThrNode pre;  //辅助字段变量pre，用于线索化二叉树时记录当前结点的前驱

    /**
     * 构建带头结点的中序线索链表，Thrt用于向调用者回传创建的头结点指针
     *@param  待线索化的线索二叉树的根结点
     * @return  线索化之后的线索二叉树头结点
     */
    public BiThrNode threadingInOrder(BiThrNode T) {
        /*初始化头结点*/
        BiThrNode Thrt = new BiThrNode();
        Thrt.LTag = PT.Link;
        Thrt.RTag = PT.Thread;
        Thrt.rchild = Thrt;
        if (T == null) {
            Thrt.lchild = Thrt;
        } else {
            Thrt.lchild = T;
            pre = Thrt;
            threadingInOrd(T);
            pre.rchild = Thrt;/*处理最后一个结点*/
            pre.RTag = PT.Thread;
            Thrt.rchild = pre;
        }
        return Thrt;
    }

    /*用于线索化二叉树中结点线索化的辅助方法，采用递归实现各个结点的线索化*/
    private void threadingInOrd(BiThrNode p) {
        if (p != null) {
            /*对p为根的二叉树进行线索化*/
            threadingInOrd(p.lchild);
            /*左子树线索化*/
            if (p.lchild == null) {/*建前驱线索*/
                p.LTag = PT.Thread;
                p.lchild = pre;
            }
            if (pre.rchild == null) {/*建后继线索*/
                pre.RTag = PT.Thread;
                pre.rchild = p;
            }
            pre = p;
            /*保持 pre 指向 p 的前驱*/
            threadingInOrd(p.rchild);
            /*右子树线索化*/
        }
        /*end if(p!=null)*/
    }
    /*InThreading*/
    // </editor-fold>

}

/*--------------------------------------------------
二叉树的三叉链表表示法
----------------------------------------------------*/
class TriTNode {/*结点结构*/
    Object data;
    TriTNode lchild, rchild;
    /*左右孩子指针*/
    TriTNode parent;
    /*双亲指针*/
}

class TriBiTree {/*树定义*/
    TriTNode root;
    //操作
}

/*-----------------------------------------------------
二叉树的双亲列表表示法
------------------------------------------------------*/
class BPTNode {/* 结点结构*/
    Object data;
    int parent;
    /*双亲的位置索引*/
    char LRTag;
    /*左、右孩子标志域*/
}

class BPTree {

    /*树结构*/
    public static final int MAX_TREE_SIZE = 100;
    BPTNode[] nodes = new BPTNode[MAX_TREE_SIZE];
    int num_node;
    /*结点数目*/
    int root;
    /*根结点的位置*/
    //操作
}

//<editor-fold desc="树和森林表示">
/*------------------------------------------
      双亲表示法
-------------------------------------------*/
class PTNode {

    Object data;
    int parent;
    /*双亲位置域*/
}

class PTree {

    public static int MAX_TREE_SIZE = 100;
    PTNode nodes[] = new PTNode[MAX_TREE_SIZE];
    int r, n;
    /*根结点的位置和结点个数*/
}

/*------------------------------------------------------
   孩子链表表示法
--------------------------------------------------------*/
//链表结点定义
class CTNode {

    int child;
    /*孩子结点编号*/
    CTNode nextchild;
}
//孩子链表定义

class CTBox {

    Object data;
    CTNode firstchild;
    /*孩子链的头指针*/
}
//孩子链表树的定义

class CTree {

    public static int MAX_TREE_SIZE = 100;
    CTBox[] nodes = new CTBox[MAX_TREE_SIZE];
    int n, r;

    /*结点数和根结点的位置*/
    public int depth(CTree T) {
        /*T 是树的孩子链表存储结构，
    返回该树的深度*/
        if (T.n == 0) {
            return 0;
        } else {
            return dpth(T, T.r);
        }
    }

    /*TreeDepth*/
    private int dpth(CTree T, int root) {
        int max = 0;
        CTNode p = T.nodes[root].firstchild;
        while (p != null) {
            int h = dpth(T, p.child);
            if (h > max) {
                max = h;
            }
            p = p.nextchild;
        }/*end while*/
        return max + 1;
    }

}

/*----------------------------------------------------
孩子-兄弟二叉树表示法
------------------------------------------------------*/
//孩子-兄弟结点定义
class CSNode {

    Object data;
    CSNode firstchild, nextsibling;
}


/**
 * 孩子兄弟表示法定义的树
 */
class CSTree {

    
    CSNode root;

    //操作定义
    
    public int depth(CSNode T) {
        if (T == null) {
            return 0;
        } else {
            int d1 = depth(T.firstchild);
            int d2 = depth(T.nextsibling);
            return Math.max(d1 + 1, d2);
        }
    }
    
    /**
     * 输出孩子兄弟表示法的二叉树中所有从根到叶子结点的路径
     * @param T   孩子兄弟二叉树
     * @param S   辅助栈
     */
    public void btree(CSNode T, Stack S) {
        if (T != null) {
            S.push(T.data);
            if (T.firstchild == null && T.nextsibling == null) {
                for (Object e:S.toArray() ) 
                    System.out.print(e);
                System.out.println();
            } else {
                btree(T.firstchild, S);
                btree(T.nextsibling, S); 
            }
            S.pop();
        }
        /* end if(T)*/
    }

    /**
     * 输出孩子兄弟表示法的原树中所有从根到叶子结点的路径
     * @param T   孩子兄弟二叉树
     * @param S   辅助栈
     */
    public void treePath(CSNode T, Stack S) {
        while (T != null) {
            S.push(T.data);
            if (T.firstchild == null) {
                for (Object e:S.toArray() ) {
                    System.out.print(e);
                }
                System.out.println();
            } else {
                treePath(T.firstchild, S);
            }
            S.pop();
            T = T.nextsibling;
        }
        /*end  while*/
    }

    /**
     *用于创建按照父子结点对给出的任意树
     * @return  创建的树的孩子-兄弟表示法的二叉树的根结点
     */
    public CSNode createCharTree() {
        CSNode T = null, p, s, r = null;
        Queue<CSNode> Q = new LinkedList(); //采用链式队列
        System.out.println("输入父子结点的两个字符(回车结束输入):");
        //String[] pairs={"#A","AB","AC","AD","BE","BF","DG","GH","HI","HJ","HK"};
        //for(int i=0;i<pairs.length;i++) {
        while(true){
            String input = getchar();
            if (input.length() == 0) {
                break; //直接输入回车代表结束
            }
           // String input=pairs[i];
            char fa = input.charAt(0), ch = input.charAt(1);
            p = getTNode(ch);
            /*创建结点*/
            Q.add(p);
            /*结点入队列*/
            if (fa == '#') {//fa为'#'时,ch为根结点
                T = p;
            } else { //非根结点的情况
                s = Q.peek();  // 取队头的结点元素
                
                while ( !s.data.equals(fa) ) { // 查询双亲结点
                    Q.poll(); //出队 
                    s = Q.peek();
                    
                }
                if (s.firstchild == null) { // 链接第一个孩子结点
                    s.firstchild = p;
                    r = p;
                } else { // 链接其它孩子结点
                    r.nextsibling = p;
                    r = p;
                }

            }
           
        }// while循环结束
        return T;
    }

    /*用于创建树的CreateTree方法的辅助方法，获取给定数据的结点对象*/
    private CSNode getTNode(Object data) {
        CSNode node = new CSNode();
        node.data = data;
        return node;
    }

}
//</editor-fold>

class TestTree {

    public static void main(String[] args) {
        //二叉树的测试代码
        BiTree tree = new BiTree();
        System.out.println("通过完全先序序列AB C  D  创建二叉树：");
        BiTNode root=tree.createPreOrderCharBiTree();
        System.out.println("调用二叉树的最左下结点非递归算法输出其中序遍历序列：");
        tree.inOrder_Path(root);
        String  pno="abcedfgh",ino="cebdagfh";
        System.out.printf("\n通过 先序字符串\n %s \n和中序字符串\n %s 创建二叉树：\n",pno,ino);
        root = tree.createCharBiTree(pno, ino);
        System.out.println("通过树的拷贝算法得到其复制树：");
        BiTNode cpRoot = tree.copyTree(root);
        tree.preOrder(cpRoot);
        System.out.printf("\n叶子结点的数量:%d\n", tree.countLeaf2(root));
        System.out.printf("\n总的结点数量是:%d\n", tree.count(root));
        System.out.printf("\n树的深度为:%d\n", tree.depth(root));
        System.out.println("得到尚未线索化的线索二叉树的根结点，该树的先序序列：");
        BiThrNode rootThr = tree.getUnThreadBiTree(root);
        tree.preOrder(rootThr);
        System.out.println("\n现在按中序线索化该二叉树,\n并按照线索二叉树的中序遍历算法输出其中序序列:");
        BiThrNode head = tree.threadingInOrder(rootThr);
        tree.inOrder_ThrTree(head);
         String expr="#2*3+(6+4)/7*2#";
        System.out.printf("\n现在创建一个中缀表达式 %s 的表达式树:\n ",expr);
       BiTNode expRoot=tree.createExpTree(expr);
        System.out.println("\n该中缀表达式的先缀表达式是:");
       tree.preOrder(expRoot);
       System.out.println("\n去括号后的中缀表达式是:");
       tree.inOrder(expRoot);
       System.out.println("\n后缀表达式为:");
       tree.postOrder(expRoot);
        //孩子-兄弟表示法的树方法的测试
        CSTree  csTree=new CSTree();
        CSNode csRoot=csTree.createCharTree();
        System.out.println("\n创建一颗普通树的孩子兄弟表示法的二叉树");
        csTree.btree(csRoot,  new Stack());
        System.out.println("该树所有的路径是：");
        csTree.treePath(csRoot, new Stack());
        
//        System.out.println();
        //System.out.println("中序");
        //tree.InOrder_iter(root);
    }
}
