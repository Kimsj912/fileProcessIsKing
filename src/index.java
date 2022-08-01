import Global.EnumContants.EFileDirectories;

import java.io.*;
import java.util.*;
import java.util.function.Function;

import static Global.EnumContants.EFileDirectories.*;

public class index {
    // TODO 1: GUI������ �Ĺݿ�...

    // Setup File Name
    private static String sectionName = "7";
    private static String sectionCategory = "Stack";

    private static File fromDir = new File(EFileDirectories.getFromFileDir()+"\\����"+sectionName);
    private static File toDir = new File(EFileDirectories.getToFileDir()+"\\"+sectionCategory);

    // Main.Main
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{
        System.out.println("���� ����� �۾� ������ �ϱ� �����Ƽ� ���� ���α׷�");
        System.out.println("���߿� �����ϸ� �����ְ���");
        System.out.println("title : FileProcessIsKing");
        System.out.println("=======================================================");

//        <-----------------view����� Filechooser��������ν� �ʿ������ �κ�------------

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
        String[] curDir = Objects.requireNonNull(fromDir.list());
        int curDirSize = curDir.length;
        for (int index = 1; index <= curDirSize; index++) {
            String fileName = curDir[index-1];
            if(!fileName.contains(extension)) continue;
            System.out.println("[" + index + "] " + fileName);
        }

        ArrayList<Integer> selectedIdxes = new ArrayList<>();
        String[] checkFileListGuide = {"�Է��Ͻ� ���丮�� ���� �Է��� �����մϴ�.","������ ���� �ε����� ����� �����Ͽ� �Է��Ͻÿ�." , "�������� ���� ���� �ε����� ����� �����Ͽ� �Է��Ͻÿ�.", "���α׷��� �����մϴ�. ���丮�� �ٽ� �Է����ּ���."};
        int inputFileListNum =
                inputProceedNumber("���� ���� ����Ʈ�� ������ �����ϴ�. ������ �����Ͻʴϱ�?\n[1] ��� ���� \n[2] �κ� ���� \n[3] �κ� ���� \n[4] ����", checkFileListGuide); // TODO: �κм��� ��� �߰�
        switch (inputFileListNum) {
            case 1:
                for (int index = 1; index <= curDirSize; index++) selectedIdxes.add(index);
                break;
            case 2: {
                // add inputFileListOrg's index
                String inputFileListOrg = br.readLine();
                if(Objects.equals(inputFileListOrg, "")) {
                    System.out.println("�Է��Ͻ� ���� �����ϴ�. �߸� �Է��ϼ̽��ϴ�.");
                    System.exit(0);
                }
                String[] inputFileList =inputFileListOrg.replace("\n"," ").strip().split(" ");
                for (String s : inputFileList) {
                    selectedIdxes.add(Integer.parseInt(s));
                }
                break;
            }
            case 3: {
                // remove inputFileListOrg's index
                String inputFileListOrg = br.readLine();
                if(Objects.equals(inputFileListOrg, "")) {
                    System.out.println("�Է��Ͻ� ���� �����ϴ�. �߸� �Է��ϼ̽��ϴ�.");
                    System.exit(0);
                }
                String[] inputFileList =inputFileListOrg.replace("\n"," ").strip().split(" ");
                int[] inputFileListInt = Arrays.stream(inputFileList).mapToInt(Integer::parseInt).toArray();

                for (int idx = 1; idx <= curDirSize; idx++) {
                    int finalIdx = idx;
                    if(Arrays.stream(inputFileListInt).anyMatch(i -> i == finalIdx)) continue;
                    selectedIdxes.add(idx);
                }
                break;
            }
            default:
                System.exit(0);
                break;
        }
//        ------------view����� Filechooser��������ν� �ʿ������ �κ�---------------------------->


        // TODO (Temporally variable, must be deleted)
//        String[] curDir = Objects.requireNonNull(fromDir.list());
//        int curDirSize = 10;
//        ArrayList<Integer> selectedIdxes = new ArrayList<>( Arrays.asList(1,2,3,4,5,6,7,8,9,10));

        // Read File
        ArrayList<String> problemNameArr = new ArrayList<>(); // fromDir�� ���� �̸��� ����
        for (int index = 1; index <= curDirSize; index++) {
            String fileName = curDir[index-1];
            // if file extension is not inputExtension, skip
            if (!fileName.contains(getDefaultExtension())) continue;
            // if file index is not in selectedIdxes, skip
            if(!selectedIdxes.contains(index)) continue;

            // get file Name
            int fileNumber = Integer.parseInt(fileName.transform((Function<? super String, ? extends String>) s -> s.substring(0, s.indexOf("."))));
            String newFileName =
                    "[0" + sectionName + String.format("%02d]", fileNumber) + fileName.substring(fileName.indexOf(".") + 1, fileName.lastIndexOf(".")).trim();
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
        boolean isSolutionChecked = false;
        String line = "";
        while((line = br.readLine())!=null ){
            // �� ������ ��
            if(line.contains("</script>")) break;

            // �� ù ��
            else if(line.contains("<script>")) {
                isStartChecked = true;
                parts.append("console.time(`time`);\n");
            }

            // script ������ ��
            else if(isStartChecked){
                // solution �Լ� ����
                if(line.contains("function solution")) {
                    isSolutionChecked = true;
                    parts.append(line+"\n");
                // solution �Լ� ����
                } else if(isSolutionChecked && line.contains("return answer")) isSolutionChecked = false;
                // solution �Լ� �̿��� ����
                else if(isSolutionChecked) continue;
                else parts.append(line+"\n");
            }
        }
        return parts.toString().trim().concat("\nconsole.timeEnd('time');");
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
