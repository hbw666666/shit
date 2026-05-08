package tankgame;

/**
 * @version 1.0
 * @anther 侯博文
 * 爆炸效果类，坦克被击中时创建，显示爆炸动画
 */
public class Bomb {
    //爆炸中心x坐标
    int x,y;
    //爆炸生命值，用于控制爆炸动画帧
    int life = 9;
    //是否存活
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //每调用一次生命值减1，生命值为0时爆炸效果结束
    public void lifeDown(){
        if(life>0){
            life--;
        }else{
            isLive = false;
        }
    }

}
