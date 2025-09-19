import javax.swing.JOptionPane;

public abstract class Product {

    private String name;
    private  double price;

   public Product(String name, double price){
        setName(name);
        setPrice(price);
   }

   public String getName(){
       return  name;
   }

   public void setName(String name) {
       if (name == null || name.trim().isEmpty()){
           JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
           return;
       }
        this.name = name.trim();
   }

   public double getPrice(){
       return price;
   }

   public void setPrice(double price){
       if (price < 0){
           JOptionPane.showMessageDialog(null, "El precio debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
           return;
       }
       this.price = price;
   }

   public abstract String getDescription();

}
