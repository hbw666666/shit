package tankgame;

import java.io.*;
import java.util.Vector;

/**
 * @version 1.0
 * @anther 侯博文
 */
public class Recorder {
    //定义变量，记录击毁坦克数
    private static int allEnemyTankNum = 0;
    //把记录文件保存到src下
    private static String recordFile = "src\\myRecord.text";
    // 定义一个vector，用于保存敌人坦克的信息
    private  static Vector<EnemyTank> enemyTanks = null;
    //定义一个node的vector，用于保存敌人的信息（上面的升级版，用于序列化和反序列化）
    private static Vector<Node> nodes = new Vector<>();


    //读取记录文件recordFile,恢复游戏
    public static Vector<Node> getEnemyTanks() {
        //每次读取前清空nodes，避免重复调用时数据叠加
        nodes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(recordFile))) {
            //第一行为累计击毁坦克数
            allEnemyTankNum=Integer.parseInt(br.readLine());
            String line = "";
            //逐行读取每个存活敌人坦克的x坐标、y坐标、方向
            while((line = br.readLine()) != null){
                String [] xyd =line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]),Integer.parseInt(xyd[1]),Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodes;
    }

    //将MyPanel中的敌人坦克Vector引用赋给Recorder，以便存档时遍历
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //返回累计击毁的敌方坦克数量
    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    //设置累计击毁的敌方坦克数量
    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //每击毁一辆敌方坦克，累计计数加1
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }

    //返回记录文件的路径
    public static String getRecordFile() {
        return recordFile;
    }


    //当游戏退出时，将allEnemyTankNum记录到myrecord.txt文件中
    public static void keepRecord() {
        //确保父目录存在
        new File(recordFile).getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(recordFile))) {
            //第一行写入累计击毁坦克数
            bw.write(Integer.toString(allEnemyTankNum));
            bw.newLine();

            //遍历敌人坦克Vector，将存活的坦克坐标和方向写入文件
            if (enemyTanks != null) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank.isLive) {
                        bw.write(enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirection());
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
