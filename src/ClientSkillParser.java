import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientSkillParser {

    private final static String FILE_NAME = "SkillName.txt";
    public static Map<Integer, ArrayList<Skill>> skillMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Start prasing client skilldata");
        try{
            File file = new File("D:\\lineage_server\\jts\\tools\\data\\" + FILE_NAME);
            LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(file)));
            String line = null;
            while((line = lnr.readLine()) != null) {
                if (line.trim().length() == 0 || line.startsWith("#") || line.startsWith("id")) {
                    continue;
                }
                String lineSplit[] = line.split("\t", -1);
                int skillId = Integer.parseInt(lineSplit[0]);
                int skillLevel = Integer.parseInt(lineSplit[1]);
                String skillName = lineSplit[2].replaceAll("u,","").replaceAll("0", "");
                String skillDescription = lineSplit[3].replaceAll("u,","").replaceAll("\0", "");
                if(skillId > 1600){
                    continue;
                }
                if(skillLevel > 100){
                    continue;
                }
                System.out.println(skillId + " | " + skillLevel + " | " + skillName + " | " + skillDescription);
                //addToMapArray(skillId, new Skill(skillLevel, skillname, skillDescription));
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream("tools/data/SkillData" + ".sql", true), "UTF8"));
                printWriter.println("INSERT INTO 'SkillData' (skill_id, skill_level, skill_name, skill_description) VALUES (" +
                        skillId + "," + skillLevel + ",'" + skillName + "','" + skillDescription + "')");
                printWriter.close();
            }
            System.out.println("size = " + skillMap.size());
            System.out.println("totalMem = " +  Runtime.getRuntime().totalMemory()/(1000*1000));
            System.out.println("freeMem = " + Runtime.getRuntime().freeMemory()/(1000*1000));
            System.out.println("usedMem = "+(Runtime.getRuntime().totalMemory()-
                    Runtime.getRuntime().freeMemory())/(1000*1000)+"M");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToMapArray(int skillId, Skill skill){

        if(skillMap.containsKey(skillId)){
            skillMap.get(skillId).add(skill);
        }else{
            skillMap.put(skillId, new ArrayList<Skill>());
            skillMap.get(skillId).add(skill);
        }
    }



    public static class Skill{
        private int level;
        private String name;
        private String description;

        public Skill(int level, String name, String description) {
            this.level = level;
            this.name = name;
            this.description = description;
        }

        public int getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }



}
