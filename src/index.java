import java.io.*;
import java.util.*;
import java.util.function.Function;

public class index {
    // TODO 1: GUI������ �Ĺݿ�...
    // File Root Setting
    private static final String fromFileDir = "C:\\Users\\�����\\Desktop\\Growth\\�����ڷ�\\����2";
    private static final String toFileDir = "C:\\Users\\�����\\Desktop\\2022�⿩��\\javascript-algorithm\\sujeong";
    // Setup File Name
    private static String sectionName = "03";
    private static String sectionCategory = "string";

    private static File fromDir = new File(fromFileDir);
    private static File toDir = new File(toFileDir+"\\"+sectionCategory);

    // Main
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{
        System.out.println("���� ����� �۾� 17�� �ϱ� �����Ƽ� ���� ���α׷�");
        System.out.println("���߿� �����ϸ� �����ְ���");
        System.out.println("title : FileProcessIsKing");


        // Check File Directories
        String[] checkFileDirGuide = {"�Է��Ͻ� ���丮�� ���� �Է��� �����մϴ�.", "���丮 ��θ� �����մϴ�."};
        int checkFileDirNum = inputProceedNumber("������ ������ ���� ��ó : " + fromDir.getAbsolutePath() + "\n" +
                "������ ������ ���� ��ó : " + toDir.getAbsolutePath() + "\n" +
                "���� ���丮�� �½��ϱ�? \n [1] ���� \n [2] �ƴϿ�", checkFileDirGuide);
        switch (checkFileDirNum) {
            case 1:
                break;
            case 2: {
                String[] checkChangeDirGuide = {"���� ���� ��θ� �Է��Ͽ��ּ���", "���� ���� ��θ� �Է��Ͽ��ּ���", "������ �����մϴ�."};
                int inputChangeDir =
                        inputProceedNumber("�׸��� �����Ͽ��ּ���. \n[1] ���� ���� ��� ���� \n[2] ���� ���� ��� ���� \n[3] ����", checkChangeDirGuide);
                switch (inputChangeDir) {
                    case 1:
                    case 2:
                        fromDir = new File(br.readLine());
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("�ùٸ� ���� �ƴմϴ�. �߸� �Է��ϼ̽��ϴ�.");
                        System.exit(0);
                        break;
                }
            }
            default:
                System.exit(0);
        }

        // Check File Extension
        String extension = ".txt";
        if (!inputProceedBoolean(extension + " ���ϸ� Ȯ���մϴ�. �����Ͻʴϱ�? (Y/N)",
                "�Է��Ͻ� ���丮�� ���� �Է��� �����մϴ�.",
                "� Ȯ���ڸ� ����Ͻðڽ��ϱ�?(��:.txt)(���α׷� ���Ḧ ���Ѵٸ� N�� �Է����ּ���)")) {
            String inputExtension = br.readLine();
            if (Objects.equals(inputExtension, "N")) {
                System.exit(0);
            } else extension = inputExtension;
        }


        // Check Destination Directory is Right
        for (int index = 0; index < Objects.requireNonNull(fromDir.list()).length; index++) {
            String fileName = Objects.requireNonNull(fromDir.list())[index];
            if(!fileName.contains(extension)) continue;
            System.out.println("[" + index + "] " + fileName);
        }

        String[] checkFileListGuide = {"�Է��Ͻ� ���丮�� ���� �Է��� �����մϴ�.", "���α׷��� �����մϴ�. ���丮�� �ٽ� �Է����ּ���."};
        int inputFileListNum =
                inputProceedNumber("���� ���� ����Ʈ�� ������ �����ϴ�. ������ �����Ͻʴϱ�?\n[1] ���� \n[2] �ƴϿ�", checkFileListGuide); // TODO: �κм��� ��� �߰�
        switch (inputFileListNum) {
            case 1:
                break;
            default:
                System.exit(0);
        }

        // Read File
        ArrayList<String> problemNameArr = new ArrayList<>(); // fromDir�� ���� �̸��� ����
        for(String fileName :Objects.requireNonNull(fromDir.list())){
            // if file extension is not inputExtension, skip
            if (!fileName.contains(extension)) continue;

            // get file Name
            int fileNumber = Integer.parseInt(fileName.transform((Function<? super String, ? extends String>) s -> s.substring(0, s.indexOf("."))));
            String newFileName =
                    "[" + sectionName + String.format("%02d]", fileNumber) + fileName.substring(fileName.indexOf(".") + 1, fileName.lastIndexOf(".")).trim();
            System.out.println();
            int inputNewFileNameNum = inputProceedNumber("���ο� �������� ������ �����ϴ�.\n"+newFileName+"\n�����Ͻʴϱ�?\n[1] ���� \n[2] ����"
                    , new String[]{"�ش� ���������� ������ �����մϴ�.", "���ο� �������� �Է����ּ���. ���Ḧ ���Ͻø� ���͸� �����ּ���."});
            switch (inputNewFileNameNum) {
                case 2:
                    newFileName = br.readLine();
                    if(newFileName.equals("") || newFileName.equals("\n")){
                        System.exit(0);
                    }
                    System.out.println("�ش� ���Ϸ� �����մϴ�. \n"+newFileName);
                case 1:
                    // get file Content & Write File
                    String content = extractSolutionPart(fileName);
                    System.out.println("�ش� ������ ����:\n"+content);
                    writeFile(newFileName, extractSolutionPart(fileName));
                    break;
                default:
                    System.out.println("�ùٸ� ���� �ƴմϴ�. �߸� �Է��ϼ̽��ϴ�.");
                    System.exit(0);
                    break;
            }

        }
        System.out.println("���� �Է��� �Ϸ�Ǿ����ϴ�.");
        System.out.println(Arrays.toString(problemNameArr.toArray()));
    }

