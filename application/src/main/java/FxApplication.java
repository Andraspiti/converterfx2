import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FxApplication extends Application {

    private final ClassTemplates templates = new ClassTemplates();

    public static void startProcess(String[] args) throws Exception {


        ProcessBuilder build = new ProcessBuilder(args);

        System.out.println(build.command());

        build.redirectError(ProcessBuilder.Redirect.INHERIT);
        try {
            Process process  = build.start();


            BufferedInputStream bis = new BufferedInputStream(process.getInputStream());
            StringBuffer sb = new StringBuffer();
            int c;
            while((c=bis.read()) != -1) {
                sb.append(c);
            }
            bis.close();
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("Create VmTestGenerated");

        StackPane root = new StackPane();

        Label label = new Label("TEST");
        TextField textField = new TextField();
        textField.setTranslateY(-25);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(label, textField);
        hbox.setSpacing(10);


/*
        button.setOnAction(e -> {
                    templates.createVmTestGenerated(1000, 0, 2048, 1000000, 10000,
                            2, 5, 0, 10000, 512, 250, 1000, 1, textField.getText(),
                            "Datacenter_0", 1000, 0, 2048, 1000000, 10000, "x86", "Linux", "Xen", 10.0, 3.0, 0.05, 0.001, 0.0,
                            0, 400000, 300, 300
                    );
                    System.out.println("VmTestGenerated is done!");
                }
        );
*/
        Button button2 = new Button("Call jars");
        button2.setTranslateY(25);

        button2.setOnAction( e -> {
            try {
                startProcess(new String[]{"cmd.exe", "/C", "mvn", "install", "-pl", "iFogSim2", "-am"});
                startProcess(new String[]{"cmd.exe", "/C", "java", "-javaagent:\"jars\\aspectj\\aspectjweaver-1.9.7.jar\"",
                        "-classpath", "iFogSim2\\target\\classes", FileHandler.uploadedFileName});
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        Button button3 = new Button("run Dissect file");
        button3.setTranslateY(50);
        button3.setOnAction(e -> {
            try {
                startProcess(new String[]{"cmd.exe", "/C", "mvn", "install", "-pl", "dissect-cf-fog-application", "-am"});
                //https://github.com/andrasmarkus/dissect-cf/tree/master/dissect-cf-application
                /* startProcess(new String[]{"cmd.exe", "/C", "java", "-cp", "dissect-cf\\dissect-cf-application\\target\\dissect-cf-fog-application-1.0.0-SNAPSHOT.jar",
                        "-Dloader.main=hu.u_szeged.inf.fog.simulator.application.demo.VmTask",
                        " org.springframework.boot.loader.PropertiesLauncher"});
                 */
                startProcess(new String[]{"cmd.exe", "/C", "java", "-jar", "dissect-cf\\dissect-cf-application\\target\\dissect-cf-fog-application-1.0.0-SNAPSHOT.jar",
                        "hu.u_szeged.inf.fog.simulator.application.demo.VmTaskGenerated"});
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        });
/*
        Button button4 = new Button("Print the most recent VM argument file");
        button4.setTranslateY(75);
        button4.setOnAction(e -> {
            try {
                //fileReader(getMostRecentOutput("output/Host"));
                //fileReader(getMostRecentOutput("output/Vm"));
                //fileReader(getMostRecentOutput("output/Cloudlet"));

                File file = new File(FileHandler.getMostRecentOutput("output/Vm"));
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }


                scanner.close();
            } catch(IOException ioException){
                ioException.printStackTrace();
            }

        });
*/

        Button button6 = new Button("Create dissect file");
        button6.setTranslateY(100);
        button6.setOnAction(e -> {
            try {

                System.out.println("Cloudlet created " + FileHandler.timesCreated("output/Cloudlet") + " time(s).");
                System.out.println("Datacenter created " + FileHandler.timesCreated("output/Datacenter") + " time(s).");
                System.out.println("Host created " + FileHandler.timesCreated("output/Host") + " time(s).");
                System.out.println("Vm created " + FileHandler.timesCreated("output/Vm") + " time(s).");


                // 0 id, 1 userid, 2 mips, 3 pesNumber, 4 ram, 5 bw, 6 size, 7 vmm
                //									               vmm   mips bw        size
                //		VirtualAppliance va = new VirtualAppliance("va", 800, 0, false, 1073741824L);
                ArrayList<String> vmArguments = FileHandler.mostRecentInArray("output/Vm");

                // 0 id, 1 length, 2 pesNumber, 3 fileSize, 4 outputSize
                ArrayList<String> cloudletArguments = FileHandler.mostRecentInArray("output/Cloudlet");

                // 0 mips, 1 ram, 2 bw, 3 id, 6 storage
                ArrayList<String> hostArguments = FileHandler.mostRecentInArray("output/Host");

                templates.multiplePhysicalMachine( templates.repairHostArguments());

                templates.repairHostArguments();
                templates.createDissectFile(17179869184L,1000000, 8589934592L, 10, 8589934592L, 10, 10, "va", 800, 0,1073741824, 1, "10", 10);

/*
                templates.createDissectFile(8,1, 8589934592L, 10, 10,
                        vmArguments.get(7), templates.mipsToTick(Integer.parseInt(vmArguments.get(2).split("\\.", 2)[0]), Integer.parseInt(cloudletArguments.get(2))),
                        Long.parseLong(vmArguments.get(5)),Long.parseLong(vmArguments.get(6)),
                        FileHandler.timesCreated("output/Vm"),
                        cloudletArguments.get(0), Long.parseLong(cloudletArguments.get(3))
                        );
                System.out.println();
*/

              // System.out.println(hostArguments.get(6 * FileHandler.timesCreated("output/Host") - 1)); - az elso host setStorage
              // System.out.println(6 * FileHandler.timesCreated("output/Host") - 1);




            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });



        Parent primaryFxml = FXMLLoader.load(getClass().getResource("primary.fxml"));

        final FileChooser fileChooser = new FileChooser();
        final Button fileOpenButton = new Button("Upload input file");
/*
        fileOpenButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file.exists()){
                FileHandler.openFile(file);
            }
        });
*/
        Button uploadAndRun = new Button("Upload and run scenario");


        uploadAndRun.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file.exists()){
                FileHandler.openFile(file);
            }

            try {
                startProcess(new String[]{"cmd.exe", "/C", "mvn", "install", "-pl", "iFogSim2", "-am"});
                startProcess(new String[]{"cmd.exe", "/C", "java", "-javaagent:\"jars\\aspectj\\aspectjweaver-1.9.7.jar\"",
                        "-classpath", "iFogSim2\\target\\classes", FileHandler.uploadedFileName});


                System.out.println("Cloudlet created " + FileHandler.timesCreated("output/Cloudlet") + " time(s).");
                System.out.println("Datacenter created " + FileHandler.timesCreated("output/Datacenter") + " time(s).");
                System.out.println("Host created " + FileHandler.timesCreated("output/Host") + " time(s).");
                System.out.println("Vm created " + FileHandler.timesCreated("output/Vm") + " time(s).");


                ArrayList<String> vmArguments = FileHandler.mostRecentInArray("output/Vm");

                ArrayList<String> cloudletArguments = FileHandler.mostRecentInArray("output/Cloudlet");

                ArrayList<String> hostArguments = FileHandler.mostRecentInArray("output/Host");

                templates.createDissectFile(17179869184L,1000000, 8589934592L, 10, 8589934592L, 10, 10, "va", 800, 0,1073741824, 1, "10", 10);




            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });


        //root.getChildren().addAll(fileOpenButton, button2, button6, uploadAndRun );
        root.getChildren().addAll( uploadAndRun, button3 );

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Converter");
        primaryStage.show();
    }





}
