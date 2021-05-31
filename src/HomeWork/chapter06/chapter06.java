package HomeWork.chapter06;

import com.sun.deploy.util.ArrayUtil;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static HomeWork.chapter06.KeyBoard.*;


/**
 * @Description: 存储并遍历图
 * @author: 龚皓靖
 * @date: 2021年05月26日 22:12
 */
public class chapter06 {
    public static void main(String[] args) {
        int state = 0;
        while (state == 0){
            switch (input("\n请选择想要的功能：\n1--邻接矩阵存储图并深度优先遍历\n" +
                    "2--邻接表存储图并深度优先遍历\n0--退出程序\n")){
                case 1:
                    System.out.println("使用邻接矩阵存储该图：");
                    MGraph mGraph = MGraph.my_createMGraph();
                    mGraph.print();
                    System.out.println("\n从顶点A出发，深度优先遍历\n：");
                    mGraph.travelDFSM(mGraph,0);
                    break;
                case 2:
                    ALGraph aG = ALGraph.create_ALGraph();
                    printf("\n从顶点0出发，深度优先遍历\n");
                    aG.travelDFSM(aG, 0);
                    break;
                case 0:
                    state = 1;
                    System.out.println("程序已退出！！！");
                    break;
                default:
                    System.out.println("请输入合适的数字！！");
                    break;
            }
        }
    }
}

/**
* @Description: 采用邻接矩阵存储方式的图
* @author: 龚皓靖
 * @param <VType>  顶点的类型参数
 * @param <EType>  边的权值的类型参数
* @date: 2021/5/26 22:32
*/
class MGraph<VType, EType> {
    VType vexs[];        /*顶点表*/
    EType edges[][];  /*边的权值存储矩阵*/
    int n, e;  //顶点数和边数

    /**
    * @Description: 构造方法，利用给定的顶点集合和边的集合初始化一个图
    * @author: 龚皓靖
    * @date: 2021/5/26 22:33
    * @param vexs: 包含有顶点集合的数组
    * @param edges: 包含有边的权值的矩阵
    * @Return:
    */
    public MGraph(VType[] vexs, EType[][] edges) {
        this.vexs = vexs;
        this.edges = edges;
        /*顶点个数*/
        n = vexs.length;

    }

