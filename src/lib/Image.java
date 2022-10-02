package lib;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lib.io.ToKeyboard;

public class Image {
  private Matrix rMat, gMat, bMat, aMat;

  /**
   * Membuat image dari file.
   * 
   * @param file file yang ingin dibaca
   * @throws IOException jika file bukan merupakan gambar.
   */
  public Image(File file) throws IOException {
    BufferedImage img = ImageIO.read(file);
    if (img == null) {
      throw new IOException("File is not an image");
    }
    setMatrixFromImage(img);
  }

  /**
   * Membuat image dari matrix r,g,b,a
   * <p>
   * Prekondisi: semua matrix memiliki ukuran yang sama
   * 
   * @param rMat
   * @param gMat
   * @param bMat
   * @param aMat
   */
  public Image(Matrix rMat, Matrix gMat, Matrix bMat, Matrix aMat) {
    this.rMat = rMat;
    this.gMat = gMat;
    this.bMat = bMat;
    this.aMat = aMat;
  }

  /**
   * 
   * @return banyak total pixel pada gambar
   */
  public int getPixelSize() {
    return rMat.getNCol() * rMat.getNRow();
  }

  /**
   * I.S: image bisa terdefinisi atau tidak
   * <p>
   * F.S: r,g,b,a image terisi dari bufferedimage yang diberikan
   * 
   * @param img bufferedimage yang akan diambil datanya
   */
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

  /**
   * 
   * @return bufferedimage berdasarkan matriks r,g,b,a image
   */
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

  /**
   * 
   * @param color
   * @return color jika berada di range 0-255, 0 jika kurang dari 0, 255 jika lebih dari 255
    */
  private static int onColorRange(int color) {
    if (color < 0) {
      return 0;
    } else if (color > 255) {
      return 255;
    } else {
      return color;
    }
  }

  /**
   * I.S. image terdefinisi
   * <p>
   * F.S. image diperbesar sebanyak scalingFactor kali
   * @param scalingFactor
    */
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
