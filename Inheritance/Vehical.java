import java.time.Year;

class vehicle{
    
    protected String brand;
    protected String model;
    protected int year;
    protected double basePrice;

    
    vehicle(String brand, String model, int year, double basePrice) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.basePrice = basePrice;
    }

    
    void displayInfo() {
        System.out.println("Vehicle Information:");
        System.out.println("Brand:" + brand);
        System.out.println("Model:" + model);
        System.out.println("year: "+ year);
        System.out.println("Base Price:$" + basePrice);
    }

    
    double calculateResaleValue() {
        int currentYear = Year.now().getValue();
        int age = currentYear - year;
        double resaleValue = basePrice * Math.pow(0.85, age);
        return resaleValue;
    }

    
    public static void main(String[] args) {
        vehicle myvehicle = new vehicle("Mitsubishi", "Pajero", 2009, 270000);
        myvehicle.displayInfo();
        System.out.printf("Resale Value: $%.2f\n", myvehicle.calculateResaleValue());
    }
}