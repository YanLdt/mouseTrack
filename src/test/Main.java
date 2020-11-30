package test;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author YanL
 * @date 22:12 2020/11/29
 */
public class Main {

    public static void main(String[] args) {

        final JTextField tf = new JTextField(12);
        final JTextField tf2 = new JTextField(10);
        final JLabel testNumber = new JLabel("测试序号");
        final JLabel moveWay = new JLabel("移动方式");
//        tf.setBounds(150, 20, 50, 20);
//        tf2.setText("测试序号: ");
        JButton b = new JButton("开始");

        JFrame jf = new JFrame("测试窗口");
        jf.setSize(250, 250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton button = new JButton("skd");
        JButton button2 = new JButton("skd2");

        //jf.add(button);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
//        panel.add(button);
//        panel.add(button2);

        panel.add(testNumber);
        panel.add(tf);


        button.setBounds(100, 100, 20, 20);
        button2.setBounds(100, 200, 50, 50);

        // 添加一个标签
        JLabel label = new JLabel("形        状");
        panel.add(label);

        // 需要选择的条目
        String[] listData = new String[]{"正方形", "圆形", "不规则", "竖", "横"};

        // 创建一个下拉列表框
        final JComboBox<String> comboBox = new JComboBox<String>(listData);
        comboBox.setPreferredSize(new Dimension(130, 25));

        // 添加条目选中状态改变的监听器
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // 只处理选中的状态
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("选中: " + comboBox.getSelectedIndex() + " = " + comboBox.getSelectedItem());
                }
            }
        });

        // 设置默认选中的条目
        comboBox.setSelectedIndex(0);

        // 添加到内容面板
        panel.add(comboBox);
        panel.add(moveWay);
        String[] ways = new String[]{"图", "框"};
        final JComboBox<String> comboBoxWay = new JComboBox<String>(ways);
        comboBoxWay.setPreferredSize(new Dimension(130, 25));
        panel.add(comboBoxWay);
        panel.add(b);
        jf.add(panel);
        jf.setVisible(true);
    }

}