package engine;

import screens.GameConstant;
import screens.PingPongTable;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Этот класс - обработчик событий мыши и клавиатуры
 * Рассчитывает движение мяча и ракеток, изменение их координат.
 */

public class PingPongEngine implements Runnable, MouseMotionListener, KeyListener, GameConstant {

    private PingPongTable pingPongTable; //Ссылка на стол
    private int playerRacket_Y = PLAYER_RACKET_Y_START;
    private int computerRacket_Y = COMPUTER_RACKET_Y_START;
    private int playerScore;
    private int computerScore;
    private int ballX;
    private int ballY;
    private boolean movingLeft = true;
    private boolean ballServed = false;

    //Значение вертикального передвижения мяча в пикселях
    private int verticalSlide;

    //Конструктор. СОдержит ссылку на объект стола
    public PingPongEngine(PingPongTable pingPongTable){
        this.pingPongTable = pingPongTable;

        Thread worker = new Thread (this);
        worker.start ();
    }

    //Обязательные методы из интерфейса MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {

        int mouse_Y = e.getY ();

        //Если мышь находится выше ракетки игрока и не выходит за приделы стола
        //- передвинуть ее вверх, в противном случае - опустить вниз

        if (mouse_Y < playerRacket_Y && playerRacket_Y > TABLE_TOP){
            playerRacket_Y -= RACKET_INCREMENT;
        } else if (playerRacket_Y < TABLE_BOTTOM) {
            playerRacket_Y += RACKET_INCREMENT;
        }

        //Установить новое положение ракетки
        pingPongTable.setPlayerRacket_y ( playerRacket_Y );
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //Обязательные методы из интерфейса KeyListener
    public void keyPressed(KeyEvent e){
        char key = e.getKeyChar ();

        if('n'== key || 'N'== key || 'т'==key || 'Т' == key){
            startNewGame();
        } else if ('q' == key || 'Q' == key || 'й' == key || 'Й' == key) {
            endGame();
        } else if ('s' == key || 'S' == key || 'ы' == key || 'Ы' == key) {
            playerServe();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

    }

    //Начать новую игру
    public void startNewGame(){
        computerScore = 0;
        playerScore = 0;
        pingPongTable.setMessageText ( "" );
    }
}
