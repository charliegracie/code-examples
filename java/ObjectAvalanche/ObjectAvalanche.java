
public class ObjectAvalanche {
	public static int _threadCount;
	public static long _iterationCount;
	public static long _warmupCount;
	public static Object[] _storage;

	public static void main(String[] args) throws InterruptedException {
		if (args.length != 3) {
			System.out.println("java ObjectAvalanche <threadCount> <warmupCount> <iterationCount>");
			return;
		}

		ObjectAvalanche._threadCount = Integer.parseInt(args[0]);
		ObjectAvalanche._warmupCount = Long.parseLong(args[1]);
		ObjectAvalanche._iterationCount = Long.parseLong(args[2]);
		
		ObjectAvalanche._storage = new Object[4];
		
		Thread warmup = new ObjectAllocator(ObjectAvalanche._warmupCount, "WarmUpThread");
		
		System.out.println("Warming up ObjectAvalanche with 1 thread iterating " + ObjectAvalanche._warmupCount + " times creating temporary objects");
		
		warmup.start();
		warmup.join();
		
		System.out.println("Warmup completed");

		System.gc();
		System.gc();
		
		Thread[] threads = new Thread[ObjectAvalanche._threadCount];
		
		for (int i = 0; i < ObjectAvalanche._threadCount; i++) {
			threads[i] = new ObjectAllocator(ObjectAvalanche._iterationCount, "ObjectAllocator"+i);
		}
		
		System.out.println("Running ObjectAvalanche with " + ObjectAvalanche._threadCount + " thread(s) iterating " + ObjectAvalanche._iterationCount + " times creating temporary objects");
		
		long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < ObjectAvalanche._threadCount; i++) {
			threads[i].start();
		}
		
		for (int i = 0; i < ObjectAvalanche._threadCount; i++) {
			threads[i].join();
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("ObjectAvalanche took " + (endTime - startTime) + "ms to complete");
	}
	

	public static class ObjectAllocator extends Thread {
		private long _iterationCount;

		public ObjectAllocator(long iterationCount, String name) {
			super(name);
			_iterationCount = iterationCount;
		}

		public void run() {
			for (long f = 0l; f < _iterationCount; f++) {
				ObjectAvalanche._storage[0] = new Double(0d);
				ObjectAvalanche._storage[1] = new Double(1d);
				ObjectAvalanche._storage[2] = new Double(2d);
				ObjectAvalanche._storage[3] = new Double(3d);
			}
		}
	}
}
