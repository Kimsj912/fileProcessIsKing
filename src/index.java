import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class index {
    // File Root Setting
    private static final String fromFileDir = "C:\\Users\\�����\\Desktop\\Growth\\�����ڷ�\\����1";
    private static final String toFileDir = "C:\\Users\\�����\\Desktop\\2022�⿩��\\javascript-algorithm\\sujeong\\basic";
    private static String sectionName = "01";

    private static File fromDir = new File(fromFileDir);
    private static File toDir = new File(toFileDir);
    // Main
    public static void main(String[] args) throws IOException{
        System.out.println("���� ����� �۾� 17�� �ϱ� �����Ƽ� ���� ���α׷�");
        System.out.println("���߿� �����ϸ� �����ְ���");
        System.out.println("title : FileProcessIsKing");


        // Read File
        ArrayList<String> problemNameArr = new ArrayList<>();
        for (String fileName : Objects.requireNonNull(fromDir.list())) {
            if(!fileName.contains(".txt")) continue;
            // get file Name
            int fileNumber = Integer.parseInt(fileName.transform((Function<? super String, ? extends String>) s -> s.substring(0,s.indexOf("."))));
            String newFileName = "["+sectionName+String.format("%02d]",fileNumber)+fileName.substring(fileName.indexOf(".") + 1, fileName.lastIndexOf(".")).trim();
            System.out.println(newFileName);
            // get file Content
            String content = extractSolutionPart(fileName);
            System.out.println(content);
            writeFile(newFileName, extractSolutionPart(fileName));
        }
        System.out.println(Arrays.toString(problemNameArr.toArray()));

    }

    public static String extractSolutionPart(String fileName) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(fromDir+"\\"+fileName));
        StringBuilder parts = new StringBuilder();

        while(br.readLine()!=null){
            parts.append(br.readLine());
        }
        parts.delete(0, parts.indexOf("f"));
        parts.delete(parts.indexOf("</"), parts.length());
        return parts.toString().trim();
    }

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
        bw.write(content);
        bw.close();
    }
}
