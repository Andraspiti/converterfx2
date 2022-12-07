import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Scanner;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileHandler {

    public static String uploadedFileName = "";

    /**
     * copies the file that is uploaded into the test/perfeval directory under ifogsim
     * also sets the uploadedFileName, which is called for maven install (the file name without the extension)
     */
    public static void openFile(File file) {
        try {
            uploadedFileName = "org.fog.test.perfeval." + file.getName().replaceFirst("[.][^.]+$", "");
            String generatedPath = "iFogSim2/src/org/fog/test/perfeval/" + file.getName();
            Files.copy(file.toPath(), Paths.get(generatedPath), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the path of the most recently created file in the given directory
     */
    public static String getMostRecentOutput(String dirPath) throws IOException {
        long max = 0;
        int counter = 0;
        int maxAt = 0;
        File directory = new File(dirPath);
        File[] allFilesInDir = directory.listFiles();

        if (allFilesInDir.length == 0){return "Empty folder!";}


        for (File file : allFilesInDir){
            String fileString = file.toString();
            if (fileString.endsWith(".txt")){
                counter++;
                BasicFileAttributes attr = Files.readAttributes(Paths.get(fileString), BasicFileAttributes.class);
                long milis = attr.creationTime().toMillis();
                if (milis > max) {
                    maxAt = counter;
                    max = milis;
                }
            }
        }
        return allFilesInDir[maxAt-1].toString();
    }

    /**
     * Simply prints the contents of a given file
     */
    public static void fileReader(String argumentPath){

        try {
            File file = new File(argumentPath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                System.out.print(scanner.nextLine() + ",\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File read unsuccessful");
            e.printStackTrace();
        }

    }

    /**
     * Returns the number of times each type of class(Cloudlet, Datacenter, Host, Vm) was instantiated
     */
    public static int timesCreated(String pathToDir) throws IOException {
        int lineCount = 0;
        int timesCreated = 0;
        String mostRecentVm = getMostRecentOutput(pathToDir);
        File file = new File(mostRecentVm);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            scanner.nextLine();
            lineCount++;
        }
        scanner.close();
        switch(pathToDir) {
            case "output/Cloudlet":
                return lineCount / 17;
            case "output/Datacenter":
                return lineCount / 15;
            case "output/Host":
                for ( String string : mostRecentInArray(pathToDir)){
                    if (string.split(":", 2)[0].equals("setId")){
                        timesCreated++;
                    }
                }
                return timesCreated;
            case "output/Vm":
                return lineCount / 9;
        }
        return 0;
    }

    /**
     * returns the content of the most recent file in the given directory as an ArrayList
     */
    public static ArrayList<String> mostRecentInArray(String pathToDir) throws IOException {
        ArrayList<String> arguments = new ArrayList<>();

        File file = new File(getMostRecentOutput(pathToDir));
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            arguments.add(scanner.nextLine());
        }
        return arguments;
    }


}
