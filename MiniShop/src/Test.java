public class Test {
    public static void main(String[] args) {

        Inventory inventario = new Inventory();

        Product papa = new Food("Papa", 3500);
        Product tv = new Appliance("Tv", 500000);
        Product manzana = new Food("Manzana", 2.50);
        Product nevera = new Appliance("Nevera", 800.00);
        Product pan = new Food("Pan", 1.20);

        System.out.println("Nombre del  alimento: " + papa.getName());
        System.out.println("Precio del alimento: " + papa.getPrice());
        System.out.println("Nombre del  electrodomestico: " + tv.getName());
        System.out.println("precio del electrodomestico: " + tv.getPrice());

        System.out.println(papa.getDescription());
        System.out.println(tv.getDescription());

        inventario.addProduct(papa, 320);
        inventario.addProduct(tv, 40);
        inventario.addProduct(manzana, 120);
        inventario.addProduct(nevera, 20);
        inventario.addProduct(pan, 300);

        for (Product p : inventario.getProducts() ) {
            System.out.printf("- " + p.getDescription());
        }

        System.out.println("Stock de manzanas " + inventario.getStock("Manzana"));
        System.out.println("Stock de Nevera " + inventario.getStock("Nevera"));
        System.out.println("Stock de Bananos " + inventario.getStock("Banana"));


        System.out.println("Existe Manzana? " + inventario.productExists("Manzana"));
        System.out.println("Total tipo de productos: "  + inventario.getTotalProductTypes());

        boolean Existe = inventario.addProduct(manzana, 30);
        System.out.println("Se pudo ingresar manzana? " + Existe);

        System.out.println(" --------  listo ------- ");
    }
}
