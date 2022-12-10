package saving;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
        if (dir.listFiles() != null) {
            for (File item : dir.listFiles()) {
                if (item.getName().equals(file1.getName()) || item.getName().equals(file2.getName()) ||
                        item.getName().equals(file3.getName()))
                    item.delete();
            }
        }
        openZip("E://Games/saveGames/zipOutputGames.zip", "E://Games/saveGames/");
        System.out.println(openProgress("E://Games/saveGames/"));
    }

    public static String saveGame(String trace, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(trace, true);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return trace;
    }

    public static void zipFiles(String traceToArchive, List<String> traceObjectToZip) {
        int k = 0;
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(traceToArchive, true))) {
            for (String traceToZip :
                    traceObjectToZip) {
                FileInputStream fis = new FileInputStream(traceToZip);
                k += 1;
                ZipEntry entry = new ZipEntry("save" + k + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void openZip(String traceToZip, String traceToDirectory) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(traceToZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fouts = new FileOutputStream(traceToDirectory + name);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fouts.write(c);
                }
                fouts.flush();
                zis.closeEntry();
                fouts.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String traceToFile) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(traceToFile + "save2.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gameProgress;
    }
}


