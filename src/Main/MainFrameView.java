package Main;

import Components.*;
import Global.EnumContants.EMainFrameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;

import static Components.FileList.translateFileIntoName;

public class MainFrameView extends JFrame {
    // Attributes
    private static MyFileChooser fromFileChooser;
    private MyFileChooser toFileChooser;

    // Components
    private FileListPanel fileListPanel;
    private FileSelectPanel fileSelectingPanel;
    private FileCheckPanel fileCheckPanel;
    private final ProcessControlPanel processControlPanel;

    // Constructor
    public MainFrameView() {
        super("파일 변환기 madeby sujK");

        // set Attributes of the JFrame
        this.setLocation(EMainFrameView.x.getValue(), EMainFrameView.y.getValue());
        this.setSize(EMainFrameView.w.getValue(), EMainFrameView.h.getValue());
        this.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.fromFileChooser = new MyFileChooser(MyFileChooser.EFileChooserType.FROM);
        this.toFileChooser = new MyFileChooser(MyFileChooser.EFileChooserType.TO);

        Container c = getContentPane();
        c.setLayout(new BorderLayout(10,10));

        this.fileSelectingPanel = new FileSelectPanel(fromFileChooser, toFileChooser);
        c.add(fileSelectingPanel, BorderLayout.NORTH); // 파일 경로 설정

        this.fileListPanel = new FileListPanel(fromFileChooser, toFileChooser);
        c.add(fileListPanel, BorderLayout.WEST);

        this.fileCheckPanel = new FileCheckPanel(fromFileChooser, toFileChooser);
        c.add(fileCheckPanel, BorderLayout.CENTER);

        this.processControlPanel = new ProcessControlPanel();
        c.add(processControlPanel, BorderLayout.SOUTH);

    }

    // Initialize
    public void initialize(){
        this.fileSelectingPanel.initialize();
        this.fileListPanel.initialize();
        this.fileCheckPanel.initialize();
        this.processControlPanel.initialize();

    }

    // Event Handlers
    public static class UpdateFromFileList implements MouseListener {
        private final FileList fileListPanel;
        private final FileCheckPanel fileCheckPanel;

        public UpdateFromFileList(FileList fileListPanel, FileCheckPanel fileCheckPanel) {
            this.fileListPanel = fileListPanel;
            this.fileCheckPanel = fileCheckPanel;
        }
        @Override
        public void mouseClicked(MouseEvent e){}
        @Override
        public void mousePressed(MouseEvent e){}
        @Override
        public void mouseReleased(MouseEvent e){}
        @Override
        public void mouseEntered(MouseEvent e){
            String[] fileNames = translateFileIntoName(fromFileChooser.getSelectedFiles());
            fileListPanel.updateFileList(fileNames);
            System.out.println("FileNames: "+Arrays.toString(fileNames));
        }
        @Override
        public void mouseExited(MouseEvent e){}
    }
    public static class ListSelectionListener implements javax.swing.event.ListSelectionListener {
        @Override
        public void valueChanged(javax.swing.event.ListSelectionEvent e) {
            System.out.println("ListSelectionListener : "+e.getSource());
        }
    }
}
