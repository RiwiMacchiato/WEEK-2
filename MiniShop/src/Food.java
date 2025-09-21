public class Food extends Product {

    public Food(String name, double price) {
        super(name, price);
    }

    public String getDescription(){
        return "Alimento " + getName() + " Con un precio de " + getPrice() + "\n";
    }
}


