
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class TopK {

    public static Hotel[] topKLargest(ArrayList<Hotel> input, int k,
            final Preferences preferences, final MaxMinLimits limits) {

        PriorityQueue<Hotel> minheap = new PriorityQueue<Hotel>(k, new Comparator<Hotel>() {

            @Override
            public int compare(Hotel hotel1, Hotel hotel2) {
                double firstHotelRating = calculateTotalRating(hotel1);

                double secondHotelRating = calculateTotalRating(hotel2);
                //testing
                if (firstHotelRating > secondHotelRating) {
                    return 1;
                } else if (firstHotelRating < secondHotelRating) {
                    return -1;
                } else {
                    return 0;
                }
            }

            private double calculateTotalRating(Hotel hotel) {

                double result = +(preferences.getLocationWeight() 
                        * (distance(hotel.getX(), hotel.getY(), preferences.getLocationx(), preferences.getLocationy()) 
                        / limits.getMax_distance())
                        )
                        
                        + (preferences.getPriceWeight() * ((limits.getMax_price() - hotel.getPrice())
                        / (limits.getMax_price() - limits.getMin_price())))
                        
                        
                        - (preferences.getReviewsWeight() * (hotel.getNumber_of_reviews())
                        / (limits.getMax_number_of_reviews() - limits.getMin_number_of_reviews()));

                return result;
            }

            public double distance(double lat1, double lon1, double lat2,
                    double lon2) {

                final int R = 6371; // Radius of the earth

                double latDistance = Math.toRadians(lat2 - lat1);
                double lonDistance = Math.toRadians(lon2 - lon1);
                double calculations = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
                double c = 2 * Math.atan2(Math.sqrt(calculations), Math.sqrt(1 - calculations));
                double distance = R * c * 1000000; // convert to meters

                distance = Math.pow(distance, 2);
                return Math.sqrt(distance);
            }

        });

        for (Hotel i : input) {
            minheap.add(i);
        }

        Hotel[] out = new Hotel[k];

        for (int i = 0; i < out.length; i++) {
            out[i] = minheap.poll();
        }
//        for (int i = 0; i < out.length; i++) {
//            System.out.println(out[i]);
//        }

        return out;
    }
}
