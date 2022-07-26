package Main;

public class Main {
    static private MainFrameView mainFrameView;
    public static void main(String[] args){
        mainFrameView = new MainFrameView();
        mainFrameView.initialize();
        mainFrameView.setVisible(true);
    }
}
