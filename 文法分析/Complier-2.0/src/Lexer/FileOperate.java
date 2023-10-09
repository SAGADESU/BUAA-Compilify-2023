package Lexer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

public class FileOperate {

    public FileOperate(){

    }
    public static void createFile(String filePath) throws IOException{
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }else {
            System.out.println("file already exists\n");
        }
    }
    public String readSourceFile(String filePath) throws IOException {
        //file operate class
        String sourceFile = Files.readString(Paths.get(filePath));
        return sourceFile;
    }

    public static void writeFile(String filePath,String content) throws IOException{
        FileWriter fileWriter = new FileWriter(filePath,true);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }
    public FileWriter createFileWriter(String filePath) throws IOException {
        return new FileWriter(filePath);
    }
}
