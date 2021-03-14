/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AlexanderDT
 */
public class MaxMinLimits {

    private double max_x = 0;
    private double min_x = 99999999;
    private double max_y = -99999999;
    private double min_y = 0;
    private double max_distance;
    private double max_price = 0;
    private double min_price = 99999;
    private int max_number_of_reviews = 0;
    private int min_number_of_reviews = 99999;
    private int max_availability_per_year = 0;
    private int min_availability_per_year = 366;

    public MaxMinLimits() {

    }

    public double getMax_distance() {
        return max_distance;
    }

    public void setMax_distance(double max_distance) {
        this.max_distance = max_distance;
    }

    public double getMax_x() {
        return max_x;
    }

    public void setMax_x(double max_x) {
        this.max_x = max_x;
    }

    public double getMin_x() {
        return min_x;
    }

    public void setMin_x(double min_x) {
        this.min_x = min_x;
    }

    public double getMax_y() {
        return max_y;
    }

    public void setMax_y(double max_y) {
        this.max_y = max_y;
    }

    public double getMin_y() {
        return min_y;
    }

    public void setMin_y(double min_y) {
        this.min_y = min_y;
    }

    public double getMax_price() {
        return max_price;
    }

    public void setMax_price(double max_price) {
        this.max_price = max_price;
    }

    public double getMin_price() {
        return min_price;
    }

    public void setMin_price(double min_price) {
        this.min_price = min_price;
    }

    public int getMax_number_of_reviews() {
        return max_number_of_reviews;
    }

    public void setMax_number_of_reviews(int max_number_of_reviews) {
        this.max_number_of_reviews = max_number_of_reviews;
    }

    public int getMin_number_of_reviews() {
        return min_number_of_reviews;
    }

    public void setMin_number_of_reviews(int min_number_of_reviews) {
        this.min_number_of_reviews = min_number_of_reviews;
    }

    public int getMax_availability_per_year() {
        return max_availability_per_year;
    }

    public void setMax_availability_per_year(int max_availability_per_year) {
        this.max_availability_per_year = max_availability_per_year;
    }

    public int getMin_availability_per_year() {
        return min_availability_per_year;
    }

    public void setMin_availability_per_year(int min_availability_per_year) {
        this.min_availability_per_year = min_availability_per_year;
    }

    public void setMaxDistance(double min_x, double min_y, double max_x,
            double max_y) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(max_x - min_x);
        double lonDistance = Math.toRadians(max_y - min_y);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(min_x)) * Math.cos(Math.toRadians(max_x))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);
        this.max_distance = Math.sqrt(distance);
    }

}
