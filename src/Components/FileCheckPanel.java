package Components;

import javax.swing.*;
import java.awt.*;

public class FileCheckPanel extends Container {

    private MyFileChooser fromFileChooser;
    private MyFileChooser toFileChooser;
    public FileCheckPanel(MyFileChooser fromFileChooser, MyFileChooser toFileChooser){
        // set values
        this.fromFileChooser = fromFileChooser;
        this.toFileChooser = toFileChooser;

        // set layout
        this.setBackground(Color.WHITE);
        GridLayout grid = new GridLayout(1,2);
        grid.setHgap(10);
        this.setLayout(grid);

        // set Contents
        this.add(new JButton("TEST"));
        this.add(new JButton("TEST"));
    }

    public void initialize(){
    }
}
