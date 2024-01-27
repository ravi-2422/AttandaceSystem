package Java.ATS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class AttendanceHandler implements ActionListener {
    static int pointer = 0;
    static ArrayList<JButton[]> blist = new ArrayList<>();
    static String path = ATS.path.get(ATS.path.size() - 1) + "/student.data";
    static ArrayList<Student> sarr = new StudentData(path).loadData();

    public static JPanel createAttendance() {
        JPanel tool = new JPanel(new GridLayout(1, 4));
        tool.setBackground(new Color(30, 41, 51));
        tool.setBounds(0, 0, ATS.WIDTH, 50);

        JButton prev = new JButton("PREV");
        prev.addActionListener(new AttendanceHandler());
        // prev.setBackground(Color.CYAN);

        JButton out = new JButton("Save and Back");
        out.addActionListener(new AttendanceHandler());
        // out.setBackground(Color.GREEN);

        JButton uic = new JButton("Summary");
        uic.addActionListener(new AttendanceHandler());
        // uic.setBackground(Color.GREEN);

        JButton next = new JButton("NEXT");
        next.addActionListener(new AttendanceHandler());
        // next.setBackground(Color.CYAN);

        tool.add(prev);
        tool.add(out);
        tool.add(uic);
        tool.add(next);
        return tool;
    }

    public static JPanel getAttendanceController() {
        path = ATS.path.get(ATS.path.size() - 1) + "/student.data";
        sarr = new StudentData(path).loadData();
        for (int i = 0; i < sarr.size(); i++)
            blist.add(null);
        JPanel child = new JPanel(new GridLayout(3, 1));
        child.setBackground(Color.BLACK);

        if (pointer < sarr.size()) {
            Student std = sarr.get(pointer);
            JPanel p = new JPanel(new GridLayout(1, 5));
            JLabel l1 = new JLabel("    " + Integer.toString(pointer + 1));
            JLabel l2 = new JLabel(std.roll);
            JLabel l3 = new JLabel(std.name);
            JButton b0 = new JButton("PRESENT-" + Integer.toString(pointer + 1));
            JButton b1 = new JButton("ABSENT-" + Integer.toString(pointer + 1));

            p.setBackground(new Color(7, 99, 197));
            l1.setForeground(Color.BLACK);
            l2.setForeground(Color.BLACK);
            l3.setForeground(Color.BLACK);

            if (sarr.get(pointer).attend == 1) {
                b0.setBackground(Color.GREEN);
                b1.setBackground(Color.PINK);
            } else if (sarr.get(pointer).attend == 2) {
                b0.setBackground(Color.PINK);
                b1.setBackground(Color.RED);
            } else {
                b0.setBackground(Color.PINK);
                b1.setBackground(Color.PINK);
            }

            b0.addActionListener(new AttendanceHandler());
            b1.addActionListener(new AttendanceHandler());

            JButton[] barr = new JButton[2];
            barr[0] = b0;
            barr[1] = b1;
            blist.add(pointer, barr);
            blist.remove(pointer + 1);

            p.add(l1);
            p.add(l2);
            p.add(l3);
            p.add(b0);
            p.add(b1);

            JPanel p0 = new JPanel();
            p0.setBackground(Color.BLACK);

            child.add(p0);
            child.add(p);
        }

        return child;
    }

    public static JPanel loadAttedance() {
        JPanel content = new JPanel();
        content.setBounds(0, 50, ATS.WIDTH, ATS.HEIGHT - 50);
        content.setBackground(Color.BLACK);
        content.setLayout(null);

        JScrollPane sp = new JScrollPane(getAttendanceController());
        sp.setBounds(0, 50, ATS.WIDTH, ATS.HEIGHT - 50);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        content.add(sp);

        return content;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Save and Back") {
            pointer = 0;
            StudentData sData = new StudentData(path);
            sData.saveStudent(sarr);
            StudentHandler.fillTodayEntry();
            HomePage.studentPane();
        } else if (e.getActionCommand() == "NEXT") {
            if(sarr.size()==0) return;
            pointer++;
            pointer %= sarr.size();
            HomePage.attendancePane();
        } else if (e.getActionCommand() == "PREV") {
            if(sarr.size()==0) return;
            pointer--;
            pointer = (pointer + sarr.size()) % sarr.size();
            HomePage.attendancePane();
        } else if (e.getActionCommand() == "Summary") {
            HomePage.changeUI(sarr);
        } else {
            String cmd = e.getActionCommand();
            int index = 0;
            if (cmd.charAt(0) == 'P') {
                index = Integer.parseInt(cmd.substring(8));
                blist.get(index - 1)[0].setBackground(Color.GREEN);
                blist.get(index - 1)[1].setBackground(Color.PINK);
                sarr.get(index - 1).attend = 1;
            } else {
                index = Integer.parseInt(cmd.substring(7));
                blist.get(index - 1)[0].setBackground(Color.PINK);
                blist.get(index - 1)[1].setBackground(Color.RED);
                sarr.get(index - 1).attend = 2;
            }

            if (sarr.get(pointer).attend != 0) {
                pointer++;
                pointer %= sarr.size();
            }

            StudentData stData = new StudentData(path);
            stData.saveStudent(sarr);
            HomePage.attendancePane();
        }
    }
}

