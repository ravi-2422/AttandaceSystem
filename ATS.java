package Java.ATS;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

public class ATS {
    static int WIDTH = 0;
    static int HEIGHT = 0;
    public static User user = null;
    public static JFrame f = null;
    public static ArrayList<String> path = null;

    ATS() {
        path = new ArrayList<>();
        path.add(System.getProperty("user.dir")+"/Data");
        setState();
    }
    
    public static void setState() {
        getDisplaySize();
        f = new JFrame("NITJ-ATS");
        f.setBounds(0, 0, WIDTH, HEIGHT);
        f.setBackground(Color.BLUE);
        f.setDefaultCloseOperation(3);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
    }

    public static void clearState() {
        f.dispose();
        setState();
    }

    static void getDisplaySize() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        WIDTH = d.width;
        HEIGHT = d.height;
    }

    void authenticate() {
        Auth.loginPage();
    }

    public static void main(String[] args) {
        ATS Instance = new ATS();
        Instance.authenticate();
    }
}
