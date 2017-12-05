package counDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ����ʱ��
 * @author wsz
 * @date 2017��12��5��
 */
public class CountDownLatchDemo implements Runnable{

	static final CountDownLatch cdl = new CountDownLatch(10);//����Ϊ��������
	static final CountDownLatchDemo demo = new CountDownLatchDemo();

	@Override
	public void run() {
		try {
			Thread.sleep(new Random().nextInt(10)*1000);
			System.out.println("ok");
			cdl.countDown();//���һ���̣߳�����-1
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for(int i = 0; i< 15;i ++) {//�̳߳�����<10����һֱ�ȴ���
									//�̳߳�����>=10 ����ʱ��ӡall over�������Ĵ�����������ӡok
			pool.submit(demo);
		}
		cdl.await();//���߳���CountDownLatch�ϵȴ���10�ξ�����ɺ����̲߳��ܼ���ִ��
		System.out.println("all over");
		cdl.countDown();
	}
}
