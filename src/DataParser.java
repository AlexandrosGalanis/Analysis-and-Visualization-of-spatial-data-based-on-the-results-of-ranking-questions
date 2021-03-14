
import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.corba.se.impl.orbutil.graph.Node;
import com.sun.corba.se.impl.orbutil.graph.NodeData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.alg.scoring.PageRank;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class DataParser {

    private String filename;
    private String weights;
    private ArrayList<Hotel> hotels = new ArrayList<>();
    private ArrayList<Preferences> pref = new ArrayList<>();

    public DataParser(String filename, String weights) {
        this.filename = filename;
        this.weights = weights;
    }

    public void covertDataset() {
        HashMap<Heap, String> graph = new HashMap<Heap, String>();

        Scanner sc = new Scanner(System.in);
        XXFile file_dataset = new XXFile();
        XXFile weights_dataset = new XXFile();
        file_dataset.openRFile(filename);
        weights_dataset.openRFile(weights);
        String line;

        Hotel hotel = null;
        double max_x = 0;
        double min_x = 99999999;
        double max_y = -99999999;
        double min_y = 0;
        double max_price = 0;
        double min_price = 99999;
        int max_number_of_reviews = 0;
        int min_number_of_reviews = 99999;
        int max_availability_per_year = 0;
        int min_availability_per_year = 366;

        //adds all logs into hotel arraylist named hotels
        //and it finds max and mix values of x and y
        long startTimerForReadingFile = System.currentTimeMillis();
        while ((line = file_dataset.readLine()) != null) {
            hotel = parseLineForLogs(line);
            //System.out.println("X:" + hotel.getX() + "Y:" + hotel.getY());
            hotels.add(hotel);
            //System.out.println(line);
            //we get the max values from every attribute
            if (hotel.getX() > max_x) {
                max_x = hotel.getX();
            }
            if (hotel.getY() > max_y) {
                max_y = hotel.getY();
            }
            if (hotel.getPrice() > max_price) {
                max_price = hotel.getPrice();
            }
            if (hotel.getNumber_of_reviews() > max_number_of_reviews) {
                max_number_of_reviews = hotel.getNumber_of_reviews();
            }
            if (hotel.getAvailability_per_year() > max_availability_per_year) {
                max_availability_per_year = hotel.getAvailability_per_year();
            }
            //we get the min value from every attribute
            if (hotel.getX() < min_x) {
                min_x = hotel.getX();
            }
            if (hotel.getY() < min_y) {
                min_y = hotel.getY();
            }
            if (hotel.getPrice() < min_price) {
                min_price = hotel.getPrice();
            }
            if (hotel.getNumber_of_reviews() < min_number_of_reviews) {
                min_number_of_reviews = hotel.getNumber_of_reviews();
            }
            if (hotel.getAvailability_per_year() < min_availability_per_year) {
                min_availability_per_year = hotel.getAvailability_per_year();
            }
        }
        long endTimerForReadingFile = System.currentTimeMillis();

        MaxMinLimits limits = setLimits(max_x, min_x, max_y, min_y, max_price,
                min_price, max_number_of_reviews, min_number_of_reviews,
                max_availability_per_year, min_availability_per_year);
        limits.setMaxDistance(min_x, min_y, max_x, max_y);

        long startTimerForPrefReadingFile = System.currentTimeMillis();
        while ((line = weights_dataset.readLine()) != null) {
            pref.add(parseLineForPreferences(line));
            //System.out.println(line);
        }
        long endTimerForPrefReadingFile = System.currentTimeMillis();

        //closes the file
        file_dataset.closeRFile();
        weights_dataset.closeRFile();

        Preferences preferences = weightsInput(sc);

        System.out.println("Give me how much chain results you want(20 is recommended)"
                + "\nmore than that may break the program or take way to many resources and time");
        while (!sc.hasNextInt()) {
            System.out.println("Thats not a number, try again");
            sc.next();
        }
        int results = sc.nextInt();
        int counter = 0;
        DefaultDirectedGraph<String, DefaultEdge> g
                = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        Hotel[] topChoices;

        //HASHMAPWAY
        //==============================================================================
        //the way i will do it
//        for (Preferences temp : pref) {
//            topChoices = TopK.topKLargest(hotels, results, temp, limits);
//            for (int i = 0; i < topChoices.length - 1; i++) {
//                //System.out.println(i + 1 + ")" + topChoices[i].toString());
//                if (graph.containsKey(new Heap(topChoices[i], topChoices[i + 1]))) {
//                    //System.out.println("no");
//                } else {
//                    graph.put(new Heap(topChoices[i], topChoices[i + 1]), String.valueOf("1"));
//
//                    //System.out.println("yes");
//                }
//            }
//        }
//        //user wants
//        topChoices = TopK.topKLargest(hotels, results, preferences, limits);
//        for (int i = 0; i < topChoices.length - 1; i++) {
//            //System.out.println(i + 1 + ")" + topChoices[i].toString());
//            if (graph.containsKey(new Heap(topChoices[i], topChoices[i + 1]))) {
//                //System.out.println("no");
//            } else {
//                graph.put(new Heap(topChoices[i], topChoices[i + 1]), String.valueOf("1"));
//
//                //System.out.println("yes");
//            }
//        }
//        for (Map.Entry m : graph.entrySet()) {
//            System.out.println(counter + 1 + ")" + m.getKey() + " " + m.getValue());
//            counter++;
//        }
        //THE PRO WAY WITH DIRECTEDGRAPH
        //=================================================================
        //what our users prefer
        long startTimerForGraph = System.currentTimeMillis();
        System.out.println("============================================");
        System.out.println("Calculating TopK and making the graph");

        //what other users prefer 
        
        for (Preferences temp : pref) {
            topChoices = TopK.topKLargest(hotels, results, temp, limits);
            for (int i = 0; i < topChoices.length - 1; i++) {
                if (!g.containsVertex(String.format("%.0f", topChoices[i].getId()))) {
                    g.addVertex(String.format("%.0f", topChoices[i].getId()));
                    counter++;
                }
                if (!g.containsVertex(String.format("%.0f", topChoices[i + 1].getId()))) {
                    g.addVertex(String.format("%.0f", topChoices[i + 1].getId()));
                    counter++;
                }
                g.addEdge(String.format("%.0f", topChoices[i + 1].getId()), String.format("%.0f", topChoices[i].getId()));
            }
        }
        
         
        //what our user preferes
        topChoices = TopK.topKLargest(hotels, results, preferences, limits);
        for (int i = 0; i < topChoices.length - 1; i++) {
            if (!g.containsVertex(String.format("%.0f", topChoices[i].getId()))) {
                g.addVertex(String.format("%.0f", topChoices[i].getId()));
                counter++;
            }
            if (!g.containsVertex(String.format("%.0f", topChoices[i + 1].getId()))) {
                g.addVertex(String.format("%.0f", topChoices[i + 1].getId()));
                counter++;
            }
            g.addEdge(String.format("%.0f", topChoices[i + 1].getId()), String.format("%.0f", topChoices[i].getId()));
            
           
            
            
            //System.out.println(g.toString());
        }

        long endTimerForGraph = System.currentTimeMillis();
        System.out.println("Finished TopK and Graph");
        System.out.println("============================================");
        System.out.println("Graph size:" + counter);
        System.out.println("Making the Image");

        long startTimerForGraphVisualization = System.currentTimeMillis();
        try {
            ImageVisualization v1 = new ImageVisualization(g);
        } catch (IOException ex) {
            System.out.println("Error, the image was not created succesfully");
            Logger.getLogger(DataParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        long endTimerForGraphVisualization = System.currentTimeMillis();

        System.out.println("The image is ready!!!");
        System.out.println("============================================");
        System.out.println("Making the pagerank");

        long startTimerForPageRank = System.currentTimeMillis();
        PageRank p1 = new PageRank(g);
        System.out.println("============================================");
        System.out.println("Calculating the score");
        String s = "";
        //System.out.println(p1.getVertexScore(s));

        String[][] pageRankResults = new String[counter][2];
        int pageRankCounter = 0;

        for (String vertex : g.vertexSet()) {
            //System.out.println(pageRankCounter + ") The PageRank score of " + vertex + " is: " + p1.getVertexScore(vertex));
            pageRankResults[pageRankCounter][0] = vertex;
            pageRankResults[pageRankCounter][1] = String.valueOf(p1.getVertexScore(vertex));
            pageRankCounter++;
        }
        long endTimerForPageRank = System.currentTimeMillis();

        System.out.println("============================================");
        System.out.println("Sorting PageRank");

        String temp;
        long startTimerForSort = System.currentTimeMillis();
        for (int i = 0; i < pageRankCounter; i++) {
            for (int j = 1; j < (pageRankCounter - i); j++) {
                if (Double.valueOf(pageRankResults[j - 1][1]) < Double.valueOf(pageRankResults[j][1])) {
                    //swap elements  
                    temp = pageRankResults[j - 1][1];
                    pageRankResults[j - 1][1] = pageRankResults[j][1];
                    pageRankResults[j][1] = temp;
                }

            }
        }
        long endTimerForSort = System.currentTimeMillis();

        for (int i = 0; i < counter; i++) {
            System.out.println(i + 1 + ")Airbnb Id:" + pageRankResults[i][0] + "   \t PageRank Value: " + pageRankResults[i][1]);

        }

        System.out.println("============================================");

        float timeReadingFile = (endTimerForReadingFile - startTimerForReadingFile) / 1000;
        float timeReadingPrefFile = (endTimerForPrefReadingFile - startTimerForPrefReadingFile) / 1000;
        float timeGraphMaking = (endTimerForGraph - startTimerForGraph) / 1000;
        float timeImageMaking = (endTimerForGraphVisualization - startTimerForGraphVisualization) / 1000;
        float timePageRankMaking = (endTimerForPageRank - startTimerForPageRank) / 1000;
        float timeSortingPageRank = (endTimerForSort - startTimerForSort) / 1000;

        System.out.println("Time it took to read the hotels's file and find the max/min: " + timeReadingFile + " seconds");
        System.out.println("Time it took to read the preferences's file: " + timeReadingPrefFile + " seconds");
        System.out.println("Time it took to make the graph and topk: " + timeGraphMaking + " seconds");
        System.out.println("Time it took to make the image: " + timeImageMaking + " seconds");
        System.out.println("Time it took to make the PageRank: " + timePageRankMaking + " seconds");
        System.out.println("Time it took to sort the PageRank: " + timeSortingPageRank + " seconds");

    }

    private void graphCreation() {

    }

    private MaxMinLimits setLimits(double max_x, double min_x, double max_y, double min_y,
            double max_price, double min_price, int max_number_of_reviews,
            int min_number_of_reviews, int max_availability_per_year,
            int min_availability_per_year) {

        MaxMinLimits limits = new MaxMinLimits();

        limits.setMax_x(max_x);
        limits.setMin_x(min_x);
        limits.setMax_y(max_y);
        limits.setMin_y(min_y);
        limits.setMax_price(max_price);
        limits.setMin_price(min_price);
        limits.setMax_number_of_reviews(max_number_of_reviews);
        limits.setMin_number_of_reviews(min_number_of_reviews);
        limits.setMax_availability_per_year(max_availability_per_year);
        limits.setMin_availability_per_year(min_availability_per_year);
//        System.out.println("max x" + max_x);
//        System.out.println("min x" + min_x);
//        System.out.println("min y" + min_y);
//        System.out.println("max y" + max_y);
//        System.out.println("max price" + max_price);
//        System.out.println("min_price" + min_price);
//        System.out.println("max_number_of_reviews" + max_number_of_reviews);
//        System.out.println("min_number_of_reviews" + min_number_of_reviews);
//        System.out.println("max_availability_per_year" + max_availability_per_year);
//        System.out.println("min_availability_per_year" + min_availability_per_year);

        return limits;
    }

    private Preferences weightsInput(Scanner sc) {

        System.out.println("Give me how much do you care about price:");
        double priceWeight = inputWeights(sc);
        System.out.println("Give me how much do you care about reviews:");
        double reviewsWeight = inputWeights(sc);
        System.out.println("Give me how much do you care about distance:");
        double locationWeight = inputWeights(sc);

        System.out.print("Enter your location: ");
        System.out.println("Give me x axe");
        double locationx = inputLocation(sc);
        System.out.println("Give me y axe");
        double locationy = inputLocation(sc);

        Preferences preferences = new Preferences(locationx, locationy, priceWeight, reviewsWeight, locationWeight);
        return preferences;
    }

    private double inputLocation(Scanner sc) {
        double num;

        while (!sc.hasNextDouble()) {
            System.out.println("Thats not correct, try again");
            sc.next();
        }
        num = sc.nextDouble();

        return num;
    }

    private double inputWeights(Scanner sc) {
        double num = -1;

        while (num < 0 || num > 10) {
            System.out.print("Enter number from 0 to 10: ");
            while (!sc.hasNextFloat()) {
                System.out.println("Thats not a number, try again");
                sc.next();
            }
            num = sc.nextFloat();
        }

        return num / 10;
    }

    private static Hotel parseLineForLogs(String line) {
        StringTokenizer st = new StringTokenizer(line, ",");

        return new Hotel(
                Double.parseDouble(st.nextToken()),
                Double.parseDouble(st.nextToken()),
                Double.parseDouble(st.nextToken()),
                Double.parseDouble(st.nextToken()),
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()));

    }

    private static Preferences parseLineForPreferences(String line) {
        StringTokenizer st = new StringTokenizer(line, ",");

        return new Preferences(
                Double.parseDouble(st.nextToken()),
                Double.parseDouble(st.nextToken()),
                Double.parseDouble(st.nextToken()),
                Double.parseDouble(st.nextToken()),
                Double.parseDouble(st.nextToken()));

    }

}
