package tankgame;

import java.util.Vector;

/**
 * @version 1.0
 * @anther 侯博文
 * 敌方坦克类，由线程驱动自动移动和射击
 */
public class EnemyTank extends Tank implements Runnable{
    //存放该坦克发射的子弹
    Vector<Shot> shots = new Vector<>();
    //敌人坦克集合的引用，用于碰撞检测
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //敌人坦克移动速度
    private int speed = 1;

    public EnemyTank(int x, int y) {
       super(x,y,2,1);
   }

   //将MyPanel中的敌人坦克集合引用传给当前坦克，用于碰撞检测
   public void setEnemyTanks(Vector<EnemyTank> enemyTanks){
        this.enemyTanks = enemyTanks;
   }

   //判断当前坦克是否与敌人坦克集合中的其他坦克碰撞
   //根据自身方向和对方方向分别判断，因为坦克宽高随方向变化（上下时40x60，左右时60x40）
   public boolean isTouchEnemyTnK(){
        switch (this.getDirection()){
            case 0://当前坦克方向为上
                for(int i = 0; i < enemyTanks.size(); i++){
                    //从敌人坦克集合vector中取出一个坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if(enemyTank!= this){
                        //对方坦克是上下方向（体型40x60）
                        if(enemyTank.getDirection()==0||enemyTank.getDirection()==2){
                            //检测当前坦克左上角是否碰撞
                            if(this.getX()>=enemyTank.getX()
                                    &&this.getX()<=enemyTank.getX()+40
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+60){
                                return true;
                            }
                            //检测当前坦克右上角是否碰撞
                            if(this.getX()+40>=enemyTank.getX()
                                    &&this.getX()+40<=enemyTank.getX()+40
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+60){
                                return true;
                            }
                        }
                        //对方坦克是左右方向（体型60x40）
                        if(enemyTank.getDirection()==1||enemyTank.getDirection()==3){
                            //检测当前坦克左上角是否碰撞
                            if(this.getX()>=enemyTank.getX()
                                    &&this.getX()<=enemyTank.getX()+60
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+40){
                                return true;
                            }
                            //检测当前坦克右上角是否碰撞
                            if(this.getX()+40>=enemyTank.getX()
                                    &&this.getX()+40<=enemyTank.getX()+60
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;
                case 1://当前坦克方向为右
                    for(int i = 0; i < enemyTanks.size(); i++){
                        //从敌人坦克集合vector中取出一个坦克
                        EnemyTank enemyTank = enemyTanks.get(i);
                        //不和自己比较
                        if(enemyTank!= this){
                            //对方坦克是上下方向（体型40x60）
                            if(enemyTank.getDirection()==0||enemyTank.getDirection()==2){
                                //检测当前坦克右上角是否碰撞
                                if(this.getX()+60>=enemyTank.getX()
                                        &&this.getX()+60<=enemyTank.getX()+40
                                        &&this.getY()>=enemyTank.getY()
                                        &&this.getY()<=enemyTank.getY()+60){
                                    return true;
                                }
                                //检测当前坦克右下角是否碰撞
                                if(this.getX()+60>=enemyTank.getX()
                                        &&this.getX()+60<=enemyTank.getX()+40
                                        &&this.getY()+40>=enemyTank.getY()
                                        &&this.getY()+40<=enemyTank.getY()+60){
                                    return true;
                                }
                            }
                            //对方坦克是左右方向（体型60x40）
                            if(enemyTank.getDirection()==1||enemyTank.getDirection()==3){
                                //检测当前坦克右上角是否碰撞
                                if(this.getX()+60>=enemyTank.getX()
                                        &&this.getX()+60<=enemyTank.getX()+60
                                        &&this.getY()>=enemyTank.getY()
                                        &&this.getY()<=enemyTank.getY()+40){
                                    return true;
                                }
                                //检测当前坦克右下角是否碰撞
                                if(this.getX()+60>=enemyTank.getX()
                                        &&this.getX()+60<=enemyTank.getX()+60
                                        &&this.getY()+40>=enemyTank.getY()
                                        &&this.getY()+40<=enemyTank.getY()+40){
                                    return true;
                                }
                            }
                        }
                    }
                    break;
                    case 2://当前坦克方向为下
                        for(int i = 0; i < enemyTanks.size(); i++){
                            //从敌人坦克集合vector中取出一个坦克
                            EnemyTank enemyTank = enemyTanks.get(i);
                            //不和自己比较
                            if(enemyTank!= this){
                                //对方坦克是上下方向（体型40x60）
                                if(enemyTank.getDirection()==0||enemyTank.getDirection()==2){
                                    //检测当前坦克左下角是否碰撞
                                    if(this.getX()>=enemyTank.getX()
                                            &&this.getX()<=enemyTank.getX()+40
                                            &&this.getY()+60>=enemyTank.getY()
                                            &&this.getY()+60<=enemyTank.getY()+60){
                                        return true;
                                    }
                                    //检测当前坦克右下角是否碰撞
                                    if(this.getX()+40>=enemyTank.getX()
                                            &&this.getX()+40<=enemyTank.getX()+40
                                            &&this.getY()+60>=enemyTank.getY()
                                            &&this.getY()+60<=enemyTank.getY()+60){
                                        return true;
                                    }
                                }
                                //对方坦克是左右方向（体型60x40）
                                if(enemyTank.getDirection()==1||enemyTank.getDirection()==3){
                                    //检测当前坦克左下角是否碰撞
                                    if(this.getX()>=enemyTank.getX()
                                            &&this.getX()<=enemyTank.getX()+60
                                            &&this.getY()+60>=enemyTank.getY()
                                            &&this.getY()+60<=enemyTank.getY()+40){
                                        return true;
                                    }
                                    //检测当前坦克右下角是否碰撞
                                    if(this.getX()+40>=enemyTank.getX()
                                            &&this.getX()+40<=enemyTank.getX()+60
                                            &&this.getY()+60>=enemyTank.getY()
                                            &&this.getY()+60<=enemyTank.getY()+40){
                                        return true;
                                    }
                                }
                            }
                        }
                        break;
                        case 3://当前坦克方向为左
                            for(int i = 0; i < enemyTanks.size(); i++){
                                //从敌人坦克集合vector中取出一个坦克
                                EnemyTank enemyTank = enemyTanks.get(i);
                                //不和自己比较
                                if(enemyTank!= this){
                                    //对方坦克是上下方向（体型40x60）
                                    if(enemyTank.getDirection()==0||enemyTank.getDirection()==2){
                                        //检测当前坦克左上角是否碰撞
                                        if(this.getX()>=enemyTank.getX()
                                                &&this.getX()<=enemyTank.getX()+40
                                                &&this.getY()>=enemyTank.getY()
                                                &&this.getY()<=enemyTank.getY()+60){
                                            return true;
                                        }
                                        //检测当前坦克左下角是否碰撞
                                        if(this.getX()>=enemyTank.getX()
                                                &&this.getX()<=enemyTank.getX()+40
                                                &&this.getY()+40>=enemyTank.getY()
                                                &&this.getY()+40<=enemyTank.getY()+60){
                                            return true;
                                        }
                                    }
                                    //对方坦克是左右方向（体型60x40）
                                    if(enemyTank.getDirection()==1||enemyTank.getDirection()==3){
                                        //检测当前坦克左上角是否碰撞
                                        if(this.getX()>=enemyTank.getX()
                                                &&this.getX()<=enemyTank.getX()+60
                                                &&this.getY()>=enemyTank.getY()
                                                &&this.getY()<=enemyTank.getY()+40){
                                            return true;
                                        }
                                        //检测当前坦克左下角是否碰撞
                                        if(this.getX()>=enemyTank.getX()
                                                &&this.getX()<=enemyTank.getX()+60
                                                &&this.getY()+40>=enemyTank.getY()
                                                &&this.getY()+40<=enemyTank.getY()+40){
                                            return true;
                                        }
                                    }
                                }
                            }
                            break;
        }return false;
   }

    @Override
    public void run() {
        while (true) {
            //记录本回合移动步数，用于判断是否碰到边界
            int steps = 0;
            //子弹数不足1颗时补充子弹
            if(isLive&&shots.size()<1){
                Shot shot = null;
                //根据当前方向创建子弹
                switch (getDirection()) {
                    case 0://上
                        shot = new Shot(getX() + 20, getY() - 5, 0);
                        break;
                    case 1://右
                        shot = new Shot(getX() + 65, getY() + 20, 1);
                        break;
                    case 2://下
                        shot = new Shot(getX() + 20, getY() + 65, 2);
                        break;
                    case 3://左
                        shot = new Shot(getX() - 5, getY() + 20, 3);
                        break;
                }
                if (shot != null) {
                    shots.add(shot);
                    new Thread(shot).start();
                }
            }
            //根据方向移动坦克，最多移动30步，碰到边界或其它坦克则停止
            switch (getDirection()) {
                case 0://上
                    for(int i = 0; i < 30; i++) {
                        if (getY() < 690&&!isTouchEnemyTnK()) {
                            moveUp();
                            steps++;
                        } else {
                            break;
                        }
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1://右
                    for(int i = 0; i < 30; i++) {
                        if (getX() <940&&!isTouchEnemyTnK()) {
                            moveRight();
                            steps++;
                        } else {
                            break;
                        }

                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2://下
                    for(int i = 0; i < 30; i++) {
                        if (getY()  >60&&!isTouchEnemyTnK()) {
                            moveDown();
                            steps++;
                        } else {
                            break;
                        }

                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3://左
                    for(int i = 0; i < 30; i++) {
                        if (getX() > 60&&!isTouchEnemyTnK()) {
                            moveLeft();
                            steps++;
                        } else {
                            break;
                        }

                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }


            //如果一步都没移动（碰到边界或障碍），随机改变方向
            if(steps == 0) {
                setDirection((int)(Math.random() * 4));
            } else {
                //正常移动后也随机改变方向，增加不可预测性
                setDirection((int)(Math.random() * 4));
            }

            //坦克死亡则退出线程
            if(!isLive){
                break;
            }


            //移动后尝试发射子弹（最多5颗）
            if (shots.size() < 5) {
                Shot shot = null;
                switch (getDirection()) {
                    case 0:
                        shot = new Shot(getX() + 20, getY() - 5, 0);
                        break;
                    case 1:
                        shot = new Shot(getX() + 65, getY() + 20, 1);
                        break;
                    case 2:
                        shot = new Shot(getX() + 20, getY()+ 65, 2);
                        break;
                    case 3:
                        shot = new Shot(getX() - 5, getY() + 20, 3);
                }
                if (shot != null) {
                    shots.add(shot);
                    new Thread(shot).start();
                }
            }
        }
    }
}
