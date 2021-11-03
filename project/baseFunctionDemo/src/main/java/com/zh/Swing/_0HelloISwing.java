package com.zh.Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ClassName HelloFrame
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/11/2
 * @Version 1.0
 */
public class _0HelloISwing {
    private static JTextArea area;

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setTitle("JAVA");
        jf.setBounds(500, 200, 300, 300);
        JPanel con = new JPanel(null);

        area = new JTextArea();
        area.setLineWrap(true);
        JScrollPane jp = new JScrollPane(area);
        jp.setBounds(10, 10, 280, 200);
        con.add(jp);
        JButton helloButton = new JButton("HELLO！");
        JButton clearButton = new JButton("JAVA");
        helloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.setText("");
                area.append("Hello!" + "\n");
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.setText("");
                area.append("JAVA!" + "\n");
            }
        });

        helloButton.setBounds(70, 220, 75, 30);
        clearButton.setBounds(150, 220, 75, 30);
        con.add(helloButton);
        con.add(clearButton);

        jf.add(con);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
