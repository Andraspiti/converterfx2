import java.io.*;
import java.util.ArrayList;

public class ClassTemplates {
    public String ifogSimFirstHalf = "package org.fog.test.perfeval;\n" +
            "import org.cloudbus.cloudsim.*;\n" +
            "import org.cloudbus.cloudsim.core.CloudSim;\n" +
            "import java.util.ArrayList;\n" +
            "import java.util.Calendar;\n" +
            "import java.util.List;\n" +
            "\n" +
            "\n" +
            "public class VmTestGenerated {\n" +
            "    private static List<Vm> vmlist;\n" +
            "    private static List<Host> hostList;\n" +
            "    private static List<Cloudlet> cloudletList;\n" +
            " public static void main(String[] args) throws Exception {\n" +
            "\n" +
            "        StartSimulation sim = new StartSimulation();\n" +
            "\n" +
            "        sim.setVmid(0);\n" +
            "        sim.setPesNumber(1); \n" +
            "\n";

    public String ifogSimSecondHalf = "\n" +
            "    }\n" +
            "}";

    public String dissectFirstHalf = "package hu.u_szeged.inf.fog.simulator.application.demo;\n" +
            "\n" +
            "import java.util.EnumMap;\n" +
            "import java.util.HashMap;\n" +
            "import java.util.Map;\n" +
            "\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.Timed;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.energy.powermodelling.PowerState;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.iaas.VirtualMachine;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.iaas.constraints.AlterableResourceConstraints;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ConsumptionEventAdapter;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.io.StorageObject;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.io.VirtualAppliance;\n" +
            "import hu.mta.sztaki.lpds.cloud.simulator.util.PowerTransitionGenerator;\n" +
            "\n" +
            "public class VmTaskGenerated {\n" +
            "\tpublic static void main(String[] args) throws Exception {\n" +
            "\t\t";