    /**
    * @Description: 无向图的简单创建方式
    * @author: 龚皓靖
    * @date: 2021/5/27 11:11

    * @Return: HomeWork.chapter06.MGraph
    */
    public static MGraph my_createMGraph(){
        int n = input("顶点个数:"), e = 0; //变量e记录边的数量
        char[] temp;
        int[] ints = new int[2];
        Character[] characters = new Character[n];
        char[] chars;
        Object[] vexs;
        Integer[][] edges = new Integer[n][n];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++){
                edges[i][j] = 0;
            }
        }
        chars = input_string("顶点标识序列:").toCharArray();
        for(int i = 0;i<chars.length;i++){
            characters[i] = chars[i];
        }
        vexs = characters;
        while (ints[0] != -1 && ints[1] != -1 ){
            temp = input_string("请输入无向图的边的顶点对:\n输入两个其他的字符退出\n已有边数:"+e+"\n").toCharArray();
            ints = transfor(vexs,temp);
            if((ints[0]>=0 && ints[0]<n) && (ints[1]>=0 && ints[1]<n)){
                edges[ints[0]][ints[1]] = 1;
                edges[ints[1]][ints[0]] = 1;
                e++;
            }
            else {
                if(ints[0] == -1 && ints[1] == -1)
                    System.out.println("图已建立完成！");
                else
                    System.out.println("请输入正确的参数！！！\n");
            }
        }
        MGraph mg = new MGraph(vexs, edges);
        mg.e = e;//设置边数
        return mg;
    }
    /**
    * @Description: 创建网络的邻接矩阵的工厂方法，不存在边的无穷大权值用空值null表示
    * @author: 龚皓靖
    * @date: 2021/5/26 22:34

    * @Return: HomeWork.chapter06.MGraph<?,java.lang.Integer>
    */
    public static MGraph<?, Integer> createMGraph() {
        int n = input("顶点个数:"), e = 0; //变量e记录边的数量
        Object[] vexs = new Object[n];
        Integer[][] edges = new Integer[n][n];
        for (int i = 0; i < n; i++) {
            vexs[i] = getchar("顶点标识:");
        }
        for (int i = 0; i < n; i++) {
            edges[i][i] = 0;  //设置对角线的权值为0
            for (int j = i + 1; j < n; j++) {
                printf("请输入顶点%s-顶点%s的权", vexs[i], vexs[j]);
                Integer w = input("(回车代表权值为∞): ");
                edges[i][j] = w;
                if (w != null && w != 0) {
                    e++;
                }
                printf("请输入顶点%s-顶点%s的权", vexs[j], vexs[i]);
                w = input("(回车代表权值为∞): ");
                edges[j][i] = w;
                if (w != null && w != 0) {
                    e++;
                }
            }
        }
        MGraph mg = new MGraph(vexs, edges);
        mg.e = e;//设置边数
        return mg;
    }

    /**
     * 输出网络的邻接矩阵
     */
    public void print() {
        for (int i = 0; i < n; i++) {
            printf(" %s ", vexs[i]);
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                printf(" %s ", edges[i][j] == null ? "∞" : edges[i][j]);
            }
            System.out.printf(" %s \n", vexs[i]);

        }

    }

    /**
     * -----------------------------------------------------
     * 以顶点k为源点对用邻接矩阵表示的图G作广度优先搜索
     * -------------------------------------------------------
     */
    public void travelBFSM(MGraph<?, Integer> G, int k) {
        Queue Q = new LinkedList();
        boolean visited[] = new boolean[G.n];
        printf("vertex:%s", G.vexs[k]);
        visited[k] = true;
        Q.add(k);
        /*访问 vk, 进队列*/
        while (!Q.isEmpty()) {
            /*队空搜索结束*/
            int i = (int) Q.poll();
            /*不空, 出队列*/
            for (int j = 0; j < G.n; j++) {
                if ( !visited[j] && G.edges[i][j] != null && G.edges[i][j] != 0 ) {
                    //如果顶点 j 没访问过且 i 和 j 之间存在边
                    printf("vertex:%s", G.vexs[j]);  /*访问*/
                    visited[j] = true;
                    Q.add(j);  /*进队*/
                }
            }
        }
    }

    /*从顶点s出发，对邻接矩阵表示的图G作深度优先搜索*/
    public void travelDFSM(MGraph<?, Integer> G, int s) {
        boolean visited[] = new boolean[G.n];
        trvDFSM(G, visited, s); //调用从s点出发的递归方法对图进行深度优先遍历

    }

    /*从顶点i出发，对图G进行深度优先递归搜索*/
    private void trvDFSM(MGraph<?, Integer> G,
                         boolean[] visited, int i) {
        printf("visit vertex:%s\n", G.vexs[i]);
        visited[i] = true;
        /*标记vi已访问*/
        for (int j = 0; j < G.n; j++) {
            if (G.edges[i][j] != null && G.edges[i][j] != 0
                    && !visited[j]) {
                trvDFSM(G, visited, j);
            }
        }
    }

    public Integer[] D, P; //数组D存储最短距离，P存储对应的顶点
    public void makeDijkstraPath(MGraph<?, Integer> G, int s) {
        boolean S[] = new boolean[G.n]; //S[]标识红点或蓝点,true为红点
        D = new Integer[G.n];
        P = new Integer[G.n];
        Integer j, k = -1, min;  //k为待求解最短距离的蓝点，-1代表未知
        for (int i = 0; i < n; i++) {
            D[i] = G.edges[s][i];   //权值为∞的边此处用null表示
            if (D[i] != null) {
                P[i] = s;
            } else {
                P[i] = -1;
            }
        }
        S[s] = true;   //最初只有源点s到自己的最短路径(0)已知
        D[s] = 0;
        for (int i = 0; i < n - 1; i++) {
            for (min = null, j = 0; j < n; j++) {
                if (D[j] == null) continue ; else if (!S[j] && (min == null || D[j] < min)) {
                    min = D[j];
                    k = j;
                }
            }
            if (min == null) {
                return;
            }
            S[k] = true;  //找到最短距离对应的蓝点k，将其加入红点集
            for (j = 0; j < n; j++) {
                if (G.edges[k][j] == null); else if (!S[j] && (D[j] == null || D[j] > D[k] + G.edges[k][j])) {
                    D[j] = D[k] + G.edges[k][j];  //调整剩余蓝点集的最短距离
                    P[j] = k; //k是j的前趋
                }
            }
        }
    }

    /**
     * 输出狄克斯特拉算法生成的单源最短路径信息
     * 参数s: 源点的顶点编号
     */
    public void outPathDistance(int s) {
        if (D == null || P == null) return;
        for (int i = 0; i < n; i++) {
            if(i==s) continue;
            printf("\n %s-%s的最短路径, 路径长度: %d  经过顶点: %s",
                    vexs[s],vexs[i], D[i], vexs[i]);
            int pre = P[i];
            while ( pre != s ) {
                printf(", %s", vexs[pre]);
                pre = P[pre];  //继续上溯找前趋
            }
            printf(",%s",vexs[s]);
        }
    }

}

