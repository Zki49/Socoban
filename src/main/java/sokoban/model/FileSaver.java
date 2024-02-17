package sokoban.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class FileSaver {

    private  final Map map;
    private static int defaultNumber = 1;
    private  static String defaultName = "board";
    private String nameOfFile;
    private File file;


    public FileSaver(Map map) {
        this.map = map;
        nameOfFile  = defaultName + defaultNumber;
        defaultNumber++;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }
    public void save(File file)throws IOException {
        /*String filePath = "boards" +File.separator + nameOfFile + ".xsb";
        this.file = new File(filePath);
        PrintWriter writer = new PrintWriter(this.file);*/
        PrintWriter writer = new PrintWriter(new FileWriter(file));

        writer.print(map);
        writer.close();
    }
}
