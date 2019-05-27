package test;

import entity.World;
import rule.MoveRule;
import utils.FileUtils;

public class test {
    public static void main(String []arg){
        World world = new World(300,300,50,0.2f,120);
        world.init();
        world.spreadNodes();
        world.findoutDynamicNodes();
        boolean result = FileUtils.nodeWrite(world.getNodes(),"E:\\nodedate\\data.txt");
        if(result){
            System.out.println("初始播撒节点写入文件成功！");
        } else {
            System.out.println("初始播撒节点写入文件失败！");
        }
        //MoveRule.DDTC.evolute(world);
        MoveRule.KMFG.evolute(world);
        boolean result1 = FileUtils.nodeWrite(world.getNodes(),"E:\\nodedate\\dataMoved.txt");
        if(result1){
            System.out.println("移动播撒节点写入文件成功！");
        } else {
            System.out.println("移动播撒节点写入文件失败！");
        }
//        boolean result2 = FileUtils.writeIntoFile(world.getLineSum()/world.getDynamicNodes().size() + "","E:\\nodedate\\dataMoved.txt");
//        if(result2){
//            System.out.println("节点平均移动距离写入文件成功！");
//        } else {
//            System.out.println("节点平均移动距离写入文件失败！");
//        }
    }
}
