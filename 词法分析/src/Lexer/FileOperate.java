package Lexer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOperate {

    public FileOperate(){

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
