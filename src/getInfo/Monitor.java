package getInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author YanL
 * @date 19:53 2020/11/9
 */
public class Monitor extends JFrame {


    public Monitor()  {

        //Container container = getContentPane();

    }

    public static void main(String[] args)  {

        Monitor frame = new Monitor();

        final JTextField tf = new JTextField();
        final JTextField tf2 = new JTextField();
        tf.setBounds(150, 50, 50, 20);
        tf2.setBounds(50, 50, 100, 20);
        tf2.setText("testNumber");
        JButton b = new JButton("start");
        b.setBounds(100, 100, 65, 30);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean [] onOff ={true};
                String num = tf.getText();
                tf.setBackground(Color.CYAN);
                new Thread(new MouseHook(onOff , num)).start();
            }
        });
        frame.add(b);
        frame.add(tf);
        frame.add(tf2);
        frame.setSize(300, 250);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("MouseTrack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
