package tankgame;

/**
 * @version 1.0
 * @anther 侯博文
 * 一个node对象，表示一个敌人坦克的信息，用于存档/读档时序列化和反序列化
 */
public class Node {
    //坦克x坐标
    public int x;
    //坦克y坐标
    public int y;
    //坦克方向
    public int direction;

    public Node(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getDirection() { return direction; }
    public void setDirection(int direction) { this.direction = direction; }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                '}';
    }

}
