package getInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.jna.Structure;
import com.sun.jna.examples.win32.Kernel32;
import com.sun.jna.examples.win32.User32;
import com.sun.jna.examples.win32.User32.HHOOK;
import com.sun.jna.examples.win32.User32.MSG;
import com.sun.jna.examples.win32.W32API.HMODULE;
import com.sun.jna.examples.win32.W32API.LRESULT;
import com.sun.jna.examples.win32.W32API.WPARAM;
import com.sun.jna.examples.win32.User32.HOOKPROC;

/**
 * @author ly
 * @date 2020-11-10
 * 全局监听鼠标点击事件并记录起始点坐标以及斜率
 * 后序加入excel文件写入，以便进行数据分析
 */
public class MouseHook implements Runnable {

    //鼠标事件编码
    public static final int WM_MOUSEMOVE = 512;
    public static final int WM_LBUTTONDOWN = 513;
    public static final int WM_LBUTTONUP = 514;
    public static final int WM_RBUTTONDOWN = 516;
    public static final int WM_RBUTTONUP = 517;
    public static final int WM_MBUTTONDOWN = 519;
    public static final int WM_MBUTTONUP = 520;


    private static HHOOK hhk;
    private static LowLevelMouseProc mouseHook;
    final static User32 LIB = User32.INSTANCE;
    private static boolean[] onOff = null;
    private static String test;

    private static double xStart;
    private static double yStart;

    private static double xEnd;
    private static double yEnd;

    public MouseHook(boolean[] onOff, String num) {
        this.onOff = onOff;
        this.test = num;
    }

    public interface LowLevelMouseProc extends HOOKPROC {
        LRESULT callback(int nCode, WPARAM wParam, MOUSEHOOKSTRUCT lParam);
    }

    public static class MOUSEHOOKSTRUCT extends Structure {
        public static class ByReference extends MOUSEHOOKSTRUCT implements
                Structure.ByReference {
        }

        ;
        public User32.POINT pt;
        public int wHitTestCode;
        public User32.ULONG_PTR dwExtraInfo;
    }

    @Override
    public void run() {
        HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        mouseHook = new LowLevelMouseProc() {
            @Override
            public LRESULT callback(int nCode, WPARAM wParam,
                                    MOUSEHOOKSTRUCT info) {
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fileName = df1.format(new Date());
                String time = df2.format(new Date());
                BufferedWriter bw1 = null;
                BufferedWriter bw2 = null;
                try {
                    bw1 = new BufferedWriter(new FileWriter(new File(
                            fileName + "_" + test + "_Mouse.txt"), true));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!onOff[0]) {
                    System.exit(0);
                }
                if (nCode >= 0) {
                    switch (wParam.intValue()) {
                        default:
                            break;
                            //左键按下  记录起点坐标
                        case MouseHook.WM_LBUTTONDOWN:
                            xStart = info.pt.x;
                            yStart = info.pt.y;
                            break;
                            //左键抬起，记录终点坐标
                        case MouseHook.WM_LBUTTONUP:
                            try {
                                xEnd = info.pt.x;
                                yEnd = info.pt.y;
                                //当前后两次坐标不等时才做记录
                                if (xStart != xEnd && yStart != yEnd) {
                                    double k = (yEnd - yStart) / (xEnd - xStart);
                                    System.out.println("=============down===============");
                                    System.out.println("left down   " + "x = " + xStart + "    " + "y = " + yStart);
                                    System.out.println("left up   " + "x = " + xEnd + "    " + "y = " + yEnd);
                                    System.out.println("k= " + k);
                                    System.out.println("=========up==========");
                                    bw1.write(time + "  ####left down " + "x=" + xStart
                                            + " y=" + yStart + "\r\n");
                                    bw1.write(time + "  ####left up  " + "x=" + xEnd
                                            + " y=" + yEnd + "\r\n");
                                    bw1.write(time + " #### k = " + k + "\r\n");
                                    bw1.write("=============up===============" + "\r\n");
                                    bw1.flush();
                                }
//                                double k = (yEnd - yStart) / (xEnd - xStart);
//                                System.out.println("left up   " + "x = " + xEnd + "    " + "y = " + yEnd);
//                                System.out.println("k= " + k);
//                                System.out.println("=========up==========");
//                                bw1.write(time + "  ####left up  " + "x=" + xEnd
//                                        + " y=" + yEnd + "\r\n");
//                                bw1.write(time + " #### k = " + k + "\r\n");
//                                bw1.write("=============up===============" + "\r\n");
//                                bw1.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
                return LIB
                        .CallNextHookEx(hhk, nCode, wParam, info.getPointer());
            }
        };
        hhk = LIB.SetWindowsHookEx(User32.WH_MOUSE_LL, mouseHook, hMod, 0);
        int result;
        MSG msg = new MSG();
        while ((result = LIB.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                System.err.println("error in get message");
                break;
            } else {
                System.err.println("got message");
                LIB.TranslateMessage(msg);
                LIB.DispatchMessage(msg);
            }
        }
        LIB.UnhookWindowsHookEx(hhk);
    }

}