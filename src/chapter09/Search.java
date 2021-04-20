package chapter09;

import static chapter09.KeyBoard.error;
import static chapter09.KeyBoard.getchar;
import static chapter09.KeyBoard.input;
import static chapter09.KeyBoard.printf;
import java.util.Scanner;

class KeyBoard {

    public static void error(String msg) {
        System.out.println(msg);
        System.exit(-1);
    }
    static Scanner reader = new Scanner(System.in);

    public static String readLine(String msg) {
        System.out.print(msg);
        return reader.nextLine();
    }

    public static Integer input(String msg) {
        String input = readLine(msg);
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getchar(String msg) {
        return readLine(msg);
    }

    public static String getchar() {
        return getchar("");
    }

    public static Integer input() {
        return input("");
    }

    public static void printf(String msg, Object... args) {
        System.out.printf(msg, args);
    }
}

class KeyType {

    Comparable key;
    Object otherInfo;
}

class SearchedSeqList {

    KeyType[] R;

    //构造方法 
    public SearchedSeqList(KeyType[] list) {
        R = list;
    }

    //顺序查找操作
    public int seqSearch(KeyType k) {
        for (int i = 0; i < R.length; i++) {
            if (R[i].key.compareTo(k.key) == 0) {
                return i;
            }
        }
        return -1;   //查找失败  
    }

