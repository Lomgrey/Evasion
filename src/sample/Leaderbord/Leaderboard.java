package sample.Leaderbord;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Leaderboard{
//    private Pane root;
    private Leaders leaders;
    private TableView<Leader> players;
    private ObservableList<Leader> playerData;

    public Leaderboard(String fileLocation){
        leaders = new Leaders(fileLocation);
    }

    public void leaderboardOutput(){
        players = new TableView<>();
        playerData = FXCollections.observableArrayList();
//        Leaders p = new Leaders(fileLocation);

        playerData.addAll(leaders.getPlayers());
//        save();

        TableColumn<Leader, String>  nameColumn = new TableColumn<>("User Name");
        nameColumn.setMinWidth(168);
        nameColumn.setMaxWidth(168);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Leader, Integer>  scoreColumn = new TableColumn<>("Score");
        scoreColumn.setMinWidth(80);
        scoreColumn.setMaxWidth(80);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        players.setItems(playerData);
        players.getColumns().addAll(nameColumn, scoreColumn);

        players.setPrefSize(250, 300);
    }

    public void add(int i, Leader leader){
        playerData.add(i, leader);
    }

    public void removeLast(){
        playerData.remove(playerData.size() - 1);
    }

    public int size(){
        return playerData.size();
    }

    public void save(){
        List<Leader> saveData = players.getItems();
        FileWriter writer;

        try{
            writer = new FileWriter(leaders.getFile());
            writer.write(Integer.toString(saveData.size()));
            for(Leader l : saveData){
                writer.write("\n" + l.getName());
                writer.write("\n" + l.getScore());
            }
            writer.close();
        } catch (IOException e){
            e.getMessage();
        }
    }

    public TableView<Leader> getTableView(){
        return players;
    }
}
