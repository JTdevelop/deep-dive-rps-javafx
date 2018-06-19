package edu.cnm.deepdive.rps.model;

import java.util.Arrays;
import java.util.Random;

// This is a regular class
public class Terrain {

  public static final int DEFAULT_SIZE = 50;

  private static final int[][] NEIGHBOR_OFFSETS = {
      {-1, 0},
      {0, -1}, {0, 1},
      {1, 0}
  };

  private final Object lock = new Object();
  private Breed[][] cells;
  private Breed[][] view;
  private Random rng;

  public Terrain(Random rng) {
    this.rng = rng;
    cells = new Breed[DEFAULT_SIZE][DEFAULT_SIZE];
    view = new Breed[DEFAULT_SIZE][];
    reset();
  }

  public void reset() {
    for (Breed[] row : cells) {
      for (int i = 0; i < row.length; i++) {
        row[i] = Breed.random(this.rng);
      }
    }
    for (int i = 0; i < cells.length; i++) {
      view[i] = Arrays.copyOf(cells[i], cells.length);
    }
  }

  public void iterate(int steps) {
    synchronized (lock) {
      for (int i = 0; i < steps; i++) {
        int playerRow = rng.nextInt(cells.length);
        int playerCol = rng.nextInt(cells[playerRow].length);
        Breed player = cells[playerRow][playerCol];
        int[] opponentLocation = getRandomNeighbor(playerRow, playerCol);
        Breed opponent = cells[opponentLocation[0]][opponentLocation[1]];
        if (player.play(opponent) == player) {
          cells[opponentLocation[0]][opponentLocation[1]] = player;
          view[opponentLocation[0]][opponentLocation[1]] = player;
        } else {
          cells[playerRow][playerCol] = opponent;
          view[playerRow][playerCol] = opponent;
        }
      }
    }
  }

  protected int[] getRandomNeighbor(int row, int col) {
    int[] offsets = NEIGHBOR_OFFSETS[rng.nextInt(NEIGHBOR_OFFSETS.length)];
    int opponentRow = (row + offsets[0] + cells.length) % cells.length;
    int opponentCol = (col + offsets[1] + cells[opponentRow].length) % cells[opponentRow].length;
    return new int[]{opponentRow, opponentCol};
  }

  public Breed[][] getCells() {
    //FIXME - Deal with gotcha.
    synchronized (lock) {
      return view;
    }
  }
}
