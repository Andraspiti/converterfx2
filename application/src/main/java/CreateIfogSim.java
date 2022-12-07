import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateIfogSim {
/*


    public TextField vms;
    public TextField ram;
    public TextField size;
    public TextField mips;
    public TextField bw;
    public TextField pesNumber;
    public TextField vmm;
    public Button createVms;
    public Button CreateOutput;


    public void createVms(ActionEvent actionEvent) {
        ClassTemplates handler = new ClassTemplates();
        String code = handler.ifogSimFirstHalf +
                "vmlist = sim.createVM(" + 2 + "," + vms.getText() + "," + 0 + "," + size.getText() + "," + ram.getText() + "," + mips.getText() + "," + bw.getText() + "," + pesNumber.getText() + ",\"" + vmm.getText() + "\"); \n" +
                handler.ifogSimSecondHalf;
        String pathToGenerated = "iFogSim2/src/org/fog/test/perfeval/VmTestGenerated.java";
        try {
            File file = new File(pathToGenerated);
            FileWriter fileWriter = new FileWriter(file);



            file.createNewFile();
            fileWriter.write(code);


            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("vmtestgenerated generated!!!!");
    }


    public void createOutput(ActionEvent actionEvent) {
        try {
            FxApplication.startProcess(new String[]{"cmd.exe", "/C", "mvn", "install", "-pl", "iFogSim2", "-am"});
            FxApplication.startProcess(new String[]{"cmd.exe", "/C", "java", "-javaagent:\"jars\\aspectj\\aspectjweaver-1.9.7.jar\"",
                    "-classpath", "iFogSim2\\target\\classes", "org.fog.test.perfeval.VmTestGenerated"});
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

 */
}
