package reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ������
 * @author wsz
 * @date 2017��12��2��
 */
public class ReentertLock implements Runnable{
	public static ReentrantLock lock = new ReentrantLock();//������
	public static long count =0L;
	
	public static void main(String[] args) throws InterruptedException {
		ReentertLock reentertLock = new ReentertLock();
		Thread t1 = new Thread(reentertLock);//�ӿڹ��캯��
		Thread t2 = new Thread(reentertLock);
		t1.start();t2.start();
		t1.join();t2.join();
		System.out.println(count);
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			lock.lock();//����
			count++;
			lock.unlock();//����,�����������������һ�£����򽫵���
		}
	}
}
