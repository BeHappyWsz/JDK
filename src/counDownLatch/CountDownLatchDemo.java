package counDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时器
 * @author wsz
 * @date 2017年12月5日
 */
public class CountDownLatchDemo implements Runnable{

	static final CountDownLatch cdl = new CountDownLatch(10);//参数为计数个数
	static final CountDownLatchDemo demo = new CountDownLatchDemo();

	@Override
	public void run() {
		try {
			Thread.sleep(new Random().nextInt(10)*1000);
			System.out.println("ok");
			cdl.countDown();//完成一个线程，计数-1
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for(int i = 0; i< 15;i ++) {//线程池数量<10，将一直等待；
									//线程池数量>=10 将按时打印all over，超过的次数将继续打印ok
			pool.submit(demo);
		}
		cdl.await();//主线程在CountDownLatch上等待，10次均已完成后，主线程才能继续执行
		System.out.println("all over");
		cdl.countDown();
	}
}