    //二分查找，要求被查找的序列按照key排好序
    public int binSearch(KeyType k) {
        int low = 0, high = R.length- 1, mid;
        while (low <= high) {
            mid = (low + high) / 2;
            if (R[mid].key.compareTo(k.key) == 0) {
                return mid;
            }
            if (R[mid].key.compareTo(k.key) > 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

}

/**
 * 二叉平衡树的结点定义
 */
class BSTNode{
   Comparable key; Object otherInfo;
   int  cnt;  //重复结点的数量
   BSTNode lchild, rchild;
   /*返回结点的键值数据*/
   public KeyType getKeyInfo(){
       KeyType keyInfo=new KeyType();      
       keyInfo.key=key;
       keyInfo.otherInfo=otherInfo;
       return keyInfo;
   }
}

/**
 * 二叉平衡树定义
 */
class BSTree {

    BSTNode root;  //树根结点
    //平衡树操作定义

    /**
     * 利用键值类型创建二叉平衡树
     * @param k  需要插入到二叉平衡树的结点数据
     */
    public void insertBST(KeyType k) {
        BSTNode f = root, p = f;
        while (p != null) {
            if (p.key.compareTo(k.key) == 0) {
                p.cnt++;
                /*树中已有key不插,累加其重复数量*/
                return;
            }
            f = p;
            p = (k.key.compareTo(p.key) < 0) ? p.lchild : p.rchild;
        }
        p = new BSTNode();
        p.key = k.key;
        p.otherInfo = k.otherInfo;
        if (root == null) {
            root = p;
        } else if (p.key.compareTo(f.key) < 0) {
            f.lchild = p;
        } else {
            f.rchild = p;
        }
    }

    /**
     * 创建排序键值为整数的二叉平衡树
     * @return  创建好的二叉平衡树的实例
     */
    public static BSTree createIntegerBST() {
        BSTree btree = new BSTree();
        KeyType k = new KeyType();
        printf("请输入整数(回车结束输入):\n");
        while (true) {
            k.key = input();
            if (k.key == null) {
                break;
            }
            btree.insertBST(k);
        }
        return btree;
    }

    /**
     * 统计二叉平衡树中存储的键值结点的总数
     * @return 
     */
    public int  count(){
       return count(root);
    }
    /*递归统计二叉排序树中的结点总数*/
    private int count(BSTNode root){
        if(root==null) return 0;
        int m=count(root.lchild);
        int n=count(root.rchild);
        return m+n+1+root.cnt;
    }
    
private KeyType[]  keys;  //keys用于存储排序后的数据
private int pos;   //pos用于存储递归中的keys当前位置
/**
 * 返回升序排序后的数据
 * @return  按键值升序排序的KeyType数组
 */
public KeyType[]  getKeysArray(){
   //根据结点数量初始化keys数组
    keys=new KeyType[count()];  
    pos=0;
    //调用设置数组方法outKeys   
    outKeys(root);   
    return keys;
}
/*按中序递归遍历二叉排序树，设置升序排序数组keys*/
private void outKeys(BSTNode T){
   if( T == null ) return;   
   outKeys(T.lchild); 
   for(int i=0;i<=T.cnt;i++){
          /*得到根结点的键值信息*/
         KeyType k=T.getKeyInfo();
         keys[pos++] = k; //设置keys数组,并移动pos指针
   }
   outKeys(T.rchild);
}

/**
 * 输出二叉平衡树中的排好序的键值
 * @param isReverse 是否降序排列，true为降序，false为升序
 */
public void printKeys(boolean isReverse) {
        getKeysArray();
        int start=0,end=keys.length;
        if(!isReverse) for(;start<end;start++) printf(" %s ",keys[start].key);
        else 
             for(start=end-1;start>=0;start--) printf(" %s ",keys[start].key);
        
 }
/**
 * 在二叉平衡树中寻找给定的key值
 * @param key  待查找的key值
 * @return   包含该key值的二叉平衡树的结点，没有找到返回null值
 */
 public BSTNode searchKey(Comparable key) {
        if (key == null) {
            error("错误的key值！");
        }
        KeyType k = new KeyType();
        k.key = key;
        return searchBST(root, k);  //从根结点开始搜索
 }

    /* 二叉搜索树的递归的搜索算法*/
    private BSTNode searchBST(BSTNode T, KeyType k) {
        if (T == null || k.key.compareTo(T.key) == 0) {
            return T;
        }
        if (k.key.compareTo(T.key) < 0) //在左子树递归搜索
        {
            return searchBST(T.lchild, k);
        } else //在右子树递归搜索
        {
            return searchBST(T.rchild, k);
        }
    }

    /**
     * 在二叉平衡树中删除指定的key值所在的结点
     * @param key  待删除的键值
     */
    public void delKey(Comparable key) {
        BSTNode parent = null, p = root, q, child;
        while (p != null) {
            if (p.key.compareTo(key) == 0) {
                break;
            }
            parent = p;
            p = key.compareTo(p.key) < 0 ? p.lchild : p.rchild;
        }
        if (p == null) {
            return; //找不到被删结点，返回 
        }
        q = p;
        if (q.lchild != null && q.rchild != null) {
            for (parent = q, p = q.rchild; p.lchild != null; parent = p) {
                p = p.lchild;
            }
        }
        /*情况3已被转换为情况2，而情况1相当于情况2中child==null*/
        child = p.lchild != null ? p.lchild : p.rchild;
        if (parent == null) {
            root = child;
        } else {//p不是根
            if (p == parent.lchild) {
                parent.lchild = child;
            } else {
                parent.rchild = child;
            }
            if (p != q) {
                q.key = p.key;
            }
        }
        p = null;
    }

}
/*查找的测试类*/
class SearchTest {

    public static void main(String[] args) {
        BSTree btree = BSTree.createIntegerBST();
        btree.printKeys(false);//升序输出排序树中的键值
        KeyType[] keyInfos=btree.getKeysArray();
        SearchedSeqList list=new SearchedSeqList(keyInfos);
        while (true) {
            Integer n = input("\n请输入要查找的键值（回车退出）：\n");
            if(n==null) break;
            BSTNode node = btree.searchKey(n);
            if (node != null) {
                int  p=list.binSearch(node.getKeyInfo());
                printf("该键值在排序后的输入序列中的位置是%d\n",p);
                String ans=getchar("是否删除这个键值(y/n)？\n").toLowerCase();
                if("y".equals(ans)||"yes".equals(ans)){
                    btree.delKey(node.key);
                    printf("\n现在二叉平衡树中的数据总数是%s：\n",btree.count());
                    btree.printKeys(false);
                }
            } else {
                printf("没有找到输入的键值%s\n", n);
                KeyType keyInfo=new KeyType();
                keyInfo.key=n;
                int p=list.seqSearch(keyInfo);
                if(p==-1) printf("在最初输入的排序序列中按照顺序查找也没有找到该元素！\n");
                else printf("但在最初的排序序列中找到了该元素，其顺序查找到的位置为%d\n",p);
            }
        }
    }
}
