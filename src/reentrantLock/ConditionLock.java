package reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ��������Condition
 * @author wsz
 * @date 2017��12��2��
 */
public class ConditionLock implements Runnable{
	public static ReentrantLock lock = new ReentrantLock();//������
	public static Condition condition = lock.newCondition();//����ص�Conditionʵ��
	
	@Override
	public void run() {
		try {
			lock.lock();
			condition.await();//�ȴ����ͷ���
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
		condition.signal();//���ѵȴ���Condition�ϵ��߳�
		lock.unlock();//����֮�󣬽���ӡ�������޷�ִ�С�
	}
}
