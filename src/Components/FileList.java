package Components;

import Main.MainFrameView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileList extends JList<String> {
    // Attributes
    private DefaultListModel<String> model;
    private JScrollPane scrollPane;

    // Constructor
    public FileList(MyFileChooser fromFileChooser, MyFileChooser toFileChooser){
        // Attributes
        this.setBackground(Color.WHITE);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.scrollPane = new JScrollPane(this);
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        model = new DefaultListModel<String>();
        this.setModel(model);

    }

    // Initialize
    public void initialize(){
        this.addListSelectionListener(new MainFrameView.ListSelectionListener());

    }

    // Methods

    public static String[] translateFileIntoName(File[] files){
        return Arrays.stream(files).map(File::getName).toArray(String[]::new);
    }

    public void updateFileList(String[] fileNames){
        if(fileNames==null||fileNames.length==0) return;
        this.model.addAll(List.of(fileNames));
        scrollPane.getVerticalScrollBar().setValue(0);
    }

    public void clearFileList(){
        this.model.clear();
    }
}
