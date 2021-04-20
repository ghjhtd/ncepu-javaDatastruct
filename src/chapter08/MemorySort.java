package chapter08;
/**
 *内排序测试类 
  */
public class MemorySort {

    public static void main(String[] args) {
        Object[] a = {3, 4, 100, 115, 22, 789, 1000, 23, 55, 66, 77};
        SortedSeqList list = new SortedSeqList(a);
        list.quickSort();
        list.printKeys();
    }
}

/**
 *待排序的记录类型定义
 */
class RecType {
    Comparable key;   //主键值为可排序的比较接口类型
    Object otherInfo;
}

/**
 * 排序方法的封装类
 */
class SortedSeqList {

    RecType R[];  //用于存放排序数据的数组

    /**
     * 利用传入的排序数组构造排序封装类
     * @param list  元素类型为排序记录类型的数组 
     */
    public SortedSeqList(RecType[] list) {
        R = list;
    }

    /**
     * 利用对象数组构造排序封装类
     * @param list  类型为Object的对象数组
     */
    public SortedSeqList(Object[] list) {
        R = new RecType[list.length];
        for (int i = 0; i < R.length; i++) {
            if (list[i] instanceof RecType) {
                R[i] = (RecType) list[i];
            } else {
                R[i] = new RecType();
                R[i].key = (Comparable) list[i];
            }
        }
    }

    /**
     * 输出存放排序数据的数组中的键值
     */
    public void printKeys() {
        if (R != null) {
            for (RecType t : R) {
                System.out.println(t.key);
            }
        }
    }

    /**
     * 堆排序算法实现
     */
    public void heapSort() {
        int n = R.length;
        for (int i = n - 1; i > 0; i--) {
            Heapify((i - 1) / 2 , i);
            RecType temp = R[0];
            R[0] = R[i];
            R[i] = temp;
        }
    }
   /*私有方法Heapify从最后一个子堆开始，直到第一个子堆为止，逐个将以low为堆顶索引的每一个子堆调整为最大堆*/
    private void Heapify(int low, int high) {
        for (; low >= 0; low--) {
            int large = low * 2 + 1;    //large为堆顶的左子树索引
            RecType temp = R[low]; 
            if (large < high
                    && R[large].key.compareTo(R[large + 1].key) < 0) {
                large++;  //存在右且大于左，则移动large到右
            }
            if (temp.key.compareTo(R[large].key) >= 0) {
                continue;  //如果堆顶已是最大，进行下次循环
            }
            R[low] = R[large];   //交换堆顶元素和最大值 
            R[large] = temp;
        }

    }

    /**
     * 希尔排序算法实现
     */
    public void shellSort() {
        int j, n = R.length;
        RecType t;
        for (int g = n / 2; g >= 1; g = g / 2) {
            for (int i = g; i < n; i++) {
                for (t = R[i], j = i; j >= g && t.key.compareTo(R[j - g].key) < 0; j -= g) {
                    R[j] = R[j - g];
                }
                R[j] = t;
            }
        }

    }

    /**
     * 快速排序实现，通过递归算法
     */
    public void quickSort() {
        quickS(0, R.length - 1);
    }

    /*用于快速排序的辅助方法，在left、right和中心位置找到并返回三者中间的划分元素，
      并按大小排列left、center、right位置上的元素，最后将center和right-1位置元素进行交换*/
    private RecType median3(int left, int right) {
        int center = (left + right) / 2;
        if (R[center].key.compareTo(R[left].key) < 0) {
            swap(left, center);
        }
        if (R[right].key.compareTo(R[left].key) < 0) {
            swap(left, right);
        }
        if (R[right].key.compareTo(R[center].key) < 0) {
            swap(right, center);
        }
        swap(center, right - 1);
        return R[right - 1];
    }
    /*快速排序的辅助方法，用于调换排序数组中位置为x，y的元素*/
    private void swap(int x, int y) {
        RecType t = R[x];
        R[x] = R[y];
        R[y] = t;
    }
   /*快速排序的主要实现方法，用于递归实现快速排序*/
    private void quickS(int left, int right) {
        if (left + 10 <= right) {
            RecType pivot = median3(left, right);
            int i = left + 1, j = right - 2;
            for (; i < j;) {
                while (R[i].key.compareTo(pivot.key) < 0) {
                    i++;
                }
                while (pivot.key.compareTo(R[j].key) < 0) {
                    j--;
                }
                if (i < j) {
                    swap(i, j);
                }
            }
            swap(i, right - 1);
            quickS(left, i - 1);
            quickS(i + 1, right);
        } else 
          insertSort(left, right - left + 1);
    }
    /*快速排序的辅助方法，用于实现快速排序中需要的插入排序*/
    private void insertSort(int left, int right) {
        for (int i = left; i < right; i++) {
            RecType t = R[i];
            int j = i - 1;
            for (; j >= left
                    && R[j].key.compareTo(t.key) > 0; j--) {
                R[j + 1] = R[j];
            }
            R[j + 1] = t;
        }
    }

