package readWriteLock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ��д������
 * @author wsz
 * @date 2017��12��5��
 */
public class ReadWriteLockDemo {
	//������
	private static Lock lock = new ReentrantLock();
	
	//��д��
	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private static Lock readLock = readWriteLock.readLock();
	private static Lock writeLock = readWriteLock.writeLock();
	//���Ա���
	private int value;
	
	//������
	public Object handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);//ģ���ʱ
			return value;
		}finally {
			lock.unlock();
		}
		
	}
	
	//д����
	public void handleWrite(Lock lock,int index) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);//ģ���ʱ
			this.value = index;
		}finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final ReadWriteLockDemo demo = new ReadWriteLockDemo();
		Runnable read = new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(demo.handleRead(readLock));
//					System.out.println(demo.handleRead(lock)); ������
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Runnable write = new Runnable() {
			@Override
			public void run() {
				try {
					demo.handleWrite(writeLock, new Random().nextInt());
//					demo.handleWrite(lock, new Random().nextInt()); ������
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		for(int i = 0; i< 20; i++) {
			new Thread(read).start();//���̲߳���
		}
		for(int i = 0; i< 5; i++) {
			new Thread(write).start();//д�̴߳���
		}
	}
}
