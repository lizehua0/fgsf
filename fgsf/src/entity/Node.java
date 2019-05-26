package entity;

public class Node {
    private String id;

    private double x;

    private double y;

    //1为静态节点，0为动态节点
    private int flag;

    @Override
    public boolean equals(Object object){
        if(object == null)
        {
            return false;
        }
        Node node = (Node)object;
        if(node.getId().equals(this.id)){
            return true;
        } else {
            return false;
        }
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
