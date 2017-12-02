package reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ������֮�ж�������
 * 1.�ṩ��������
 * 2.��������
 * 3.���ж��߳��޷��������
 * @author wsz
 * @date 2017��12��2��
 */
public class IntLock implements Runnable{
	
	public static ReentrantLock lock1 = new ReentrantLock();//������
	public static ReentrantLock lock2 = new ReentrantLock();//������
	//���Ƽ���˳�����ڴ�������
	private int lock;	

	public IntLock(int lock) {
		super();
		this.lock = lock;
	}

	public static void main(String[] args) throws InterruptedException {
		IntLock intLock1 = new IntLock(1);
		IntLock intLock2 = new IntLock(2);
		Thread t1 = new Thread(intLock1);//t1��ռ����lock1��������lock2
		Thread t2 = new Thread(intLock2);//t2��ռ��lock2��������lock1
		t1.start();t2.start();//�������γ�����
		Thread.sleep(2000);
		t2.interrupt();//�ж�t2��t1��ִ�н�����t2����������
	}

	@Override
	public void run() {
		try {
			if(lock == 1) {                                
				lock1.lockInterruptibly();//�ڵȴ���ʱ������Ӧ�ж�
				try {
					Thread.sleep(1000);
				}catch (InterruptedException e) {}
				lock2.lockInterruptibly();//�ڵȴ���ʱ������Ӧ�ж�
			}else {
				lock2.lockInterruptibly();//�ڵȴ���ʱ������Ӧ�ж�
				try {
					Thread.sleep(1000);
				}catch (InterruptedException e) {}
				lock1.lockInterruptibly();//�ڵȴ���ʱ������Ӧ�ж�
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			if(lock1.isHeldByCurrentThread()) {//Queries if this lock is held by the current thread. 
				lock1.unlock();
			}
			if(lock2.isHeldByCurrentThread()) {//�жϵ�ǰ�߳��Ƿ�ӵ�и���
				lock2.unlock();
			}
			System.out.println(Thread.currentThread().getId()+"�߳��˳�");
		}
	}

}
