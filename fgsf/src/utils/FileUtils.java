package utils;

import entity.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileUtils {
    /**
     * 在指定路径下创建一个文件
     */
    public static boolean createFile(String filePath){
        boolean flag = false;
        File newFile = new File(filePath);
        if(!newFile.exists()){
            try{
                newFile.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
            flag = true;
        }
        return flag;
    }

    /**
     * 向文件中写入数据
     */
    public static boolean writeIntoFile(String content,String filePath){
        boolean flag = false;
        File newFile = new File(filePath);
        try{
        if(!newFile.exists()){
                newFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(content);
        fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        flag = true;
        return flag;
    }

    /**
     * 将一组节点坐标写入文件当中
     */
    public static boolean nodeWrite(Map<Integer,Node> nodes, String filePath){
        boolean flag = false;
        File newFile = new File(filePath);
        String line = System.getProperty("line.separator");
        try{
            if(!newFile.exists()){
                newFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(filePath);
            for(Map.Entry<Integer,Node> entry:nodes.entrySet()){
                fileWriter.write(entry.getValue().getX()+" ");
                fileWriter.write(entry.getValue().getY()+"");
                fileWriter.write(line);
            }
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("一共向文件写入" + nodes.size() + "个数据！");
        flag = true;
        return flag;
    }
}
