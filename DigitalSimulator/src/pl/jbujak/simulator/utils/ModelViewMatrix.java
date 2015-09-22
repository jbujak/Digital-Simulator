package pl.jbujak.simulator.utils;

import java.nio.DoubleBuffer;

public class ModelViewMatrix {
	private double[][] matrix;

	public ModelViewMatrix() {
		matrix = new double[4][4];
	}

	public void set(DoubleBuffer buffer) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matrix[i][j] = buffer.get();
			}
		}
	}

	public void set(int row, int column, double value) {
		matrix[row][column] = value;
	}

	public double get(int x, int y) {
		return matrix[x][y];
	}

	public Vector multiplyByVector(Vector vector) {
		double[] result = new double[4];
		double[] vectorArray = vector.getArray();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				result[i] += matrix[i][j] * vectorArray[j];
			}
		}

		return new Vector(result);
	}

	public String toString() {
		String result = "";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				result += matrix[i][j];
				result += " ";
			}
			result += "\n";
		}
		return result;
	}
}
