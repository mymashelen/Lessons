package lesson3;

public class Main {
    public static void main(String[] args) {
        Product[] productArr = new Product[5];
        productArr[0] = new Product("Samsung S25 Ultra", "01.02.2025",
                "Samsung Corp.", "Korea", 5599, true);
        productArr[1] = new Product("iPhone 16 Pro", "15.01.2025",
                "Apple Inc.", "USA", 4999, false);
        productArr[2] = new Product("Xiaomi Mi 14", "10.12.2024",
                "Xiaomi Corporation", "China", 2999, true);
        productArr[3] = new Product("MacBook Air M3", "05.03.2025",
                "Apple Inc.", "USA", 3999, false);
        productArr[4] = new Product("Sony WH-1000XM5", "20.11.2024",
                "Sony Corporation", "Japan", 899, true);
        for (Product product : productArr) {
            product.info();
            System.out.println();
        }

        Park.Attraction[] attractions = {
                new Park.Attraction("Американские горки", "10:00-20:00", 15.99),
                new Park.Attraction("Колесо обозрения", "11:00-22:00", 9.99),
                new Park.Attraction("Дом с привидениями", "14:00-23:00", 8.75)
        };

        Park centralPark = new Park("Центральный парк развлечений", "Ленина 112", attractions);
        centralPark.infoPark();
    }
}
