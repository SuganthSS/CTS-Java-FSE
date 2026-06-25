import java.util.*;

class Product implements Comparable<Product> {

    int id;
    String name;
    String category;

    Product(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    @Override
    public int compareTo(Product p) {
        if (this.id > p.id)
            return 1;
        else if (this.id < p.id)
            return -1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return name + " (" + category + ") ID: " + id;
    }
}

public class EcommerceSearch {

    // Linear Search
    public static Product linearSearch(Product[] products, int id) {

        for (int i = 0; i < products.length; i++) {
            if (products[i].id == id) {
                return products[i];
            }
        }

        return null;
    }

    // Binary Search
    public static Product binarySearch(Product[] products, int id) {

        int low = 0;
        int high = products.length - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (products[mid].id == id) {
                return products[mid];
            }

            if (products[mid].id < id)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return null;
    }

    public static void main(String[] args) {

        Product[] products = {
                new Product(105, "Laptop", "Electronics"),
                new Product(101, "Mouse", "Electronics"),
                new Product(108, "Keyboard", "Electronics"),
                new Product(102, "Desk", "Furniture"),
                new Product(110, "Chair", "Furniture")
        };

        int searchId = 108;

        System.out.println("Linear Search Result");

        Product result = linearSearch(products, searchId);

        if (result != null)
            System.out.println("Found: " + result);
        else
            System.out.println("Item not found.");

        System.out.println("\nBinary Search Result");

        Arrays.sort(products);

        result = binarySearch(products, searchId);

        if (result != null)
            System.out.println("Found: " + result);
        else
            System.out.println("Item not found.");
    }
}