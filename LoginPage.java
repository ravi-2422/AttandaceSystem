package Java.ATS;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class LoginPage implements ActionListener{
    static JTextField tf1;
    static JPasswordField tf2;

    public static JPanel loginUI() {
        JPanel p = new JPanel(new GridBagLayout());
        //(30,41,51)
        p.setBackground(new Color(18, 40, 60));

        JLabel l1 = new JLabel("Login");
        l1.setFont(new Font("Tahoma", Font.BOLD, 18));
        l1.setForeground(Color.GREEN);

        JLabel l2 = new JLabel("Username:");
        l2.setForeground(Color.PINK);
        JLabel l3 = new JLabel("Password:");
        l3.setForeground(Color.RED);
        JLabel l4 = new JLabel("Register new account");
        l4.setForeground(Color.CYAN);

        tf1 = new JTextField(30);
        tf2 = new JPasswordField(30);
        JButton b1 = new JButton("Login");
        JButton b2 = new JButton("Register");
        b1.addActionListener(new LoginPage());
        b2.addActionListener(new LoginPage());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        p.add(l1, c);

        c.gridy = 1;
        c.gridwidth = 1;
        p.add(l2, c);

        c.gridx = 1;
        p.add(tf1, c);

        c.gridx = 0;
        c.gridy = 2;
        p.add(l3, c);

        c.gridx = 1;
        p.add(tf2, c);

        c.gridy = 3;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        p.add(b1, c);
    
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.EAST;
        p.add(b2, c);

        c.anchor = GridBagConstraints.WEST;
        p.add(l4, c);

        return p;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "Login") {
            if(User.validate(tf1.getText(), new String(tf2.getPassword()))) {
                ATS.path.add(ATS.path.get(ATS.path.size()-1)+ "/" + ATS.user.uname);
                System.out.println(ATS.path.get(ATS.path.size()-1));
                Auth.homePage();
            } else{
                tf1.setForeground(Color.RED);
                tf2.setForeground(Color.RED);
            }
        }
        if(e.getActionCommand() == "Register") {
            Auth.regPage();
        }
    }
}

class MyPanel extends JPanel {
    static Image image = Toolkit.getDefaultToolkit().getImage("./Java/ATS/Admin/logo.png");
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(image, 0, 0, this);
    }
  }
