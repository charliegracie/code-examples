
public class PrimitiveMathTest {
	private int _warmup;
	private int _loopCount;
	private long _iterationCount;

	public PrimitiveMathTest(int warmup, int loopCount, long iterationCount) {
		_warmup = warmup;
		_loopCount = loopCount;
		_iterationCount = iterationCount;
	}

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Usage: java PrimitiveMathTest <warmup> <loopCount> <iterationCount>");
		}
		
		int warmup = Integer.parseInt(args[0]);
		int loopCount = Integer.parseInt(args[1]);
		long iterationCount = Long.parseLong(args[2]);
		
		PrimitiveMathTest test = new PrimitiveMathTest(warmup, loopCount, iterationCount);
		
		test.run();
	}
	
	private void run() {
		long start = System.currentTimeMillis();
		double doubleResult = testDoubleMath(_warmup, _loopCount, _iterationCount);
		long end = System.currentTimeMillis();
		System.out.println("Test of doube math took " + (end-start) + "ms to complete with " + doubleResult + " result");
		
		System.gc();
		System.gc();
		
		start = System.currentTimeMillis();
		float floatResult = testFloatMath(_warmup, _loopCount, _iterationCount);
		end = System.currentTimeMillis();
		System.out.println("Test of float math took " + (end-start) + "ms to complete with " + floatResult + " result");
		
		System.gc();
		System.gc();
		
		start = System.currentTimeMillis();
		long longResult = testLongMath(_warmup, _loopCount, _iterationCount);
		end = System.currentTimeMillis();
		System.out.println("Test of long math took " + (end-start) + "ms to complete with " + longResult + " result");
		
		System.gc();
		System.gc();
		
		start = System.currentTimeMillis();
		int intResult = testIntMath(_warmup, _loopCount, _iterationCount);
		end = System.currentTimeMillis();
		System.out.println("Test of int math took " + (end-start) + "ms to complete with " + intResult + " result");
	}

	public double testDoubleMath(int warmup, int loopCount, long iterationCount) {
		loopingDoDoubleMath(warmup, iterationCount);
		
		return loopingDoDoubleMath(loopCount, iterationCount);
	}

	public double loopingDoDoubleMath(int loopCount, long iterationCount) {
		double result = 0;
		for (int i = 0; i < loopCount; i++) {
			result += doDoubleMath(iterationCount);
		}
		return result;
	}
	
	public double doDoubleMath(long iterationCount) {
		double result = 1d;
		double end = (double)iterationCount;
		for (double f = 0d; f < end; f++) {
			result += f / (result + 1);
		}
		return result;
	}
	
	private float testFloatMath(int warmup, int loopCount, long iterationCount) {
		loopingDoFloatMath(warmup, iterationCount);
		
		return loopingDoFloatMath(loopCount, iterationCount);
	}

	private float loopingDoFloatMath(int loopCount, long iterationCount) {
		float result = 0;
		for (int i = 0; i < loopCount; i++) {
			result += doFloatMath(iterationCount);
		}
		return result;
	}

	private float doFloatMath(long iterationCount) {
		float result = 1f;
		float end = (float)iterationCount;
		for (float f = 0f; f < end; f++) {
			result += f / (result + 1);
		}
		return result;
	}
	
	private long testLongMath(int warmup, int loopCount, long iterationCount) {
		loopingDoLongMath(warmup, iterationCount);
		
		return loopingDoLongMath(loopCount, iterationCount);
	}

	private long loopingDoLongMath(int loopCount, long iterationCount) {
		long result = 0;
		for (int i = 0; i < loopCount; i++) {
			result += doLongMath(iterationCount);
		}
		return result;
	}

	private long doLongMath(long iterationCount) {
		long result = 1l;
		long end = (long)iterationCount;
		for (long f = 0l; f < end; f++) {
			result += f / (result + 1);
		}
		return result;
	}
	
	private int testIntMath(int warmup, int loopCount, long iterationCount) {
		loopingDoIntMath(warmup, iterationCount);
		
		return loopingDoIntMath(loopCount, iterationCount);
	}

	private int loopingDoIntMath(int loopCount, long iterationCount) {
		int result = 0;
		for (int i = 0; i < loopCount; i++) {
			result += doIntMath(iterationCount);
		}
		return result;
	}

	private int doIntMath(long iterationCount) {
		int result = 1;
		int end = (int)iterationCount;
		for (int f = 0; f < end; f++) {
			result += f / (result + 1);
		}
		return result;
	}
	
}
