package model;

import java.util.Objects;

public class Item {

    private String name;
    private int amount;
    private String price;

    public Item(String name, int count, String price) {
        this.name = name;
        this.amount = count;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name.toLowerCase().trim(), item.name.toLowerCase().trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, price);
    }
}
