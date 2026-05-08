package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;

/**
 * @version 3.0
 * @anther 侯博文
 * 绘图区域
 */
//构建画布
public class MyPanel extends JPanel implements KeyListener ,Runnable{
    /**
     * 下面是定义自己坦克，敌人坦克（放入enemyTanks）及其数量大小，炸弹（放入bombs）
     */
    //定义自己坦克
    Hero hero = null;
    //定义一个存放node对象的vector，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //定义敌人坦克放入vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //敌人坦克数量
    int enemyTankSize = 3;
    //定义一个vector,用于存放炸弹
    //当子弹击中坦克，则创建一个Bomb对象，加入bombs vector中，并启动一个线程，用来绘制爆炸效果
    Vector<Bomb> bombs = new Vector<>();
    //定义三张图用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    /**
     * 下面是构造器
     * 初始化敌人所有坦克，以及自己的坦克，创建敌人坦克的子弹（放入子弹vector），并启动子弹和坦克线程
     */
    public MyPanel(String key) {
        //先判断记录的文件是否存在
        //如果存在就读取存档，如果文件不存在则提示只能开启新游戏
        File file = new File(Recorder.getRecordFile());
        if(file.exists()) {
            nodes = Recorder.getEnemyTanks();
        }else{
            System.out.println("文件不存在，只能开启新游戏");
            key="1";
        }
        //将本面板的敌人坦克Vector引用传给Recorder，用于存档
        Recorder.setEnemyTanks(enemyTanks);
        //创建己方坦克
        hero = new Hero(100,100,0,1);
        switch ( key){

        case "1"://新游戏，创建3辆敌人坦克
            //创建敌人坦克
            for(int i=0;i<enemyTankSize;i++){
                //创建敌人坦克，横向排列
                EnemyTank enemyTank = new EnemyTank(100*(i+1),0);
                //将enemyTanks设置给enemyTank，用于碰撞检测
                enemyTank.setEnemyTanks(enemyTanks);
                //设置方向朝下
                enemyTank.setDirection(2);
                //将新的坦克加入vector
                enemyTanks.add(enemyTank);
                //启动坦克线程
                new Thread(enemyTank).start();
                //创建敌方坦克子弹
                Shot shot =new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirection());
                //将新创建的子弹放入该坦克的子弹vector中
                enemyTank.shots.add(shot);
                //启动子弹线程
                new Thread(shot).start();

            }
            break;
        case "2"://继续上局，从存档恢复敌人坦克
            //创建敌人坦克
            for(int i=0;i<nodes.size();i++){
                    Node node = nodes.get(i);
                    //从存档数据恢复敌人坦克的位置和方向
                    EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                    //将enemyTanks设置给enemyTank，用于碰撞检测
                    enemyTank.setEnemyTanks(enemyTanks);
                    //恢复存档的方向
                    enemyTank.setDirection(node.getDirection());
                    //将新的坦克加入vector
                    enemyTanks.add(enemyTank);
                    //启动坦克线程
                    new Thread(enemyTank).start();
                    //创建敌方坦克子弹
                    Shot shot =new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirection());
                    //将新创建的子弹放入该坦克的子弹vector中
                    enemyTank.shots.add(shot);
                    //启动子弹线程
                    new Thread(shot).start();

                }
            break;
        default:
                System.out.println("请输入正确的参数！");
        }

        //初始化图片对象（使用 ImageIO 同步加载，确保爆炸特效立即生效）
        try {
            image1 = ImageIO.read(MyPanel.class.getResource("/豆包.png"));
            image2 = ImageIO.read(MyPanel.class.getResource("/豆包 (1).png"));
            image3 = ImageIO.read(MyPanel.class.getResource("/豆包 (2).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //显示右上角击毁坦克信息面板
    public void showInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font=new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        //绘制提示文字
        g.drawString("您累计击毁敌方坦克",1000,30);
        //在右侧绘制一个静态坦克图标作为展示
        draw(1020,60,g,0,0);
        g.setColor(Color.black);
        //显示累计击毁数
        g.drawString(Recorder.getAllEnemyTankNum()+"",1080,55);

    }
    /**
    *重写paint
     * 绘制方法
     * @param g
     * 画笔
     * 画出自己和敌人坦克，并通过遍历vector，绘制敌方所有坦克以及每个坦克的，
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 整个面板设为白色背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1300, 850);
        // 坦克运动区域（战场）设为黑色
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 750);
        showInfo(g);
        //如果自己坦克存活则画出自己坦克
        if(hero != null && hero.isLive == true) {
            draw(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
            hero.setSpeed(10);
            //画出自己坦克所有存活子弹，移除死亡子弹
            for (int i = 0; i < hero.shots.size(); i++) {
                Shot shot = hero.shots.get(i);
                if (shot != null && shot.isLive == true) {
                    g.fill3DRect(shot.getX(), shot.getY(), 2, 2, false);
                } else {//子弹死亡时，从vector中删除该子弹
                    hero.shots.remove(shot);
                }
            }
        }
        //画出敌方坦克和子弹
        for(int i=0;i<enemyTanks.size();i++) {
            //敌方坦克存活时绘制坦克
            if (enemyTanks.get(i).isLive == true) {
                EnemyTank enemyTank = enemyTanks.get(i);
                draw(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 0);
                //画出该坦克所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出当前子弹
                    Shot shot = enemyTanks.get(i).shots.get(j);
                    //当子弹存活时绘制子弹
                    if (shot.isLive) {
                        g.fill3DRect(shot.getX(), shot.getY(), 2, 2, false);
                    } else {
                        //从vector中删除该子弹
                        enemyTanks.get(i).shots.remove(j);
                    }
                }
            }
        }

        //如果bombs vector不为空，则绘制炸弹（爆炸效果）
        for (int i = 0; i < bombs.size(); i++){
            Bomb bomb = bombs.get(i);
            //根据剩余生命值显示不同阶段的爆炸图片
            if(bomb.life>6){
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
            }else if(bomb.life>3){
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            }else{
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //炸弹生命值减1
            bomb.lifeDown();
           //当炸弹生命值为0时，从vector中删除该炸弹
            if(bomb.life==0){
                bombs.remove(bomb);
            }


        }
    }

    /**
     * 绘制坦克
     * @param x 坦克左上角x坐标
     * @param y 坦克左上角y坐标
     * @param g 画笔
     * @param direction 坦克方向 0上 1右 2下 3左
     * @param type 坦克类型 0敌方 1己方
     */
    public void draw(int x, int y,Graphics g,int direction,int type){
        //根据坦克类型设置颜色
        switch (type){
            case 0://敌人的坦克-青色
                g.setColor(Color.cyan);
                break;
            case 1://我们的坦克-黄色
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克方向，绘制对应形状坦克（坦克宽高随方向变化）
        switch (direction){
            case 0://朝上：40x60（宽x高）
                g.fill3DRect(x,y,10,60,false);//左轮子
                g.fill3DRect(x+30,y,10,60,false);//右轮子
                g.fill3DRect(x+10,y+10,20,40,false);//盖子
                g.fillOval(x+10,y+20,20,20);//圆形盖子
                g.drawLine(x+20,y+30,x+20,y);//炮管朝上
                break;
            case 1://朝右：60x40
                g.fill3DRect(x,y,60,10,false);//上轮子
                g.fill3DRect(x,y+30,60,10,false);//下轮子
                g.fill3DRect(x+10,y+10,40,20,false);//盖子
                g.fillOval(x+20,y+10,20,20);//圆形盖子
                g.drawLine(x+30,y+20,x+60,y+20);//炮管朝右
                break;
            case 2://朝下：40x60
                g.fill3DRect(x,y,10,60,false);//左轮子
                g.fill3DRect(x+30,y,10,60,false);//右轮子
                g.fill3DRect(x+10,y+10,20,40,false);//盖子
                g.fillOval(x+10,y+20,20,20);//圆形盖子
                g.drawLine(x+20,y+30,x+20,y+60);//炮管朝下
                break;
            case 3://朝左：60x40
                g.fill3DRect(x,y,60,10,false);//上轮子
                g.fill3DRect(x,y+30,60,10,false);//下轮子
                g.fill3DRect(x+10,y+10,40,20,false);//盖子
                g.fillOval(x+20,y+10,20,20);//圆形盖子
                g.drawLine(x+30,y+20,x,y+20);//炮管朝左
                break;
            default:
                System.out.println("暂时未处理");
        }
    }

    /**
     * 继承Runable类，实现多线程，用于刷新画布上的内容
     */
    @Override
    public void run() {//每隔100毫秒重绘一次
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //每帧检测敌方是否击中己方
            hitHero();
            //每帧检测己方是否击中敌方
            hitEnemyTank();
            //刷新画面
            this.repaint();
        }
    }


    /**
  * 继承KeyListener类，实现键盘监听（按键键入事件，未使用）
  */
    @Override
    public void keyTyped(KeyEvent e) {
    }


    /**
     * 继承KeyListener类，实现键盘监听
     * 键盘按下方法,用于控制己方坦克移动和射击
     * W上 D右 S下 A左 J射击
     * @param e 键盘事件
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //W键：改为向上移动
        if(e.getKeyCode()==KeyEvent.VK_W) {
            hero.setDirection(0);
            if(hero.getY()>0) {
                hero.moveDown();
            }
        }
        //D键：改为向右移动
        else if(e.getKeyCode()==KeyEvent.VK_D) {
            hero.setDirection(1);
            if(hero.getX()<940) {
                hero.moveRight();
            }
        }
        //S键：改为向下移动
        else if(e.getKeyCode()==KeyEvent.VK_S){
            hero.setDirection(2);
            if(hero.getY()<690) {
                hero.moveUp();
            }
        }
        //A键：改为向左移动
        else if(e.getKeyCode()==KeyEvent.VK_A){
            hero.setDirection(3);
            if(hero.getX()>0) {
                hero.moveLeft();
            }
        }
        //J键：发射子弹
        if(e.getKeyCode()==KeyEvent.VK_J){

                hero.shotEnemyTank();

        }
        this.repaint();
    }
    /**
     * 继承KeyListener类，实现键盘监听（按键释放事件，未使用）
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }


    /**
     * 判断一颗子弹是否击中坦克
     * 根据坦克方向使用不同的碰撞检测区域（上下方向40x60，左右方向60x40）
     * @param s 子弹
     * @param enemyTank 目标坦克
     */
    public void hitTank(Shot s,Tank enemyTank){
        switch (enemyTank.getDirection()){
            case 0://坦克朝上，碰撞区域40x60
            case 2://坦克朝下，碰撞区域40x60
                if(s.getX()>=enemyTank.getX()&&s.getX()<=enemyTank.getX()+40
                        &&s.getY()>=enemyTank.getY()&&s.getY()<=enemyTank.getY()+60){
                    s.isLive=false;
                    enemyTank.isLive=false;
                    //我方击毁一个敌人坦克时，累计击毁数加1
                    if(enemyTank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    //在坦克位置创建爆炸效果
                    Bomb bomb = new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                    System.out.println("击中敌方坦克！坐标：" + enemyTank.getX() + "," + enemyTank.getY());
                }
                break;
            case 1://坦克朝右，碰撞区域60x40
            case 3://坦克朝左，碰撞区域60x40
                if(s.getX()>=enemyTank.getX()&&s.getX()<=enemyTank.getX()+60
                        &&s.getY()>=enemyTank.getY()&&s.getY()<=enemyTank.getY()+40){
                    s.isLive=false;
                    enemyTank.isLive=false;
                    if(enemyTank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                    System.out.println("击中敌方坦克！坐标：" + enemyTank.getX() + "," + enemyTank.getY());
                }
                break;
        }
    }

    /**
     * 判断我方多颗子弹是否击中敌人坦克
     * 遍历我方所有子弹和所有敌人坦克，检测碰撞
     */
    public void hitEnemyTank(){
        //遍历己方所有子弹
        for(int i=0;i<hero.shots.size();i++){
            Shot shot = hero.shots.get(i);
            if(shot.isLive){
                //遍历敌方所有坦克
                for(int j=0;j<enemyTanks.size();j++){
                    EnemyTank enemyTank = enemyTanks.get(j);
                    if(enemyTank.isLive){
                        hitTank(shot,enemyTank);
                        //如果敌方坦克被击毁
                        if(!enemyTank.isLive){
                            //将该敌方坦克的所有子弹设为死亡
                            for(int k=0;k<enemyTank.shots.size();k++){
                                enemyTank.shots.get(k).isLive=false;
                            }
                            //从集合中移除该坦克
                            enemyTanks.remove(j);
                            j--;
                            break;
                        }
                    }
                }
            }
        }
    }


    /**
     * 敌方多辆坦克的多颗子弹是否击中我方坦克
     * 遍历所有敌方坦克的子弹，检测是否击中己方坦克
     */
    public void hitHero(){
        //遍历所有敌方坦克
        for(int i=0;i<enemyTanks.size();i++){
            EnemyTank enemyTank1 = enemyTanks.get(i);
            //遍历该敌方坦克的所有子弹
            for(int j=0;j<enemyTank1.shots.size();j++){
                Shot shot1 = enemyTank1.shots.get(j);
                //如果己方坦克存活，检测是否被击中
                if(hero.isLive== true){
                    hitTank(shot1,hero);
                }
            }
        }
    }
}
