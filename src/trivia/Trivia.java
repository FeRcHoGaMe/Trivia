/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trivia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;

public class Trivia extends Application {

    private static final String BACKGROUND_SOUND = "Sounds/Beijing_Bass.wav";
    private static final String CLICK_SOUND = "Sounds/RespuestaCorrecta.wav";


    private static Clip backgroundClip;
    private static Clip clickClip;

    private static TriviaController triviaController;
    private static Timer questionTimer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Trivia.fxml"));
        Parent root = loader.load();
        triviaController = loader.getController();

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("trivia.css").toExternalForm()); // Enlaza el archivo CSS
        primaryStage.setTitle("Trivia Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadSounds();

        // Reproduce el sonido de fondo
        playBackgroundMusic();

        // Agrega animación al título del panel de registro
        Label titleLabel = (Label) root.lookup("#titleLabel");
        addTitleAnimation(titleLabel);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loadSounds() {
        try {
            AudioInputStream backgroundStream = AudioSystem.getAudioInputStream(new File(BACKGROUND_SOUND));
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(backgroundStream);
            
            FloatControl volumeControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
        
             // Define el volumen deseado en decibelios (-40.0f = silencio, 0.0f = volumen máximo)
            float volume = -25.0f; // Ajusta este valor según el volumen deseado
        
              // Calcula el rango válido para el control de volumen
             float minVolume = volumeControl.getMinimum();
             float maxVolume = volumeControl.getMaximum();
        
                // Ajusta el volumen en el rango válido
              volume = Math.max(minVolume, Math.min(volume, maxVolume));
        
                // Establece el volumen en el clip
               volumeControl.setValue(volume);

            AudioInputStream clickStream = AudioSystem.getAudioInputStream(new File(CLICK_SOUND));
            clickClip = AudioSystem.getClip();
            clickClip.open(clickStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playClickSound() {
        if (clickClip != null) {
            clickClip.setFramePosition(0);
            clickClip.start();
        }
    }

    public static void playBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
        }
    }

    public static void startQuestionTimer() {
        if (questionTimer != null) {
            questionTimer.cancel();
        }

        questionTimer = new Timer();
        int tiempoLimite = 15; // Tiempo límite en segundos

        questionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Se agotó el tiempo, llama al método endGame() del controlador
                triviaController.endGame();
            }
        }, tiempoLimite * 1000);
    }

    public static void cancelQuestionTimer() {
        if (questionTimer != null) {
            questionTimer.cancel();
        }
    }

    private void addTitleAnimation(Label titleLabel) {
    Reflection reflection = new Reflection();
    reflection.setFraction(0.2);

    LinearGradient gradient = new LinearGradient(
            0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.RED),
            new Stop(0.5, Color.YELLOW),
            new Stop(1, Color.GREEN)
    );

    InnerShadow innerShadow = new InnerShadow();
    innerShadow.setOffsetX(2);
    innerShadow.setOffsetY(2);
    innerShadow.setColor(Color.BLACK);

    titleLabel.setTextFill(gradient);
    titleLabel.setEffect(innerShadow);

    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), titleLabel);
    scaleTransition.setFromX(1);
    scaleTransition.setFromY(1);
    scaleTransition.setToX(1.5);
    scaleTransition.setToY(1.5);
    scaleTransition.setCycleCount(TranslateTransition.INDEFINITE);
    scaleTransition.setAutoReverse(true);
    scaleTransition.play();
}

}
