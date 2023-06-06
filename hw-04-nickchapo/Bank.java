// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.Semaphore;

public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts

	public static final int ACCOUNTBALANCE = 1000;

	public static final int TRANSACTOINSCAPACITY = 100;

	private final Transaction nullTrans = new Transaction(-1,0,0);

	private CountDownLatch countDownLatch;

	private ArrayBlockingQueue<Transaction> transactions;

	private ArrayList<Account> accounts;


	public Bank(int numWorkers) {
		countDownLatch = new CountDownLatch(numWorkers);
		accounts = new ArrayList<>();
		for (int i = 0; i < ACCOUNTS; i++) {
			accounts.add(new Account(this,i,ACCOUNTBALANCE));
		}

		transactions = new ArrayBlockingQueue<>(TRANSACTOINSCAPACITY);
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}
	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);

			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;

				tokenizer.nextToken();
				int to = (int)tokenizer.nval;

				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;

				// Use the from/to/amount
				transactions.put(new Transaction(from,to,amount));
			}
			reader.close();

		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(String file, int numWorkers) {
		for (int i = 0; i < numWorkers; i++) {
			new Worker().start();
		}
		readFile(file);

		for (int i = 0; i < numWorkers; i++) {
			try {
				transactions.put(nullTrans);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}



	/*
	 Looks at commandline args and calls Bank processing.
	*/
	public static void main(String[] args) {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}

		String file = args[0];

		int numWorkers = 1;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
		}

		Bank bank = new Bank(numWorkers);

		bank.processFile(file,numWorkers);
		try {
			bank.getCountDownLatch().await();

			System.out.println("Every transaction completed!");
			bank.getAccounts().forEach(System.out::println);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}



	public class Worker extends Thread {
		@Override
		public void run() {
			while (true){
				try {
					Transaction transaction = transactions.take();

					if(transaction.equals(nullTrans)) break;

					accounts.get(transaction.getFrom()).makeTransaction(-transaction.getAmount());
					accounts.get(transaction.getTo()).makeTransaction(transaction.getAmount());

				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			countDownLatch.countDown();
		}
	}
}

