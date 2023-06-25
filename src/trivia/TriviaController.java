/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trivia;

import javafx.scene.Node;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import javafx.animation.FadeTransition;
import javafx.application.Platform;

public class TriviaController implements Initializable {

    @FXML
    private VBox registerPanel;

    @FXML
    private VBox themePanel;

    @FXML
    private VBox questionPanel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label questionLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private RadioButton answer1RadioButton;

    @FXML
    private RadioButton answer2RadioButton;

    @FXML
    private RadioButton answer3RadioButton;

    @FXML
    private RadioButton answer4RadioButton;

    private int score;
    private String currentTheme;
    private int currentQuestionIndex;
    private Map<String, List<String[]>> questionsByTheme;
    private String selectedTheme;
    private List<String> answerOptions;
    private Timeline questionTimer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    score = 0;
    gameEnded = false;

    questionsByTheme = new HashMap<>();
    questionsByTheme.put("Cultura", Arrays.asList(
        new String[]{"¿En qué año ocurrió la Revolución Francesa?", "1789", "1790", "1788", "1792", "1"},
        new String[]{"¿Cuál es el autor de la obra 'Don Quijote de la Mancha'?", "Miguel de Cervantes", "William Shakespeare", "Gabriel García Márquez", "Federico García Lorca", "1"},
        new String[]{"¿Cuál es la capital de Australia?", "Canberra", "Sídney", "Melbourne", "Brisbane", "1"},
        new String[]{"¿Cual es el autor de el libro Su Santidad Pecadora?", "Martín Sacristán Tordesillas", "Oscar Wilde", "Gabriel García Márquez", "Federico García Lorca", "1"},
        new String[]{"¿Cuál es la moneda oficial de Japón?", "Yen", "Won", "Dólar", "Euro", "1"},
        new String[]{"¿Quién pintó la Mona Lisa?", "Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Salvador Dalí", "1"},
        new String[]{"¿Quién pintó la famosa obra La última cena?", "Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Salvador Dalí", "1"},
        new String[]{"¿Cuál es la obra literaria más conocida del escritor William Shakespeare?", "Romeo y Julieta", "Macbeth", "Hamlet", "La tempestad", "1"},
        new String[]{"¿Cuál es el nombre de la obra de teatro más famosa de William Shakespeare?", "Hamlet", "Romeo y Julieta", "Macbeth", "La tempestad", "1"},
        new String[]{"¿¿Quién pintó la famosa obra de arte La persistencia de la memoria?", "Salvador Dalí", "Pablo Picasso", "Vincent van Gogh", "Leonardo da Vinci", "1"},
        new String[]{"¿Compositor conocido como el Príncipe de la Música, uno de los más grandes músicos?", "Johann Sebastian Bach", "Ludwig van Beethoven", "Wolfgang Amadeus Mozart", "Franz Schubert", "1"}
    ));
    questionsByTheme.put("Deportes", Arrays.asList(
        new String[]{"¿En qué deporte de élite se utiliza una raqueta para golpear una pelota?", "Tenis", "Golf", "Bádminton", "Ping Pong", "1"},
        new String[]{"¿Cuál es el país de origen del fútbol?", "Inglaterra", "Brasil", "España", "Italia", "1"},
        new String[]{"¿Cuál es el jugador de baloncesto con más campeonatos de la NBA?", "Michael Jordan", "LeBron James", "Kobe Bryant", "Magic Johnson", "1"},
        new String[]{"¿Donde nació el jugador al que le decían El Rey Pelé?", "Brasil", "Argentina", "Uruguay", "Colombia", "1"},
        new String[]{"¿Cuándo se celebró el primer mundial de fútbol?", "1930", "1934", "1938", "1950", "1"},
        new String[]{"¿Quién ganó el mundial de fútbol de 2010?", "España", "Alemania", "Brasil", "Argentina", "1"},
        new String[]{"¿Qué revista concede el Balón de Oro?", "La revista France Football", "FIFA", "UEFA", "ESPN", "1"},
        new String[]{"¿Cuál es el país de origen del tenista Roger Federer?", "Suiza", "España", "Estados Unidos", "Australia", "1"}
    ));
    questionsByTheme.put("Historia", Arrays.asList(
        new String[]{"¿En qué año se descubrió América?", "1492", "1491", "1490", "1493", "1"},
        new String[]{"¿Cuál fue el primer presidente de Estados Unidos?", "George Washington", "Abraham Lincoln", "Thomas Jefferson", "John Adams", "1"},
        new String[]{"¿Quién fue el líder de la Revolución Rusa en 1917?", "Vladimir Lenin", "Joseph Stalin", "Mijaíl Gorbachov", "Nicolás II", "1"},
        new String[]{"¿En qué país fue firmado el Tratado de Versalles y fue el acuerdo de paz que puso fin a la Primera Guerra Mundial?", "Francia", "Reino Unido", "Estados Unidos", "Alemania", "1"},
        new String[]{"¿En qué año se firmó la Declaración de Independencia de los Estados Unidos?", "1776", "1789", "1804", "1824", "1"},
        new String[]{"¿Cuál fue el líder político y militar de la Unión Soviética durante la Segunda Guerra Mundial?", "Joseph Stalin", "Vladimir Lenin", "Mijaíl Gorbachov", "Nikita Jrushchov", "1"},
        new String[]{"¿En qué año cayó el Muro de Berlín, marcando el final de la Guerra Fría?", "1989", "1991", "1987", "1993", "1"},
        new String[]{"¿Qué año marcó el lanzamiento del primer iPhone de Apple?", "2007", "2005", "2009", "2010", "1"},
        new String[]{"¿Cuál fue la civilización antigua que construyó las famosas pirámides de Giza?", "Civilización Egipcia", "Civilización Azteca", "Civilización Inca", "Civilización Maya", "1"}
    ));

