package com.zh.Swing;

import java.awt.event.*;
import javax.swing.*;

/**
 * @ClassName _1Frame
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/11/2
 * @Version 1.0
 */
public class _1Frame extends JFrame {
    public void launchFrame() {

        this.setTitle("亲爱的朋友");
        this.setVisible(true);
        this.setSize(400, 400);
        this.setLocation(100, 100);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        _1Frame f = new _1Frame();
        f.launchFrame();
    }

}
