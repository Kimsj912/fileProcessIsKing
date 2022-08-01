import Global.EnumContants.EFileDirectories;

import java.io.*;
import java.util.*;
import java.util.function.Function;

import static Global.EnumContants.EFileDirectories.*;

public class index {
    // TODO 1: GUI만들기는 후반에...

    // Setup File Name
    private static String sectionName = "7";
    private static String sectionCategory = "Stack";

    private static File fromDir = new File(EFileDirectories.getFromFileDir()+"\\섹션"+sectionName);
    private static File toDir = new File(EFileDirectories.getToFileDir()+"\\"+sectionCategory);

    // Main.Main
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{
        System.out.println("파일 만드는 작업 여러번 하기 귀찮아서 만든 프로그램");
        System.out.println("나중에 개선하면 쓸모있겄지");
        System.out.println("title : FileProcessIsKing");
        System.out.println("=======================================================");

//        <-----------------view만들며 Filechooser사용함으로써 필요없어진 부분------------

        // Check File Directories
        String[] checkFileDirGuide = {"입력하신 디렉토리로 파일 입력을 진행합니다.", "디렉토리 경로를 변경합니다."};
        int checkFileDirNum = inputProceedNumber("파일을 추출할 문서 출처 : " + fromDir.getAbsolutePath() + "\n" +
                "파일을 생성할 문서 출처 : " + toDir.getAbsolutePath() + "\n" +
                "파일 디렉토리가 맞습니까? \n [1] 맞음 \n [2] 아니오", checkFileDirGuide);
        switch (checkFileDirNum) {
            case 1:
                break;
            case 2: {
                String[] checkChangeDirGuide = {"추출 문서 경로를 입력하여주세요", "생성 문서 경로를 입력하여주세요", "변경을 종료합니다."};
                int inputChangeDir =
                        inputProceedNumber("항목을 선택하여주세요. \n[1] 추출 문서 경로 수정 \n[2] 생성 문서 경로 수정 \n[3] 종료", checkChangeDirGuide);
                switch (inputChangeDir) {
                    case 1:
                    case 2:
                        fromDir = new File(br.readLine());
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("올바른 값이 아닙니다. 잘못 입력하셨습니다.");
                        System.exit(0);
                        break;
                }
            }
            default:
                System.exit(0);
        }

        // Check File Extension
        String extension = ".txt";
        if (!inputProceedBoolean(extension + " 파일만 확인합니다. 동의하십니까? (Y/N)",
                "입력하신 디렉토리로 파일 입력을 진행합니다.",
                "어떤 확장자를 사용하시겠습니까?(예:.txt)(프로그램 종료를 원한다면 N을 입력해주세요)")) {
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
        String[] checkFileListGuide = {"입력하신 디렉토리로 파일 입력을 진행합니다.","선택할 파일 인덱스를 띄어쓰기로 구분하여 입력하시오." , "변경하지 않을 파일 인덱스를 띄어쓰기로 구분하여 입력하시오.", "프로그램을 종료합니다. 디렉토리를 다시 입력해주세요."};
        int inputFileListNum =
                inputProceedNumber("현재 파일 리스트는 다음과 같습니다. 진행을 동의하십니까?\n[1] 모두 진행 \n[2] 부분 선택 \n[3] 부분 제거 \n[4] 종료", checkFileListGuide); // TODO: 부분선택 기능 추가
        switch (inputFileListNum) {
            case 1:
                for (int index = 1; index <= curDirSize; index++) selectedIdxes.add(index);
                break;
            case 2: {
                // add inputFileListOrg's index
                String inputFileListOrg = br.readLine();
                if(Objects.equals(inputFileListOrg, "")) {
                    System.out.println("입력하신 값이 없습니다. 잘못 입력하셨습니다.");
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
                    System.out.println("입력하신 값이 없습니다. 잘못 입력하셨습니다.");
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
//        ------------view만들며 Filechooser사용함으로써 필요없어진 부분---------------------------->


        // TODO (Temporally variable, must be deleted)
//        String[] curDir = Objects.requireNonNull(fromDir.list());
//        int curDirSize = 10;
//        ArrayList<Integer> selectedIdxes = new ArrayList<>( Arrays.asList(1,2,3,4,5,6,7,8,9,10));

        // Read File
        ArrayList<String> problemNameArr = new ArrayList<>(); // fromDir의 파일 이름들 저장
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
            int inputNewFileNameNum = inputProceedNumber("새로운 폴더명은 다음과 같습니다.\n"+newFileName+"\n동의하십니까?\n[1] 동의 \n[2] 거절"
                    , new String[]{"해당 폴더명으로 생성을 진행합니다.", "새로운 폴더명을 입력해주세요. 종료를 원하시면 엔터를 눌러주세요."});
            switch (inputNewFileNameNum) {
                case 2:
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
                default:
                    System.out.println("올바른 값이 아닙니다. 잘못 입력하셨습니다.");
                    System.exit(0);
                    break;
            }

        }
        System.out.println("파일 입력이 완료되었습니다.");
        System.out.println(Arrays.toString(problemNameArr.toArray()));
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
                parts.append("console.time(`time`);\n");
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
            System.out.println("올바른 입력이 아닙니다. 다시 입력해주세요. (취소를 위해선 n 또는 N을 입력해주세요)");
            return inputProceedBoolean(question, yesGuide, noGuide);
        }
    }

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
