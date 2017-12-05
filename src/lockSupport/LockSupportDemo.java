package lockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport����
 * ��Ȼ�޷���֤unpark()����������park()����֮��
 * LockSupportʹ�������ź����Ļ��ơ���Ϊÿһ���߳��ṩһ����ɣ�
 * �����ɿ��ã�park()�������������أ������������ɣ�����ɱ�Ϊ�����ã�
 * �����ɲ����ã��ͻ�������unpark()ʹ��һ����ɱ�Ϊ���ã��������ۼӣ�
 * @author wsz
 * @date 2017��12��5��
 */
public class LockSupportDemo {

	public static Object u = new Object();
	static ChangeObjectThread t1  = new ChangeObjectThread("t1");
	static ChangeObjectThread t2  = new ChangeObjectThread("t2");
	
	public static class ChangeObjectThread extends Thread{
		
		public ChangeObjectThread(String name) {
			super.setName(name);
		}
		
		@Override
		public void run() {
			synchronized(u) {
				System.out.println("in "+getName());
				LockSupport.park();
			}
			
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		t1.start();
		Thread.sleep(1000);
		t2.start();
		LockSupport.unpark(t1);//����������״̬��ΪWAITING
		LockSupport.unpark(t2);//����������״̬��ΪWAITING
		t1.join();
		t2.join();
	}

}
