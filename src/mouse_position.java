
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
public class mouse_position {
	
	public interface USER32 extends StdCallLibrary{
		USER32 INSTANCE = (USER32) Native.loadLibrary("user32", USER32.class);
		boolean GetCursorPos(int[] coor);
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] pointer = new int[2];
		 for(int i=0;i<5;i++){
			try {
				Thread.sleep(3000);
				}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		 USER32.INSTANCE.GetCursorPos(pointer);//��ȡ��ǰ���λ��
		 System.out.println(pointer[0]+"---"+pointer[1]);
		 }
	}
}
