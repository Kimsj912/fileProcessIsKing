package Main;

import Components.MyFileChooser;
import Global.EnumContants.EMainFrameView;

import javax.swing.*;

public class MainFrameView extends JFrame {
    // Constructor
    public MainFrameView() {
        super("파일 변환기 madeby sujK");

        // set Attributes of the JFrame
        this.setLocation(EMainFrameView.x.getValue(), EMainFrameView.y.getValue());
        this.setSize(EMainFrameView.w.getValue(), EMainFrameView.h.getValue());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // get From File Directory
        MyFileChooser fromFileChooser = new MyFileChooser(MyFileChooser.EFileChooserType.FROM);
        int fromFileChooserStatus = fromFileChooser.showOpenDialog(this);
        if(fromFileChooserStatus == JFileChooser.APPROVE_OPTION) {
            System.out.println(fromFileChooser.toString());
        }

        // get To File Directory
        MyFileChooser toFileChooser = new MyFileChooser(MyFileChooser.EFileChooserType.TO);
        int toFileReturnVal = toFileChooser.showOpenDialog(this);
        if(toFileReturnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println(toFileChooser.toString());
        }


    }


    public void initialize(){
    }
}
