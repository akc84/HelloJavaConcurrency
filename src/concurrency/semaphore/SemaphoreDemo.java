package concurrency.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreDemo {
	
	public static void main(String[] args)
	{
		Semaphore semaphore = new Semaphore(1);
		
		ExecutorService exec= Executors.newFixedThreadPool(20);
		IntStream.rangeClosed(1, 10).forEach((p)->{
			exec.execute(new RunnableImpl(semaphore));
		});
	}

}

class RunnableImpl implements Runnable{

	private Semaphore semaphore;
	
	RunnableImpl(Semaphore semaphore){
		this.semaphore = semaphore;
	}
	
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println("STARTED Run method for Thread::"+name);
		try {
			semaphore.acquire();
			System.out.println("Acquired Semaphore lock by Thread::"+name);
			TimeUnit.SECONDS.sleep(10);
			semaphore.release();
			System.out.println("Released Semaphore lock by Thread::"+name);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		System.out.println("ENDED Run method for Thread::"+name);
	}
	
}