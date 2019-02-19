package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sample.Cast.*;
import sample.Collide.Collide;
import sample.Leaderbord.Leader;
import sample.Leaderbord.Leaderboard;
import sample.Leaderbord.Leaders;
import sample.Levels.Easy;
import sample.Levels.Hard;
import sample.Levels.Level;
import sample.Levels.Normal;
import sample.Menu.MenuBox;
import sample.Menu.MenuItem;
import sample.Menu.SubMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private AnimationTimer timer; // Таймер основного цикла
    private Pane root;
    private Scene scene;
    private javafx.scene.shape.Rectangle background;
    private Level level;// Ссылка на объект уровня сложности

    private List<ObjectInteraction> objects;// Объекты взаимодействия
    private Player hero;// Игрок
    private MenuBox mainMenu;
    private MenuBox saveRecordMenu;
    private SubMenu leadersMenu;
    private Leaderboard leaderboard;// Список лидеров

    private int color = 1;
    private int delay = 0;
    private int newLeader;
    private boolean gamePlay = false;

    private String leadersFile = "C://ToEvade Game";
    private Text scoreLabel;
    private Text speedLabel;
    private Text lifeLabel;
    private Text delayLabel;
    private String delayStatus;
    private HBox scoreEnd;
    private HBox highScoreText;

    private javafx.scene.paint.Color[] colors = {
            Color.WHITE, Color.INDIANRED, Color.DARKSLATEGRAY
    };

    public void init(Scene scene){
        this.scene = scene;
        root = (Pane)scene.getRoot();// Установка панели в которую добавляются все объекты
        // Инициализация списка лидеров
        leaderboard = new Leaderboard(leadersFile);
        // Задний план. Цвет плана зависит от количества набранных очков
        background = new Rectangle(scene.getWidth() + 10
                ,scene.getHeight() + 10
                ,colors[0]);
        root.getChildren().add(background);
        // Лэйбл показа набранных очков
        scoreLabel = new Text("Score: 0");
        scoreLabel.setFont(Font.font(16));
        scoreLabel.setTranslateX(scene.getWidth() - 80);
        scoreLabel.setTranslateY(20);
        root.getChildren().add(scoreLabel);
        // Лэйбл показа текущей скорости объектов
        speedLabel = new Text("Speed: 0");
        speedLabel.setFont(Font.font(16));
        speedLabel.setTranslateX(scene.getWidth() - 80);
        speedLabel.setTranslateY(40);
        root.getChildren().add(speedLabel);
        // Лъйбл показа уровня жизней игрока
        lifeLabel = new Text("Life: ");
        lifeLabel.setFont(Font.font(16));
        lifeLabel.setTranslateX(15);
        lifeLabel.setTranslateY(scene.getHeight() - 10);
        root.getChildren().add(lifeLabel);
        // Инициализация лэйбла показа задержки
        delayLabel = new Text();
        delayLabel.setFont(Font.font(29));
        delayLabel.setTranslateX(30);
        delayLabel.setTranslateY(30);
        // Инициализация участников игры
        objects = new ArrayList<>();
        // Инизиализация игрока
        hero = new Player(root);
        initHero();
        // Подключение команд клавиатуры
        commandBorder();
        // Главный игровой цикл
        timer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if(now - lastUpdate >= 28_000_000)
                    onUpdate();
            }
        };
        // Инициальзация меню игры
        initMenu();
        showMainMenu(true);
        // Вывод сообщения подсказки
        massageToStart();
    }

    private void newGame(){
        if(level == null)
            level = new Normal();
        level.reset();

        gamePlay = true;
        background.setFill(colors[0]);

        root.getChildren().clear();
        root.getChildren().addAll(
                background, scoreLabel, speedLabel, lifeLabel
        );
        objects.clear();

        initHero();
        timer.start();
    }

    private void exitGame(){
        leaderboard.save();
        System.exit(0);
    }

    private void onUpdate(){
        //Перемещение врагов
        level.moveEnemies(objects);
        //Вероятность появления врагов
        if(Math.random() < level.probability())
            objects.add(spawnObject());
        // Обновление лэйблов
        scoreLabel.setText("Score: " + (int)level.getScore());
        speedLabel.setText("Speed: " + level.getSpeed());
        // Проветка состояния игры
        checkState();
        // Смена заднего плана
        if((int)(level.getScore() % 10) == 0 && level.getScore() > 8) {
            if(color > colors.length - 1)
                color = 0;
            changeBackgroundColor(colors[color++]);
        }
    }

    private void initMenu(){
        MenuItem newGame = new MenuItem("NEW GAME");
        MenuItem ldItem = new MenuItem("LEADERBOARD");
        MenuItem exitGame = new MenuItem("EXIT");
        SubMenu main = new SubMenu(
                newGame, ldItem, exitGame
        );
        mainMenu = new MenuBox(main);

        MenuItem back = new MenuItem("BACK");
        back.setColorBtn(Color.DARKGOLDENROD);
        MenuItem ng = new MenuItem("NEW GAME");
        MenuItem recordList = new MenuItem(leaderboard);
        Text inRecordList = new Text("(Сохраняются последние 5 лучших результатов)");
        leadersMenu = new SubMenu(
                recordList, back
        );

        MenuItem save = new MenuItem("SAVE");
        TextField text = new TextField();
        MenuItem textField = new MenuItem(text);
        SubMenu saveMenu = new SubMenu(
                textField, save, ng
        );
        saveMenu.setSpacing(20);
        saveRecordMenu = new MenuBox(saveMenu);

        MenuItem easy = new MenuItem("EASY");
        MenuItem normal = new MenuItem("NORMAL");
        MenuItem hard = new MenuItem("HARD");

        SubMenu selectLevel = new SubMenu(
                easy, normal, hard
        );
        MenuBox selectLevelMenu = new MenuBox(selectLevel);

        // Установка позиций всех SubMenu
        main.setPosition(scene.getWidth() * 0.335, scene.getHeight() * 0.47);
        leadersMenu.setPosition(186, 100);
        selectLevel.setPosition(scene.getWidth() * 0.335, scene.getHeight() * 0.42);
        saveMenu.setPosition(scene.getWidth() * 0.335, 320);

        // Обработчики событий для элементов меню
        newGame.setOnMouseClicked(e -> {
            mainMenu.setVisible(false);
            selectLevelMenu.setVisible(true);
            root.getChildren().add(selectLevelMenu);
        });
        ng.setOnMouseClicked(e -> newGame());
        exitGame.setOnMouseClicked(e -> exitGame());

        easy.setOnMouseClicked(e -> {
            level = new Easy();
            newGame();
        });
        normal.setOnMouseClicked(e -> {
            level = new Normal();
            newGame();
        });
        hard.setOnMouseClicked(e -> {
            level = new Hard();
            newGame();
        });

        ldItem.setOnMouseClicked(e -> leaderboardClicked(inRecordList));
        save.setOnMouseClicked(e -> savedClicked(text));

        back.setOnMouseClicked(e -> back(main, inRecordList));
    }

    private void back(SubMenu main, Text inRecordList){
        if(scoreEnd != null) {
            scoreEnd.setVisible(true);
        }
        massageInRecordList(false, inRecordList);
        mainMenu.setSubMenu(main);
    }

    private void savedClicked(TextField text){
        leaderboard.add(
                newLeader, new Leader(text.getText(), (int)level.getScore())
        );
        if(leaderboard.size() > 5)
            leaderboard.removeLast();

        leaderboard.save();
        saveRecordMenu.setVisible(false);
        highScoreText.setVisible(false);
        showMainMenu(true);
    }

    private void leaderboardClicked(Text text){
        if(scoreEnd != null)
            scoreEnd.setVisible(false);
        mainMenu.setSubMenu(leadersMenu);
        massageInRecordList(true, text);
    }

    private void initHero(){
        hero.startPosition();
        moveHero(hero.getShape());
        hero.setLife(3);
        root.getChildren().add(hero.getShape());
    }

    private ObjectInteraction spawnObject(){
        ObjectInteraction obj = null;
        Point p = null;
        double random = Math.random();
        int w = (int) scene.getWidth();
        int h = (int) scene.getHeight();

        if (random < 0.25) {
            p = new Point(1, 0);
        }
        else if (random >= 0.25 && random < 0.5) {
            p = new Point(-1, 0);
        }
        else if (random >= 0.5 && random < 0.75){
            p = new Point(0, 1);
        }
        else if (random >= 0.75) {
            p = new Point(0, -1);
        }

        random = Math.random();
        if(random > 0.06)
            obj = initEnemy(p, w, h);
        else if(random <= 0.06 && random > 0.03)
            obj = initHelper(p, w, h);
        else if(random <= 0.03)
            obj = initImmortality(p, w, h);

        obj.generatePosition();
        root.getChildren().add(obj.getShape());
        return obj;
    }

    private ObjectInteraction initEnemy(Point p, int x, int y){
        return new Enemy(p, x, y);
    }

    private ObjectInteraction initHelper(Point p, int x, int y){
        return new Helper(p, x, y);
    }

    private ObjectInteraction initImmortality(Point p, int x, int y){
        return new Immortality(p, x, y);
    }

    private void checkState(){
        if(delay != 0){
            delay--;
            delayLabel.setText(delayStatus + ": " + delay);
            return;
        }else {
            hero.getShape().setRadius(hero.getSize());
            hero.getShape().setFill(Color.DARKCYAN);
            hero.getShape().setOpacity(1);
            root.getChildren().remove(delayLabel);
        }

        for(ObjectInteraction o : objects) {
            if (o.getShape()
                    .getBoundsInParent()
                    .intersects(hero.getShape().getBoundsInParent())
                    && Collide.isColliding(hero.getShape(), (Rectangle) o.getShape())) {

                hero.setLife(hero.getLife() + o.getDamage());
                if(hero.getLife() != 0){
                    root.getChildren().removeAll(o.getShape());
                    objects.remove(o);

                    if(o.getDamage() > 0) {
                        return;
                    }
                    if(o.getDamage() == 0){
                        hero.getShape().setFill(Color.GREEN);
                        hero.getShape().setOpacity(0.4);
                        hero.getShape().setRadius(hero.getSize() + 20);
                        delay = 100;
                        delayStatus = "IMMORTALITY MOD";
                        delayLabel.setText(delayStatus + ": " + delay);
                        root.getChildren().add(delayLabel);
                        return;
                    }

                    hero.getShape().setFill(Color.RED);
                    hero.getShape().setOpacity(0.4);
                    delay = 60;
                    delayStatus = "DELAY";
                    delayLabel.setText(delayStatus + ": " + delay);
                    root.getChildren().add(delayLabel);
                    return;
                }

                timer.stop();

                if(checkRecords()) {
                    changeBackgroundColor(Color.WHITE, 0.4);
                    leaderboard.save();
                } else {
                    showMainMenu(true);
                    changeBackgroundColor(Color.BROWN, 0.4);
                }
                gamePlay = false;
                root.getChildren().remove(scoreLabel);
                massageScore((int)level.getScore());

                massageToStart();

                return;
            }
        }
    }

    private boolean checkRecords(){
        Leaders leaders = new Leaders(leadersFile);
        ArrayList<Integer> scoreOfLeaders = leaders.getScoreOfLeaders();

        for (Integer i : scoreOfLeaders)
            if ((int)(level.getScore() - 0.05) == i) return false;

        for (int i = 0; i < scoreOfLeaders.size(); i++) {
            if(level.getScore() > scoreOfLeaders.get(i)){
                newLeader = i;
                showSavedMenu();
                massageNewRecord(i);
                return true;
            }
        }
        return false;
    }

    private void massageInRecordList(boolean show, Text text){
        text.setFont(javafx.scene.text.Font.font(16));
        text.setTranslateX(135);
        text.setTranslateY(80);
        text.setVisible(true);
        if(show)
            root.getChildren().add(text);
        else
            root.getChildren().remove(text);
    }

    private void massageNewRecord(int sc){
        String end = "New " + (sc+1) + " High Score";
        highScoreText= new HBox();
        highScoreText.setTranslateX(215);
        highScoreText.setTranslateY(260);
        root.getChildren().add(highScoreText);

        animationOut(end, highScoreText, 20);
    }

    private void massageScore(int sc){
        String end = "Your Score: " + sc;
        scoreEnd = new HBox();
        scoreEnd.setTranslateX(150);
        scoreEnd.setTranslateY(120);
        root.getChildren().add(scoreEnd);

        animationOut(end, 48);
    }

    private void massageToStart(){
        Text restart = new Text("(Press Enter to start)");
        restart.setFont(javafx.scene.text.Font.font(16));
        restart.setTranslateX(233);
        restart.setTranslateY(scene.getHeight() - 20);
        root.getChildren().add(restart);
    }

    private void changeBackgroundColor(Color color){
        changeBackgroundColor(color, 1.5);
    }

    private void changeBackgroundColor(Color color, double duration){
        FillTransition ft = new FillTransition(Duration.seconds(duration), background);
        ft.setFromValue((Color) background.getFill());
        ft.setToValue(color);
        ft.play();
    }

    private void animationOut(String s ,int font){
        animationOut(s, scoreEnd, font);
    }

    private void animationOut(String s, HBox hbox, int font){
        for (int i = 0; i < s.toCharArray().length; i++) {
            char letter = s.charAt(i);

            Text text = new Text(String.valueOf(letter));
            text.setFont(javafx.scene.text.Font.font(font));
            text.setOpacity(0);

            hbox.getChildren().add(text);

            FadeTransition ft = new FadeTransition(Duration.seconds(0.6), text);
            ft.setToValue(1);
            ft.setDelay(Duration.seconds(i * 0.05));
            ft.play();
        }
    }

    private void moveHero(Circle circle){
        scene.setCursor(Cursor.HAND);
        scene.setOnMousePressed(e ->{
            if(!gamePlay) return;

            updatePosition(circle, e.getSceneX(), e.getSceneY());
            circle.setCursor(Cursor.NONE);
        });
        scene.setOnMouseDragged(e ->{
            if(!gamePlay) return;

            updatePosition(circle, e.getSceneX(), e.getSceneY());
            circle.setCursor(Cursor.NONE);
        });
        scene.setOnMouseReleased(e -> circle.setCursor(Cursor.HAND));
    }

    private void updatePosition(Circle hero, double newX, double newY) {

        hero.setTranslateX(newX);
        hero.setTranslateY(newY);
    }

    private void showSavedMenu(){
        saveRecordMenu.setVisible(true);
        root.getChildren().remove(saveRecordMenu);
        root.getChildren().add(saveRecordMenu);
    }
    
    private void showMainMenu(boolean show){
        root.getChildren().remove(mainMenu);
        root.getChildren().add(mainMenu);

        FadeTransition ft = new FadeTransition(Duration.seconds(0.6), mainMenu);
        if(show){
            mainMenu.setVisible(true);
            ft.setFromValue(0);
            ft.setToValue(1);
        }else{
            mainMenu.setVisible(false);
            ft.setFromValue(1);
            ft.setToValue(0);
        }
        ft.play();
    }

    private void commandBorder(){
        scene.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER && !gamePlay){
                newGame();
            }else if(e.getCode() == KeyCode.ESCAPE){
                gamePlay = !gamePlay;
                showMainMenu(!gamePlay);
                if(gamePlay) {
                    timer.start();
                } else {
                    timer.stop();
                }
            }
        });
    }
}
