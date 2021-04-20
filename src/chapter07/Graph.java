package chapter07;

import static chapter07.KeyBoard.error;
import static chapter07.KeyBoard.getchar;
import java.util.Scanner;
import static chapter07.KeyBoard.printf;
import static chapter07.KeyBoard.input;
import static java.lang.Integer.MAX_VALUE;
import java.util.LinkedList;
import java.util.Queue;

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

class TreeEdgeNode{
    int fromVex, toVex; //边的起点编号和终点编号
    Integer w; //边的权
}
/*最小生成树的类定义*/
class MST { 
     /*最小生成树对应的图，边的权值为整数*/
    MGraph<?, Integer> G;  
    public MST(MGraph<?, Integer> G) {
       this.G = G;
       mst = new TreeEdgeNode[G.n-1]; //共n-1条边
    }
    TreeEdgeNode[] mst;  //存放最小生成树的数组

    //Prime算法生成树的方法定义
    
    /*参数r为出发顶点的编号，构造初始候选轻边集mst[0..n-2], 共n-1条边；树边集E为空，红点集U={r}*/
   private  void initCandidateSet(int r){
        int i, k = 0;
        for(i=0; i < G.n; i++)
        if(i != r) {
              mst[k]=new   TreeEdgeNode();
             //初始候选轻边的起点均为r，后续须将其调整为新加入的红点
            mst[k].fromVex = r; 
            mst[k].toVex = i;     //边的终点为i
            mst[k++].w = G.edges[r][i];  //设定边的权值
        }
    }

    /*参数k为候选轻边集mst数组中的蓝点集对应边的起始编号，
      算法从当前候选轻边集mst[k..n-2]中选取最短紫边*/
   private  int selectLightEdge(int k){
       Integer min = null, i, minpos=-1;
      for ( i = k; i < G.n - 1; i++ ) 
            if ( mst[i].w==null ) continue; 
            else if (min==null || mst[i].w< min ) {
                 min = mst[i].w;
                minpos = i;
            }
      if (min == null) 
         error("图是非边通的");
      return minpos; //返回最短紫边在mst数组中的序号
   }


    /*参数k为mst数组中蓝点集的起始序号，v为新加入的红点编号,
   根据新红点v调整初始候选轻边集mst[k..n-2]中的最短紫边*/
   private void modifyCandidateSet(int k, int v){ 
       Integer  d;
       for(int i=k; i < G.n-1; i++){
            /*d是新紫边(v,mst[i].toVex]的权值*/
            d = G.edges[v][mst[i].toVex]; 
           if(d !=null && ( mst[i].w==null || d < mst[i].w) ){
               /*须设置边的起点为新红点v，边的权值为新紫边的权值*/
               mst[i].fromVex = v; mst[i].w = d; 
           }
       }
   }

    /**
     * 以编号r的顶点为根，出发构造G的最小生成树mst
     * @param r  最小生成树的根结点的顶点编号
     */
    public  void makePrimMST(int r){  
         int k, m, v;  TreeEdgeNode e;
         initCandidateSet(r);
         for(k = 0; k < G.n-1; k++){
              m = selectLightEdge(k); //得到最短紫边在mst中的序号m
              /*交换mst中m和k位置上的元素，把最短紫边扩充到树中*/
              e = mst[m]; mst[m] = mst[k]; mst[k] = e;  
              /*v是新红点,此时mst[0..k]中均为红点集*/
              v = mst[k].toVex; 
              /*将mst中剩余蓝点的最短紫边的起点设为v，并调整其权值*/
              modifyCandidateSet(k+1, v);
         }
     }
    /**
    * 输出普里姆算法生成的最小生成树
    */
    public void print() {
        for (int i = 0; i < mst.length; i++) {
            TreeEdgeNode ten = mst[i];
            printf("\n%s(%d)---%s(%d)，权:%d\n",G.vexs[ten.fromVex], ten.fromVex, G.vexs[ten.toVex], ten.toVex, ten.w);
        }
    }
}

/**
 * 
  采用邻接矩阵存储方式的图
 * @param <VType>  顶点的类型参数
 * @param <EType>  边的权值的类型参数
 */
class MGraph<VType, EType> {
    /**
     * 构造方法，利用给定的顶点集合和边的集合初始化一个图
     * @param vexs   包含有顶点集合的数组
     * @param edges  包含有边的权值的矩阵
     */
    public MGraph(VType[] vexs, EType[][] edges) {
        this.vexs = vexs;
        this.edges = edges;
        /*顶点个数*/
        n = vexs.length;

    }
    VType vexs[];        /*顶点表*/
    EType edges[][];  /*边的权值存储矩阵*/
    int n, e;  //顶点数和边数

    //操作定义
    /**
     * -----------------------------------------------------------------------
     * 创建网络的邻接矩阵的工厂方法，不存在边的无穷大权值用空值null表示
     * -----------------------------------------------------------------------
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
        printf("visit vertex:%s", G.vexs[i]);
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
     * 从顶点s出发，对邻接表表示的图G作深度优先搜索 
     */
    public void travelDFSM(ALGraph G, int s) {
        boolean[] visited = new boolean[G.n];
        trvDFSM(G, visited, s);  //调用从s点出发的递归方法对图进行深度优先遍历
    }

    /*从顶点i为出发，对邻接表表示的图G作深度优先搜索 */
    private void trvDFSM(ALGraph G, boolean[] visited, int i) {
        EdgeNode p = null;
        System.out.printf("visit vertex:%s",
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

class Test {

    public static void main(String[] args) {
        //测试邻接矩阵存储方式的图
        MGraph G = MGraph.createMGraph();
        G.print();
        printf("从顶点0出发，广度优先遍历：\n");
        G.travelBFSM(G, 0);
        printf("\n从顶点0出发，深度优先遍历：\n");
        G.travelDFSM(G,0);
       printf("构造最小生成树：");
        MST mst = new MST(G);
        mst.makePrimMST(0);
        mst.print();
        printf("\n执行狄克斯特拉算法得到从0点出发的最短路径：\n");
        G.makeDijkstraPath(G, 0);
        System.out.println("Distance :");
        for (int i = 0; i < G.D.length; i++) {
            System.out.printf("[%d]=%d", i, G.D[i]);
        }
        System.out.println();
        System.out.println("Path:");
        for (int i = 0; i < G.P.length; i++) {
            System.out.printf("[%d]=%d", i, G.P[i]);
        }
        G.outPathDistance(0);
        System.out.println();
        //测试邻接表存储方式的图  
        ALGraph aG = ALGraph.createALGraph();
        printf("\n从顶点0出发，深度优先遍历\n");
        aG.travelDFSM(aG, 0);
        printf("\n从顶点0出发，广度优先遍历\n");
        aG.travelBFSM(aG, 0);
        printf("\n拓扑排序：\n");
        aG.topologicalSort(aG);
    }
}
