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
 * 分而治之数列求和
 * 分而治之可以有效处理量数据，把数据分成批次进行处理，然后再整合起来获得结果。
 * 注意：如果划分层次太深，一直得不到返回，1线程数量太多，系统性能严重下降；2栈溢出。
 * RecursiveTask<Long>返回V类型、RecursiveAction无返回类型；均继承ForkJoinTask。
 * @author wsz
 * @date 2017年12月6日
 */
public class ForkJoinDemo extends RecursiveTask<Long>{
	private static final long serialVersionUID = 1L;
	//临界值
	private static final int THRESHOLD = 1000;
	private long start ;
	private long end;
	
	public ForkJoinDemo(long start, long end) {
		this.start = start;
		this.end   = end;
	}
	
	//需要实现继承的抽象方法
	@Override
	protected Long compute() {
		long sum = 0L;
		boolean canCompute = (end - start) < THRESHOLD;//判断是否可分割
		if(canCompute) {//小于可分割界限，直接进行计算
			for(long i = start; i<end ; i++) {
				sum +=i;
			}
		}else {//进行分割
			long step = (start+end)/100;//小任务组数
			ArrayList<ForkJoinDemo> subTasks = new ArrayList<ForkJoinDemo>();
			long pos = start;
			for (int i = 0; i < 100; i++) {
				long lastOne = pos+step;//当前组任务界限
				if(lastOne > end) //到达最后
					lastOne = end;
				ForkJoinDemo subTask = new ForkJoinDemo(pos, lastOne);
				subTasks.add(subTask);
				subTask.fork();//fork后将开启一个新线程，需要注意性能
				pos += step+1;//进行下一组
			}
			for (ForkJoinDemo forkJoinDemo : subTasks) {
				sum += forkJoinDemo.join();
			}
		}
		return sum;
	}
	
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();//使用线程池，fork后不会立即开启线程，用于节省系统资源
		ForkJoinDemo task = new ForkJoinDemo(0L,5000L);
		ForkJoinTask<Long> result = forkJoinPool.submit(task);
		Long res;
		try {
			res = result.get();//若任务还没结束，将进行等待
			System.out.println(res);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		List<String> synchronizedList = Collections.synchronizedList(new ArrayList<String>());
		Map<String, Object> synchronizedMap = Collections.synchronizedMap(new HashMap<String,Object>());
	}
}
