package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class FileSelectPanel extends JPanel {

    public FileSelectPanel(MyFileChooser fromFileChooser, MyFileChooser toFileChooser) {
        GridLayout grid = new GridLayout(2,2);
        grid.setVgap(10);
        grid.setHgap(10);
        this.setLayout(grid);
        this.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10)); //상하좌우 10씩 띄우기
        this.setBackground(Color.WHITE);

        JButton fromFileButton = new JButton("타겟 파일 선택");
        fromFileButton.addActionListener(e -> {
            int fromFileChooserStatus = fromFileChooser.showOpenDialog(this);
            if(fromFileChooserStatus == JFileChooser.APPROVE_OPTION) {
                System.out.println(fromFileChooser.toString());
                fromFileButton.setText(getNamesForBtn(fromFileChooser.getSelectedFiles()));
            }
        });

        JButton toFileButton = new JButton("결과 파일 위치 선택");
        toFileButton.addActionListener(e -> {
            int toFileReturnVal = toFileChooser.showOpenDialog(this);
            if(toFileReturnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println(toFileChooser.toString());
                toFileButton.setText(getNamesForBtn(toFileChooser.getSelectedFiles()));
            }
        });

        // Add Elements
        this.add(new JLabel("타겟 파일 선택"));
        this.add(new JLabel("결과 위치 선택"));
        this.add(fromFileButton);
        this.add(toFileButton);
    }

    public String getNamesForBtn(File[] selectedFiles){
        StringBuilder sb = new StringBuilder();
        for(File f : selectedFiles){
            if(f.equals(selectedFiles[selectedFiles.length-1])) sb.append(f.getName());
            else sb.append(f.getName()).append(", ");
        }
        return sb.toString();
    }

    public void initialize(){
    }
}
