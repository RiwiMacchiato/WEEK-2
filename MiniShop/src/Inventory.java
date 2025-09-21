import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    private  ArrayList<Product> products;
    private  HashMap<String, Integer> stock;

    public Inventory(){
        products = new ArrayList<>();
        stock = new HashMap<>();
    }

    public boolean addProduct(Product product, int quantity){
        String name = product.getName();

        if (stock.containsKey(name)){
            return false;
        }

        if (quantity < 0){
            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        products.add(product);
        stock.put(name,quantity);

        return true;
    }

    public ArrayList<Product> getProducts(){
        return products;
    }

    public int getStock(String name){
        return stock.getOrDefault(name, 0);
    }

    public boolean productExists(String name){
        return stock.containsKey(name);
    }

    public boolean updateStock (String name, int newAmount){
        if (!stock.containsKey(name)){
            return  false;
        }

        if (newAmount < 0){
            JOptionPane.showMessageDialog(null, "El stock no puede ser negativo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        stock.put(name, newAmount);
        return true;
    }

    public int getTotalProductTypes(){
        return products.size();
    }

}
