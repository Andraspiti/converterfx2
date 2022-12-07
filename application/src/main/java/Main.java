import javafx.application.Application;

public class Main {

    public static void main(String[] args) throws Exception {

        //ProcessBuilder install = new ProcessBuilder("cmd.exe", "/C", "mvn" ,"install", "-pl", "iFogSim2", "-am");

/*
        handler.createVmTestGenerated(1000, 0, 2048, 1000000, 10000,
                2, 5, 0, 10000, 512, 250, 1000, 1, "Xen",
                "Datacenter_0", 1000, 0, 2048, 1000000, 10000, "x86", "Linux", "Xen", 10.0, 3.0, 0.05, 0.001, 0.0,
                0, 400000, 300, 300
                             );

        File file = new File("iFogSim2/src/org/fog/test/perfeval/VmTestGenerated.java");
        if(file.exists() && !file.isDirectory()){
            System.out.println("VmTestGenerated exists");
        }
        if (file.isFile()) {
            System.out.println("VmTestGenerated is File");
        }
        fileReader("iFogSim2/src/org/fog/test/perfeval/VmTestGenerated.java");

        String[] install = {"cmd.exe", "/C", "mvn" ,"install", "-pl", "iFogSim2", "-am"};
        String[] jar = {"cmd.exe", "/C", "java", "-javaagent:\"jars\\aspectj\\aspectjweaver-1.9.7.jar\"",
                "-classpath", "iFogSim2\\target\\classes", "org.fog.test.perfeval.VmTestGenerated"};

        startProcess(install);
        startProcess(jar);

        String argumentPath = getMostRecentOutput("output");
*/
        Application.launch(FxApplication.class, args);

        //launch(args);
    }
}