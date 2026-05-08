package tankgame;

import java.util.Vector;

/**
 * @version 1.0
 * @anther 侯博文
 * 己方坦克类，由玩家键盘控制
 */
public class Hero extends Tank{
    //单发子弹
    Shot shot = null;
    //存放多颗子弹的集合
    Vector< Shot> shots = new Vector<>();

    public  Hero(int x, int y, int direction,int speed) {
        super(x, y,direction,speed);
    }

    //发射子弹，最多同时存在5颗
    public void shotEnemyTank(){
        if(shots.size()==5){
            return;
        }
        //根据当前方向创建子弹
        switch (getDirection()){
            case 0://上
                 shot = new Shot(getX()+20,getY(),0);
                break;
            case 1://右
                 shot = new Shot(getX()+60,getY()+20,1);
                break;
            case 2://下
                 shot = new Shot(getX()+20,getY()+60,2);
                break;
            case 3://左
                 shot = new Shot(getX(),getY()+20,3);
                break;
            default:
                System.out.println("暂时未处理");
        }
        //把新创建子弹加入到集合中
        shots.add(shot);
        //启动子弹线程
        new Thread(shot).start();
    }

}
