package model;

public class Bill {
	private int id;
    private String month;
    private PhoneNumber phoneNumber;
    private int calls;
    private int dataUsed;
    private boolean payed;
    private float total;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCalls() {
        return calls;
    }

    public void setCalls(int calls) {
        this.calls = calls;
    }

    public int getDataUsed() {
        return dataUsed;
    }

    public void setDataUsed(int dataUsed) {
        this.dataUsed = dataUsed;
    }

	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
	public Bill(int id, String month, PhoneNumber phoneNumber, int calls, int dataUsed, boolean payed, float total) {
		this.id = id;
		this.month = month;
		this.phoneNumber = phoneNumber;
		this.calls = calls;
		this.dataUsed = dataUsed;
		this.payed = payed;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    
}
