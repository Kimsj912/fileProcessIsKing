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
        JButton curFileTransferBtn = new JButton("���� ���� ��ȯ");
        this.add(curFileTransferBtn);

        JButton allFileTransferBtn = new JButton("��ü ���� ��ȯ");
        this.add(allFileTransferBtn);
    }

    // Initialize
    public void initialize(){

    }
}
