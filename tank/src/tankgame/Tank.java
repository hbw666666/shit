package tankgame;

/**
 * @version 1.0
 * @anther 侯博文
 * 坦克基类，定义坦克的坐标、方向、速度和移动方法
 */
public class Tank {
    //坦克x坐标
    private int x;
    //坦克y坐标
    private int y;
    //坦克方向 0上 1右 2下 3左
    private int direction;
    //坦克移动速度
    private int speed = 1;
    //坦克是否存活
    boolean isLive = true;

    public Tank(int x, int y, int direction, int speed) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    //向下移动（y坐标增加）
    public void moveUp(){
        y+= speed;
    }

    //向上移动（y坐标减少）
    public void moveDown(){
        y-= speed;
    }

    //向左移动
    public void moveLeft(){
        x-= speed;
    }

    //向右移动
    public void moveRight(){
        x+= speed;
    }

    public int getX() {

        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
