package com.canoe.login.window;

import com.canoe.Game;
import com.canoe.login.jdbc.Conn;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.Enumeration;

public class LoginWindow {
    private JFrame mainFrame = new JFrame("登录");
    private Container container = mainFrame.getContentPane();
    private JLabel titleLabel = new JLabel("登录/注册", JLabel.CENTER);

    private JPanel inputField = new JPanel();
    private JLabel usernameLabel = new JLabel("用户名:", JLabel.CENTER);
    private JTextField username = new JTextField();
    private JLabel passwordLabel = new JLabel("密码:", JLabel.CENTER);
    private JPasswordField password = new JPasswordField();

    private JPanel buttonField = new JPanel();
    private JButton save = new JButton("登录");
    private JButton cancel = new JButton("取消");

    private JPanel errPanel = new JPanel();

    private JLabel errLabel = new JLabel("账号或密码错误");

    // 登录结果
    private volatile Boolean loginRes = false;

    public LoginWindow() {
        init();
        setFont(new Font("微软雅黑", Font.PLAIN, 14));
    }


    private void init() {
        container.setLayout(new GridLayout(4, 1, 0, 10));
        container.add(titleLabel);

        inputField.setLayout(new GridLayout(2, 2, 5, 5));
        inputField.add(usernameLabel);
        inputField.add(username);
        inputField.add(passwordLabel);
        inputField.add(password);
        container.add(inputField);

        errPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        errLabel.setForeground(Color.red);
        errPanel.add(errLabel);
        errPanel.setVisible(false);

        buttonField.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonField.add(save);
        buttonField.add(cancel);
        container.add(errPanel);
        container.add(buttonField);

        // 设置用户名的焦点事件
        // 当失去获取焦点/失去焦点后 控制错误信息不显示
        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                errPanel.setVisible(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                errPanel.setVisible(false);
            }
        });

        // 设置密码输入框的焦点事件
        // 当失去获取焦点/失去焦点后 控制错误信息不显示
        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                errPanel.setVisible(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                errPanel.setVisible(false);
            }
        });

        // 添加登录事件监听
        save.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameText = username.getText();
                String passwordText = new String(password.getPassword());

                // jdbc的login
                Conn conn = new Conn();
                try {
                    loginRes = conn.login(usernameText, passwordText);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                // 登录成功后，修改登录结果，唤醒主线程
                if (loginRes) {
                    mainFrame.dispose();

                    // 加锁
                    synchronized (Game.obj) {
                        loginRes = true;

                        // 唤醒主线程
                        Game.obj.notify();
                    }
                } else {
                    // 设置显示错误信息
                    errPanel.setVisible(true);
                }
            }
        });

        // 取消事件，
        cancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                // 加锁
                synchronized (Game.obj) {
                    // 唤醒主线程
                    Game.obj.notify();
                }
            }
        });

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(350, 280);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }


    // 获取登录结果
    public Boolean getLoginRes() {
        return loginRes;
    }
    private void setFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }
}



