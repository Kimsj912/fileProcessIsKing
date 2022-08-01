package Components;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;

public class ProcessControlPanel extends JPanel {

    // Constructor
    public ProcessControlPanel(){
        // Attributes
        this.setBackground(Color.darkGray);
        this.setLayout(new FlowLayout( FlowLayout.RIGHT));
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        // Components
        JButton curFileTransferBtn = new JButton("현재 파일 변환");
        this.add(curFileTransferBtn);

        JButton allFileTransferBtn = new JButton("전체 파일 변환");
        this.add(allFileTransferBtn);
    }

    // Initialize
    public void initialize(){

    }
}
