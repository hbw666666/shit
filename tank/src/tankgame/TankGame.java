package tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @version 1.0
 * @anther 侯博文
 * 游戏主窗口类，程序入口
 */
public class  TankGame extends JFrame {
    //游戏主画布面板
    MyPanel myPanel = null;
    //控制台输入，用于选择新游戏或继续上局
    static Scanner scanner = new Scanner(System.in);


    public TankGame() {
        System.out.println("请输入选择 1：新游戏，2：继续上局");
        String key = scanner.next();
        //根据用户选择创建画布
        myPanel = new MyPanel(key);
        //启动画布重绘线程
        new Thread(myPanel).start();
        //将画板添加到到窗口
        this.add(myPanel);
        //设置窗口大小
        this.setSize(1300,850);
        //设置关闭窗口时退出程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示窗口
        this.setVisible(true);
        //添加键盘监听
        this.addKeyListener(myPanel);
        //在JFrame窗口中添加关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //关闭窗口时保存游戏记录
                Recorder.keepRecord();
                System.out.println("监听到窗口关闭，击毁数已保存");
                System.exit(0);
            }
        });
    }

    //程序入口
    public static void main(String[] args) {
        TankGame tankGame = new  TankGame();
    }
}
