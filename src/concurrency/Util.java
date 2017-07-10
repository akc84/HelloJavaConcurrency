package concurrency;

import java.util.concurrent.Callable;

public class Util {

	 public static final String lineSeparator = "____________________________________________________________";
	 
	 public static Callable<Integer> callableImpl = ()->{
	 	 String name = Thread.currentThread().getName();
		 System.out.println("STARTING Callable : "+ name);
		 Integer returnVal = Integer.valueOf(name.substring(name.lastIndexOf("-")+1));
		 System.out.println("    ENDING Callable : "+ name + " returning "+returnVal);
		 return returnVal;		
	 	};	

	 public static Runnable runnableImpl = ()->{
		 	 String name = Thread.currentThread().getName();
			 System.out.println("STARTING thread : "+ name);
			 long factor = Long.parseLong(name.substring(name.lastIndexOf("-")+1));
			 long sleepTime = (factor)*1000l;
			 System.out.println(name+" sleeping for "+ sleepTime +"ms");
			 try {
				 
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException("InterruptedException");
			}
			 System.out.println("ENDING thread : "+ name);
	 };	 	
	 	
}
