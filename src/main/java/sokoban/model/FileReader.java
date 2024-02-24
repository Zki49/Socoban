package sokoban.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    private Map  map;

    private List<String> elementFile = new ArrayList<>();
    public void open(File file) throws IOException {

    }
    public void generateMap(){

    }

    public Map getMap() {
        return map;
    }

    public void readFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        elementFile.clear();
        while (scanner.hasNextLine()) {
            elementFile.add(scanner.nextLine());
        }

    }

    public List<String> getElement() {
        return elementFile;
    }
}
