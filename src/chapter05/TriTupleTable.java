package chapter05;

import static java.lang.System.out;
import static java.lang.System.exit;
import static chapter05.ErrUtil.error;
/*-------------------------------------------------------
 错误处理工具类，提供出错信息打印并退出程序的执行
--------------------------------------------------------*/
class ErrUtil {

    public static void error(String msg) {
        out.println(msg);
        exit(-1);
    }
}

class TriTupleNode {
    int i, j; //非0元的行、列
    Object v;
}

class TriTupleTable {

    public static final int MaxSize = 100;
    //三元组表空间
    TriTupleNode[] data = new TriTupleNode[MaxSize];
    int m, n, t;//行数、列数、非0元个数

    //操作定义
    public TriTupleTable() {
    }

    public TriTupleTable(Object[][] matrix) {
        assigned(matrix);
    }

    /*从二维数组获取三元组表数据*/
    public void assigned(Object[][] matrix) {
        m = matrix.length;
        n = matrix[0].length;
        t = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != null && !matrix[i][j].equals(0)) {
                    data[t] = new TriTupleNode();
                    data[t].i = i;
                    data[t].j = j;
                    data[t].v = matrix[i][j];
                    t++;
                }
            }
        }
    }

    /*将三元组表转换二维数组*/
    public Object[][] toMatrix() {
        Object[][] matrix = new Object[m][n];
        for (int i = 0; i < t; i++) {
            TriTupleNode node = data[i];
            matrix[node.i][node.j] = node.v;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == null) {
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }

    /*转置运算*/
    public TriTupleTable transMatrix() {
        TriTupleTable b = new TriTupleTable(), a = this;
        b.m = a.n;
        b.n = a.m;
        b.t = a.t;
        int q = 0;
        for (int col = 0; col < a.n; col++) {
            for (int p = 0; p < a.t; p++) {
                if (a.data[p].j == col) {
                    b.data[q] = new TriTupleNode();
                    b.data[q].i = a.data[p].j;
                    b.data[q].j = a.data[p].i;
                    b.data[q].v = a.data[p].v;
                    q++;
                }
            }
        }
        return b;

    }
    
    /*相乘运算*/
    public   TriTupleTable multiMatrix(TriTupleTable B){
     if(n!=B.m)  error("不符合矩阵相乘要求");
     Object[][]  a=toMatrix(), b=B.toMatrix(), c=new Object[m][B.n];
     for(int i=0;i<m;i++)
            for(int j=0;j<B.n;j++){
                double s=0;
                for(int k=0;k<n;k++){
                    double aval=Double.parseDouble(a[i][k]+"");
                    double bval=Double.parseDouble(b[k][j]+"");
                    s=s+aval*bval;
                }
                if(s-(int)s==0) c[i][j]=(int)s; else c[i][j]=s;
            }
      TriTupleTable  C=new TriTupleTable();  C.assigned(c);
      return C;

    }

    public void print() {
        Object[][] matrix = this.toMatrix();
        System.out.print("{");
        for (int i = 0; i < m; i++) {
            System.out.print("\n[");
            for (int j = 0; j < n - 1; j++) {
                System.out.printf("%s, ", matrix[i][j]);
            }
            System.out.printf("%s]", matrix[i][n - 1]);
        }
        System.out.println("\n}");
    }
}

/*类RTriTupleTable继承了TriTupleTable*/
class RTriTupleTable extends TriTupleTable{
   //增加的行的索引表
   int[] rowTable=new int[MaxSize];
   
   //操作定义

    public RTriTupleTable() {
    }

    public RTriTupleTable(Object[][] matrix) {
        super(matrix);
        //初始化行表中非零元素的行位置
        for(int i=0;i<t;i++ ) rowTable[i] =data[i].i;
    }
   
}


class TestTriTupleTable {

    public static void main(String[] args) {
        Object[][] matrix = {
            {0, 0, 0, 0, 1, 5, 0},
            {4, 0, 0, 0, 2, 8, 0},
            {1, 0, 0, 0, 0, 0, 3}
        };
        TriTupleTable table = new TriTupleTable(matrix);
        table.print();
        out.println("转置后：");
        TriTupleTable transTable = table.transMatrix();
        transTable.print();
        out.println("与其转置矩阵相乘：");
        TriTupleTable multiTable=table.multiMatrix(transTable);
        multiTable.print();
    }
}
