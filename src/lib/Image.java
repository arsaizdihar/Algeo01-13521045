package lib;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
  Matrix matrix;

  public Image(File file) throws IOException {
    BufferedImage img = ImageIO.read(file);
    if (img == null) {
      throw new IOException("File is not an image");
    }
    setMatrixFromImage(img);
  }

  public Image(Matrix matrix) {
    this.matrix = matrix;
  }

  private void setMatrixFromImage(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();
    double[][] result = new double[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // get grayscale
        int rgb = img.getRGB(j, i);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb & 0xFF);
        result[i][j] = (r + g + b) / 3;
      }
    }
    matrix = new Matrix(height, width);
    matrix.setContents(result);
  }

  public void exportImage(String filename) {
    BufferedImage img = new BufferedImage(matrix.getNCol(), matrix.getNRow(), BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < matrix.getNRow(); i++) {
      for (int j = 0; j < matrix.getNCol(); j++) {
        int rgb = (int) matrix.getElmt(i, j);
        int r = rgb;
        int g = rgb;
        int b = rgb;
        int color = (r << 16) | (g << 8) | b;
        img.setRGB(j, i, color);
      }
    }

    try {
      File file = new File(filename);
      ImageIO.write(img, "jpeg", file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
