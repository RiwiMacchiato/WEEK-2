import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.util.*;
public class main {
    // --------------------------------------------------------------------------------------------------------------------//

    private static ArrayList<Product> products = new ArrayList<>();
    private static HashMap<String, Integer> Stock = new HashMap<>();
    private static ArrayList<String> tickets = new ArrayList<>();
    private static double totalPurchases = 0;

    // --------------------------------------------------------------------------------------------------------------------//
    public static void addProduct(){
        String[] types= {"Alimento","Electrodomestico"};
        String type = (String) JOptionPane.showInputDialog(null,"Elige una opcion","Tipos",JOptionPane.QUESTION_MESSAGE,null,types,types[0]);

        if (type == null) return;

        String name = JOptionPane.showInputDialog(null,"Nombre del producto: ","Agregar Producto",JOptionPane.QUESTION_MESSAGE);
        if (name.isEmpty() || name == null){
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio, intentalo de nuevo.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Stock.containsKey(name)) {
            JOptionPane.showMessageDialog(null, "El producto ya existe en el inventario", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        int stock;

        try {
            price = Double.parseDouble(JOptionPane.showInputDialog(null,"Precio del producto: ","Agregar Producto",JOptionPane.QUESTION_MESSAGE));
            stock = Integer.parseInt(JOptionPane.showInputDialog(null,"Cantidad de productos: ","Agregar Producto",JOptionPane.QUESTION_MESSAGE));
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El precio o la cantidad deben ser un numero valido, intentalo de nuevo.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (price <= 0 || stock < 0) {
            JOptionPane.showMessageDialog(null, "El precio o la cantidad deben ser mayores a 0, intentalo de nuevo.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
       
        Product product;
        if (type.equals("Alimento")) {
            product = new Food(name, price);
        } else {
            product = new Appliance(name, price);
        }
        products.add(product);
        Stock.put(name.toLowerCase(), stock);
        JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
    }
    //--------------------------------------------------------------------------------------------------------------------//
    //inventario
    public static void inventory(){
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el inventario", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder food = new StringBuilder("ALIMENTOS\n");
        StringBuilder Appliance = new StringBuilder("ELECTRODOMESTICOS\n");
        for(Product product : products){
            String line =product.getName() + " - $" + product.getPrice()+" - Stock: "+Stock.get(product.getName())+" - Descripcion: "+product.getDescription()+"\n";
            if (product instanceof Food) {
                food.append(line);
            }else if (product instanceof Appliance) {
                Appliance.append(line);
            }
        }
        JOptionPane.showMessageDialog(null, food.toString()+ "\n" + Appliance.toString(), "Inventario", JOptionPane.INFORMATION_MESSAGE);
    }

    // --------------------------------------------------------------------------------------------------------------------//
    public static void BuyProduct(){
        
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el inventario", "Error", JOptionPane.ERROR_MESSAGE);
            return;

        }

        String name = JOptionPane.showInputDialog(null,"Ingresa el nombre del producto:", "Comprar Producto", JOptionPane.QUESTION_MESSAGE);
        if (name == null) return;
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null,"El nombre no puede esta vacio, intentalo de nuevo", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean found = false;
        for(Product product : products){
            if (product.getName().equalsIgnoreCase(name)){
                found = true;
                break;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "El producto no existe en el inventario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int availableStock = Stock.get(name.toLowerCase());
        int quantity;
        try {
            quantity = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la cantidad a comprar: ","Comprar Producto",JOptionPane.QUESTION_MESSAGE));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: ingrese un numero valido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if( quantity <= 0) {
            JOptionPane.showMessageDialog(null, "Cantidad invalida, debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (availableStock < quantity) {
            JOptionPane.showMessageDialog(null, "cantidad en Stock insuficiente. Disponible: " + availableStock, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Stock.put(name, availableStock - quantity);
        double price = 0;
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                price = product.getPrice();
                break;
            }
        }
        double totalPrice = price * quantity;
        totalPurchases += totalPrice;
        tickets.add(name + " x" + quantity + " = $" + totalPrice);
        JOptionPane.showMessageDialog(null, "Compra realizada exitosamente. Total: $" + totalPrice, "Exito", JOptionPane.INFORMATION_MESSAGE);

    }
    // --------------------------------------------------------------------------------------------------------------------//
    public static void exit(){
        StringBuilder ticket = new StringBuilder("FACTURA\n");
        for (String line : tickets) {
            ticket.append(line).append("\n");
        }
        ticket.append("TOTAL = $").append(totalPurchases);

        JOptionPane.showMessageDialog(null, ticket.toString(), "Saliendo...", JOptionPane.INFORMATION_MESSAGE);
    }
    // --------------------------------------------------------------------------------------------------------------------//
    //estadisticas
    public static void statistics(){
        if (tickets.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se han realizado compras aun", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Product expansiveProduct = products.get(0);
        Product cheapProduct = products.get(0);
        for (Product product : products) {
            if (product.getPrice() > expansiveProduct.getPrice()) {
                expansiveProduct = product;
            }
            if (product.getPrice() < cheapProduct.getPrice()) {
                cheapProduct = product;
            }
        }
        JOptionPane.showMessageDialog(null,
                "Producto mas costoso: " + expansiveProduct.getName() + " $" + expansiveProduct.getPrice() + "\n" +
                        "Producto mas economico: " + cheapProduct.getName() + " $" + cheapProduct.getPrice() + "\n"+
                        "Total de compras realizadas: $" + totalPurchases,
                "Estadisticas", JOptionPane.INFORMATION_MESSAGE);
    }

    // --------------------------------------------------------------------------------------------------------------------//
    //buscar producto
    public static void searchProduct(){
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el inventario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String name = JOptionPane.showInputDialog(null,"Ingrese el nombre o parte del nombre del producto a buscar: ","Buscar Producto",JOptionPane.QUESTION_MESSAGE);
        if (name == null) return;
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio, intentalo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder results = new StringBuilder("Resultados:\n");
        for (Product product : products ){
            if (product.getName().toLowerCase().contains(name.toLowerCase())){
                results.append(product.getName() + " - $"+product.getPrice()+" - Stock: " + Stock.get(product.getName())+ " - "+ product.getDescription()+"\n");
            }
        }
        JOptionPane.showMessageDialog(null, results.toString(), "Buscar", JOptionPane.INFORMATION_MESSAGE);

    }
     
    // --------------------------------------------------------------------------------------------------------------------//

    public static void main(String[] args) {
        String option;
        do {
            option = JOptionPane.showInputDialog(null,"Menu Principal\n"+
            "1- Agregar Producto\n"+
            "2. Ver Inventario\n" +
            "3. Comprar producto\n" +
            "4. Estadisticas\n" +
            "5. Buscar producto\n" +
            "6. Salir","MiniTienda",JOptionPane.QUESTION_MESSAGE);

            if(option == null) break;

            switch (option) {
                case "1":
                    addProduct();
                    break;
                case "2":
                    inventory();
                    break;
                case "3":
                    BuyProduct();
                    break;
                case "4":
                    statistics();
                    break;
                case "5":
                   searchProduct();
                    break;
                case "6":
                   exit();
            
                default:
                    break;
            }

            
        } while (!"6".equals(option));
    }
}
