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
    public void keyTyped(KeyEvent e) {

    }

    //Начать новую игру
    public void startNewGame(){
        computerScore = 0;
        playerScore = 0;
        pingPongTable.setMessageText ( "Счет  Компьютер: 0 Игрок: 0" );

        playerServe();
    }

    //Завершить игру
    public void endGame(){
        System.exit ( 0 );
    }

    //Обязательный метод run() из  интерфейса Runnable
    @Override
    public void run() {

        boolean canBounce = false;

        while (true){

            if(ballServed){ //Если мяч движется
                //Мяч, движется влево?
                if(movingLeft && ballX > BALL_MIN_X){

                    if((ballY >= computerRacket_Y) && (ballY < (computerRacket_Y+ RACKET_LENGTH))){
                        canBounce = true;
                    }else {
                        canBounce = false;
                    }

                    ballX -= BALL_INCREMENT;

                    //Добавить смещение вверх или вниз к любым движенями мяча влево или вправо

                    ballY -= verticalSlide;

                    pingPongTable.setBallPosition ( ballX, ballY );

                    //Может отсуочить?
                    if(ballX <= COMPUTER_RACKET_X && canBounce){
                        movingLeft = false;
                    }
                }

                //Мяч движется вправо?
                if(!movingLeft && (ballX <= BALL_MAX_X)){

                    if(ballY >= playerRacket_Y && ballY < (playerRacket_Y + RACKET_LENGTH)){
                        canBounce = true;
                    }else {
                        canBounce = false;
                    }

                    ballX +=RACKET_INCREMENT;
                    pingPongTable.setBallPosition ( ballX, ballY );

                    //Может отскочить?
                    if (ballX >= PLAYER_RACKET_X && canBounce){
                        movingLeft = true;
                    }
                }
                //Перемещать ракетку компьютера, тчобы блокировать мяч
                if(computerRacket_Y < ballY && computerRacket_Y < TABLE_BOTTOM){
                    computerRacket_Y += RACKET_INCREMENT;
                } else if (computerRacket_Y > TABLE_TOP) {
                    computerRacket_Y -= RACKET_INCREMENT;
                }

                pingPongTable.setComputerRacket_Y ( computerRacket_Y );

                //Приостановить

                try {
                    Thread.sleep ( SLEEP_TIME );
                }catch (InterruptedException e){
                    e.printStackTrace ();
                }

                //обговить счет если мяч в зеленной зоне

                if(isBallOnThetable()){

                    if(ballX > BALL_MAX_X){
                        computerScore++;
                        displayScore();
                    }else if (ballX< BALL_MIN_X){

                        playerScore++;
                        displayScore();
                    }
                }
            }
        }
    }

    //Податьс стекущей позиции ракетки игрока

    private void playerServe(){

        ballServed = true;
        ballX = PLAYER_RACKET_X - 1;
        ballY =playerRacket_Y;

        if(ballY > TABLE_HEIGHT/2){
            verticalSlide =-1;
        }else {
            verticalSlide = 1;
        }

        pingPongTable.setBallPosition ( ballX,ballY );
        pingPongTable.setPlayerRacket_y ( playerRacket_Y );
    }

    private void displayScore(){

        ballServed = false;

        if(computerScore == WINNING_SCORE){
            pingPongTable.setMessageText ( "Компьютер победил!" + computerScore +  " : "+playerScore );
        } else if (playerScore == WINNING_SCORE) {
            pingPongTable.setMessageText ( "игрок победил! "+ playerScore + " : "+ computerScore );
        }else {
            pingPongTable.setMessageText ( "Компьютер: "+computerScore + " Игрок: "+ playerScore );
        }
    }

    //Проверить, пересек ли мяч верхнюю или нижнюю границу стола
    private boolean isBallOnThetable(){

        if(ballY >= BALL_MIN_Y && ballY <= BALL_MAX_Y){
            return true;
        }else {
            return false;
        }
    }

}
