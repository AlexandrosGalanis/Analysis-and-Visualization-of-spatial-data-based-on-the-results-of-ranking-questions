
class Preferences {

    private double locationx;
    private double locationy;
    private double priceWeight;
    private double reviewsWeight;
    private double locationWeight;

    public Preferences(double locationx, double locationy, double priceWeight,
            double reviewsWeight,double locationWeight) {
        
        this.locationx = locationx;
        this.locationy = locationy;
        this.priceWeight = priceWeight;
        this.reviewsWeight=reviewsWeight;
        this.locationWeight=locationWeight;
    }

    public double getLocationx() {
        return locationx;
    }
    public void setLocationx(double locationx) {
        this.locationx = locationx;
    }
    
    public double getLocationy() {
        return locationy;
    }
    public void setLocationy(double locationy) {
        this.locationy = locationy;
    }
    
    public double getPriceWeight() {
        return priceWeight;
    }
    public void setPriceWeight(double priceWeight) {
        this.priceWeight = priceWeight;
    }
    
    public double getReviewsWeight() {
        return reviewsWeight;
    }
    public void setReviewsWeight(double reviewsWeight) {
        this.reviewsWeight = reviewsWeight;
    }
    
    public double getLocationWeight() {
        return locationWeight;
    }
    public void setLocationWeight(double locationWeight) {
        this.locationWeight = locationWeight;
    }

        @Override
    public String toString() {
        return "Preferences{" + "locationx=" 
                + locationx + ", locationy=" 
                + locationy + ", price=" 
                + priceWeight + ", number_of_reviews=" 
                + reviewsWeight + ", availability_per_year=" 
                + locationWeight + '}';
    }

}
