package lib.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lib.gui.components.Button;
import lib.gui.helper.Callback;

public class Window extends JFrame {

  public void start() {
    setTitle("Mathricks");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(new MainPanel());
    setResizable(false);
    setSize(400, 500);
    setVisible(true);
  }
}

class MainPanel extends JPanel {
  public CardLayout cardLayout;

  public MainPanel() {
    cardLayout = new CardLayout();
    setLayout(cardLayout);
    MainMenu mainMenu = new MainMenu(this, new Menu[] {
        new SPLMenu(),
        new Menu("DETERMINANT", "Determinan"),
        new Menu("INVERSE", "Matriks balikan"),
        new Menu("POLINOM", "Interpolasi Polinom"),
        new Menu("BICUBIC", "Interpolasi Bicubic"),
        new Menu("REGRESSION", "Regresi Linier Berganda"),
    });
    add(mainMenu, "MAIN");
    cardLayout.show(this, "MAIN");
  }

  public void addMenu(Component comp, String name) {
    add(comp, name);
  }

  public void showMenu(String name) {
    cardLayout.show(this, name);
  }

}

class MainMenu extends JPanel {
  public MainMenu(MainPanel mainPanel, Menu[] menus) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBackground(Color.white);
    JLabel mainLabel = new JLabel("Main Menu");
    mainLabel.setFont(new Font("Arial", Font.BOLD, 20));
    mainLabel.setAlignmentX(CENTER_ALIGNMENT);
    add(mainLabel);

    for (Menu menu : menus) {
      add(Box.createRigidArea(new Dimension(0, 5)));
      Button btn = new Button(menu.getTitle());
      btn.setAlignmentX(CENTER_ALIGNMENT);
      mainPanel.addMenu(menu.getPanel(), menu.getName());
      btn.addActionListener(e -> {
        mainPanel.showMenu(menu.getName());
      });
      menu.onBack = () -> mainPanel.showMenu("MAIN");
      add(btn);
    }

  }
}

class Menu {
  private String name;
  protected JPanel panel;
  private String title;
  public Callback onBack;

  public Menu(String name, String title) {
    this.title = title;
    this.name = name;
    panel = new JPanel();
    panel.setBackground(Color.white);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    JLabel label = new JLabel(this.title);
    label.setFont(new Font("Arial", Font.BOLD, 20));
    label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    Button btn = new Button("Back");
    btn.setAlignmentX(JButton.CENTER_ALIGNMENT);
    btn.addActionListener(e -> {
      onBack.run();
    });
    panel.add(btn);
    panel.add(label);
  }

  public String getName() {
    return name;
  }

  public JPanel getPanel() {
    return panel;
  }

  public String getTitle() {
    return title;
  }

}

class SPLMenu extends Menu {
  int count = 0;

  public SPLMenu() {
    super("SPL", "Sistem Persamaan Linier");
    count = 0;
    Button countBtn = new Button("Count: 0");
    countBtn.setAlignmentX(JButton.CENTER_ALIGNMENT);
    countBtn.addActionListener(e -> {
      this.count++;
      countBtn.setText("Count: " + count);
    });
    panel.add(countBtn);
  }
}