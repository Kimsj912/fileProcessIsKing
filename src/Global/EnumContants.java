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
        private static final String fromFileDir = "C:\\Users\\김수정\\Desktop\\Growth\\강의자료\\섹션4";
        private static final String toFileDir = "C:\\Users\\김수정\\Desktop\\2022년여름\\javascript-algorithm\\sujeong";
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
