package routeoptimization.Visual;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import routeoptimization.Algorithms.Node;

public final class MainPanel extends JPanel{
    GraphicsPanel graphics_panel;
    MainPanel mp;
    private JComboBox<String> cb_source, cb_destination, cb_algorithm, cb_sleep_time;
    public JLabel  jl_algo_name, jl_read_only_label, jl_sleep_time,
            jl_source_name, jl_destination_name,
            jl_nodes_expanded, jl_path_length, jl_path;
    private JButton btn_start, btn_pause, btn_stop;
    private final GridBagConstraints gbc;
    private final Map<String, Node> distanceMap;
    private final Map<String, Integer> longsMap;
    private boolean paused=false;
    // Constructors
    MainPanel(Map<String, Node> distanceMap, Map<String, Integer> longsMap){
        // Initialize the panel
        super(new GridBagLayout());
        // Set the back ground color
        super.setBackground(Color.WHITE);
        // then make the graphics Panel and add it
        this.init_graphicsPanel(longsMap);
        // Set the distance map
        this.distanceMap=new TreeMap<>(distanceMap);
        // Set the longs Map
        this.longsMap=longsMap;
        // Initialize Combo boxes
        this.init_comboxes();
        // Initialize Buttons
        this.init_buttons();
        // Initialize Text Fields
        this.init_Labels();
        
        // Add Action Listeners
        this.addActionHandler();
        // Layout Manager
        gbc=new GridBagConstraints();
        
        // Build Panel
        this.buildMainPanel();
        mp=this;
    }
    public GraphicsPanel getGraphicsPanel(){
        return this.graphics_panel;
    }
    private void buildMainPanel(){
        // Combo boxes 
        gbc.fill=GridBagConstraints.BOTH;
        this.addGraphicsPanel();
        this.addComboBoxes();
        this.addTextFields();
        this.addButtons();
    }
    private void addComponent(JComponent cmp, GridBagConstraints cns,
            int gridx, int gridy, int gridWidth, int gridHeight){
        if(gridx!=-1) cns.gridx=gridx;
        if(gridy!=-1) cns.gridy=gridy;
        if(gridWidth!=-1) cns.gridwidth=gridWidth;
        if(gridHeight!=-1) cns.gridheight=gridHeight;
        this.add(cmp, cns);
    }
    private void init_comboxes(){
        String [] cities=new String[distanceMap.size()+1];
        int cityIdx=0; cities[cityIdx]="--Select--";
        cityIdx++;
        for(Map.Entry e: distanceMap.entrySet()){
            cities[cityIdx]=(String) e.getKey().toString();
            ++cityIdx;
        }
        cb_source=new JComboBox<>(cities);    // Can supply string list here
        cb_source.setMaximumRowCount(4);
        cb_destination=new JComboBox<>(cities);
        cb_destination.setMaximumRowCount(4);
        String[] possibleAlgos={"--Select--","BFS","DFS","DFID","H_ONLY",
                                "AStar(H=0)","AStar(H=E-W Dist)"};
        cb_algorithm=new JComboBox<>(possibleAlgos);
        cb_algorithm.setMaximumRowCount(4);
        String[] possibleDelays={"--Select--","500","1000","2000","3000"};
        cb_sleep_time=new JComboBox<>(possibleDelays);
        cb_sleep_time.setMaximumRowCount(4);
    }
    private void init_buttons() {
        btn_start=new JButton("Start");
        btn_pause=new JButton("Pause");
        btn_stop=new JButton("Stop");
    }
    private void init_Labels(){
        jl_algo_name=new JLabel("Not yet Selected!");
        jl_sleep_time=new JLabel("Undefined");
        jl_source_name=new JLabel("Not Specified");
        jl_destination_name=new JLabel("Not Specified");
        jl_nodes_expanded=new JLabel("None");
        jl_path_length=new JLabel("NILL");
        jl_path=new JLabel("None");
    }
    private void addComboBoxes(){
        jl_read_only_label=new JLabel("Source: ");
        this.addComponent(jl_read_only_label,gbc,8,6,1,1);
        this.addComponent(cb_source,gbc,9,6,2,1);
        jl_read_only_label=new JLabel("Destination: ");
        this.addComponent(jl_read_only_label,gbc,8,7,1,1);
        this.addComponent(cb_destination,gbc,9,7,2,1);
        jl_read_only_label=new JLabel("Algorithm: ");
        this.addComponent(jl_read_only_label,gbc,8,8,1,1);
        this.addComponent(cb_algorithm,gbc,9,8,2,1);
        jl_read_only_label=new JLabel("Timer(ms): ");
        this.addComponent(jl_read_only_label,gbc,8,9,1,1);
        this.addComponent(cb_sleep_time,gbc,9,9,2,1);
    }

    private void init_graphicsPanel(Map<String, Integer> longMaps) {
        this.graphics_panel=new GraphicsPanel(longMaps);
        this.graphics_panel.setBackground(Color.RED);
        this.graphics_panel.repaint();
    }
    private void addGraphicsPanel(){
        this.addComponent(graphics_panel, gbc, 0, 0, 8, 30);
    }

