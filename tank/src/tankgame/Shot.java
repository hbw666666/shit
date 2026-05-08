package tankgame;

/**
 * @version 1.0
 * @anther 侯博文
 * 射击子弹
 */
public class Shot implements Runnable {
    //子弹x坐标
     int x;
    //子弹y坐标
     int y;
    //子弹方向 0上 1右 2下 3左
     int direction;
    //子弹速度
     int speed=2;
    //子弹是否存活
     boolean isLive = true;

     public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void run() {
        while( true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //根据方向移动子弹
            switch (direction){
                case 0://上
                    y-=speed;
                    break;
                case 1://右
                    x+=speed;
                    break;
                case 2://下
                    y+=speed;
                    break;
                case 3://左
                    x-=speed;
                    break;
            }
            //子弹超出边界则销毁
            if(!(x>=0&&x<=1000&&y>=0&&y<=750&&isLive)){
                isLive = false;
                break;
            }
        }
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