    ToggleGroup answerGroup = new ToggleGroup();
    answer1RadioButton.setToggleGroup(answerGroup);
    answer2RadioButton.setToggleGroup(answerGroup);
    answer3RadioButton.setToggleGroup(answerGroup);
    answer4RadioButton.setToggleGroup(answerGroup);

    passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null && !newValue.isEmpty()) {
            passwordField.setText("*".repeat(newValue.length()));
        }
    });
}


    private void generateQuestions(String theme) {
        currentTheme = theme;
        currentQuestionIndex = 0;

        String[] questionData = questionsByTheme.get(currentTheme).get(currentQuestionIndex);
        questionLabel.setText(questionData[0]);
        setAnswerOptions(questionData);
        clearSelectedAnswer();

        score = 0;
        scoreLabel.setText("Puntaje: " + score);

        startQuestionTimer();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }

    private void clearSelectedAnswer() {
        ToggleGroup answerGroup = answer1RadioButton.getToggleGroup();
        answerGroup.selectToggle(null);
    }

    private String getCorrectAnswer() {
        return questionsByTheme.get(currentTheme).get(currentQuestionIndex)[1];
    }

    private void setAnswerOptions(String[] questionData) {
    answerOptions = new ArrayList<>();

    // Agregar respuesta correcta
    answerOptions.add(questionData[1]);

    // Obtener todas las respuestas posibles
    List<String> allAnswers = new ArrayList<>(Arrays.asList(questionData[1], questionData[2], questionData[3], questionData[4]));

    // Mezclar todas las respuestas posibles
    Collections.shuffle(allAnswers);

    // Configurar las opciones de respuesta en los RadioButtons
    answer1RadioButton.setText(allAnswers.get(0));
    answer2RadioButton.setText(allAnswers.get(1));
    answer3RadioButton.setText(allAnswers.get(2));
    answer4RadioButton.setText(allAnswers.get(3));
}


    private void showCorrectAnswers() {
        questionPanel.setVisible(false);
        themePanel.setVisible(true);

        StringBuilder correctAnswers = new StringBuilder();
        for (String[] questionData : questionsByTheme.get(currentTheme)) {
            correctAnswers.append(questionData[0]).append(": ").append(questionData[1]).append("\n");
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Respuestas correctas");
        alert.setHeaderText(null);
        alert.setContentText("Las respuestas correctas son:\n\n" + correctAnswers.toString());
        alert.showAndWait();
    }

    private void startQuestionTimer() {
        final int[] timeInSeconds = {15};

        questionTimer = new Timeline();
        questionTimer.setCycleCount(Timeline.INDEFINITE);
        questionTimer.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timerLabel.setText("Tiempo restante: " + timeInSeconds[0] + " segundos");
                timeInSeconds[0]--;

                if (timeInSeconds[0] < 0) {
                    questionTimer.stop();
                    endGame();  // Tiempo agotado, terminar el juego
                }
            }
        }));
        questionTimer.playFromStart();
    }

    private void stopQuestionTimer() {
        timerLabel.setText("");
        if (questionTimer != null) {
            questionTimer.stop();
        }
    }

    private boolean gameEnded = false;

   public void endGame() {
    if (gameEnded) {
        return;
    }

    stopQuestionTimer();
    gameEnded = true;

    StringBuilder correctAnswers = new StringBuilder();
    for (String[] questionData : questionsByTheme.get(currentTheme)) {
        String question = questionData[0];
        String correctAnswer = questionData[1];
        correctAnswers.append(question).append(": ").append(correctAnswer).append("\n");
    }

    Platform.runLater(() -> {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Fin del juego");
        alert.setHeaderText(null);
        alert.setContentText("Juego terminado. Tu puntaje (" + usernameField.getText() + ") es " + score + " puntos.\n\n" +
                "Respuestas correctas:\n\n" + correctAnswers.toString());
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(500, 300);
        alert.show();

        // Cerrar automáticamente después de tres segundos
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            alert.hide();  // Ocultar la ventana de respuestas

            // Cerrar la aplicación JavaFX
            Platform.exit();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    });
}


    private String getSelectedAnswer(String[] questionData) {
        if (answer1RadioButton.getText().equals(questionData[1])) {
            return answer1RadioButton.getText();
        } else if (answer2RadioButton.getText().equals(questionData[1])) {
            return answer2RadioButton.getText();
        } else if (answer3RadioButton.getText().equals(questionData[1])) {
            return answer3RadioButton.getText();
        } else if (answer4RadioButton.getText().equals(questionData[1])) {
            return answer4RadioButton.getText();
        }

        return null;
    }


