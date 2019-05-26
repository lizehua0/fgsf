package entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Grid {
    //记录空格子
    private  Grid airGrid;

    private String id;

    private double x;

    private double y;
    //边长
    private double length;


    Node nodes[];
    Grid grids[] = new Grid[4];

    Grid (String id,double x,double y,double length){
        this.id = id;
        this.x = x;
        this.y = y;
        this.length = length;
    }
    /**
     * 找出空的小格子数量
     * @return
     */
    public int numOfAirGrid(Map<Integer,Node> nodes){
        int num = 0,flag = 0;
        for (Grid grid:grids){
            for (Map.Entry<Integer,Node> entry : nodes.entrySet()){
                if(isIncludeNode(entry.getValue(),grid)){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                num++;
                airGrid = grid;
            }
            flag = 0;
        }
        return num;
    };

    /**
     * 判断节点是否在格子内
     * @return
     */
    public boolean isIncludeNode(Node node, Grid grid){

        if(node.getX() > grid.getX() || node.getY() > grid.getY()||node.getX() <= grid.getX() - grid.getLength()||
                node.getY() <= grid.getY()-grid.getLength()){
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断节点是否在格子内
     * @return
     */
    public boolean isIncludeNode(Node node){

        if(node.getX() > this.x || node.getY() > this.getY()||node.getX() <= this.getX() - this.getLength()||
                node.getY() <= this.getY() - this.getLength()){
            return false;
        } else {
            return true;
        }
    }

    /**
     * 划分小格子
     * @return
     */
    public void divideLittleGrid(){
         int num = 0;
        for(double j = this.y - length/2;j <= y ;j += length/2) {
            for(double i = this.x - length/2;i <= x ;i += length/2) {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                grids[num] = new Grid(uuid, i, j, length/2);
                num++;
            }
        }
    }

    public Grid getAirGrid() {
        return airGrid;
    }

    public void setAirGrid(Grid airGrid) {
        this.airGrid = airGrid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public Grid[] getGrids() {
        return grids;
    }

    public void setGrids(Grid[] grids) {
        this.grids = grids;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
    public static void main(String srg[]){
       Grid grid = new Grid("a",2,2,1);
       grid.divideLittleGrid();
       Node node = new Node();
       node.setX(1.25);
       node.setY(1.25);
       Map<Integer,Node> map = new HashMap<>();
       map.put(1,node);
        System.out.println(grid.isIncludeNode(node,grid));
        System.out.println(grid.numOfAirGrid(map));
//       for(Grid grid1:grid.getGrids()){
//           System.out.println(grid1.getX() + " " + grid1.getY());
//       }
    }
}
