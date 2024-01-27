package Java.ATS;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class ProgrammeHandler implements ActionListener {
    public static JPanel createDegree() {
        JPanel tool = new JPanel(null);
        tool.setBackground(new Color(30,41,51));
        tool.setBounds(0, 0, ATS.WIDTH / 6, ATS.HEIGHT);

        JLabel l1 = new JLabel("Enter Programme Name");
        l1.setForeground(Color.YELLOW);
        l1.setFont(new Font("Tahoma", Font.BOLD, 15));
        l1.setBounds(10, 120, ATS.WIDTH / 6 - 10, 20);

        HomePage.tf = new JTextField();
        HomePage.tf.setBounds(10, 150, ATS.WIDTH / 6 - 20, 20);

        JButton b = new JButton("Create Programme");
        b.setBounds(10, 200, ATS.WIDTH / 6 - 20, 50);
        b.addActionListener(new ProgrammeHandler());

        JButton out = new JButton("Signout");
        out.setBounds(10, 500, ATS.WIDTH / 6 - 20, 50);
        out.addActionListener(new ProgrammeHandler());
        tool.add(out);

        tool.add(l1);
        tool.add(HomePage.tf);
        tool.add(b);
        HomePage.info(tool);

        return tool;
    }

    public static JPanel loadDegree() {
        JPanel content = new JPanel();
        content.setBackground(Color.BLACK);
        content.setBounds(ATS.WIDTH / 6, 0, 5 * ATS.WIDTH / 6, ATS.HEIGHT);

        JPanel child = new JPanel(new GridLayout(10, 1));
        child.setBackground(Color.BLACK);
        
        String rel = ATS.path.get(ATS.path.size() - 1) + "/Programme";
        File f = new File(rel);
        String[] arr = f.list();
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                JPanel p = new JPanel(new GridLayout(1, 2));
                JLabel l = new JLabel(" TAP ON :  " + arr[i] + "  PROGRAMME");
                JButton b = new JButton(arr[i]);
                if(i%2==0) {
                    p.setBackground(Color.WHITE);
                    l.setForeground(Color.BLACK);
                } else {
                    p.setBackground(new Color(30,41,51));
                    l.setForeground(Color.WHITE);
                } 
                
                b.addActionListener(new ProgrammeHandler());
                p.add(l);
                p.add(b);
                child.add(p);
            }
        }

        JScrollPane sp1 = new JScrollPane(child);
        sp1.setBounds(ATS.WIDTH / 6, 0, 5 * ATS.WIDTH / 6, ATS.HEIGHT);
        content.add(sp1);
        content.setLayout(null);

        return content;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Create Programme") {
            String str = HomePage.tf.getText();
            HomePage.tf.setText("");
            if (str.length() == 0) {
                HomePage.tf.setBackground(Color.RED);
                return;
            }
            String rel = ATS.path.get(ATS.path.size() - 1) + "/Programme";
            File f = new File(rel);
            f.mkdir();
            f = new File(rel + "/" + str);
            f.mkdir();
            HomePage.programmePane();
        } else if (e.getActionCommand() == "Signout") {
            int confirmed = JOptionPane.showConfirmDialog(ATS.f, "Are you sure you want to Logout?", "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                ATS.user = null;
                ATS.path.remove(ATS.path.size() - 1);
                Auth.loginPage();
            }
        } else {
            String rel = ATS.path.get(ATS.path.size() - 1) + "/Programme/" + e.getActionCommand();
            ATS.path.add(rel);
            HomePage.batchPane();
        }
    }
}