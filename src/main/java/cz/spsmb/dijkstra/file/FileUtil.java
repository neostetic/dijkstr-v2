package cz.spsmb.dijkstra.file;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileUtil {

    public static String inputFile = "src/main/java/cz/spsmb/dijkstra/data/input.txt";

    public static FileNode readFromFile() throws IOException {
        if (new File(inputFile).exists()) {
            String string = getTextFromFile();
            ArrayList<String> createNodes = new ArrayList<>();
            ArrayList<String[]> updateNodes = new ArrayList<>();
            String[] strings = string.toString().split(";");
            for (String s : strings) {
                String[] codeId = s.split(":");
                if (codeId[0].equals("Create")) {
                    createNodes.add(codeId[1]);
                } else if (codeId[0].equals("Update")) {
                    updateNodes.add(codeId[1].split(","));
                }
            }
            deleteFile();
            return new FileNode(createNodes, updateNodes);
        } else return null;
    }

    public static void writeToFile(String nodeName) {
        try(FileWriter fw = new FileWriter(inputFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print("Create:" + nodeName + ";");
        } catch (IOException ignored) {}
    }

    public static void writeToFile(String nodeName1, String nodeName2, int distance) {
        try(FileWriter fw = new FileWriter(inputFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print("Update:" + nodeName1 + "," + nodeName2 +  "," + distance + ";");
        } catch (IOException ignored) {}
    }

    public static boolean exists() {
        return new File(inputFile).exists();
    }

    public static void deleteFile() {
        File file = new File(inputFile);
        file.delete();
    }

    private static String getTextFromFile() throws IOException {
        FileReader fileReader = new FileReader(inputFile);
        int i;
        StringBuilder string = new StringBuilder();
        while ((i = fileReader.read()) != -1) {
            string.append((char) i);
        }
        fileReader.close();
        return string.toString();
    }
}
