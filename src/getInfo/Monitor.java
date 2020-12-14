package getInfo;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author YanL
 * @date 19:53 2020/11/9
 */
public class Monitor extends JFrame {

    private static String[] title = {"Number", "StartX", "StartY", "StartTime","EndX", "EndY", "EndTime", "distance", "Time","K", "形状", "移动方式"};

    private static File file;

    private static Thread thread;


    public Monitor()  {

        //Container container = getContentPane();

    }

    public static void main(String[] args)  {


        final JLabel testNumber = new JLabel("测试序号");
        //测试序号文本框
        final JTextField tf = new JTextField(12);

        final JLabel moveWay = new JLabel("移动方式");

        //按钮
        JButton b = new JButton("开始");
        JButton stop = new JButton("结束");

        JFrame jf = new JFrame("测试窗口");

        jf.setSize(250, 250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //显示面板
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        panel.add(testNumber);
        panel.add(tf);

        // 添加一个标签
        JLabel label = new JLabel("形        状");
        panel.add(label);

        // 需要选择的条目
        String[] listData = new String[]{"正方形", "圆形", "不规则", "竖", "横"};

        // 创建一个下拉列表框
        final JComboBox<String> comboBox = new JComboBox<String>(listData);
        comboBox.setPreferredSize(new Dimension(130, 25));

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
        panel.add(stop);
        jf.add(panel);
        jf.setVisible(true);


        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean [] onOff ={true};
                String num = tf.getText();
                String shape = (String)comboBox.getSelectedItem();
                String mode = (String)comboBoxWay.getSelectedItem();

                String[] params = new String[]{num, shape, mode};
                System.out.println(Arrays.toString(params));
                tf.setBackground(Color.CYAN);
                try{
                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String fileName = df1.format(new Date());
                    String filePath = fileName + "_" + num + "_Mouse.xls";
                    file = new File(filePath);
                    WritableSheet writableSheet;
                    WritableWorkbook workbook;
                    if(!file.exists()){
                        workbook = Workbook.createWorkbook(file);
                        writableSheet = workbook.createSheet("sheet0", 0);
                    }else {
                        Workbook workbook1 = Workbook.getWorkbook(file);
                        workbook = Workbook.createWorkbook(file, workbook1);
                        writableSheet = workbook.getSheet(0);
                    }
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
                thread = new Thread(new MouseHook(onOff, params, file));
                thread.start();
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread.suspend();
            }
        });
        /*
        Monitor frame = new Monitor();

        final JTextField tf = new JTextField();
        final JTextField tf2 = new JTextField();
        tf.setBounds(150, 20, 50, 20);
        tf2.setBounds(50, 20, 100, 20);

        JPanel content = new JPanel();

        JLabel label = new JLabel("证件类型");
        content.add(label);

        String[] listData = new String[]{"正方形","圆形","不规则","横","竖"};
        final JComboBox<String> comboBox = new JComboBox<>(listData);
        comboBox.setEditable(true);
        comboBox.setSelectedItem(0);
        content.add(comboBox);
        content.add(tf2);
        content.add(tf);
        frame.setContentPane(content);
        tf2.setText("testNumber");
        JButton b = new JButton("start");
        content.add(b);
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
//        frame.add(b);
//        frame.add(tf);
//        frame.add(tf2);
        frame.setSize(300, 250);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("MouseTrack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        */
    }
}
