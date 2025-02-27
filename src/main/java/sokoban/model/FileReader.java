package sokoban.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileReader {


    private String nameFile ;
    private List<String> elementFile = new ArrayList<>();




    void readFile(File file, String nameFile) throws FileNotFoundException {
        this.nameFile = nameFile;
        Scanner scanner = new Scanner(file);
        elementFile.clear();
        while (scanner.hasNextLine()) {
            elementFile.add(scanner.nextLine());
        }

    }

     List<String> getElement() {
        return elementFile;
    }



     String getNameFile() {
        return nameFile;
    }

     boolean isValideFile(File file) throws FileNotFoundException {

            Scanner scanner = new Scanner(file);
            elementFile.clear();
            while (scanner.hasNextLine()) {
                elementFile.add(scanner.nextLine());
            }
            if(!elementFile.isEmpty()) {
                return isValideSize() && isValideElementInfile();
            }
            return false;

    }
     boolean isValideSize(){
        String firstLine = elementFile.get(0);
        if(firstLine.length() >= 10 && firstLine.length() <= 50){
            for(String line : elementFile){
                if(line.length() != firstLine.length())
                    return false;
            }
            return elementFile.size() >= 10 && elementFile.size() <= 50;
        }

        return false;

    }
     boolean isValideElementInfile(){
        for(String line : elementFile){
            for(int i = 0; i < line.length(); ++i){
                if(!CellDesign.isValideSymbole(line.charAt(i))){
                    return false;
                }
            }
        }
        return true;
    }
}
