package sample.Leaderbord;


import java.io.*;
import java.util.ArrayList;

public class Leaders {
    private File path;
    private static BufferedReader reader;
    private ArrayList<Leader> playerList;

    public Leaders(String fileLocation){
        playerList = new ArrayList<>();
        path = new File(fileLocation);
        path.mkdirs();
        fileLocation += "/leaderbord.txt";
        path = new File(fileLocation);
        initPath();
        try{
            reader = new BufferedReader(new FileReader(fileLocation));
        }catch (IOException e){
            e.getMessage();
        }
    }

    private void initPath(){
        try {
            if(!path.exists()) {
                path.createNewFile();
                write();
            }
        } catch (IOException e){
            e.getMessage();
        }
    }

    private void write(){
//        playerList.add(new Leader("???", 0));
        try{
            FileWriter writer = new FileWriter(getFile());
            writer.write(String.valueOf(1));
            writer.write("\n???");
            writer.write("\n0");
            writer.close();
        } catch (IOException e){
            e.getMessage();
        }
    }

    public ArrayList<Leader> getPlayers(){
        if(Leaders.isEmpty(path)) {
            playerList.add(new Leader("???", 0));
            return playerList;
        }

        try{
            int count = Integer.valueOf(reader.readLine());
            for(int i = 0; i < count; i++){
                Leader player = new Leader(
                        reader.readLine(), Integer.valueOf(reader.readLine())
                );
                playerList.add(player);
            }
        }catch (IOException e){
            e.getMessage();
        }

        return playerList;
    }

    // Проверка файла на пустоту
    private static boolean isEmpty(File file){
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            int data = fis.read();     // Читаем первый символ с потока байтов
            return data == -1;          // Если data = -1 значит файл пуст
        } catch (IOException e){
            e.getMessage();
        }

        return false;
    }

    public ArrayList<Integer> getScoreOfLeaders(){
        ArrayList<Integer> scoreOfLeaders = new ArrayList<>();
        try{
            int count = Integer.valueOf(reader.readLine());
            for(int i = 0; i < count; i++){
                reader.readLine();
                scoreOfLeaders.add(Integer.valueOf(reader.readLine()));
            }
        }catch (IOException e){
            e.getMessage();
        }
        return scoreOfLeaders;
    }

    public File getFile(){
        return path;
    }
}
