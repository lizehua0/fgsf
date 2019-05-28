package entity;

import rule.MoveRule;
import utils.NodeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class World {
    //动态节点所占比例
   // private float pi;

    private double length;

    private double width;
    //大格子的边长
    private double sideLength;
    //总节点个数
    private int numOfNode;
    //静态节点个数
    private int numOfStaticNode;
    //动态节点的个数
    private int numOfDynamicNode;
    //最小移动路径和
    private double lineSum;

    private Map<Integer,Grid> grids = new HashMap<>();

    private Map<Integer,Node> nodes = new HashMap<>();

    private Map<Integer,Node> dynamicNodes = new HashMap<>();

    public World(double length, double width,double sideLength,int numOfStaticNode,int numOfDynamicNode){
        this.numOfStaticNode = numOfStaticNode;
        this.numOfDynamicNode = numOfDynamicNode;
        this.length = length;
        this.width = width;
        this.sideLength = sideLength;
        this.numOfNode = numOfStaticNode + numOfDynamicNode;
    }
    /*
    初始化整个区域
     */
    public void init(){
        int gridNum = 0;
        for(double j = sideLength;j <= width;j += sideLength){
            for(double i = sideLength;i <= length;i += sideLength)
            {
                gridNum++;
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                Grid grid = new Grid(uuid,i,j,sideLength);
                grid.divideLittleGrid();
                grids.put(new Integer(gridNum),grid);
            }
        }
    }

    /*
    播撒节点
     */
    public void spreadNodes(){
        for(int i = 0;i < numOfStaticNode;i++){
            Node node = NodeUtils.randomNode(new Node(),length,width);
            node.setFlag(1);
            nodes.put(new Integer(i), node);
        }
        for(int i = numOfStaticNode;i < numOfNode;i++){
            Node node = NodeUtils.randomNode(new Node(),length,width);
            node.setFlag(0);
            nodes.put(new Integer(i), node);
        }

    }

    /*
    找出动态节点
     */
    public void findoutDynamicNodes(){

        for(Map.Entry<Integer,Node> entry:nodes.entrySet()){
            if(entry.getValue().getFlag() == 0){
                dynamicNodes.put(entry.getKey(),entry.getValue());
            }
        }
    }

    /**
     * 找出大空格子（km算法需要）这里的空洞不包括静态节点
     * @return
     */
    public Map<Integer,Grid> getBigGrids(){
        Map<Integer,Grid> bigAirDrids = new HashMap<>();
        int flag = 0;
        for(Map.Entry<Integer,Grid> gridEntry:grids.entrySet())
        {
            for(Map.Entry<Integer,Node> nodeEntry:nodes.entrySet())
            {
                if (gridEntry.getValue().isIncludeNode(nodeEntry.getValue()) && nodeEntry.getValue().getFlag() == 1) {
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                bigAirDrids.put(gridEntry.getKey(), gridEntry.getValue());
            }
            flag = 0;
        }
        System.out.println("空洞数量：" + bigAirDrids.size() + "!");
        return bigAirDrids;
    }

    /**
     * 找出空格子包括静态节点
     * 这个方法就是测试最后还剩几个空洞用的
     * @return
     */
    public Map<Integer,Grid> getBigGrids1(){
        Map<Integer,Grid> bigAirDrids1 = new HashMap<>();
        int flag = 0;
        for(Map.Entry<Integer,Grid> gridEntry:grids.entrySet())
        {
            for(Map.Entry<Integer,Node> nodeEntry:nodes.entrySet())
            {
                if (gridEntry.getValue().isIncludeNode(nodeEntry.getValue())) {
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                bigAirDrids1.put(gridEntry.getKey(), gridEntry.getValue());
            }
            flag = 0;
        }
        return bigAirDrids1;
    }

    public Map<Integer, Grid> getGrids() {
        return grids;
    }

    public void setGrids(Map<Integer, Grid> grids) {
        this.grids = grids;
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    public Map<Integer, Node> getDynamicNodes() {
        return dynamicNodes;
    }

    public void setDynamicNodes(Map<Integer, Node> dynamicNodes) {
        this.dynamicNodes = dynamicNodes;
    }

//    public float getPi() {
//        return pi;
//    }
//
//    public void setPi(float pi) {
//        this.pi = pi;
//    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    public int getNumOfNode() {
        return numOfNode;
    }

    public void setNumOfNode(int numOfNode) {
        this.numOfNode = numOfNode;
    }

    public double getLineSum() {
        return lineSum;
    }

    public void setLineSum(double lineSum) {
        this.lineSum = lineSum;
    }

    public static void main(String arg[]){
        World world = new World(300,300,25,96,24);
        world.init();
//        for(int i = 1;i <= world.getGrids().size();i++){
//            System.out.println(i + ":   " +  world.getGrids().get(i).getX() + "   " + world.getGrids().get(i).getY());
//        }
        world.spreadNodes();
        world.findoutDynamicNodes();
        Map<Integer,Grid> bigAirGrids = world.getBigGrids();
        MoveRule.KMFG.evolute(world);
        System.out.println(world.getLineSum());
//        int totalNumOfAirGrid = 0,totalNumOfAirGridMoved = 0;
//        for(Map.Entry<Integer,Grid> entry: world.getGrids().entrySet()){
//            totalNumOfAirGrid += entry.getValue().numOfAirGrid(world.getNodes());
//        }
//        System.out.println("总小格子数量为： " + world.getGrids().size()*4);
//        System.out.println("总节点数量为： " + world.getNodes().size());
//        System.out.println("总空格子数量为： " + totalNumOfAirGrid);
//        MoveRule.DDTC.evolute(world);
//        for(Map.Entry<Integer,Grid> entry: world.getGrids().entrySet()){
//            totalNumOfAirGridMoved += entry.getValue().numOfAirGrid(world.getNodes());
//            System.out.println(entry.getKey() + ":   ");
//            for(int i = 0; i < 4;i++)
//                System.out.println(entry.getValue().getGrids()[i].getX() + "   " + entry.getValue().getGrids()[i].getY());
//        }
//        System.out.println("移动之后总空格子数量为： " + totalNumOfAirGridMoved + "   格子总数为" + world.getGrids().size()*4);
    }
}