/**
 * -------------------------------------------------------
 * 使用邻接表表示图，EdgeNode用来表示边的类型定义
 * @param <EType> 边的权值类型参数
 * -----------------------------------------------------
 */
class EdgeNode<EType> {//边表结点

    int adjvex;//邻接点域
    EType edge; //边的权值
    EdgeNode<EType> next;//链域
}

/**
 *顶点的结点类型定义
 * @param <VType> 边的权值类型
 * @param <EType> 顶点的数据类型
 */
class VertexNode<VType, EType> {//顶点表结点

    VType vertex;//顶点域
    EdgeNode<EType> firstedge;//边表头结点
}

/**
 * 用邻接表表示的图
 * @param <VType> 顶点类型
 * @param <EType> 边的权值类型
 */
class ALGraph<VType, EType> {

    VertexNode<VType, EType>[] adjlist;//邻接表
    int n, e;//顶点数和边数
    /**
     * 建立有向网络的邻接表，算法复杂度为O(n+e).
     */
    public static ALGraph createALGraph() {
        ALGraph al = new ALGraph();
        al.n = input("顶点数:");
        al.e = input("边数:");
        al.adjlist = new VertexNode[al.n];
        for (int i = 0; i < al.n; i++) {
            al.adjlist[i] = new VertexNode();  //建立顶点
            al.adjlist[i].vertex = getchar("顶点" + i + "的标识:"); //设置顶点值
        }
        for (int k = 0; k < al.e; k++) {
            int i = input("边起点:"), j = input("边终点:");
            printf("%s-%s的权值:", al.adjlist[i].vertex, al.adjlist[j].vertex);
            Integer w = input();
            EdgeNode s = new EdgeNode();
            s.adjvex = j;
            s.edge = w;
            s.next = al.adjlist[i].firstedge;
            al.adjlist[i].firstedge = s; //头插法
        }
        return al;
    }

    /**
    * @Description: 使用邻接表表示无向图
    * @author: 龚皓靖
    * @date: 2021/5/27 12:03

    * @Return: HomeWork.chapter06.ALGraph
    */
    public static ALGraph create_ALGraph() {
        ALGraph al = new ALGraph();
        al.n = input("顶点数:");
        al.e = input("边数:");
        al.adjlist = new VertexNode[al.n];
        for (int i = 0; i < al.n; i++) {
            al.adjlist[i] = new VertexNode();  //建立顶点
            al.adjlist[i].vertex = getchar("顶点" + i + "的标识:"); //设置顶点值
        }
        for (int k = 0; k < al.e; k++) {
            int i = input("边起点:"), j = input("边终点:");
            printf("%s-%s的权值:", al.adjlist[i].vertex, al.adjlist[j].vertex);
            Integer w = input();
            EdgeNode s = new EdgeNode();
            s.adjvex = j;
            s.edge = w;
            s.next = al.adjlist[i].firstedge;
            al.adjlist[i].firstedge = s; //头插法
            EdgeNode f = new EdgeNode();
            f.adjvex = i;
            f.edge = w;
            f.next = al.adjlist[j].firstedge;
            al.adjlist[j].firstedge = f; //头插法
        }
        return al;
    }

    /**
     * 从顶点s出发，对邻接表表示的图G作深度优先搜索
     */
    public void travelDFSM(ALGraph G, int s) {
        boolean[] visited = new boolean[G.n];
        trvDFSM(G, visited, s);  //调用从s点出发的递归方法对图进行深度优先遍历
    }

