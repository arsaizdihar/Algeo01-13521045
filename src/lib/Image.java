package lib;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
  private Matrix rMat, gMat, bMat, aMat;

  public Image(File file) throws IOException {
    BufferedImage img = ImageIO.read(file);
    if (img == null) {
      throw new IOException("File is not an image");
    }
    setMatrixFromImage(img);
  }

  public int getPixelSize() {
    return rMat.getNCol() * rMat.getNRow();
  }

  public Image(Matrix rMat, Matrix gMat, Matrix bMat, Matrix aMat) {
    this.rMat = rMat;
    this.gMat = gMat;
    this.bMat = bMat;
    this.aMat = aMat;
  }

  private void setMatrixFromImage(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();
    rMat = new Matrix(height, width);
    gMat = new Matrix(height, width);
    bMat = new Matrix(height, width);
    aMat = new Matrix(height, width);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Color c = new Color(img.getRGB(j, i), true);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();
        rMat.setElmt(i, j, r);
        gMat.setElmt(i, j, g);
        bMat.setElmt(i, j, b);
        aMat.setElmt(i, j, a);
      }
    }
  }

  public BufferedImage getBufferedImage() {
    BufferedImage img = new BufferedImage(rMat.getNCol(), rMat.getNRow(), BufferedImage.TYPE_INT_ARGB);
    for (int i = 0; i < rMat.getNRow(); i++) {
      for (int j = 0; j < rMat.getNCol(); j++) {
        int r = onColorRange((int) rMat.getElmt(i, j));
        int g = onColorRange((int) gMat.getElmt(i, j));
        int b = onColorRange((int) bMat.getElmt(i, j));
        int a = onColorRange((int) aMat.getElmt(i, j));
        int rgb = new Color(r, g, b, a).getRGB();
        img.setRGB(j, i, rgb);
      }
    }

    return img;
  }

  private static int onColorRange(int color) {
    if (color < 0) {
      return 0;
    } else if (color > 255) {
      return 255;
    } else {
      return color;
    }
  }

  public void scale(int scalingFactor) {
    rMat = rMat.getNTimesSizeMatrix(scalingFactor);
    ToKeyboard.printMessage("25% done");
    gMat = gMat.getNTimesSizeMatrix(scalingFactor);
    ToKeyboard.printMessage("50% done");
    bMat = bMat.getNTimesSizeMatrix(scalingFactor);
    ToKeyboard.printMessage("75% done");
    aMat = aMat.getNTimesSizeMatrix(scalingFactor);
    ToKeyboard.printMessage("100% done");
  }
}
