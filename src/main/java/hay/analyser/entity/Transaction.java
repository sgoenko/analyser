package hay.analyser.entity;

import java.util.Date;

public class Transaction {
	private String id;
	private Date date;
	private Double amount;
	private String merchant;
	private String type;
	private String related;
	
	public Transaction() {
	}

	public Transaction(String id, Date date, Double amount, String merchant, String type, String related) {
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.merchant = merchant;
		this.type = type;
		this.related = related;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

}
