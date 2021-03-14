
import java.io.File;
import java.io.IOException;

public class TestMain {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        System.out.println("Reads a file");
        String path = "";

        /* This is the name of the dataset: i.e. a txt file
		 * where every line corresponds to an object/point:
		 * id latitude, longitude Number_of_cuisine	Price	Rating
		 * where id is the id of the object and p,r is the initial/reduced point. */
        String filename = path + "AB_NYC_2019.csv";
        String weights = path + "uber-raw-data-sep14.csv";

        DataParser parser = new DataParser(filename, weights);
        //reads the files
        parser.covertDataset();

    }

}
