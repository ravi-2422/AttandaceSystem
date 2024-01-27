package Java.ATS;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class BatchHandler implements ActionListener {
    public static JPanel createBatch() {
        JPanel tool = new JPanel(null);
        tool.setBackground(new Color(30,41,51));
        tool.setBounds(0, 0, ATS.WIDTH / 6, ATS.HEIGHT);

        JLabel l1 = new JLabel("Enter Batch(Year)");
        l1.setForeground(Color.YELLOW);
        l1.setBounds(10, 120, ATS.WIDTH / 6 - 10, 20);
        l1.setFont(new Font("Tahoma", Font.BOLD, 15));

        HomePage.tf = new JTextField();
        HomePage.tf.setBounds(10, 150, ATS.WIDTH / 6 - 20, 20);

        JButton b = new JButton("Create Batch");
        b.setBounds(10, 200, ATS.WIDTH / 6 - 20, 50);
        b.addActionListener(new BatchHandler());

        JButton out = new JButton("Back");
        out.setBounds(10, 500, ATS.WIDTH / 6 - 20, 50);
        out.addActionListener(new BatchHandler());
        tool.add(out);

        tool.add(l1);
        tool.add(HomePage.tf);
        tool.add(b);
        HomePage.info(tool);

        return tool;
    }
    
    public static JPanel loadBatch() {
        JPanel content = new JPanel();
        content.setBackground(Color.BLACK);
        content.setBounds(ATS.WIDTH / 6, 0, 5 * ATS.WIDTH / 6, ATS.HEIGHT);

        JPanel child = new JPanel(new GridLayout(10, 1));
        child.setBackground(Color.BLACK);
        
        File f = new File(ATS.path.get(ATS.path.size() - 1));
        String[] arr = f.list();
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                JPanel p = new JPanel(new GridLayout(1, 2));
                JLabel l = new JLabel(" TAP ON :  " + arr[i] + "  BATCH");
                JButton b = new JButton(arr[i]);

                if(i%2==1) {
                    p.setBackground(Color.WHITE);
                    l.setForeground(Color.BLACK);
                } else {
                    p.setBackground(new Color(30,41,51));
                    l.setForeground(Color.WHITE);
                }
                 
                b.addActionListener(new BatchHandler());
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
        if (e.getActionCommand() == "Create Batch") {
            String str = HomePage.tf.getText();
            HomePage.tf.setText("");
            if (str.length() == 0) {
                HomePage.tf.setBackground(Color.RED);
                return;
            }
            File f = new File(ATS.path.get(ATS.path.size() - 1) + "/" + str);
            f.mkdir();
            HomePage.batchPane();
        } else if (e.getActionCommand() == "Back") {
            ATS.path.remove(ATS.path.size()-1);
            HomePage.programmePane();
        } else {
            String rel = ATS.path.get(ATS.path.size()-1) + "/" + e.getActionCommand();
            ATS.path.add(rel);
            HomePage.departmentPane();
        }
    }
}