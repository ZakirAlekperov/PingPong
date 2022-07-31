/**
 * Этот класс рисует стол для пинг-понга, шар, ракетки, отображает счет
 */

package screens;

import engine.PingPongEngine;

import javax.swing.*;
import java.awt.*;

public class PingPongTable extends JPanel implements GameConstant{

    private JLabel label;
    private int computerRacket_y = COMPUTER_RACKET_Y_START;
    private int playerRacket_y = PLAYER_RACKET_Y_START;
    private int ballX = BALL_START_X;
    private int ballY = BALL_START_Y;

    Dimension preferredSize = new Dimension (TABLE_WIDTH, TABLE_HEIGHT);

    //Этот метод устанавливает размер окна. Вызывается виртуальной машиной
    public Dimension getPreferredSize(){
        return preferredSize;
    }

    //Конструктор. Создает обработчик события мыши.
    PingPongTable(){

        PingPongEngine pingPongEngine = new PingPongEngine (this);

        //Обрабатывает движение мыши для передвижения ракеток.
        addMouseMotionListener ( pingPongEngine );

        //Обрабатывает события клавиатуры
        addKeyListener ( pingPongEngine );
    }

    //Добавить панель JPanel в окно
    void addPanelToFrame(Container container){

        container.setLayout ( new BoxLayout ( container, BoxLayout.Y_AXIS ) );
        container.add ( this );

        label = new JLabel ("Нажми N для новой игры, S  для сохранения, Q для выхода");
        container.add(label);
    }

    //Перерисовать окно. Этот метод вызывается виртуальной машиной, когда
    //нужно обновить экран или вызывается метод repaint() из PingPongEngine
    public void paintComponent(Graphics g){

        super.paintComponent ( g );

        //Нарисовать зеленый стол
        g.setColor ( Color.GREEN );
        g.fillRect ( 0,0,TABLE_WIDTH, TABLE_HEIGHT );

        //Нарисовать правую ракетку
        g.setColor ( Color.yellow);
        g.fillRect ( PLAYER_RACKET_X, playerRacket_y, RACKET_WIDTH, RACKET_LENGTH );

        //Нарисовать левую ракетку
        g.setColor ( Color.BLUE );
        g.fillRect ( COMPUTER_RACKET_X, computerRacket_y, RACKET_WIDTH, RACKET_LENGTH );

        //Нарисовать мяч
        g.setColor ( Color.RED );
        g.fillOval ( ballX, ballY,10, 10);

        //Нарисовать белые линии
        g.setColor ( Color.white );
        g.drawRect ( 10,10,300,200 );
        g.drawLine ( 160,10,160,210 );

        //Установить фокус на стол, чтобы обработчик клавиатуры мог посылать команды столу
        requestFocus ();
    }

    //Установить текущее положение ракетки игрока
    public void setPlayerRacket_y(int playerRacket_y){
        this.playerRacket_y = playerRacket_y;
        repaint ();
    }

    //Вернуть текущее положение ракетки ребенка
    public int getPlayerRacket_Y(){
        return playerRacket_y;
    }

    //Установить текущее положение ракетки компьютера

    public void setComputerRacket_Y(int yCoordinate) {
        this.computerRacket_y = yCoordinate;
        repaint ();
    }

    //Установить игровое сообщение
    public void setMessageText(String text){
        label.setText ( text );
        repaint ();
    }

    //Установить позицию мяча
    public void setBallPosition(int ballx, int bally){
        this.ballX = ballx;
        this.ballY = bally;
        repaint ();
    }

    public static void main(String[] args) {

        //Создать экземпляр окна
        JFrame f = new JFrame ("Пинг-Понг");

        // Убедиться, что окно может быть зарыто по нажатию на крестик
        f.setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );

        PingPongTable pingPongTable = new PingPongTable ();
        pingPongTable.addPanelToFrame ( f.getContentPane () );

        pingPongTable.addPanelToFrame ( f.getContentPane () );

        //Установить размер окна и сделать его видимым
        f.setBounds ( 0,0, TABLE_WIDTH+5, TABLE_HEIGHT+40 );
        f.setVisible ( true );
    }
}
