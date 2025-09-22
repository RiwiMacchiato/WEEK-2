public class Food extends Product {

    Food(String name, double price) {
        super(name, price);
    }

    @Override
    String getDescription() {
        return "El producto es alimento";
    }

}
