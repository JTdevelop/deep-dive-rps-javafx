package edu.cnm.deepdive.rps.controller;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class Controller {

  @FXML
  private ResourceBundle resources;
  @FXML
  private Text iterationsLabel;
  @FXML
  private ScrollPane viewScroller;
  @FXML
  private CheckBox fitcheckbox;
  @FXML
  private Slider speedSlider;
  @FXML
  private Slider mixingSlider;
  @FXML
  private Button start;
  @FXML
  private Button reset;
  @FXML
  private Button stop;

  @FXML
  private void initialize() {
    // TODO Perform any additional initialization,  as required.
  }

  @FXML
  private void fitView(ActionEvent actionEvent) {
  }

  @FXML
  private void start(ActionEvent actionEvent) {
  }

  @FXML
  private void reset(ActionEvent actionEvent) {
  }

  @FXML
  private void stop(ActionEvent actionEvent) {
  }

}
