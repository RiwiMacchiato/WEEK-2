public class Appliance extends Product {

    Appliance(String name, double price) {
        super(name, price);
    }

    @Override
    String getDescription() {
        return "El producto es Electrodomestio";
    }

} 