package utils;

import entity.Grid;
import entity.Node;

import java.util.Random;
import java.util.UUID;


public class NodeUtils {

    /**
     * 播撒单个节点
     * @param node
     */
    public static Node randomNode(Node node,double maxX, double maxY){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        node.setId(uuid);
        node.setX(0 + new Random().nextDouble()*maxX);
        node.setY(0 + new Random().nextDouble()*maxY);
        return node;
    };

    /**
     * @param grid
     * @return 返回网格中心节点
     */
    public static Node centerNode(Grid grid){

        Node node = new Node();
        node.setX(grid.getX() - grid.getLength()/2);
        node.setY(grid.getY() - grid.getLength()/2);
        return node;
    }

    /**
     * @param node1
     * @param node2
     * @return 返回两个节点间的距离
     */
    public static double distanceOfNode(Node node1,Node node2){
        double result = Math.sqrt(Math.pow((node1.getX()-node2.getX()),2) + Math.pow((node1.getY()-node2.getY()),2));
        result = (double) Math.round(result*100)/100;
        return result;
    };
}
