package sample.Menu;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class MenuBox extends Pane {
    private SubMenu subMenu;

    public MenuBox(SubMenu subMenu) {
        this.subMenu = subMenu;

        setVisible(false);
        Rectangle background = new Rectangle(610, 610, Color.BLACK);
        background.setOpacity(0.4);
        getChildren().addAll(background, subMenu);
    }

    public void setSubMenu (SubMenu subMenu){
        getChildren().remove(this.subMenu);
        this.subMenu = subMenu;
        getChildren().add(subMenu);
    }
}
