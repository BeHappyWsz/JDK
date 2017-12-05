package readWriteLock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 * @author wsz
 * @date 2017年12月5日
 */
public class ReadWriteLockDemo {
	//重入锁
	private static Lock lock = new ReentrantLock();
	
	//读写锁
	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private static Lock readLock = readWriteLock.readLock();
	private static Lock writeLock = readWriteLock.writeLock();
	//测试变量
	private int value;
	
	//读操作
	public Object handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);//模拟耗时
			return value;
		}finally {
			lock.unlock();
		}
		
	}
	
	//写操作
	public void handleWrite(Lock lock,int index) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);//模拟耗时
			this.value = index;
		}finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final ReadWriteLockDemo demo = new ReadWriteLockDemo();
		Runnable read = new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(demo.handleRead(readLock));
//					System.out.println(demo.handleRead(lock)); 重入锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Runnable write = new Runnable() {
			@Override
			public void run() {
				try {
					demo.handleWrite(writeLock, new Random().nextInt());
//					demo.handleWrite(lock, new Random().nextInt()); 重入锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		for(int i = 0; i< 20; i++) {
			new Thread(read).start();//读线程并行
		}
		for(int i = 0; i< 5; i++) {
			new Thread(write).start();//写线程串行
		}
	}
}
