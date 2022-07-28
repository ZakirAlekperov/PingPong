/**
 * Этот класс рисует стол для пинг-понга и отображает координаты точки,
 * где пользователь кликнул мышью
 */

package screens;

import engine.PingPongEngine;

import javax.swing.*;
import java.awt.*;

public class PingPongTable extends JPanel implements GameConstant{

    JLabel label;

    public Point point = new Point (0,0);

    public int computerRacet_x = 15;
    private int playerRacket_y = PLAYER_RACKET_Y_START;

    Dimension preferredSize = new Dimension (TABLE_WIDTH, TABLE_HEIGHT);

    //Этот метод устанавливает размер окна

    public Dimension getPreferredSize(){
        return preferredSize;
    }

    //Конструктор. Создает обработчик события мыши.
    PingPongTable(){

        PingPongEngine pingPongEngine = new PingPongEngine (this);

        //Обрабатывает клики мыши для отображения ее координаты
        addMouseListener ( pingPongEngine );

        //Обрабатывает движение мыши для передвижения ракеток.
        addMouseMotionListener ( pingPongEngine );
    }

    //Добавить панел JPanel в окно
    void addPanelToFrame(Container container){
        container.setLayout ( new BoxLayout ( container, BoxLayout.Y_AXIS ) );

        container.add ( this );

        label = new JLabel ("Нажми для показа координаты");
        container.add(label);
    }

    //Перерисовать окно. Этот метод вызывается виртуальной машиной, ктогда
    //нужно обновить экран или вызывается метод repaint() из PingPongEngine
    public void paintComponent(Graphics g){

        super.paintComponent ( g );
        g.setColor ( Color.GREEN );

        //Нарисвоать стол
        g.fillRect ( 0,0,TABLE_WIDTH, TABLE_HEIGHT );
        g.setColor ( Color.yellow);

        //Нарисовать правую ракетку
        g.fillRect ( PLAYER_RACKET_X_START, playerRacket_y, 5, 30 );
        g.setColor ( Color.BLUE );

        //Нарисвовать левую ракетку
        g.fillRect ( computerRacet_x, 100, 5,30 );
        g.setColor ( Color.RED );

        //нарисвоать мяч
        g.fillOval ( 25,110,10,10 );
        g.setColor ( Color.white );

        g.drawRect ( 10,10,300,200 );
        g.drawLine ( 160,10,160,210 );

        //Отобразить точку как маленький квадрат 2х2 пикселей
        if(point != null){

            label.setText ( "Координаты (x,y): "+point.x + " "+ point.y );
            g.fillRect ( point.x,point.y, 2,2 );
        }
    }

    //Установить текущее положение ракетки игрока
    public void setPlayerRacket_y(int xCoordinate){
        this.playerRacket_y = xCoordinate;
    }

    //Вернуть текущее положение ракетки ребенка
    public int getPlayerRacket_Y(int xCoordinate){
        return playerRacket_y;
    }
    public static void main(String[] args) {
        //Создать экземпляр окна
        JFrame f = new JFrame ("Пинг-Понг");

        // Убедиться, что окно может быть зарыто по нажатию на крестик
        f.setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );

        PingPongTable pingPongTable = new PingPongTable ();

        pingPongTable.addPanelToFrame ( f.getContentPane () );

        //Установить размер окна и сделать его видимым
        f.pack ();
        f.setVisible ( true );
    }
}
