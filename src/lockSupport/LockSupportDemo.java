package lockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport案例
 * 依然无法保证unpark()方法发生在park()方法之后。
 * LockSupport使用类似信号量的机制。它为每一个线程提供一个许可，
 * 如果许可可用，park()函数会立即返回，并消费这个许可（将许可变为不可用）
 * 如果许可不可用，就会阻塞，unpark()使得一个许可变为可用（但不可累加）
 * @author wsz
 * @date 2017年12月5日
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
		LockSupport.unpark(t1);//进行阻塞，状态变为WAITING
		LockSupport.unpark(t2);//进行阻塞，状态变为WAITING
		t1.join();
		t2.join();
	}

}
