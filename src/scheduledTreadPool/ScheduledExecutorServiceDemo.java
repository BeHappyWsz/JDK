package scheduledTreadPool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ��ʱ����
 * �������������׳��쳣����������ִ�ж������ж�
 * @author wsz
 * @date 2017��12��5��
 */
public class ScheduledExecutorServiceDemo {

	public static void main(String[] args) {

		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
		/**
		 * scheduleAtFixedRate��֤֮ǰ�����������
		 * ������ʱ��>��������2sʱ����һ�����������ִ��
		 */
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);//ģ�����ִ��1s
					System.out.println(new Random().nextInt());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 3, 2, TimeUnit.SECONDS);//3��ʼ�ӳ٣�ÿ2��ִ��һ��
	}
}
