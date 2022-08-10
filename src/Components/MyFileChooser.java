package Components;

import Global.EnumContants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class MyFileChooser extends JFileChooser {
    public enum EFileChooserType { FROM, TO };
    private int status;

    // Constructor
    public MyFileChooser(EFileChooserType isFromFile) {
        super();
        if(isFromFile.equals(EFileChooserType.FROM)) {
            this.setDialogTitle("추출 하고 싶은 파일을 모두 선택해주세요.");
            this.setMultiSelectionEnabled(true);
            this.setCurrentDirectory(new File(EnumContants.EFileDirectories.getFromFileDir()));
            this.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt only", "txt");
            this.setFileFilter(filter);
        } else {
            this.setDialogTitle("선택한 파일들이 생성될 폴더 하나를 선택해주세요.");
            this.setMultiSelectionEnabled(true);
            this.setCurrentDirectory(new File(EnumContants.EFileDirectories.getToFileDir()));
            this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
    }

    @Override
    public int showOpenDialog(Component parent) throws HeadlessException{
        return (status = super.showOpenDialog(parent));
    }

    @Override
    public String toString(){
        // if status is not equal to JFileChooser.APPROVE_OPTION, then return null
        if(status != JFileChooser.APPROVE_OPTION) return null;
        return "You chose to open this dir: " +
                    this.getCurrentDirectory() + "\n" +
                    "You chose to open this file: " +
                    Arrays.toString(this.getSelectedFiles());
    }
}
