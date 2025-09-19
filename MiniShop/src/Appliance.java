public class Appliance extends Product{

    public Appliance(String name, double price){
        super (name, price);
    }

    public String getDescription(){
        return "Electrodomestico " + getName() + " Con un precio de " + getPrice() + "\n";
    }



}
