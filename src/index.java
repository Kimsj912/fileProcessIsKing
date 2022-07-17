import java.io.*;
import java.util.*;
import java.util.function.Function;

public class index {
    // TODO 1: GUI만들기는 후반에...
    // File Root Setting
    private static final String fromFileDir = "C:\\Users\\김수정\\Desktop\\Growth\\강의자료\\섹션2";
    private static final String toFileDir = "C:\\Users\\김수정\\Desktop\\2022년여름\\javascript-algorithm\\sujeong";
    // Setup File Name
    private static String sectionName = "03";
    private static String sectionCategory = "string";

    private static File fromDir = new File(fromFileDir);
    private static File toDir = new File(toFileDir+"\\"+sectionCategory);

    // Main
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{
        System.out.println("파일 만드는 작업 17번 하기 귀찮아서 만든 프로그램");
        System.out.println("나중에 개선하면 쓸모있겄지");
        System.out.println("title : FileProcessIsKing");


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
        for (int index = 0; index < Objects.requireNonNull(fromDir.list()).length; index++) {
            String fileName = Objects.requireNonNull(fromDir.list())[index];
            if(!fileName.contains(extension)) continue;
            System.out.println("[" + index + "] " + fileName);
        }

        String[] checkFileListGuide = {"입력하신 디렉토리로 파일 입력을 진행합니다.", "프로그램을 종료합니다. 디렉토리를 다시 입력해주세요."};
        int inputFileListNum =
                inputProceedNumber("현재 파일 리스트는 다음과 같습니다. 진행을 동의하십니까?\n[1] 맞음 \n[2] 아니오", checkFileListGuide); // TODO: 부분선택 기능 추가
        switch (inputFileListNum) {
            case 1:
                break;
            default:
                System.exit(0);
        }

        // Read File
        ArrayList<String> problemNameArr = new ArrayList<>(); // fromDir의 파일 이름들 저장
        for(String fileName :Objects.requireNonNull(fromDir.list())){
            // if file extension is not inputExtension, skip
            if (!fileName.contains(extension)) continue;

            // get file Name
            int fileNumber = Integer.parseInt(fileName.transform((Function<? super String, ? extends String>) s -> s.substring(0, s.indexOf("."))));
            String newFileName =
                    "[" + sectionName + String.format("%02d]", fileNumber) + fileName.substring(fileName.indexOf(".") + 1, fileName.lastIndexOf(".")).trim();
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
