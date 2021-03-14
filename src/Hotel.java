/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author icsd14027
 */
public class Hotel {

    private double id;
    private double x;
    private double y;
    private double price;
    private int number_of_reviews;
    private int availability_per_year;

    public Hotel(double id, double x, double y, double price, int number_of_reviews,
            int availability_per_year) {

        
        this.id = id;
        this.x = x;
        this.y = y;
        this.price = price;
        this.number_of_reviews = number_of_reviews;
        this.availability_per_year = availability_per_year;
    }

    public double getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getPrice() {
        return price;
    }

    public int getNumber_of_reviews() {
        return number_of_reviews;
    }

    public int getAvailability_per_year() {
        return availability_per_year;
    }

    public void SetId(double id) {
        this.id = id;
    }

    public void SetX(double x) {
        this.x = x;
    }

    public void SetY(double y) {
        this.y = y;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumber_of_reviews(int number_of_reviews) {
        this.number_of_reviews = number_of_reviews;
    }

    public void setAvailability_per_year(int availability_per_year) {
        this.availability_per_year = availability_per_year;
    }

    @Override
    public String toString() {
        return "Hotel{" + "id=" + id + ", x=" + x + ", y=" + y + ", price=" 
                + price + ", number_of_reviews=" 
                + number_of_reviews + ", availability_per_year=" 
                + availability_per_year + '}';
    }
}