    /**
     * 插入排序算法实现
     */
    public void insertSort() {
        for (int i = 1; i < R.length; i++) {
            RecType t = R[i];
            int j = i - 1;
            for (; j >= 0
                    && R[j].key.compareTo(t.key) > 0; j--) {
                R[j + 1] = R[j];
            }
            R[j + 1] = t;
        }
    }
   /**
    * 快速排序算法使用自定义顺序栈的非递归算法实现
    */
    public void s_quick() {
        int i, j, low, high, n = R.length;
        int[][] stack = new int[n][2];
        stack[0][0] = 0;
        stack[0][1] = n - 1;
        int top = 1;
        while (top > 0) {
            /* 栈非空 */
            top--;
            low = i = stack[top][0];
            high = j = stack[top][1];
            RecType t = R[low];
            while (i < j) {
                /* 对R[low]至R[high]以R[low]为基准作划分 */
                while (i < j && R[j].key.compareTo(t.key) > 0) {
                    j--;
                }
                if (i < j) {
                    R[i++] = R[j];
                }
                while (i < j && R[i].key.compareTo(t.key) < 0) {
                    i++;
                }
                if (i < j) {
                    R[j--] = R[i];
                }
            }
            R[i] = t;
            if (i - 1 > low) {
                /* 左边子序列进栈 */
                stack[top][0] = low;
                stack[top][1] = i - 1;
                top++;
            }
            if (high > i + 1) {
                /* 右边子序列进栈 */
                stack[top][0] = i + 1;
                stack[top][1] = high;
                top++;
            }
        }
    }

    /**
     * 选择排序算法实现
     */
    public void selectSort() {
        int n = R.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (R[j].key.compareTo(R[minIndex].key) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                RecType temp = R[i];
                R[i] = R[minIndex];
                R[minIndex] = temp;
            }
        }
    }

    /**
     * 归并排序算法实现
     * @param low  待排序数组的起始下标
     * @param high 待排序数组的终点下标
     */
    public void mergeSort(int low, int high) {
        RecType[] r = new RecType[high - low + 1];
        int len = 1, n = high - low + 1;
        while (len < n) {
            mergePass(r, len);
            len *= 2;
        }
    }
   
    /**
     * 可以直接使用的归并排序算法实现，用于对排序封装类中存储排序数据的数组R进行排序
     */
    public void mergeSort() {
        mergeSort(0, R.length - 1);
    }

    /*归并排序的辅助方法*/
    private void mergePass(RecType[] y, int len) {
        int i = 0, n = y.length;
        while (i + 2 * len < n) {
            merge(y, i, i + len - 1, i + 2 * len - 1);
            i += 2 * len;
        }
        if (i + len <= n - 1) {
            merge(y, i, i + len - 1, n - 1);
        }
    }
     /*归并排序的辅助方法*/
    private void merge(RecType[] y,
            int low, int m, int high) {
        int i = low, j = m + 1, p = low;

        while (i <= m && j <= high) {
            y[p++] = (R[i].key.compareTo(R[j].key) <= 0)
                    ? R[i++] : R[j++];
        }
        while (i <= m) {
            y[p++] = R[i++];
        }
        while (j <= high) {
            y[p++] = R[j++];
        }
        for (i = low; i <= high; i++) {
            R[i] = y[i];
        }
    }

    /*用于基数排序的队列结点定义*/
    class Node {
        RecType data;
        Node next;
    }
   /*用于基数排序的队列定义*/
    class Queue {

        Node f;
        Node r;
    } //链表队列
    
    /**
     * 基数排序中基数的长度和键值的长度
     */
    public int Radix = 10, KeySize = 10;

    /**
     * 基数排序算法实现，仅适用于最多10位数字的正整数的排序
     */
    public void radixSort() {
        /*10个箱子，每个都是一个链表队列*/
        Queue[] B = new Queue[Radix];
        for (int i = 0; i < Radix; i++) {
            B[i] = new Queue();
        }
        for (int i = KeySize - 1; i >= 0; i--) {
            distribute(B, i);
            collect(B);
        }
    }

    /*基数排序的辅助方法，利用队列将每一位置上的数分配到对应的基数上*/
    private void distribute(Queue B[], int j) {
        for (int i = 0; i < R.length; i++) {
            String key = "" + R[i].key;
            /*先右对齐关键字，再取关键字的第j位数字k*/
            int p = j - KeySize + key.length();
            /*如关键字长度小于KeySize，则左侧补0*/
            char ch = p >= 0 ? key.charAt(p) : '0';
            int k = ch - '0'; //得到第j位上的数字k
            Node node = new Node();
            node.data = R[i];
            if (B[k].f == null) {
                B[k].f = B[k].r = node;
            } else {
                B[k].r.next = node;
                B[k].r = node;
            }
        }

    }
   
    /*基数排序的辅助方法，将分配到各个基数上的队列中的数取出，进行排序收集*/
    private void collect(Queue[] B) {
        int i = 0, j;
        for (j = 0; j < B.length; j++) {
            while (B[j].f != null) {
                R[i++] = B[j].f.data;
                B[j].f = B[j].f.next;
            }
        }

    }

}
