package sokoban.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


class FileSaver {

    private MapDesign mapDesign;
    private static int defaultNumber = 1;
    private  static String defaultName = "board";
    private String nameOfFile;
    private File file;


    FileSaver(MapDesign mapDesign) {
        this.mapDesign = mapDesign;
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
        PrintWriter writer = new PrintWriter(new FileWriter(file));

        //appel la methode toString
        writer.print(mapDesign);
        writer.close();
    }

    public void setMap(MapDesign mapDesign) {
        this.mapDesign = mapDesign;
    }
}
