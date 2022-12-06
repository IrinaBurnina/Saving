package saving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress levelOne = new GameProgress(300, 10, 1, 123.5);
        GameProgress levelTwo = new GameProgress(250, 13, 2, 1234.8);
        GameProgress levelFive = new GameProgress(111, 22, 5, 4352.3);
        List<String> listOfSavings = new ArrayList<>();
        File dir = new File("E://Games/saveGames/");
        File file1 = new File(dir, "save1.dat");
        File file2 = new File(dir, "save2.dat");
        File file3 = new File(dir, "save3.dat");
        String trace = "E://Games/saveGames/";
        listOfSavings.add(saveGame(trace + file1.getName(), levelOne));
        listOfSavings.add(saveGame(trace + file2.getName(), levelTwo));
        listOfSavings.add(saveGame(trace + file3.getName(), levelFive));
        zipFiles("E://Games/saveGames/zipOutputGames.zip", listOfSavings);
        for (File item :
                dir.listFiles()) {
            if (item.getName().equals(file1.getName()) || item.getName().equals(file2.getName()) || item.getName().equals(file3.getName())) {
                item.deleteOnExit();
            }

        }

    }

    public static String saveGame(String trace, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(trace, true);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return trace;
    }

    public static void zipFiles(String traceToArchive, List<String> traceObjectToZip) {
        int k = 0;
        for (String traceToZip :
                traceObjectToZip) {
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(traceToArchive, true));
                 FileInputStream fis = new FileInputStream(traceToZip)) {

                k += 1;
                ZipEntry entry = new ZipEntry(k + "save.txt");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}


