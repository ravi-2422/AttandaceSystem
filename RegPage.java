package Java.ATS;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class RegPage implements ActionListener {
    static JTextField tf1;
    static JTextField tf2;
    static JTextField tf3;
    static JTextField tf4;
    static JPasswordField pf1;
    static JPasswordField pf2;

    public static JPanel registerUI() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.BLACK);

        JLabel l1 = new JLabel("Register");
        l1.setFont(new Font("Tahoma", Font.BOLD, 18));
        l1.setForeground(Color.GREEN);

        JLabel l2 = new JLabel("Salutation:");
        l2.setForeground(Color.PINK);
        JLabel l3 = new JLabel("First Name:");
        l3.setForeground(Color.PINK);
        JLabel l4 = new JLabel("Last Name:");
        l4.setForeground(Color.PINK);
        JLabel l5 = new JLabel("Username:");
        l5.setForeground(Color.CYAN);
        JLabel l6 = new JLabel("Password:");
        l6.setForeground(Color.RED);
        JLabel l7 = new JLabel("re-enter:");
        l7.setForeground(Color.GREEN);

        tf1 = new JTextField(30);
        tf1.setForeground(Color.BLACK);
        tf2 = new JTextField(30);
        tf2.setForeground(Color.BLACK);
        tf3 = new JTextField(30);
        tf3.setForeground(Color.BLACK);
        tf4 = new JTextField(30);
        tf4.setForeground(Color.BLACK);
        pf1 = new JPasswordField(30);
        pf2 = new JPasswordField(30);

        JButton b1 = new JButton("Submit");
        b1.addActionListener(new RegPage());
        JButton b2 = new JButton("Back");
        b2.addActionListener(new RegPage());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        p.add(l1, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        p.add(l2, c);

        c.gridy = 2;
        p.add(l3, c);

        c.gridy = 3;
        p.add(l4, c);

        c.gridy = 4;
        p.add(l5, c);

        c.gridy = 5;
        p.add(l6, c);

        c.gridy = 6;
        p.add(l7, c);

        c.gridx = 2;
        c.gridy = 1;
        p.add(tf1, c);
        c.gridy = 2;
        p.add(tf2, c);
        c.gridy = 3;
        p.add(tf3, c);
        c.gridy = 4;
        p.add(tf4, c);
        c.gridy = 5;
        p.add(pf1, c);
        c.gridy = 6;
        p.add(pf2, c);

        c.gridwidth = 2;
        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;
        p.add(b1, c);

        c.gridwidth = 2;
        c.gridy = 8;
        c.anchor = GridBagConstraints.EAST;
        p.add(b2, c);

        return p;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Submit") {
            if(tf1.getText().length()==0) {
                tf1.setBackground(Color.RED);
                return;
            }
            if(tf2.getText().length()==0) {
                tf2.setBackground(Color.RED);
                return;
            }if(tf3.getText().length()==0) {
                tf3.setBackground(Color.RED);
                return;
            }if(tf4.getText().length()==0) {
                tf4.setBackground(Color.RED);
                return;
            }if(new String(pf1.getPassword()).length()==0) {
                pf1.setBackground(Color.RED);
                return;
            }
            if(!(new String(pf1.getPassword()).equals(new String(pf2.getPassword())))) {
                pf1.setBackground(Color.RED);
                pf2.setBackground(Color.RED);
                return;
            }
            
            User user = new User();
            user.addUser(tf1.getText(), tf2.getText(), tf3.getText(), tf4.getText(), new String(pf1.getPassword()));
            Auth.loginPage();
        }
        if (e.getActionCommand() == "Back") {
            Auth.loginPage();
        }
    }
}
