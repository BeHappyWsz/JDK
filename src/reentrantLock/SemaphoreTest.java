package reentrantLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * @author wsz
 * @date 2017年12月2日
 */
public class SemaphoreTest implements Runnable{
	//型号量准入数、是否公平锁;设置为3将以每3个线程一组打印信息
	final Semaphore semp = new Semaphore(3, false);
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(20);//开启保护20个线程的线程池
		SemaphoreTest demo = new SemaphoreTest();
		for(int i=0 ;i <50 ;i++) {
			exec.submit(demo);
		}
	}

	@Override
	public void run() {
		try {
			semp.acquire(); //尝试获得一个准入的许可，如无法获得将等待，直到获得一个许可或者当前线程被中断
			Thread.sleep(2000);//模拟耗时
			System.out.println(Thread.currentThread().getId()+":ok ok");
			semp.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
