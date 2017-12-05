package scheduledTreadPool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务
 * 如果任务程序本身抛出异常，后续所有执行都将被中断
 * @author wsz
 * @date 2017年12月5日
 */
public class ScheduledExecutorServiceDemo {

	public static void main(String[] args) {

		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
		/**
		 * scheduleAtFixedRate保证之前的任务已完成
		 * 当操作时间>调度周期2s时，后一个任务会立即执行
		 */
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);//模拟操作执行1s
					System.out.println(new Random().nextInt());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 3, 2, TimeUnit.SECONDS);//3初始延迟，每2秒执行一次
	}
}
