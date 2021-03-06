package edu.cnm.deepdive.rps.controller;

import edu.cnm.deepdive.rps.model.Terrain;
import edu.cnm.deepdive.rps.view.TerrainView;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class Controller {

  private static final int STEPS_PER_ITERATION = 75;
  private static final long MAX_SLEEP_PER_ITERATION = 10;

  @FXML
  private TerrainView terrainView;
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

  private double defaultViewHeight;
  private double defaultViewWidth;
  private double fitViewHeight;
  private double fitViewWidth;
  private String iterationFormat;
  private Terrain terrain;
  private boolean running = false;
  private Runner runner = null;
  private final Object lock = new Object();
  private Timer timer;

  @FXML
  private void initialize() {
    terrain = new Terrain(new Random());
    defaultViewHeight = terrainView.getWidth();
    defaultViewWidth = terrainView.getHeight();
    fitViewWidth = viewScroller.getPrefWidth();
    fitViewHeight = viewScroller.getPrefHeight();
    iterationFormat = iterationsLabel.getText();
    terrainView.setSource(terrain.getCells());
    terrainView.draw();
    timer = new Timer();
  }

  @FXML
  private void fitView(ActionEvent actionEvent) {
    if (fitcheckbox.isSelected()) {
      terrainView.setWidth(fitViewWidth);
      terrainView.setHeight(fitViewHeight);
    } else {
      terrainView.setWidth(defaultViewWidth);
      terrainView.setHeight(defaultViewHeight);
    }
    if (!running) {
      terrainView.draw();
    }
  }

  @FXML
  private void start(ActionEvent actionEvent) {
    running = true;
    start.setDisable(true);
    stop.setDisable(false);
    reset.setDisable(true);
    timer.start();
    runner = new Runner();
    runner.start();
  }

  @FXML
  private void reset(ActionEvent actionEvent) {
    terrain.reset();
    draw();
  }

  @FXML
  private void stop(ActionEvent actionEvent) {
    running = false;
    runner = null;
    start.setDisable(false);
    stop.setDisable(true);
    reset.setDisable(false);
    timer.stop();
  }

  private void draw() {
    synchronized (lock) {
      terrainView.draw();
      iterationsLabel.setText(String.format(iterationFormat, terrain.getIterations()));
    }
  }

  private class Timer extends AnimationTimer {

    @Override
    public void handle(long now) {
      draw();
    }
  }

  private class Runner extends Thread {

    @Override
    public void run() {
      while (running) {
        synchronized (lock) {
          //terrain = MIX IT UP
          terrain.mix((int) mixingSlider.getValue());
          terrain.iterate(STEPS_PER_ITERATION);
        }
        try {
          Thread.sleep(1 + MAX_SLEEP_PER_ITERATION - (long) speedSlider.getValue());
        } catch (InterruptedException e) {
          // DO NOTHING.
        }
      }
    }
  }
}
