package concurrency;

import static concurrency.Util.callableImpl;
import static concurrency.Util.lineSeparator;
import static concurrency.Util.runnableImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class ExecutorServiceDemo {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException
	 {	
		ExecutorService exec = Executors.newFixedThreadPool(40);
		demoSubmit(exec);
		System.out.println(lineSeparator);
		List<Callable<Integer>> input = new ArrayList<>();
		IntStream.rangeClosed(1, 7).forEach((p) -> {
			input.add(callableImpl);
		});
		demoInvokeAll(exec, input);
		System.out.println(lineSeparator);
		System.out.println("Invoke Any:::" + exec.invokeAny(input));
		exec.shutdownNow();
        
	 }

	private static void demoInvokeAll(ExecutorService exec, List<Callable<Integer>> input) throws InterruptedException {
		List<Future<Integer>> allFutures = exec.invokeAll(input);
		allFutures.stream().forEach((p) -> {
			try {
				System.out.println("Returned from InvokeAll:::" + p.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
	}

	private static void demoSubmit(ExecutorService exec) {
		IntStream.rangeClosed(1, 5).forEach((p) -> {
			Future<?> response = exec.submit(runnableImpl);
			try {			
				System.out.println("Response:: " + response.get());//For Runnable, null will be returned
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		});
	}	 	
	
}
