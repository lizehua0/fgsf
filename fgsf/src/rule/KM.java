package rule;

public class KM {
    double [][]graph; //记录每个移动阶段到空洞的距离边权值
    double []grids; //记录空洞的权重
    double []nodes; //记录动态节点的权重
    boolean []gridsVisited; //记录空洞是否被访问的
    boolean []nodesVisited; //记录移动节点权重
    boolean []isMoved; //记录当前是否已经有与该节点的匹配的空洞
    int []movedNode; //记录节点的移动
    double [] less; //记录对应每个节点最少减少多少权重才能匹配边权值
    double sum = 0;//记录最终总和
    public KM(int len){
       this.graph = new double [len][len];
       this.gridsVisited = new boolean [len];
       this.nodesVisited = new boolean [len];
       this.grids = new double [len];
       this.nodes = new double [len];
       this.isMoved = new boolean [len];
       this.movedNode = new int [len];
       this.less = new double[len];
    }

    public void kmInit(double [][] graph){
        this.graph = graph;
        for(int i = 0;i < graph.length;i++)
        {
            movedNode[i] = -1;
        }
        //初始化每个X顶点的顶标为与之相连的边中最大的权
        for (int k = 0; k < graph.length; k++) {
            grids[k] = graph[k][0];
            for (int l = 0; l < graph.length; l++) {
                grids[k] = grids[k] >= graph[k][l] ? grids[k] : graph[k][l];
            }
        }
    }
    public void km(){
        for(int i = 0;i < gridsVisited.length;i++)
        {
            for(int j = 0;j < nodesVisited.length;j++) {
                less[j] = Double.MAX_VALUE;
            }
            while(true) {
                for (int j = 0; j < nodesVisited.length; j++)
                {
                    gridsVisited[j] = false;
                    nodesVisited[j] = false;
                }
                if(line(i)) {break;}
                double diff = Double.MAX_VALUE;
                for(int j = 0;j < nodesVisited.length;j++)
                {
                    diff = (diff >= less[j]&&less[j]>0) ? less[j]:diff;
                }
               // System.out.println(diff + " " + i);
                for(int j = 0;j < nodesVisited.length;j++)
                {
                    if(gridsVisited[j]) {grids[j] -= diff;}
                    if(nodesVisited[j]) {nodes[j] += diff;}
                    //if (!gridsVisited[j] && !nodesVisited[j]){less[j] -= diff;}
                }
            }
        }
    System.out.println("");
    }

    /**
     * 寻找第i个格子的曾广路径
     * @param i
     * @return
     */
    public boolean line(int i){
        gridsVisited[i] = true;
        for(int j = 0;j < nodesVisited.length;j++)
        {
            if(nodesVisited[j]) {
                continue;
            }
            double gap = grids[i] + nodes[j] - graph[i][j];
            gap = (double) Math.round(gap*100)/100;
//            if(gap == -5.6843418860808015E-14){
//                System.out.println(grids[i] + "   "+ nodes[j] + "   "+ graph[i][j]);
//            }
            //System.out.println("****"+gap + "   grid:  " + grids[i]+"   node:  "+ nodes[j] +"   graph:  "+graph[i][j]+"*****");
            if(gap == 0) {
                nodesVisited[j] = true;
                //System.out.println("第" + i + "个空洞在找第" + j + "个节点，但是该节点已经被" +  movedNode[j] + "个空洞占领");
                if (movedNode[j] == -1 || line(movedNode[j])) {
                    movedNode[j] = i;
                    return true;
                }
            } else {
                less[j] = gap <= less[j] ? gap : less[j];
            }
        }
        return false;
    }

    /**
     * 求出km算法之后的路径总和
     * @return
     */
    public void lineSum(){
        for(int i = 0;i < movedNode.length;i++)
        {
            sum += graph[movedNode[i]][i];
        }
    }
    public double[][] getGraph() {
        return graph;
    }

    public void setGraph(double[][] graph) {
        this.graph = graph;
    }

    public double[] getGrids() {
        return grids;
    }

    public void setGrids(double[] grids) {
        this.grids = grids;
    }

    public double[] getNodes() {
        return nodes;
    }

    public void setNodes(double[] nodes) {
        this.nodes = nodes;
    }

    public boolean[] getGridsVisited() {
        return gridsVisited;
    }

    public void setGridsVisited(boolean[] gridsVisited) {
        this.gridsVisited = gridsVisited;
    }

    public boolean[] getNodesVisited() {
        return nodesVisited;
    }

    public void setNodesVisited(boolean[] nodesVisited) {
        this.nodesVisited = nodesVisited;
    }

    public boolean[] getIsMoved() {
        return isMoved;
    }

    public void setIsMoved(boolean[] isMoved) {
        this.isMoved = isMoved;
    }

    public int[] getMovedNode() {
        return movedNode;
    }

    public void setMovedNode(int[] movedNode) {
        this.movedNode = movedNode;
    }

    public double[] getLess() {
        return less;
    }

    public void setLess(double[] less) {
        this.less = less;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public static void main(String arg[]){
//        KM km = new KM(5);
//        double data[][]={{226.2450440254008,172.47225879007095,180.13836628998504,63.268752579549826,98.01129047185395},{248.1473296559494,178.48389491872473,137.70847977489737,276.26251641612265,359.825955142821},{219.72481490048546,176.34681442750852,199.71551567772482,58.50008421226616,73.37349715548794},{223.42621053129702,156.39059962778865,132.85360964124402,258.82484961154404,341.324149481562},{215.9193567516577,183.57482906245897,220.39224894540706,63.96549579822057,49.10322911677667}};
//        km.kmInit(data);
//        km.km();
//        km.lineSum();
//        System.out.println(km.sum);
        double d = 289.93261667748436;
        d = (double) Math.round(d*100)/100;
        System.out.println(d);

    }
}
