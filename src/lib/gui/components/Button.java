package lib.gui.components;

import java.awt.Cursor;

import javax.swing.JButton;

public class Button extends JButton {
  public Button(String text) {
    super(text);
    setFocusPainted(false);
    setCursor(new Cursor(Cursor.HAND_CURSOR));
    setBorder(new RoundedBorder(6));
  }
}
