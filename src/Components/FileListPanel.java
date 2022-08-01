package Components;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileListPanel extends JPanel {
    // Attributes
    private FileList fileList;

    // Constructor
    public FileListPanel(MyFileChooser fromFileChooser, MyFileChooser toFileChooser){
        // Attributes
        this.setLayout(new BorderLayout(10,10));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Components
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new GridLayout(1,2,10,10));
        buttonPanel.setBackground(Color.WHITE);
        JButton updateBtn = new JButton("lsit Update");
        updateBtn.addActionListener(e -> {
            this.updateFileList(fromFileChooser.getSelectedFiles());
        });
        JButton resetBtn = new JButton("list reset");
        resetBtn.addActionListener(e -> {
            this.clearFileList();
        });
        buttonPanel.add(updateBtn);
        buttonPanel.add(resetBtn);
        this.add(buttonPanel, BorderLayout.NORTH);

        this.fileList = new FileList(fromFileChooser, toFileChooser);
        this.add(fileList, BorderLayout.CENTER);


    }

    // Initialize
    public void initialize(){
        this.fileList.initialize();
    }

    // Methods
    public void updateFileList(File[] files){
        this.fileList.updateFileList(FileList.translateFileIntoName(files));
    }
    public void clearFileList(){
        this.fileList.clearFileList();
    }
}
