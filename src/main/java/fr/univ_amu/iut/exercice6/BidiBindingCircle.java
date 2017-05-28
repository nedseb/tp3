package fr.univ_amu.iut.exercice6;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import static javafx.beans.binding.Bindings.bindBidirectional;

public class BidiBindingCircle extends Application {

    private Circle c = new Circle();
    private Slider slider = new Slider();
    private TextField textField = new TextField();

    private Pane pane = new Pane();
    private BorderPane root = new BorderPane();
    private Scene scene = new Scene(root);

    @Override
    public void start(Stage stage) throws Exception {
        addPane();
        addSlider();
        addTextField();
        createBindings();
        configStage(stage);
    }

    private void addTextField() {
        textField.setTextFormatter(new TextFormatter<String>(change -> {
            change.setText(change.getText().replace(',', '.'));
            String content = change.getControlNewText();
            if (content.isEmpty() || Double.parseDouble(content) > 250)
                change.setText("");
            return change;
        }));

        root.setBottom(textField);
    }

    private void createBindings() {
        c.centerXProperty().bind(pane.widthProperty().divide(2));
        c.centerYProperty().bind(pane.heightProperty().divide(2));

        bindBidirectional(slider.valueProperty(), c.radiusProperty());
        bindBidirectional(textField.textProperty(), c.radiusProperty(), new NumberStringConverter());

        c.setRadius(150);
    }

    private void configStage(Stage stage) {
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("BidiBindingCircle");
        stage.show();
    }

    private void addSlider() {
        slider.setMax(250);
        BorderPane.setMargin(slider, new Insets(30, 10, 30, 10));
        root.setTop(slider);
    }

    private void addPane() {
        pane.getChildren().add(c);
        pane.setPrefWidth(500);
        pane.setPrefHeight(500);
        root.setCenter(pane);
    }
}
