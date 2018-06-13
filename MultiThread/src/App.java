
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

	public static List<Integer> idList = new ArrayList<Integer>();
	public static int counter = 1;

	public static class Elevator {
		private BlockingQueue<Person> elevator;

		Elevator(int definedSize) {
			elevator = new ArrayBlockingQueue<>(definedSize);
		}

		public void enter(Person p) {
			try {
				elevator.put(p);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public int size() {
			return elevator.size();
		}
		
		public Person exit() {
			Person out = null;
			try {
				out = elevator.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return out;
		}
	}

	private static Elevator e = new Elevator(10);

	public static class Person {
		String action = "";
		int id = 0;

		Person(String action, int id) {
			this.action = action;
			this.id = id;

		}
	}

	public static void main(String ... args) throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				while (true) {
					e.enter(new Person("enter", counter++));
				}

			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					exit();
				} catch (InterruptedException ignored) {
				}
			}
		});
		t1.start();
		t2.start();

	}

	private static void exit() throws InterruptedException {
		Random random = new Random();
		while (true) {
			Thread.sleep(100);
			if (random.nextInt(10) == 0) {
				int value = e.exit().id;
				System.out.println("person " + value + " exit. Busy elevator size is: " + e.size());
			}
		}
	}
}