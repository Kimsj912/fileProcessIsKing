import Components.MyFileChooser;
import Global.EnumContants.EFileDirectories;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

import static Global.EnumContants.EFileDirectories.*;

public class index {
    // TODO 1: GUI������ �Ĺݿ�...

    // Setup File Name
    private static String sectionName = "7";

    private static File fromDir;
    private static File toDir;

    // Main.Main
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{
        System.out.println("���� ����� �۾� ������ �ϱ� �����Ƽ� ���� ���α׷�");
        System.out.println("���߿� �����ϸ� �����ְ���");
        System.out.println("title : FileProcessIsKing");
        System.out.println("=======================================================");

        // FileChooser (from)
        MyFileChooser fromFileChooser = new MyFileChooser(MyFileChooser.EFileChooserType.FROM);
        int fromFileChooserStatus = fromFileChooser.showOpenDialog(null);
        while(fromFileChooserStatus != JFileChooser.APPROVE_OPTION){
            if(fromFileChooserStatus == JFileChooser.CANCEL_OPTION){
                System.out.println("��ҵǾ����ϴ�.");
                System.exit(0);
            }
            fromFileChooserStatus = fromFileChooser.showOpenDialog(null);
        }
        File[] curDir = fromFileChooser.getSelectedFiles();
        fromDir = fromFileChooser.getSelectedFiles()[0].getAbsoluteFile();

        sectionName = fromDir.getParent().substring(fromDir.getParent().lastIndexOf("\\")+1);

        assert curDir != null;
        int curDirSize = curDir.length;

        // FileChooser (to)
        MyFileChooser toFileChooser = new MyFileChooser(MyFileChooser.EFileChooserType.TO);
        int toFileChooserStatus = toFileChooser.showOpenDialog(null);
        while(toFileChooserStatus != JFileChooser.APPROVE_OPTION){
            if(toFileChooserStatus == JFileChooser.CANCEL_OPTION){
                System.out.println("��ҵǾ����ϴ�.");
                System.exit(0);
            }
            toFileChooserStatus = toFileChooser.showOpenDialog(null);
        }
        toDir = toFileChooser.getSelectedFiles()[0].getAbsoluteFile();

        // Read File
        boolean allAccept = false;
        for (int index = 1; index <= curDirSize; index++) {
            String fileName = curDir[index-1].getName();
            // if file extension is not inputExtension, skip
            if (!fileName.contains(getDefaultExtension())) continue;

            // get file Name
            int fileNumber = Integer.parseInt(fileName.transform((Function<? super String, ? extends String>) s -> s.substring(0, s.indexOf("."))));
            String newFileName =
                    "["+ String.format("%02d",Integer.parseInt(sectionName.substring(2))) + String.format("%02d]", fileNumber) + fileName.substring(fileName.indexOf(".") + 1, fileName.lastIndexOf(".")).trim();
            System.out.println();

            // ��ü ���� �Ŀ� �ش� if�������� �۾����� �ݺ�
            if(allAccept){
                // get file Content & Write File
                System.out.println("���ο� �������� ������ �����ϴ�.\n"+newFileName);
                String content = extractSolutionPart(fileName);
                System.out.println("�ش� ������ ����:\n"+content);
                writeFile(newFileName, extractSolutionPart(fileName));
                continue;
            }

            // ��ü ���� �ƴϸ� �ش� switch ���� ���� ���ϴ� �ൿ�� ������ �� �ְ� ��
            int inputNewFileNameNum = inputProceedNumber("���ο� �������� ������ �����ϴ�.\n"+newFileName+"\n�����Ͻʴϱ�?\n[1] ���� \n[2] ����\n[3] ���� ��ü ����"
                    , new String[]{"�ش� ���������� ������ �����մϴ�.", "���ο� �������� �Է����ּ���. ���Ḧ ���Ͻø� ���͸� �����ּ���.", "���� ��� ������ ������ �����մϴ�."});
            switch (inputNewFileNameNum) {
                case 2:
                    // get new File name from User
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
                case 3: // ��ü ����
                    allAccept =true;
                    break;
                default:
                    System.out.println("�ùٸ� ���� �ƴմϴ�. �߸� �Է��ϼ̽��ϴ�.");
                    System.exit(0);
                    break;
            }
        }

        // Terminate file processing
        System.out.println("���� �Է��� �Ϸ�Ǿ����ϴ�.");
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
                parts.append("""
                        // Ǯ�̽ð�(�ҿ�ð�)

                        // ���̵��

                        // ��������

                        +console.time(`time`);
                        """);
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

        // ���� ���� �ۼ� �� ���ڵ� ����
        OutputStreamWriter bw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        bw.write(content);
        bw.close();
    }

    // ���� ���� ó�� �Լ�
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
