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
    // TODO 1: GUI만들기는 후반에...

    // Setup File Name
    private static String sectionName = "7";

    private static File fromDir;
    private static File toDir;

    // Main.Main
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{
        System.out.println("파일 만드는 작업 여러번 하기 귀찮아서 만든 프로그램");
        System.out.println("나중에 개선하면 쓸모있겄지");
        System.out.println("title : FileProcessIsKing");
        System.out.println("=======================================================");

        // FileChooser (from)
        MyFileChooser fromFileChooser = new MyFileChooser(MyFileChooser.EFileChooserType.FROM);
        int fromFileChooserStatus = fromFileChooser.showOpenDialog(null);
        while(fromFileChooserStatus != JFileChooser.APPROVE_OPTION){
            if(fromFileChooserStatus == JFileChooser.CANCEL_OPTION){
                System.out.println("취소되었습니다.");
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
                System.out.println("취소되었습니다.");
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

            // 전체 동의 후엔 해당 if문까지의 작업만을 반복
            if(allAccept){
                // get file Content & Write File
                System.out.println("새로운 폴더명은 다음과 같습니다.\n"+newFileName);
                String content = extractSolutionPart(fileName);
                System.out.println("해당 파일의 내용:\n"+content);
                writeFile(newFileName, extractSolutionPart(fileName));
                continue;
            }

            // 전체 동의 아니면 해당 switch 문을 통해 원하는 행동을 선택할 수 있게 함
            int inputNewFileNameNum = inputProceedNumber("새로운 폴더명은 다음과 같습니다.\n"+newFileName+"\n동의하십니까?\n[1] 동의 \n[2] 거절\n[3] 이후 전체 동의"
                    , new String[]{"해당 폴더명으로 생성을 진행합니다.", "새로운 폴더명을 입력해주세요. 종료를 원하시면 엔터를 눌러주세요.", "이후 모든 파일의 생성을 시작합니다."});
            switch (inputNewFileNameNum) {
                case 2:
                    // get new File name from User
                    newFileName = br.readLine();
                    if(newFileName.equals("") || newFileName.equals("\n")){
                        System.exit(0);
                    }
                    System.out.println("해당 파일로 진행합니다. \n"+newFileName);
                case 1:
                    // get file Content & Write File
                    String content = extractSolutionPart(fileName);
                    System.out.println("해당 파일의 내용:\n"+content);
                    writeFile(newFileName, extractSolutionPart(fileName));
                    break;
                case 3: // 전체 동의
                    allAccept =true;
                    break;
                default:
                    System.out.println("올바른 값이 아닙니다. 잘못 입력하셨습니다.");
                    System.exit(0);
                    break;
            }
        }

        // Terminate file processing
        System.out.println("파일 입력이 완료되었습니다.");
    }

    // 문제 부분 추출
    public static String extractSolutionPart(String fileName) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(fromDir+"\\"+fileName));
        StringBuilder parts = new StringBuilder();

        //              isStartChecked    isEndChecked
        // 읽기 전         false               false
        // 읽는 중         true                false
        // 읽은 후         false               true
        boolean isStartChecked = false;
        boolean isSolutionChecked = false;
        String line = "";
        while((line = br.readLine())!=null ){
            // 맨 마지막 줄
            if(line.contains("</script>")) break;

            // 맨 첫 줄
            else if(line.contains("<script>")) {
                isStartChecked = true;
                parts.append("""
                        // 풀이시간(소요시간)

                        // 아이디어

                        // 개선과정

                        +console.time(`time`);
                        """);
            }

            // script 내부일 때
            else if(isStartChecked){
                // solution 함수 시작
                if(line.contains("function solution")) {
                    isSolutionChecked = true;
                    parts.append(line+"\n");
                // solution 함수 종료
                } else if(isSolutionChecked && line.contains("return answer")) isSolutionChecked = false;
                // solution 함수 이외의 내용
                else if(isSolutionChecked) continue;
                else parts.append(line+"\n");
            }
        }
        return parts.toString().trim().concat("\nconsole.timeEnd('time');");
    }

    // 파일 작성하기
    public static void writeFile(String fileName, String content) throws IOException{
        // Check dir exists
        File root = new File(toDir+"\\"+fileName);
        if(!root.exists()) root.mkdirs();

        // Write index.js File
        File file = new File(toDir+"\\"+fileName+"\\index.js");
        if(!file.exists()) {
            System.out.println(file);
            if(!file.createNewFile()){
                System.out.println(fileName+"에서의 파일 생성 실패");
            }
        }

        // 실제 파일 작성 및 인코딩 설정
        OutputStreamWriter bw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        bw.write(content);
        bw.close();
    }

    // 숫자 선택 처리 함수
    public static int inputProceedNumber(String question, String[] guide) throws IOException{
        System.out.println("\n"+question);
        int inputDirCheckNum  = Integer.parseInt(br.readLine());
        if(guide.length < inputDirCheckNum) {
            System.out.println("올바른 입력이 아닙니다.");
        } else {
            System.out.println(guide[inputDirCheckNum-1]);
        }
        return inputDirCheckNum;
    }
}
