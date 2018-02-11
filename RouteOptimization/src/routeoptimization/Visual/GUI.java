package routeoptimization.Visual;

import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import routeoptimization.Algorithms.Node;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Anwar
 */
@SuppressWarnings("serial")
public final class GUI extends JFrame{
    private MainPanel main_panel;
    // Constructors
    public GUI(String title, Map<String, Node> distanceMap, Map<String, Integer> longsMap){
        // Frame Title
        super(title);
        // Initialize Panels
        this.init_panel(main_panel);
        // Initialize Combo boxes
        this.buildFrame(distanceMap, longsMap);
    }
    private void init_panel(JPanel panel){
        panel=new JPanel();
    }
    // Building the Frame
    public void buildFrame(Map<String, Node> distanceMap, Map<String, Integer> longsMap){
        this.buildMainPanel(distanceMap, longsMap);
        super.add(this.main_panel);
    }
    private void buildMainPanel(Map<String, Node> distanceMap, Map<String, Integer> longsMap){
        this.main_panel=new MainPanel(distanceMap, longsMap);
    }
    public GraphicsPanel getGraphicsPanel(){
        return main_panel.getGraphicsPanel();
    }
}