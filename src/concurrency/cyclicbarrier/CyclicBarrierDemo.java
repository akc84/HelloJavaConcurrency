package concurrency.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class CyclicBarrierDemo {

	public static void main(String[] args) throws InterruptedException {		
		ExecutorService exec = Executors.newFixedThreadPool(5);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
		IntStream.rangeClosed(1,4).forEach((p) -> {
			exec.execute(new RunnableImpl(cyclicBarrier));
		});
		//cyclicBarrier.reset(); BarrierBrokenException on all waiting threads
		TimeUnit.SECONDS.sleep(10); //Barrier will be broken by timeout in 9 seconds
		exec.execute(new RunnableImpl(cyclicBarrier));	//this will also fail as Barrier already broken
		
		if(cyclicBarrier.isBroken())
			cyclicBarrier.reset();
		
		IntStream.rangeClosed(1,4).forEach((p) -> {
			exec.execute(new RunnableImpl(cyclicBarrier));
		});
		TimeUnit.SECONDS.sleep(5);		
		exec.execute(new RunnableImpl(cyclicBarrier));	

	}

}

class RunnableImpl implements Runnable{

	private CyclicBarrier cyclicBarrier;
	
	RunnableImpl(CyclicBarrier cyclicBarrier){
		this.cyclicBarrier = cyclicBarrier;
	}
	
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println("STARTED Run method for Thread::"+name);
		try {
			System.out.println("Calling Await on cyclic barrier by Thread::"+name);
			cyclicBarrier.await(9,TimeUnit.SECONDS);
			System.out.println("Finished waiting on cyclic barrier by Thread::"+name);
		} catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
			e.printStackTrace();
			System.out.println("is Barrier Broken:::"+cyclicBarrier.isBroken());
		}
		System.out.println("ENDED Run method for Thread::"+name);
	}
	
}