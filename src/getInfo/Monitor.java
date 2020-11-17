package getInfo;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author YanL
 * @date 19:53 2020/11/9
 */
public class Monitor extends JFrame {

    private static String[] title = {"Number", "StartX", "StartY", "EndX", "EndY", "K"};

    private static File file;


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
                try{
                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String fileName = df1.format(new Date());
                    String filePath = fileName + "_" + num + "_Mouse.xls";
                    file = new File(filePath);
                    WritableWorkbook workbook = Workbook.createWorkbook(file);
                    WritableSheet writableSheet = workbook.createSheet("sheet0", 0);
                    Label label = null;
                    for(int i = 0; i < title.length; i ++){
                        label = new Label(i, 0, title[i]);
                        writableSheet.addCell(label);
                    }
                    workbook.write();
                    workbook.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
                new Thread(new MouseHook(onOff , num, file)).start();
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
