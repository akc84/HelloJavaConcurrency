package concurrency;
import static concurrency.Util.*;

import java.util.stream.IntStream;


public class SimpleThreadDemo {

 public static void main(String[] args)
 {	 
	 IntStream.rangeClosed(1, 7).forEach((i) -> {
		 Thread t = new Thread(runnableImpl," Thread-"+i);
		 t.start();
	 });
 }
}
