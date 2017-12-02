package reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁与Condition
 * @author wsz
 * @date 2017年12月2日
 */
public class ConditionLock implements Runnable{
	public static ReentrantLock lock = new ReentrantLock();//重入锁
	public static Condition condition = lock.newCondition();//绑定相关的Condition实例
	
	@Override
	public void run() {
		try {
			lock.lock();
			condition.await();//等待并释放锁
			System.out.println("aaa");
		}catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		ConditionLock cl = new ConditionLock();
		Thread t1 = new Thread(cl);
		t1.start();
		Thread.sleep(2000);
		lock.lock();
		condition.signal();//唤醒等待在Condition上的线程
		lock.unlock();//解锁之后，将打印，否则无法执行。
	}
}
