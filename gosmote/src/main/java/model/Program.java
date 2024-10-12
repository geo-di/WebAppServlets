package model;

public class Program {
	private int id;
	private String name;
    private int minutes;
    private int data;
    private float price;
    private float extraMinutesCharge;
    private float extraDataCharge;

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getExtraMinutesCharge() {
        return extraMinutesCharge;
    }

    public void setExtraMinutesCharge(float extraMinutesCharge) {
        this.extraMinutesCharge = extraMinutesCharge;
    }

    public float getExtraDataCharge() {
        return extraDataCharge;
    }

    public void setExtraDataCharge(float extraDataCharge) {
        this.extraDataCharge = extraDataCharge;
    }

    public Program(int id, String name,int minutes, int data, float price, float extraMinutesCharge, float extraDataCharge) {
        this.id = id;
    	this.name = name;
    	this.minutes = minutes;
        this.data = data;
        this.price = price;
        this.extraMinutesCharge = extraMinutesCharge;
        this.extraDataCharge = extraDataCharge;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
