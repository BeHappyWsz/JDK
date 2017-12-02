package reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁之中断锁请求
 * 1.提供死锁环境
 * 2.结束死锁
 * 3.被中断线程无法完成任务
 * @author wsz
 * @date 2017年12月2日
 */
public class IntLock implements Runnable{
	
	public static ReentrantLock lock1 = new ReentrantLock();//重入锁
	public static ReentrantLock lock2 = new ReentrantLock();//重入锁
	//控制加锁顺序，用于创造死锁
	private int lock;	

	public IntLock(int lock) {
		super();
		this.lock = lock;
	}

	public static void main(String[] args) throws InterruptedException {
		IntLock intLock1 = new IntLock(1);
		IntLock intLock2 = new IntLock(2);
		Thread t1 = new Thread(intLock1);//t1先占有锁lock1，再请求lock2
		Thread t2 = new Thread(intLock2);//t2先占有lock2，再请求lock1
		t1.start();t2.start();//开启后形成死锁
		Thread.sleep(2000);
		t2.interrupt();//中断t2，t1将执行结束，t2放弃并结束
	}

	@Override
	public void run() {
		try {
			if(lock == 1) {                                
				lock1.lockInterruptibly();//在等待锁时可以响应中断
				try {
					Thread.sleep(1000);
				}catch (InterruptedException e) {}
				lock2.lockInterruptibly();//在等待锁时可以响应中断
			}else {
				lock2.lockInterruptibly();//在等待锁时可以响应中断
				try {
					Thread.sleep(1000);
				}catch (InterruptedException e) {}
				lock1.lockInterruptibly();//在等待锁时可以响应中断
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			if(lock1.isHeldByCurrentThread()) {//Queries if this lock is held by the current thread. 
				lock1.unlock();
			}
			if(lock2.isHeldByCurrentThread()) {//判断当前线程是否拥有该锁
				lock2.unlock();
			}
			System.out.println(Thread.currentThread().getId()+"线程退出");
		}
	}

}
