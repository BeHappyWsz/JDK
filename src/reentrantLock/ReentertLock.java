package reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 * @author wsz
 * @date 2017年12月2日
 */
public class ReentertLock implements Runnable{
	public static ReentrantLock lock = new ReentrantLock();//重入锁
	public static long count =0L;
	
	public static void main(String[] args) throws InterruptedException {
		ReentertLock reentertLock = new ReentertLock();
		Thread t1 = new Thread(reentertLock);//接口构造函数
		Thread t2 = new Thread(reentertLock);
		t1.start();t2.start();
		t1.join();t2.join();
		System.out.println(count);
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			lock.lock();//加锁
			count++;
			lock.unlock();//解锁,次数必须与加锁次数一致，否则将导致
		}
	}
}
