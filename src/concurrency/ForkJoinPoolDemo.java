package concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolDemo {
	 public static void main(String[] args) throws InterruptedException, ExecutionException
	 {
		ForkJoinPool pool = new ForkJoinPool(2);
		Long result = pool.submit(new FibonacciRecursiveTask(11l)).get();
		System.out.println(result);
	 
	 }
}

class FibonacciRecursiveTask extends RecursiveTask<Long>
{
	private static final long serialVersionUID = -7709228558652564109L;
	
	private Long index;
	
	FibonacciRecursiveTask(Long index)
	{
		this.index = index;
	}

	@Override
	protected Long compute() {
		if(index <= 1)
			return 0l;
		else if(index == 2)
			return 1l;

		FibonacciRecursiveTask subTask1 = new FibonacciRecursiveTask(index-1);
		FibonacciRecursiveTask subTask2 = new FibonacciRecursiveTask(index-2);
		subTask1.fork();
		subTask2.fork();
		return subTask1.join()+subTask2.join();

	}
	
}
