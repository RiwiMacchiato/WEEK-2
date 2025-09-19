public class Test {
    public static void main(String[] args) {

        Product food = new Food("Papa", 2000);
        Product appliance = new Appliance("Tv", 500000);

        System.out.println("Nombre del  alimento: " + food.getName());
        System.out.println("Precio del alimento: " + food.getPrice());
        System.out.println("Nombre del  electrodomestico: " + appliance.getName());
        System.out.println("precio del electrodomestico: " + appliance.getPrice());

        System.out.printf(food.getDescription());
        System.out.println(appliance.getDescription());

    }
}
