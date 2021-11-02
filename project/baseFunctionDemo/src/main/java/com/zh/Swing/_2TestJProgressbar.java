package com.zh.Swing;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


/**
 * @ClassName TestJProgressBar
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/11/2
 * @Version 1.0
 */

@SuppressWarnings("serial")
public class _2TestJProgressbar extends JFrame {

    private JProgressBar progressBar;
    private JPanel canv;
    private int currentValue = 0;
    private Timer timer;

    public _2TestJProgressbar() {
        this.setTitle("JProgress Bar Demo");
        this.setLocation(300, 300);
        this.setSize(500, 200);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        this.progressBar = new JProgressBar();
        this.progressBar.setSize(400, 80);
        this.progressBar.setMaximum(100);
        this.progressBar.setValue(0);
        this.progressBar.setVisible(true);
        this.progressBar.setLocation(50, 50);

        this.canv = new JPanel();

        this.canv.add(progressBar);

        this.getContentPane().add(canv);

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                currentValue++;
                System.out.println(currentValue + "");
                progressBar.setValue(currentValue);
                if (currentValue >= 100) {
                    timer.cancel();
                }
            }

        }, 0, 100);
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        new _2TestJProgressbar().show();
    }

}
