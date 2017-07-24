package sample.Menu;


import javafx.scene.layout.VBox;

public class SubMenu  extends VBox{
    public SubMenu(MenuItem...items) {
        setSpacing(16);
        getChildren().addAll(items);
//        for(MenuItem item : items)
//            getChildren().add(item);
    }

    public void addItem(int index, MenuItem mi){
        getChildren().add(index, mi);
    }

    public void setItem(int index, MenuItem mi){
        MenuItem tmp = (MenuItem) getChildren().get(index);
        if (mi.getNode()
                .equals(tmp.getNode())) {
            getChildren().remove(index);
            getChildren().add(index, mi);
        }else {
            getChildren().add(index, mi);
        }
    }

    public void setPosition(double x, double y){
        setTranslateX(x);
        setTranslateY(y);
    }
}
