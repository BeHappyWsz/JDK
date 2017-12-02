package reentrantLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * �ź���
 * @author wsz
 * @date 2017��12��2��
 */
public class SemaphoreTest implements Runnable{
	//�ͺ���׼�������Ƿ�ƽ��;����Ϊ3����ÿ3���߳�һ���ӡ��Ϣ
	final Semaphore semp = new Semaphore(3, false);
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(20);//��������20���̵߳��̳߳�
		SemaphoreTest demo = new SemaphoreTest();
		for(int i=0 ;i <50 ;i++) {
			exec.submit(demo);
		}
	}

	@Override
	public void run() {
		try {
			semp.acquire(); //���Ի��һ��׼�����ɣ����޷���ý��ȴ���ֱ�����һ����ɻ��ߵ�ǰ�̱߳��ж�
			Thread.sleep(2000);//ģ���ʱ
			System.out.println(Thread.currentThread().getId()+":ok ok");
			semp.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
