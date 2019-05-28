package rule;

import entity.Grid;
import entity.World;
import entity.Node;
import utils.NodeUtils;

import java.util.Map;
import java.util.logging.Logger;

public enum MoveRule {


    /**
     * 迭代填充最近空缺区域
     * pararm pi 移动节点所占总结点数的比率
     */
    DDTC {
        @Override
        public World evolute(World world){
            Map<Integer,Grid> grids = world.getGrids();
            Map<Integer,Node> nodes = world.getNodes();
            Map<Integer,Node> dynamicNodes = world.getDynamicNodes();
            if(grids.isEmpty() || nodes.isEmpty()){
                System.out.println("请初始化世界！");
                return null;
            } else {
                if(dynamicNodes.isEmpty()){
                    System.out.println("无动态节点，无需移动！");
                    return world;
                } else {
                    int max = 0,temp = 0,min = 0,moveNum = 0;
                    double minDistance = Double.MAX_VALUE;
                    do{
                        max = 0;
                        minDistance = Double.MAX_VALUE;
                        //找出空格子最多的一个大格子
                    for (Map.Entry<Integer, Grid> entry : grids.entrySet()) {
                        if (entry.getValue().numOfAirGrid(world.getNodes()) >= max) {
                            max = entry.getValue().numOfAirGrid(world.getNodes());
                            temp = entry.getKey();
                        }
                    }

                    if(grids.get(temp).getAirGrid() != null) {
                        Node centerNode = NodeUtils.centerNode(grids.get(temp).getAirGrid());
                        System.out.println("空格子最多的大格子是： " + temp  + "小格子的坐标是： " + grids.get(temp).getAirGrid().getX() + "  " + grids.get(temp).getAirGrid().getY());
                        //找出距离最短的一个动态节点
                        for (Map.Entry<Integer, Node> entry : dynamicNodes.entrySet()) {
                            if (NodeUtils.distanceOfNode(centerNode, entry.getValue()) < minDistance) {
                                minDistance = NodeUtils.distanceOfNode(centerNode, entry.getValue());
                                min = entry.getKey();
                            }
                        }
                        world.getNodes().get(min).setX(centerNode.getX());
                        world.getNodes().get(min).setY(centerNode.getY());
                        moveNum++;
                        dynamicNodes.remove(min);
                    }
                    }while(max != 0 && !dynamicNodes.isEmpty());
                    System.out.println("一共移动了" + moveNum + "个节点！");
                    return world;
                }
            }
        }
    },

    /**
     * 通过km算法进行覆盖
     */
    KMFG {
        @Override
        public World evolute(World world){
            Map<Integer,Grid> grids = world.getGrids();
            Map<Integer,Node> nodes = world.getNodes();
            Map<Integer,Node> dynamicNodes = world.getDynamicNodes();
            if(grids.isEmpty() || nodes.isEmpty()){
                System.out.println("请初始化世界！");
                return null;
            } else {
                if(dynamicNodes.isEmpty()){
                    System.out.println("无动态节点，无需移动！");
                    return world;
                } else {
                    int len = world.getDynamicNodes().size() >= world.getBigGrids().size() ?
                            world.getDynamicNodes().size():world.getBigGrids().size();
                    Node centerBigGrids[] = new Node[world.getBigGrids().size()];//格子数组
                    Node dyNodes[] = new Node[world.getDynamicNodes().size()];//节点数组
                    int num = 0;//记录数组下标
                    for(Map.Entry<Integer,Grid> entry:world.getBigGrids().entrySet())
                    {
                        centerBigGrids[num] =NodeUtils.centerNode(entry.getValue());
                        num++;
                    }
                    num = 0;
                    for(Map.Entry<Integer,Node> entry:world.getDynamicNodes().entrySet())
                    {
                        dyNodes[num] =entry.getValue();
                        num++;
                    }
                    double graph[][] = new double[len][len];
                    if(centerBigGrids.length >= dyNodes.length ) {
                        for (int i = 0; i < len; i++) {
                            for (int j = 0; j < dyNodes.length; j++) {
                                    graph[i][j] = 0 - NodeUtils.distanceOfNode(centerBigGrids[i], dyNodes[j]);
                            }
                            for(int k = dyNodes.length; k < len;k++)
                            {
                                graph[i][k] = 0;
                            }
                        }
                    } else {
                        for (int i = 0; i < centerBigGrids.length; i++) {
                                for (int j = 0; j < len; j++) {
                                    graph[i][j] = 0 - NodeUtils.distanceOfNode(centerBigGrids[i], dyNodes[j]);
                            }
                        }
                        for(int l = centerBigGrids.length; l < len;l++)
                        {
                            for (int j = 0; j < len; j++) {
                                graph[l][j] = 0;
                            }
                        }
                    }
//                    for(int i = 0;i < len;i++)
//                    {
//                        for(int j = 0;j < len;j++)
//                        {
//                           System.out.print(graph[i][j] + "   ");
//                        }
//                        System.out.println("");
//                    }
                    KM km = new KM(len);
                    km.kmInit(graph);
                    km.km();
                    km.lineSum();
                    world.setLineSum(km.getSum());
                    num = 0; //此时num标记匹配数组的位置
                    int movedNode[] = km.getMovedNode();
                    for(int i = 0; i < dyNodes.length;i++)
                    {
                        for(Map.Entry<Integer,Node> entry:world.getNodes().entrySet())
                        {
                            if( entry.getValue().equals(dyNodes[i]) && movedNode[i] < centerBigGrids.length){
                                entry.getValue().setX(centerBigGrids[movedNode[i]].getX());
                                entry.getValue().setY(centerBigGrids[movedNode[i]].getY());
                               // System.out.println("此时第" + i + "个节点移动到了第" + movedNode[i] + "个格子里！");
                                num++;
                            }
                        }
                    }
                    System.out.println("一共移动了" + num + "个节点！");
                    //System.out.println("一共有" + dyNodes.length + "个节点！");
                }
            }
            return world;
        }
    };

    /**节点的移动策略
     * @return 移动后的节点分布*/
    abstract public World evolute(World world);
}
