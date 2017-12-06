package forkJoin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
/**
 * �ֶ���֮�������
 * �ֶ���֮������Ч���������ݣ������ݷֳ����ν��д���Ȼ��������������ý����
 * ע�⣺������ֲ��̫�һֱ�ò������أ�1�߳�����̫�࣬ϵͳ���������½���2ջ�����
 * RecursiveTask<Long>����V���͡�RecursiveAction�޷������ͣ����̳�ForkJoinTask��
 * @author wsz
 * @date 2017��12��6��
 */
public class ForkJoinDemo extends RecursiveTask<Long>{
	private static final long serialVersionUID = 1L;
	//�ٽ�ֵ
	private static final int THRESHOLD = 1000;
	private long start ;
	private long end;
	
	public ForkJoinDemo(long start, long end) {
		this.start = start;
		this.end   = end;
	}
	
	//��Ҫʵ�ּ̳еĳ��󷽷�
	@Override
	protected Long compute() {
		long sum = 0L;
		boolean canCompute = (end - start) < THRESHOLD;//�ж��Ƿ�ɷָ�
		if(canCompute) {//С�ڿɷָ���ޣ�ֱ�ӽ��м���
			for(long i = start; i<end ; i++) {
				sum +=i;
			}
		}else {//���зָ�
			long step = (start+end)/100;//С��������
			ArrayList<ForkJoinDemo> subTasks = new ArrayList<ForkJoinDemo>();
			long pos = start;
			for (int i = 0; i < 100; i++) {
				long lastOne = pos+step;//��ǰ���������
				if(lastOne > end) //�������
					lastOne = end;
				ForkJoinDemo subTask = new ForkJoinDemo(pos, lastOne);
				subTasks.add(subTask);
				subTask.fork();//fork�󽫿���һ�����̣߳���Ҫע������
				pos += step+1;//������һ��
			}
			for (ForkJoinDemo forkJoinDemo : subTasks) {
				sum += forkJoinDemo.join();
			}
		}
		return sum;
	}
	
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();//ʹ���̳߳أ�fork�󲻻����������̣߳����ڽ�ʡϵͳ��Դ
		ForkJoinDemo task = new ForkJoinDemo(0L,5000L);
		ForkJoinTask<Long> result = forkJoinPool.submit(task);
		Long res;
		try {
			res = result.get();//������û�����������еȴ�
			System.out.println(res);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		List<String> synchronizedList = Collections.synchronizedList(new ArrayList<String>());
		Map<String, Object> synchronizedMap = Collections.synchronizedMap(new HashMap<String,Object>());
	}
}
