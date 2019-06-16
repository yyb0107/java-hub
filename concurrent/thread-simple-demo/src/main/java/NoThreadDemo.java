import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bingo
 * @title: NoThreadDemo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/16  17:39
 */
public class NoThreadDemo {

    public static void main(String[] args) {
        List<File> files = new ArrayList<>();
        String targetDir = "";
        FileCopy fileCopy = new FileCopy(files,targetDir);
        fileCopy.copy();
    }


}
