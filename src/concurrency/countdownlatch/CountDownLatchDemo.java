package concurrency.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


public class CountDownLatchDemo {
	public static void main(String[] args) throws InterruptedException
	{
		ExecutorService exec = Executors.newFixedThreadPool(5);
		CountDownLatch latch = new CountDownLatch(5);

		IntStream.rangeClosed(1,4).forEach((p) -> {
			exec.execute(new RunnableImpl(latch));
		});
		
		TimeUnit.SECONDS.sleep(10); 
		exec.execute(new RunnableImpl(latch));
		
		//latch once counted down does not automatically reset
		IntStream.rangeClosed(1,4).forEach((p) -> {
			exec.execute(new RunnableImpl(latch));
		});
		
		TimeUnit.SECONDS.sleep(10); 
		exec.execute(new RunnableImpl(latch));			
		
	}
}


class RunnableImpl implements Runnable{

	private CountDownLatch latch;
	
	RunnableImpl(CountDownLatch latch){
		this.latch = latch;
	}
	
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println("STARTED Run method for Thread::"+name);
		System.out.println("Calling Countdown on Latch by Thread::"+name);
		latch.countDown();
		System.out.println("Calling Await on Latch by Thread::"+name);
		try {
			latch.await(5,TimeUnit.SECONDS);//Timeout does not throw exception
			System.out.println("Finished waiting on Latch by Thread::"+name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("ENDED Run method for Thread::"+name);
	}
	
}