    /*从顶点i为出发，对邻接表表示的图G作深度优先搜索 */
    private void trvDFSM(ALGraph G, boolean[] visited, int i) {
        EdgeNode p = null;
        System.out.printf("visit vertex:%s\n",
                G.adjlist[i].vertex);
        visited[i] = true;/*标记vi已访问*/
        p = G.adjlist[i].firstedge;
        /*取vi边表的头指针*/
        while (p != null) {
            if (!visited[p.adjvex]) {
                trvDFSM(G, visited, p.adjvex);
            }
            p = p.next;
        }
    }

    /**
     * 以vk为源点对用邻接表表示的图G作广度优先搜索
     */
    public void travelBFSM(ALGraph G, int k) {
        Queue Q = new LinkedList();
        boolean[] visited = new boolean[G.n];
        printf("vertex:%s", G.adjlist[k].vertex);
        visited[k] = true;
        Q.add(k);
        /*访问 vk, 进队列*/
        while (!Q.isEmpty()) {
            /*队空搜索结束*/
            int i = (int) Q.poll();
            /*不空, 出队列*/
            EdgeNode p = G.adjlist[i].firstedge;
            /*取顶点 vk 的第一个邻接顶点*/
            while (p != null) {
                /*若邻接顶点 w 存在*/
                if (!visited[p.adjvex]) {
                    printf("visit vertex:%s",
                            G.adjlist[p.adjvex].vertex);
                    /*访问*/
                    visited[p.adjvex] = true;
                    Q.add(p.adjvex);
                    /*进队*/
                }
                p = p.next;
            }
        }
    }

    /**
     * 拓扑排序算法实现
     * @param G  待排序的有向网络，采用邻接表进行存储
     */
    public void topologicalSort(ALGraph G) {
        int[] count=new int[G.n];  //存储各顶点入度的顺序栈
        int  i,  j,  top = -1;
        EdgeNode p;
        //count初始化，记录每一个顶点的入度
        for(i = 0; i < G.n; i++)
            for(p=G.adjlist[i].firstedge; p!=null; p= p.next)
                count[p.adjvex]++;    //顶点adjex入度加一
        for ( i = 0; i < G.n; i++ )    //入度为零顶点进栈
            if ( count[i] == 0 ) {
                count[i] = top;  top = i;
            }
        for ( i = 0; i <G.n; i++ )    //期望输出n个顶点
            if ( top == -1 ){               //中途栈空,转出
                printf("网络中有回路(有向环)\n" ); return;
            }else {         //继续拓扑排序
                j = top;  top = count[top];     //退栈
                printf(" %s\n ", G.adjlist[j].vertex); //输出
                p = G.adjlist[j].firstedge;
                while ( p!=null ) {    //扫描该顶点的出边表
                    j = p.adjvex;	  //另一顶点
                    /*该顶点入度减一，减至零，进栈*/
                    if ( --count[j]==0 ) {count[j] = top;  top = j; }
                    p =p.next;
                }
            }
    }
}


/**
 * -------------------------------------------------
 * KeyBoard为辅助类，用于简化键盘输入及数据输出
 * --------------------------------------------------
 */
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

    /**
    * @Description: 寻找数组中某个元素的索引
    * @author: 龚皓靖
    * @date: 2021/5/26 23:34
    * @param arr: 传入的数组
    * @param value: 想要寻找的值
    * @Return: int 寻找值的索引
    */
    public static int found(Object[] arr,Object value){
        int index = -1;
        for (int i = 0;i<arr.length;i++){
            if (arr[i].equals(value)){
                index = i;
                break;
            }
        }
        return index;
    }

    /**
    * @Description: 将字符数组转换为对应的索引数组
    * @author: 龚皓靖
    * @date: 2021/5/26 23:39
    * @param arr : 想要查找的数组
    * @param found : 想要转换的数组
    * @Return: int[] 转化后的索引数组
    */
    public static int[] transfor(Object[] arr, char[] found){
        int[] ints = new int[found.length];
        for(int i = 0;i < found.length;i++){
            ints[i] = found(arr,found[i]);
        }
        return ints;
    }

    public static String input_string(String msg) {
        String input = readLine(msg);
        char[] chars;
        try {
            return input;
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