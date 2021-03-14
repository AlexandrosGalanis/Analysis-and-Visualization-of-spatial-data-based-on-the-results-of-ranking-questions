
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class ImageVisualization {

    public ImageVisualization(DefaultDirectedGraph<String, DefaultEdge> g) throws IOException {
        givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(g);
    }

    public void givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(DefaultDirectedGraph<String, DefaultEdge> g) throws IOException {

        JGraphXAdapter<String, DefaultEdge> graphAdapter
                = new JGraphXAdapter<String, DefaultEdge>(g);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image
                = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File("src/graph.png");
        ImageIO.write(image, "PNG", imgFile);

//        assertTrue(imgFile.exists());
    }

}
