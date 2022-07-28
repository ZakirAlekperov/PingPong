package engine;

import screens.GameConstant;
import screens.PingPongTable;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PingPongEngine implements MouseListener, MouseMotionListener, GameConstant {

    PingPongTable pingPongTable;

    public int playerRacket_y = PLAYER_RACKET_Y_START;

    //Констркутор. СОдержит сылку на объект стола
    public PingPongEngine(PingPongTable pingPongTable){
        this.pingPongTable = pingPongTable;
    }

    //Обязатеьные методы из интерфейса MouseListener
    public void mousePressed(MouseEvent e){
        //Взять х и у координаты указателя мыши и установить их белой тчке на экране
        pingPongTable.point.x = e.getX ();
        pingPongTable.point.y = e.getY ();

        //Внтури вызывает метод paintComponent() и обновляет окнно

        pingPongTable.repaint ();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    //Обязатеьные методы из интефрейса MouseMotionListener

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int mouse_y = e.getY ();

        //Если мышь находится выше ракетки игрока и не выходит за приделы стола
        //- передвинуть ее вверх,  в противном случае - опустить вниз

        if (mouse_y < playerRacket_y && playerRacket_y > TABLE_TOP){
            playerRacket_y -= RACKET_INCREMENT;
        } else if (playerRacket_y < TABLE_BOTTOM) {
            playerRacket_y += RACKET_INCREMENT;
        }

        //Установить новое положение ракетки
        pingPongTable.setPlayerRacket_y ( playerRacket_y );
        pingPongTable.repaint ();
    }

}