// FXML

    
    @FXML
private void handleRegisterButton(ActionEvent event) {
    VBox registerPanel = (VBox) ((Node) event.getSource()).getParent(); // Obtener el VBox padre del botón
    VBox themePanel = (VBox) registerPanel.getScene().lookup("#themePanel");

    FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1), registerPanel);
    fadeOutTransition.setFromValue(1.0);
    fadeOutTransition.setToValue(0.0);
    fadeOutTransition.setOnFinished(e -> {
        registerPanel.setVisible(false);
        themePanel.setVisible(true);
        FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1), themePanel);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
    });
    fadeOutTransition.play();
}


    @FXML
    private void handleThemeButton(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        selectedTheme = selectedButton.getText();

        themePanel.setVisible(false);
        questionPanel.setVisible(true);

        generateQuestions(selectedTheme);
    }

    private static final int CORRECT_ANSWER_SCORE = 15;

    @FXML
    private void handleNextButton(ActionEvent event) {
        if (gameEnded) {
            return;
        }

        stopQuestionTimer();

        ToggleGroup answerGroup = answer1RadioButton.getToggleGroup();
        RadioButton selectedRadioButton = (RadioButton) answerGroup.getSelectedToggle();
        if (selectedRadioButton == null) {
            showAlert("Advertencia", "Por favor, selecciona una respuesta antes de continuar.");
            return;
        }

        String selectedAnswer = selectedRadioButton.getText();
        String correctAnswer = getCorrectAnswer();

        if (selectedAnswer.equals(correctAnswer)) {
            score += CORRECT_ANSWER_SCORE;
        }

        scoreLabel.setText("Puntaje: " + score + " " + usernameField.getText());

        currentQuestionIndex++;
        if (currentQuestionIndex < questionsByTheme.get(currentTheme).size()) {
            // Continuar con la siguiente pregunta
            String[] questionData = questionsByTheme.get(currentTheme).get(currentQuestionIndex);
            questionLabel.setText(questionData[0]);
            setAnswerOptions(questionData);
            clearSelectedAnswer();

            startQuestionTimer();
        } else {
            // No hay más preguntas, terminar el juego
            endGame();
        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        stopQuestionTimer();

        questionPanel.setVisible(false);
        themePanel.setVisible(true);
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        endGame();
    }

}