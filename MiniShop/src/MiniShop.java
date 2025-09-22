import javax.swing.JOptionPane;
import java.util.ArrayList;

public class MiniShop {

    private static Inventory inventory = new Inventory();
    private static double totalPurchases = 0.0;
    private static String ticket;

    public static void main(String[] args) {

        JOptionPane.showMessageDialog(null, "\nMini Tienda Con JOptionPane\n", "Mini Tienda", JOptionPane.INFORMATION_MESSAGE);

        boolean toContinue = true;
        while (toContinue) {
            toContinue = showMenu();
        }

        showFinalTicket();
    }

    private static boolean showMenu() {
        String[] options = {
                "\n1. Agregar producto",
                "2. Mostrar inventario",
                "3. Comprar producto",
                "4. Estadisticas",
                "5. Buscar producto",
                "6. Salir"
        };

        String menu = "Minitienda" + String.join("\n", options);
        String selection = JOptionPane.showInputDialog(null, menu, "Menu Principal", JOptionPane.QUESTION_MESSAGE);

        if (selection == null) {
            return false;
        }

        try {
            int option = Integer.parseInt(selection.trim());
            return executeOption(option);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese un numero valido (1-6)", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
    }

    private static boolean executeOption(int option) {
        switch (option) {
            case 1:
                addProduct();
                break;
            case 2:
                showInventory();
                break;
            case 3:
                buyProduct();
                break;
            case 4:
                showStatistics();
                break;
            case 5:
                searchProduct();
                break;
            case 6:
                return false;
            default:
                JOptionPane.showMessageDialog(null, "Opcion invalida (1-6)", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    private static void addProduct(){
        try {
            String[] types = {"Food", "Appliance"};
            String selectedType = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de producto:", "Tipo de Producto", JOptionPane.QUESTION_MESSAGE, null, types, null);

            if (selectedType == null) return;

            String name = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto:", "Nombre", JOptionPane.QUESTION_MESSAGE);

            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            name = name.trim();
            boolean exists = false;
            for (Product p : inventory.getProducts()) {
                if (p.getName().toLowerCase().equals(name.toLowerCase())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                JOptionPane.showMessageDialog(null, "El producto ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String priceStr = JOptionPane.showInputDialog(null, "Ingrese el precio:", "Precio", JOptionPane.QUESTION_MESSAGE);

            if (priceStr == null) return;

            String stockStr = JOptionPane.showInputDialog(null, "Ingrese el stock inicial:", "Stock", JOptionPane.QUESTION_MESSAGE);

            if (stockStr == null) return;

            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);

            Product product;
            switch (selectedType) {
                case "Food":
                    product = new Food(name, price);
                    break;
                case "Appliance":
                    product = new Appliance(name, price);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Tipo no valido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            if (inventory.addProduct(product, stock)) {
                JOptionPane.showMessageDialog(null, "Producto agregado exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese numeros validos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static void showInventory(){
        if (inventory.getProducts().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el inventario", "Inventario Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder inventoryList = new StringBuilder("=== INVENTARIO ===\n\n");
        for (Product product : inventory.getProducts()) {
            inventoryList.append(product.getDescription()).append("Stock: ").append(inventory.getStock(product.getName())).append(" unidades\n\n");
        }

        JOptionPane.showMessageDialog(null, inventoryList.toString(), "Inventario", JOptionPane.INFORMATION_MESSAGE);
    }
    private static void buyProduct(){
        if (inventory.getProducts().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos disponibles", "Inventario Vacio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String name = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto:", "Comprar Producto", JOptionPane.QUESTION_MESSAGE);

            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            name = name.trim();
            if (!inventory.productExists(name)) {
                JOptionPane.showMessageDialog(null, "El producto no existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int currentStock = inventory.getStock(name);
            if (currentStock == 0) {
                JOptionPane.showMessageDialog(null, "Producto sin stock", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String quantityStr = JOptionPane.showInputDialog(null, "Cantidad a comprar (Stock disponible: " + currentStock + "):", "Cantidad", JOptionPane.QUESTION_MESSAGE);

            if (quantityStr == null) return;

            int quantity = Integer.parseInt(quantityStr);

            if (quantity <= 0) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (quantity > currentStock) {
                JOptionPane.showMessageDialog(null, "Stock insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product product = null;
            for (Product p : inventory.getProducts()) {
                if (p.getName().toLowerCase().equals(name.toLowerCase())) {
                    product = p;
                    break;
                }
            }

            double subtotal = product.getPrice() * quantity;
            totalPurchases += subtotal;

            if (ticket == null) ticket = "";
            ticket += product.getName() + " x" + quantity + " = $" + subtotal + "\n";

            inventory.updateStock(name, currentStock - quantity);

            JOptionPane.showMessageDialog(null, "Compra realizada!\n" + product.getName() + " x" + quantity + "\nSubtotal: $" + subtotal, "Compra Exitosa", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese numeros validos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static void showStatistics(){
        if (inventory.getProducts().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos para mostrar estadisticas", "Sin Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product cheapest = inventory.getProducts().get(0);
        Product expensive = inventory.getProducts().get(0);

        for (Product product : inventory.getProducts()) {
            if (product.getPrice() < cheapest.getPrice()) {
                cheapest = product;
            }
            if (product.getPrice() > expensive.getPrice()) {
                expensive = product;
            }
        }

        StringBuilder stats = new StringBuilder("=== ESTADISTICAS ===\n\n");
        stats.append("Producto mas barato:\n").append(cheapest.getDescription()).append("\n");
        stats.append("Producto mas caro:\n").append(expensive.getDescription()).append("\n");
        stats.append("Total de productos diferentes: ").append(inventory.getTotalProductTypes());

        JOptionPane.showMessageDialog(null, stats.toString(), "Estadisticas", JOptionPane.INFORMATION_MESSAGE);
    }
    private static void searchProduct(){
        if (inventory.getProducts().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos para buscar", "Sin Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String searchTerm = JOptionPane.showInputDialog(null, "Ingrese el nombre o parte del nombre a buscar:", "Buscar Producto", JOptionPane.QUESTION_MESSAGE);

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un termino de busqueda", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        searchTerm = searchTerm.trim();
        StringBuilder results = new StringBuilder("=== RESULTADOS DE BUSQUEDA ===\n\n");
        boolean found = false;

        for (Product product : inventory.getProducts()) {
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.append(product.getDescription()).append("Stock: ").append(inventory.getStock(product.getName())).append(" unidades\n\n");
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No se encontraron productos que coincidan con: " + searchTerm, "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, results.toString(), "Resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void showFinalTicket(){
        String finalTicket = " ===== Ticket Final ===== \n" +
                (ticket.length() > 0 ? ticket.toString() : "No se realizaron compras" +
                "\n\nTotal: $" + totalPurchases + "\n");
        JOptionPane.showMessageDialog(null, finalTicket, "Ticket Final", JOptionPane.INFORMATION_MESSAGE);
    }
}

