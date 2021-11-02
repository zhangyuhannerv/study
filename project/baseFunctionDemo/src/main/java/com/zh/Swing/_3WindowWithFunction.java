package com.zh.Swing;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class _3WindowWithFunction implements ActionListener {
    private static int currentValue = 0;

    JFrame frame = new JFrame("解析sts文件");
    JTabbedPane tabPane = new JTabbedPane();//选项卡布局
    Container con = new Container();//布局1
    Container con1 = new Container();//布局2
    JLabel label1 = new JLabel("选择文件");
    JLabel label2 = new JLabel("选择目录");
    JTextField text1 = new JTextField();
    JTextField text1_1 = new JTextField();
    JTextField text2 = new JTextField();
    JTextField text2_1 = new JTextField();
    JButton button1 = new JButton("...");
    JButton button1_1 = new JButton("解析");
    JButton button2 = new JButton("...");
    JButton button2_1 = new JButton("解析");
    JFileChooser jfc = new JFileChooser();//文件选择器

    // 进度条
    JProgressBar jProgressBar1 = new JProgressBar();

    _3WindowWithFunction() {
        jfc.setCurrentDirectory(new File("C:\\Users\\13551\\Desktop\\"));//文件选择器的初始目录定为d盘
        //下面两行是取得屏幕的高度和宽度
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));//设定窗口出现位置
        frame.setSize(300, 150);//设定窗口大小
        frame.setContentPane(tabPane);//设置布局

        //下面设定标签等的出现位置和高宽
        label1.setBounds(10, 10, 70, 20);
        label2.setBounds(10, 10, 70, 20);

        text1.setBounds(80, 10, 120, 20);
        jProgressBar1.setBounds(10, 35, 250, 25);
        jProgressBar1.setStringPainted(true);
        // text1_1.setBounds(10, 35, 250, 25);

        text2.setBounds(80, 10, 120, 20);

        button1.setBounds(210, 10, 50, 20);
        button1_1.setBounds(100, 60, 70, 20);

        button2.setBounds(210, 10, 50, 20);
        button2_1.setBounds(100, 50, 70, 20);

        button1.addActionListener(this);//添加事件处理
        button2.addActionListener(this);//添加事件处理
        button1_1.addActionListener(this);//添加事件处理
        button2_1.addActionListener(this);//添加事件处理

        con.add(label1);
        con.add(text1);
        con.add(jProgressBar1);
        // con.add(text1_1);
        con.add(button1);
        con.add(button1_1);

        con1.add(label2);
        con1.add(text2);
        con1.add(button2);
        con1.add(button2_1);


        con1.add(jfc);
        tabPane.add("车内噪声", con);//添加布局1
        tabPane.add("轨道振动加速度", con1);//添加布局2
        frame.setVisible(true);//窗口可见
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使能关闭窗口，结束程序
    }

    @Override
    public void actionPerformed(ActionEvent e) {//事件处理
        new Thread(() -> {
            if (e.getSource().equals(button1)) {//判断触发方法的按钮是哪个
                jfc.setFileSelectionMode(0);//设定只能选择到文件
                int state = jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
                if (state == 1) {
                    return;//撤销则返回
                } else {
                    File f = jfc.getSelectedFile();//f为选择到的目录
                    text1.setText(f.getAbsolutePath());
                }
            }
            if (e.getSource().equals(button2)) {
                jfc.setFileSelectionMode(1);//设定只能选择到文件夹
                int state = jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
                if (state == 1) {
                    return;//撤销则返回
                } else {
                    File f = jfc.getSelectedFile();//f为选择到的文件
                    text2.setText(f.getAbsolutePath());
                }
            }

            if (e.getSource().equals(button1_1)) {
                File file = new File(text1.getText());
                jProgressBar1.setMinimum(0);
                jProgressBar1.setMaximum(BigDecimal.valueOf(file.getTotalSpace() / 4).setScale(0, RoundingMode.UP).intValue());
                jProgressBar1.setValue(500000);
                // parseStsToTxt(file, jProgressBar1);
            }

            if (e.getSource().equals(button2_1)) {
                File file = new File(text1.getText());
            }
        }).start();


    }

    public static void main(String[] args) {
        new _3WindowWithFunction();
    }

    public static void parseStsToTxt(File file, JProgressBar bar) {
        String newFilePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".")) + ".txt";
        try (DataInputStream in = new DataInputStream(new FileInputStream(file));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(newFilePath))) {
            int saveStep = 100000;
            int i = 0;
            byte[] tmp = new byte[4];
            while (in.read(tmp) == 4) {
                i++;
                currentValue = i;
                bar.setValue(500000);

                ByteBuffer buffer = ByteBuffer.wrap(tmp);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                String value = String.format("%.3f", buffer.getFloat());
                if (i == 1 || i % saveStep == 0) {
                    out.writeUTF(value);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