    public void createVmTestGenerated(int hostMips, int hostId, int hostRam, long hostStorage, int hostBw,
                                      int userId, int vms, int idShift, long vmSize, int vmRam, int vmMips, long vmBw, int vmPesNumber, String vmm,
                                      String centerName, int centerMips, int centerHostId, int centerRam, long centerStorage, int centerBw, String centerArch,
                                      String centerOs, String centerVmm, double time_zone, double centerCost, double costPerMem, double costPerStorage, double costPerBw,
                                      int cloudletId, long cloudletLength, long cloudletFilesize, long cloudletOutputSize
    ){
        String pathToGenerated = "iFogSim2/src/org/fog/test/perfeval/VmTestGenerated.java";
        try {
            File file = new File(pathToGenerated);
            FileWriter fileWriter = new FileWriter(file);
            String code =
                    "package org.fog.test.perfeval;\n" +
                            "import org.cloudbus.cloudsim.*;\n" +
                            "import org.cloudbus.cloudsim.core.CloudSim;\n" +
                            "import java.util.ArrayList;\n" +
                            "import java.util.Calendar;\n" +
                            "import java.util.List;\n" +
                            "\n" +
                            "\n" +
                            "public class VmTestGenerated {\n" +
                            "    private static List<Vm> vmlist;\n" +
                            "    private static List<Host> hostList;\n" +
                            "    private static List<Cloudlet> cloudletList;\n" +
                            " public static void main(String[] args) throws Exception {\n" +
                            "\n" +
                            "        StartSimulation sim = new StartSimulation();\n" +
                            "\n" +
                            "        sim.setVmid(0);\n" +
                            "        sim.setPesNumber(1); \n" +
                            "\n" +
                            "\n" +
                            "        hostList = sim.createHost(" + hostMips + "," + hostId + "," + hostRam + "," + hostStorage + "," + hostBw + ");\n" +
                            "\n" +
                            "        vmlist = sim.createVM(" + userId + ","  + vms + "," + idShift + "," + vmSize + "," + vmRam + "," + vmMips + "," + vmBw + "," + vmPesNumber + ",\"" + vmm + "\"); //creating 5 vms\n" +
                            "\n" +
                            "        int num_user = 1; // number of cloud users\n" +
                            "        Calendar calendar = Calendar.getInstance();\n" +
                            "        boolean trace_flag = false;\n" +
                            "\n" +
                            "        CloudSim.init(num_user, calendar, trace_flag);\n" +
                            "\n" +
                            "\n" +
                            "        Datacenter datacenter0 = sim.createDatacenter(\"" + centerName + "\"," + centerMips + "," + centerHostId + "," + centerRam + "," + centerStorage + ",\n" +
                            centerBw + ",\"" + centerArch + "\",\"" + centerOs + "\",\"" + centerVmm + "\"," + time_zone + "," + centerCost + "," + costPerMem + "," + costPerStorage + "," + costPerBw + ");\n" +
                            "\n" +
                            "\n" +
                            "        cloudletList = new ArrayList<Cloudlet>();\n" +
                            "\n" +
                            //int cloudletId, long cloudletLength, long cloudletFilesize, long cloudletOutputSize
                            "        sim.createCloudlet(" + cloudletId + "," + cloudletLength + "," + cloudletFilesize + "," + cloudletOutputSize + ");\n" +
                            "\n" +
                            "    }" +
                            "\n" +
                            "}";

            file.createNewFile();
            fileWriter.write(code);


            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDissectFile(long capacity, long bws,
                                  double cores, double perCoreProcessing, long memory, int onD, int offD,
                                  String vmId, double startupProcess, long nl, long reqDisk,
                                  int vmCount,
                                  String storageId, long storageSize
    ){
        String pathToGenerated = "dissect-cf/dissect-cf-application/src/main/java/hu/u_szeged/inf/fog/simulator/application/demo/VmTaskGenerated.java";

        try{
        File file = new File(pathToGenerated);
        FileWriter fileWriter = new FileWriter(file);
        String code = dissectFirstHalf +
                "final EnumMap<PowerTransitionGenerator.PowerStateKind, Map<String, PowerState>> transitions =\n" +
                "PowerTransitionGenerator.generateTransitions(20, 296, 493, 50, 108);\n" +
                "\n" +
                "HashMap<String, Integer> latencyMap = new HashMap<String, Integer>();\n" +
                "\n" +
                "Repository repo = new Repository(" + capacity + "L," + "\"repo\", " + bws + "," + bws + "," + bws + "," + "latencyMap, \n" +
                "transitions.get(PowerTransitionGenerator.PowerStateKind.storage),transitions.get(PowerTransitionGenerator.PowerStateKind.network));\n" +
                "\n" +
                "repo.setState(NetworkNode.State.RUNNING);\n" +
                "\n" +                                  //PhysicalMachine(double cores, double perCorePocessing, long memory, Repository disk, int onD, int offD,
                multiplePhysicalMachine(repairHostArguments()) +
                "PhysicalMachine pm = new PhysicalMachine(" + cores + "," + perCoreProcessing + ","  + memory + "L," + "repo," + 10 + "," + 10 + "," + "transitions.get(PowerTransitionGenerator.PowerStateKind.host));\n" +
                "\n" +
                "pm.turnon();\n" +
                "\n" +
                "Timed.simulateUntilLastEvent();\n" +
                "\n" +
                "System.out.println(\"Time: \"+Timed.getFireCount()+ \" PM state: \"+pm.getState());\n" +
                "\n" +                      //public VirtualAppliance(final String id, final double startupProcess, final long nl, boolean vary, final long reqDisk)

                "VirtualAppliance va = new VirtualAppliance( " + "\"" + vmId + "\","  + startupProcess + "," + nl + "L," + "false" + "," + reqDisk + ");\n" +
                "\n" +
                "\n" +
                "\n" +
                "repo.registerObject(va);\n" +
                "\n" +
                "AlterableResourceConstraints arc = new AlterableResourceConstraints(4,1,4294967296L);\n" +
                "\n" +
                                               //
                "VirtualMachine vm = pm.requestVM(va, arc, repo," + vmCount + ")[0];\n" +
                "\n" +
                "Timed.simulateUntilLastEvent();\n" +
                "\n" +
                "System.out.println(\"Time: \"+Timed.getFireCount()+ \" PM state: \"+pm.getState()+ \" VM state: \"+vm.getState());\n" +
                "\n" +
                "vm.newComputeTask(100000, ResourceConsumption.unlimitedProcessing, new ConsumptionEventAdapter() {\n" +
                "\n" +
                "@Override\n" +
                "public void conComplete() {\n" +
                "System.out.println(\"Time: \"+Timed.getFireCount());\n" +
                                                                   //cloudlet[0] + "," + cloudlet[3]
                " StorageObject storageObj = new StorageObject(\"" + storageId + "\"," + storageSize + ", false); \n" +
                "repo.registerObject(storageObj);" +
                "}\n" +
                "});\n" +
                "\n" +
                "Timed.simulateUntilLastEvent();"
                + ifogSimSecondHalf;

            fileWriter.write(code);


            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> repairHostArguments() throws IOException {
        ArrayList<String> hostArray = FileHandler.mostRecentInArray("output/Host");
        int numberOfHosts = FileHandler.timesCreated("output/Host") * 5;

        for (String string : hostArray){
            System.out.println(string);
        }
        System.out.println(numberOfHosts - 1);
        hostArray.add(0, hostArray.get(numberOfHosts - 1));

        return hostArray;


    }

    // processing in dissect is 1 MIPSMS / number of CPU cores
    // 1 MIPMS = 1 MIPS/60
    public double mipsToTick(int mips, int pesNumber){
        return Math.round(mips / 60.0 / pesNumber);
    }

    /**
     * returns a string containing the number of physical machines according to the number of Hosts instantiated in iFogSim
     */
    public String multiplePhysicalMachine(ArrayList<String> hostArguments) throws IOException {
        String result = "";
        String pmName = "pm";
        int numberOfHosts = FileHandler.timesCreated("output/Host");
        ArrayList<String> storage = new ArrayList<>();
        ArrayList<String> mips = new ArrayList<>();
        ArrayList<String> ram = new ArrayList<>();
        ArrayList<String> bwTemp = new ArrayList<>();
        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> bw = new ArrayList<>();
        int pesNumber =  Integer.parseInt(FileHandler.mostRecentInArray("output/Cloudlet").get(2));


        for (int i = 0; i<numberOfHosts * 5; i++){
            if (hostArguments.get(i).split(":", 2)[0].equals("setStorage")){
                storage.add(hostArguments.get(i).split(":", 2)[1]);
            }
            if (hostArguments.get(i).split(":", 2)[0].equals("setMips")){
                mips.add(hostArguments.get(i).split(":", 2)[1]);
            }
            if (hostArguments.get(i).split(":", 2)[0].equals("setRam")){
                ram.add(hostArguments.get(i).split(":", 2)[1]);
            }
            if (hostArguments.get(i).split(":", 2)[0].equals("setAvailableBw")){
                bwTemp.add(hostArguments.get(i).split(":", 2)[1]);
            }
            if (hostArguments.get(i).split(":", 2)[0].equals("setId")){
                id.add(hostArguments.get(i).split(":", 2)[1]);
            }
        }


        for (int i = 0; i<bwTemp.size(); i++){
            if (i % 2 == 0){
                bw.add(bwTemp.get(i));
            }
        }


        for (int i=0; i<numberOfHosts; i++){
            String mipsString = mips.get(i).strip();
            int mipsNumber = Integer.parseInt(mipsString.split("\\.", 2)[0]);

            result += "PhysicalMachine " + pmName + i + " = new PhysicalMachine(" + pesNumber + "," + mipsToTick(mipsNumber, pesNumber) + ","  + storage.get(i) + "L," + "repo," + 10 + "," + 10 + "," + "transitions.get(PowerTransitionGenerator.PowerStateKind.host));\n" +
                    pmName + i + ".turnon();\n";

        }

        System.out.println(result);
        return result;
    }


}