    // ���� �κ� ����
    public static String extractSolutionPart(String fileName) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(fromDir+"\\"+fileName));
        StringBuilder parts = new StringBuilder();

        //              isStartChecked    isEndChecked
        // �б� ��         false               false
        // �д� ��         true                false
        // ���� ��         false               true
        boolean isStartChecked = false;
        boolean isEndChecked = false;
        String line;
        while((line = br.readLine())!=null){
            if(line.contains("</script>")) isEndChecked = true;
            if(isStartChecked && !isEndChecked){
                parts.append(line+"\n");
            }
            if(line.contains("<script>")) isStartChecked = true;
        }
        return parts.toString().trim();
    }

    // ���� �ۼ��ϱ�
    public static void writeFile(String fileName, String content) throws IOException{
        // Check dir exists
        File root = new File(toDir+"\\"+fileName);
        if(!root.exists()) root.mkdirs();

        // Write index.js File
        File file = new File(toDir+"\\"+fileName+"\\index.js");
        if(!file.exists()) {
            System.out.println(file);
            if(!file.createNewFile()){
                System.out.println(fileName+"������ ���� ���� ����");
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        // TODO: Add Solution File Creation
        bw.write(content);
        bw.close();
    }

    public static Boolean inputProceedBoolean(String question, String yesGuide, String noGuide) throws IOException{
        System.out.println("\n"+question);
        String inputDirCheck  = br.readLine().toUpperCase(Locale.ROOT);
        if(inputDirCheck.equals("Y")) {
            System.out.println(yesGuide);
            return true;
        } else if(inputDirCheck.equals("N")) {
            System.out.println(noGuide);
            return false;
        } else{
            System.out.println("�ùٸ� �Է��� �ƴմϴ�. �ٽ� �Է����ּ���. (��Ҹ� ���ؼ� n �Ǵ� N�� �Է����ּ���)");
            return inputProceedBoolean(question, yesGuide, noGuide);
        }
    }

    public static int inputProceedNumber(String question, String[] guide) throws IOException{
        System.out.println("\n"+question);
        int inputDirCheckNum  = Integer.parseInt(br.readLine());
        if(guide.length < inputDirCheckNum) {
            System.out.println("�ùٸ� �Է��� �ƴմϴ�.");
        } else {
            System.out.println(guide[inputDirCheckNum-1]);
        }
        return inputDirCheckNum;
    }
}
