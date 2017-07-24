package sample.Menu;


import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sample.Leaderbord.Leaderboard;

public class MenuItem extends StackPane{

    private String node;
    private Rectangle rect;
    private Color colorBtn = Color.WHITE;

    public MenuItem(String name) {
        rect = new Rectangle(200, 40, Color.WHITE);
        rect.setOpacity(0.5);

        node = "rectangle";

        Text text = new Text(name);
        text.setFont(Font.font(15));
        text.setFill(Color.BLACK);

        setAlignment(Pos.CENTER);
        getChildren().addAll(rect, text);

        FillTransition ft = new FillTransition(Duration.seconds(0.3), rect);
        setOnMouseEntered(e ->{
            ft.setFromValue((Color) rect.getFill());
            ft.setToValue(Color.GOLD);
            ft.setCycleCount(Animation.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
        });
        setOnMouseExited(e ->{
            rect.setFill(colorBtn);
            ft.stop();
        });
    }

    public MenuItem(Leaderboard ld) {
        node = "leaderboard";
        ld.leaderboardOutput();
        getChildren().add(ld.getTableView());
    }

    public MenuItem(TextField text) {
        node = "textField";
        text.setFocusTraversable(false);
        text.setPromptText("Enter Your Name");
        text.setPrefWidth(200);
        text.setFont(Font.font(15));
        getChildren().add(text);
    }

    public void setColorBtn(Color color){
        colorBtn = color;
        rect.setFill(color);
    }

    public String getNode(){
        return node;
    }
}
