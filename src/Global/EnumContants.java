package Global;

public class EnumContants {
    // MainFrameView Enum Data (Set Frame Size etc.)
    public enum EMainFrameView {
        x(200), y(100), w(800), h(600);

        private int value;
        EMainFrameView(int value) {this.value = value; }
        public int getValue() { return this.value;}
    }

    public enum EFileDirectories {
        ;
        // File Root Setting
        private static final String fromFileDir = "C:\\Users\\�����\\Desktop\\Growth\\�����ڷ�\\����4";
        private static final String toFileDir = "C:\\Users\\�����\\Desktop\\2022�⿩��\\javascript-algorithm\\sujeong";
        private static final String defaultExtension = "txt";

        public static String getFromFileDir(){
            return fromFileDir;
        }

        public static String getToFileDir(){
            return toFileDir;
        }
        public static String getDefaultExtension(){
            return defaultExtension;
        }
    }

}
