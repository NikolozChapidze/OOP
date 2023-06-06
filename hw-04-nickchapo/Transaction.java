// Transaction.java
/*
 (provided code)
 Transaction is just a dumb struct to hold
 one transaction. Supports toString.
*/
public class Transaction {
	private final int from;
	private final int to;
	private final int amount;

	public Transaction(int from, int to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public String toString() {
		return("from:" + from + " to:" + to + " amt:" + amount);
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Transaction
				&& ((Transaction) obj).from == this.from
				&& ((Transaction) obj).to == this.to
				&& ((Transaction) obj).amount == this.amount;
	}
}
