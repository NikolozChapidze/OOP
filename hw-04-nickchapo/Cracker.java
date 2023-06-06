// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.*;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();

	private CountDownLatch countDownLatch;

	private final int passwordLength;

	private final byte[] result;

	private final int workerNum;

	public Cracker(String targ, int length, int workerNum) {
		passwordLength	= length;
		result			= hexToArray(targ);
		countDownLatch = new CountDownLatch(workerNum);
		this.workerNum = workerNum;
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public void startCracking(){
		int lengthOfIntervals = CHARS.length / workerNum;
		for (int i = 0; i < workerNum; i++) {
			int end = (i+1) * lengthOfIntervals;
			if(i == workerNum-1){
				end = CHARS.length;
			}
			new Thread(new Worker(i*lengthOfIntervals,end)).start();
		}
	}


	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}

	public class Worker implements Runnable{

		private final int start;

		private final int end;

		private MessageDigest digest;

		public Worker(int start, int end) {
			this.start = start;
			this.end = end;
			digest = null;
			try {
				this.digest = MessageDigest.getInstance("SHA");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void run() {
			for (int i = start; i < end; i++) {
				wrapper(""+ CHARS[i]);
			}
			countDownLatch.countDown();
		}

		private void wrapper(String currentPass){
			if (currentPass.length() == passwordLength){
				digest.update(currentPass.getBytes());
				if (Arrays.equals(result, digest.digest())){
					System.out.println("Found real password : " + currentPass);
				}
				return;
			}
			for(final var nextChar : CHARS){
				wrapper(currentPass + nextChar);
			}
		}
	}
	
	
	public static void main(String[] args) {
		if (args.length == 1) {
			System.out.println(hexToString(generateHash(args[0])));
		}
		if (args.length < 2) {
			System.out.println("Args: target length [workers]");
			System.exit(1);
		}
		// args: targ len [num]
		String targ = args[0];
		int len = Integer.parseInt(args[1]);
		int num = 1;
		if (args.length>2) {
			num = Integer.parseInt(args[2]);
		}
		// a! 34800e15707fae815d7c90d49de44aca97e2d759
		// xyz 66b27417d37e024c46526c2f6d358a754fc552f3
		Cracker cracker = new Cracker(targ, len, num);
		cracker.startCracking();

		try {
			cracker.getCountDownLatch().await();
			System.out.println("Cracking finished!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static byte[] generateHash(String password) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA");
			messageDigest.update(password.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return messageDigest.digest();
	}
}