class UIChanger implements ActionListener {
    static int pointer = 0;
    static ArrayList<Student> sarr = null;

    static JPanel changeUI(ArrayList<Student> array) {
        sarr = array;
        JPanel parent = new JPanel(null);
        parent.setBackground(Color.BLACK);

        JPanel tool = new JPanel(new GridLayout(1, 4));
        tool.setBackground(new Color(30, 41, 51));
        tool.setBounds(0, 0, ATS.WIDTH, 50);

        JButton out = new JButton("Save and Back");
        out.setBounds(ATS.WIDTH / 2 - 200, 10, 200, 30);
        out.addActionListener(new UIChanger());
        // out.setBackground(Color.GREEN);

        JButton prev = new JButton("PREV");
        prev.addActionListener(new UIChanger());
        // prev.setBackground(Color.BLACK);

        JButton next = new JButton("NEXT");
        next.addActionListener(new UIChanger());
        // next.setBackground(Color.BLACK);

        JButton allP = new JButton("ALL PRESENT");
        allP.addActionListener(new UIChanger());
        // prev.setBackground(Color.CYAN);

        JButton allA = new JButton("ALL ABSENT");
        allA.addActionListener(new UIChanger());
        // next.setBackground(Color.CYAN);

        JButton resetA = new JButton("RESET");
        resetA.addActionListener(new UIChanger());
        // prev.setBackground(Color.CYAN);

        JPanel infc = new JPanel(new GridLayout(9, 10));
        infc.setBounds(0, 50, ATS.WIDTH, ATS.HEIGHT - 100);
        infc.setBackground(Color.BLACK);

        for (int i = pointer; i < pointer + 90 && i < sarr.size(); i++) {
            JButton b = new JButton(sarr.get(i).roll);
            if (sarr.get(i).attend == 0)
                b.setBackground(Color.WHITE);
            else if (sarr.get(i).attend == 1)
                b.setBackground(Color.GREEN);
            else
                b.setBackground(Color.RED);
            b.addActionListener(new UIChanger());
            infc.add(b);
        }

        tool.add(prev);
        tool.add(allA);
        tool.add(out);
        tool.add(allP);
        tool.add(resetA);
        tool.add(next);

        parent.add(infc);
        parent.add(tool);
        return parent;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "NEXT") {
            if(sarr.size()==0) return;
            pointer += 10;
            if (pointer >= sarr.size())
                pointer = sarr.size() - 10;
            HomePage.changeUI(sarr);
        } else if (e.getActionCommand() == "PREV") {
            if(sarr.size()==0) return;
            pointer -= 10;
            if (pointer < 0)
                pointer = 0;
            HomePage.changeUI(sarr);
        } else if (e.getActionCommand() == "ALL PRESENT") {
            for (int i = 0; i < sarr.size(); i++)
                sarr.get(i).attend = 1;
            HomePage.changeUI(sarr);
        } else if (e.getActionCommand() == "ALL ABSENT") {
            for (int i = 0; i < sarr.size(); i++)
                sarr.get(i).attend = 2;
            HomePage.changeUI(sarr);
        } else if (e.getActionCommand() == "RESET") {
            for (int i = 0; i < sarr.size(); i++)
                sarr.get(i).attend = 0;
            HomePage.changeUI(sarr);
        } else if (e.getActionCommand() == "Save and Back") {
            pointer = 0;
            StudentData sData = new StudentData(AttendanceHandler.path);
            sData.saveStudent(sarr);
            StudentHandler.fillTodayEntry();
            HomePage.studentPane();
        } else {
            String cmd = e.getActionCommand();
            int index = Integer.parseInt(cmd.substring(cmd.length()-3)) - 1;
            sarr.get(index).attend++;
            sarr.get(index).attend %= 3;
            HomePage.changeUI(sarr);
        }
    }
}