    private void addTextFields() {
        this.jl_read_only_label=new JLabel("Algorithm: ");
        this.addComponent(jl_read_only_label, gbc, 8, 0, 1, 1);
        this.addComponent(jl_algo_name, gbc, 9, 0, 2, 1);

        this.jl_read_only_label=new JLabel("Timer: ");
        this.addComponent(jl_read_only_label, gbc, 8, 1, 1, 1);
        this.addComponent(jl_sleep_time, gbc, 9, 1, 2, 1);        

        this.jl_read_only_label=new JLabel("Source: ");
        this.addComponent(jl_read_only_label, gbc, 8, 2, 1, 1);
        this.addComponent(jl_source_name, gbc, 9, 2, 2, 1);
        
        this.jl_read_only_label=new JLabel("Destination: ");
        this.addComponent(jl_read_only_label, gbc, 8, 3, 1, 1);
        this.addComponent(jl_destination_name, gbc, 9, 3, 2, 1);
        
        this.jl_read_only_label=new JLabel("Nodes Expanded: ");
        this.addComponent(jl_read_only_label, gbc, 8, 4, 1, 1);
        this.addComponent(jl_nodes_expanded, gbc, 9, 4, 2, 1);
        
        this.jl_read_only_label=new JLabel("Path length(kms): ");
        this.addComponent(jl_read_only_label, gbc, 8, 5, 1, 1);
        this.addComponent(jl_path_length, gbc, 9, 5, 2, 1);
        
        this.jl_read_only_label=new JLabel("Path: ");
        this.addComponent(jl_read_only_label, gbc, 0, 33, 1, 1);
        this.addComponent(jl_path, gbc, 1, 33, 8, 1);
        
    }

    private void addButtons() {
        this.addComponent(btn_start, gbc, 8,10,1,1);
        this.addComponent(btn_pause, gbc, 9,10,1,1);
        this.addComponent(btn_stop, gbc, 10,10,1,1);
    }

    private void addActionHandler() {
        EventHandler handler=new EventHandler();
        this.cb_algorithm.addActionListener(handler);
        this.cb_source.addActionListener(handler);
        this.cb_destination.addActionListener(handler);
        this.cb_sleep_time.addActionListener(handler);
        this.btn_start.addActionListener(handler);
        this.btn_pause.addActionListener(handler);
        this.btn_stop.addActionListener(handler);
        this.init_configuration();
    }
    
    private void init_configuration(){
        this.cb_destination.setEnabled(false);
        this.cb_algorithm.setEnabled(false);
        this.cb_sleep_time.setEnabled(false);
        this.btn_start.setEnabled(false);
        this.btn_pause.setEnabled(false);
        this.btn_stop.setEnabled(false);
    }

    private class EventHandler implements ActionListener{
        Thread th;
        @Override
        public void actionPerformed(ActionEvent e) {
             if(e.getSource()==cb_algorithm){
                 String algo=(String)cb_algorithm.getSelectedItem();
                 // Here get the algo index too
                 jl_algo_name.setText(algo);
                 cb_sleep_time.setEnabled(true);
             }
             else if(e.getSource()==cb_source){
                 String source=(String)cb_source.getSelectedItem();
                 // Here get the source index
                 jl_source_name.setText(source);
                 cb_destination.setEnabled(true);
                 graphics_panel.source=source;
                 graphics_panel.highLightCity(source);
             }
             else if(e.getSource()==cb_destination){
                 String destination=(String)cb_destination.getSelectedItem();
                 // Here get the algo index too
                 jl_destination_name.setText(destination);
                 cb_algorithm.setEnabled(true);
                 graphics_panel.destination=destination;
                 graphics_panel.highLightCity(destination);
             }
             else if(e.getSource()==cb_sleep_time){
                 String sleep_period=(String)cb_sleep_time.getSelectedItem();
                 // Here get the algo index too
                 jl_sleep_time.setText(sleep_period);
                 btn_start.setEnabled(true);
             }
             else if(e.getSource()==btn_start){
                 graphics_panel.refreshMap();
                 graphics_panel.distanceMap=distanceMap;
                 graphics_panel.source=cb_source.getSelectedItem().toString();
                 graphics_panel.destination=cb_destination.getSelectedItem().toString();
                 graphics_panel.mp=mp;
                 graphics_panel.algorithmName=cb_algorithm.getSelectedItem().toString();
                 mp.jl_path.setText("Discovering!");
                 try{
                     graphics_panel.sleepTime=Integer.valueOf(cb_sleep_time.getSelectedItem().toString());
                 }
                 catch(NumberFormatException ex){
                     return;
                 }
                 th=new Thread(graphics_panel);
                 th.start();
                 btn_pause.setEnabled(true);
                 btn_stop.setEnabled(true);
                 btn_start.setEnabled(false);
             }
             else if(e.getSource()==btn_pause){
                 if(!paused){
                    graphics_panel.pause();
                    btn_pause.setText("Resume");
                    paused=true;
                 }
                 else{
                     graphics_panel.resume();
                     btn_pause.setText("Pause");
                     paused=false;
                 }
                 btn_stop.setEnabled(true);
             }
             else if(e.getSource()==btn_stop){
                graphics_panel.stop();
                btn_pause.setEnabled(false);
                btn_start.setEnabled(true);
             }
             btn_start.setEnabled(true);
        }        
    }
}