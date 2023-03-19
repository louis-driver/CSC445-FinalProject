import javax.swing.JPanel;

//Louis Driver
// Source for hexagon: https://stackoverflow.com/questions/35853902/drawing-hexagon-using-java-error

//This is a JPanel that represents an Abalone board during play

import java.awt.*;
import javax.swing.*;

public class AbalonePanel extends JPanel
{
    AbaloneGraph graph; 
    Polygon hexagon1;
    Polygon hexagon2;

    //Test Main class
    public static void main(String[] args)
    {
        
        int frameWidth = 1000;
        int frameHeight = 1000;
        JFrame frame = new JFrame();
        AbaloneGraph graph = new AbaloneGraph();
        AbalonePanel panel = new AbalonePanel(graph);

        frame.setSize(frameWidth, frameHeight);
        frame.setTitle("Graph");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public AbalonePanel(AbaloneGraph graph)
    {
        this.graph = graph;

    }

    //Iterates through the graph to assign locations proportional to the size
    // of the panel.
    private void assignPieceLocations()
    {

    }

    private void createHexagons()
    {
        // Create large hexagon
        Point center1 = new Point(this.getWidth()/2, this.getHeight()/2);
        int radius1 = this.getHeight()/2;
        if (this.getWidth()/2 < radius1)
            radius1 = this.getWidth()/2;
        hexagon1 = createHexagon(center1, radius1);
        // Create interior hexagon
        Point center2 = new Point(this.getWidth()/2, this.getHeight()/2);
        int radius2 = (int) (this.getHeight()/2.4);
        if (this.getWidth()/2.5 < radius2)
            radius2 = (int) (this.getWidth()/2.4);
        hexagon2 = createHexagon(center2, radius2);
        // Create interior spaces for pieces
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        createHexagons();
        g.setColor(Color.BLACK);
        g.fillPolygon(hexagon1);
        g.setColor(Color.GRAY);
        g.fillPolygon(hexagon2);
    }

    private Polygon createHexagon(Point center, int radius)
    {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; ++i)
        {
            int x = (int) (center.x + radius * Math.cos(i*2*Math.PI/6));
            int y = (int) (center.y + radius * Math.sin(i*2*Math.PI/6));
            hexagon.addPoint(x, y);
        }
        return hexagon;
    }
}
