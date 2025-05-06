package com.example.myproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloController {

    @FXML
    private Label attemptslabel; // Лейбл для отображения текста "Осталось попыток"

    @FXML
    private Label attemptsnumberlabel; // Лейбл для отображения количества оставшихся попыток

    @FXML
    private Button checkbtn; // Кнопка для проверки введенного числа

    @FXML
    private Label guessedhints; // Лейбл для отображения подсказок

    @FXML
    private Label guessednumbers; // Лейбл для отображения введенных чисел

    @FXML
    private TextField guessingfield; // Текстовое поле для ввода числа

    @FXML
    private Label hintslabel; // Лейбл для отображения текста "Подсказки"

    @FXML
    private Label loseText; // Лейбл для отображения текста при проигрыше

    @FXML
    private Label mainlabel; // Лейбл для основного текста

    @FXML
    private Label numberslabel; // Лейбл для отображения текста "Числа"

    @FXML
    private Label secondarylabel; // Лейбл для второстепенного текста

    @FXML
    private Label winText; // Лейбл для отображения текста при выигрыше

    private int secretNumber; // Загаданное число
    private int attempts = 7; // Количество попыток
    private List<Integer> guesses = new ArrayList<>();// Список введенных чисел
    private List<String> hints = new ArrayList<>();
    private Random random = new Random();// Генератор случайных чисел
    int guess;
    int wins;
    int losses;
    String newHint;

    // Метод инициализации контроллера, вызывается после загрузки FXML
    @FXML
    public void initialize() {
        startNewGame(); // Начинаем новую игру при инициализации контроллера
    }

    // Метод для начала новой игры
    private void startNewGame() {
        secretNumber = random.nextInt(100) + 1; // Генерация случайного числа от 1 до 100
        attempts = 7; // Установка количества попыток
        guesses.clear();// Очистка списка введенных чисел
        hints.clear();
        updateLabels(); // Обновление текста лейблов
        guessingfield.clear(); // Очистка текстового поля
        guessedhints.setText("");
    }

    // Обработчик нажатия на кнопку "Проверить"
    @FXML
    void btnclick(ActionEvent event) {
        checkGuess(); // Вызов метода проверки введенного числа
    }

    // Метод для проверки введенного числа
    private void checkGuess() {
        try {
            guess = Integer.parseInt(guessingfield.getText()); // Преобразование введенного текста в число
            if (guess < 1 || guess > 100) { // Проверка диапазона введенного числа
                showAlert(AlertType.ERROR, "Неверный ввод", "Пожалуйста, введите число от 1 до 100.");
                return;
            }

            if (guesses.contains(guess)) { // Проверка, было ли уже введено это число
                showAlert(AlertType.WARNING, "Повторный ввод", "Вы уже вводили это число.");
                return;
            }

            if(guess < secretNumber){
                newHint = "Больше";
            }
            else if (guess > secretNumber){
                newHint = "Меньше";
            }
            else{
                newHint = " ";
            }

            attempts--; // Уменьшение количества попыток
            guesses.add(guess);// Добавление числа в список введенных
            hints.add(newHint);

            updateLabels(); // Обновление текста лейблов

            if (guess == secretNumber) { // Проверка на выигрыш
                winGame();
                return;
            }

            if (attempts == 0) { // Проверка на проигрыш
                loseGame();
                return;
            }

            guessingfield.clear(); // Очистка текстового поля
        } catch (NumberFormatException e) { // Обработка исключения, если введен неверный формат числа
            showAlert(AlertType.ERROR, "Неверный формат", "Пожалуйста, введите целое число.");
        }
    }

    // Метод для обновления текста лейблов
    private void updateLabels() {
        attemptsnumberlabel.setText(Integer.toString(attempts));
        guessednumbers.setText(formatList(guesses));
        guessedhints.setText(hintList(hints));
        winText.setText("Побед: " + wins);
        loseText.setText("Поражений: " + losses);
    }

    // Метод для форматирования списка чисел в виде столбика
    private String formatList (List < Integer > list) {
        StringBuilder sb = new StringBuilder();
            for (Integer i : list) {
                sb.append(i).append("\n"); // Добавление каждого числа с новой строки
            }
            return sb.toString();
    }


    private String hintList(List < String > list) {
        StringBuilder sb = new StringBuilder();
        for (String s  : list) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }


    @FXML
    private DialogPane dialog;

    // Метод для отображения всплывающего окна
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();// Отображение окна и ожидание закрыти

    }

    // Метод для обработки выигрыша
    private void winGame() {
        wins++;
        showAlert(AlertType.INFORMATION, "Выигрыш!", "Вы угадали число. Было загадано " + secretNumber + ".");
        startNewGame();
    }

    // Метод для обработки проигрыша
    private void loseGame() {
        losses++;
        showAlert(AlertType.INFORMATION, "Поражение!", "Вы не угадали число. Было загадано " + secretNumber + ".");
        startNewGame();
    }

}
