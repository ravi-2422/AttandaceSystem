package Java.ATS;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

class StudentHandler extends Thread implements ActionListener {
    static LocalDateTime cal = LocalDateTime.now();
    static JLabel l1 = null;
    static int total = 0;
    static int totalWork = 0;
    static JTextField tf1 = null;

    public static JPanel createStudent() {
        JPanel tool = new JPanel(null);
        tool.setBackground(new Color(30, 41, 51));
        tool.setBounds(0, 0, ATS.WIDTH / 6, ATS.HEIGHT);

        LocalDate d = LocalDate.of(cal.getYear(), cal.getMonth(), cal.getDayOfMonth());
        String date = cal.getDayOfMonth() + "/" + cal.getMonthValue() + "/" + cal.getYear() + "  "
                + d.getDayOfWeek().name();
        String time = " ";
        if (cal.getHour() > 12)
            time += Integer.toString(cal.getHour() - 12) + ":" + cal.getMinute() + ":" + cal.getSecond() + " PM";
        else
            time += cal.getHour() + ":" + cal.getMinute() + ":" + cal.getSecond() + " AM";

        l1 = new JLabel("Date: " + date + "  " + time);
        l1.setFont(new Font("Tahoma", Font.BOLD, 13));
        l1.setForeground(Color.MAGENTA);
        l1.setBounds(10, 120, ATS.WIDTH / 6 - 20, 20);

        StudentHandler c = new StudentHandler();
        c.start();

        String[] str = ATS.path.get(ATS.path.size() - 1).split("/");
        int len = str.length;
        String temp = str[len - 4] + " " + str[len - 3] + " " + str[len - 2] + " " + str[len - 1];

        JLabel l2 = new JLabel(temp);
        l2.setForeground(Color.GREEN);
        l2.setFont(new Font("Tahoma", Font.BOLD, 18));
        l2.setBounds(10, 150, ATS.WIDTH / 6 - 20, 20);

        JButton b = new JButton("Register a Student");
        b.setBounds(10, 200, ATS.WIDTH / 6 - 20, 50);
        b.addActionListener(new StudentHandler());

        JButton b0 = new JButton("Register from file");
        b0.setBounds(10, 300, ATS.WIDTH / 6 - 20, 50);
        b0.addActionListener(new StudentHandler());

        JButton b1 = new JButton("Take Attendance");
        b1.setBounds(10, 400, ATS.WIDTH / 6 - 20, 50);
        b1.addActionListener(new StudentHandler());

        JButton out = new JButton("Save and Back");
        out.setBounds(10, 500, ATS.WIDTH / 6 - 20, 50);
        out.addActionListener(new StudentHandler());

        JLabel l3 = new JLabel("Enter Attendance Below in(%)");
        l3.setFont(new Font("Tahoma", Font.BOLD, 18));
        l3.setForeground(Color.BLUE);
        l3.setBounds(10, 700, ATS.WIDTH / 6 - 20, 30);

        tf1 = new JTextField("");
        tf1.setBounds(10, 750, ATS.WIDTH / 6 - 20, 20);

        JButton b2 = new JButton("Submit");
        b2.setBounds(10, 800, ATS.WIDTH / 6 - 20, 50);
        b2.addActionListener(new StudentHandler());

        tool.add(l1);
        tool.add(l2);
        tool.add(b);
        tool.add(b0);
        tool.add(b1);
        tool.add(out);
        tool.add(l3);
        tool.add(tf1);
        tool.add(b2);
        HomePage.info(tool);

        return tool;
    }

    static void updateCSVData() {
        try {
            String rel = ATS.path.get(ATS.path.size() - 1) + "/" + cal.getMonth() + cal.getYear() + ".csv";
            String path = ATS.path.get(ATS.path.size() - 1) + "/student.data";
            BufferedReader br = new BufferedReader(new FileReader(new File(rel)));
            ArrayList<Student> sarr = new StudentData(path).loadData();
            ArrayList<String> data = new ArrayList<String>();
            String line = "";
            while ((line = br.readLine()) != null)
                data.add(line.replaceAll("\n", ""));
            int count = 1;
            BufferedWriter bw = new BufferedWriter(new FileWriter(rel));
            bw.write(data.get(0) + "\n");
            int i = 0, j = 1;
            while (i < sarr.size() && j < data.size()) {
                String[] temp = data.get(j).replaceAll("\n", "").split(", ");
                if (sarr.get(i).roll.compareTo(temp[1]) < 0) {
                    String res = Integer.toString(count) + ", " + sarr.get(i).roll + ", " + sarr.get(i).name;
                    for (int k = 0; k < temp.length - 3; k++)
                        res += ", -";
                    bw.write(res + "\n");
                    i++;
                } else if (sarr.get(i).roll.compareTo(temp[1]) > 0) {
                    String res = Integer.toString(count);
                    for (int k = 1; k < temp.length; k++)
                        res += ", " + temp[k];
                    bw.write(res + "\n");
                    j++;
                } else {
                    String res = Integer.toString(count);
                    for (int k = 1; k < temp.length; k++)
                        res += ", " + temp[k];
                    bw.write(res + "\n");
                    j++;
                    i++;
                }
                count++;
            }
            while (i < sarr.size()) {
                String res = Integer.toString(count) + ", " + sarr.get(i).roll + ", " + sarr.get(i).name;
                for (int k = 0; k < data.get(0).split(", ").length - 3; k++)
                    res += ", -";
                bw.write(res + "\n");
                i++;
                count++;
            }
            while (j < data.size()) {
                String[] temp = data.get(j).replaceAll("\n", "").split(", ");
                String res = Integer.toString(count);
                for (int k = 1; k < temp.length; k++)
                    res += ", " + temp[k];
                bw.write(res + "\n");
                j++;
                count++;
            }
            bw.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static JTable getCSVData() {
        String rel = ATS.path.get(ATS.path.size() - 1) + "/" + cal.getMonth() + cal.getYear() + ".csv";
        File f = new File(rel);
        if (!f.exists()) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(rel));
                String header = "S.No., Roll No., Name" + ", " + cal.getDayOfMonth() + "-" + cal.getMonth();
                bw.write(header + "\n");
                String path = ATS.path.get(ATS.path.size() - 1) + "/" + "student.data";
                StudentData stData = new StudentData(path);
                stData.setAttend();
                ArrayList<Student> sarr = stData.loadData();
                total = sarr.size();
                for (int j = 0; j < total; j++) {
                    Student temp = sarr.get(j);
                    String line = Integer.toString(j + 1) + ", " + temp.roll + ", " + temp.name + ", -";
                    bw.write(line + "\n");
                }
                bw.close();

                String crel = ATS.path.get(ATS.path.size() - 1) + "/" + "config.txt";
                f = new File(crel);
                if (!f.exists()) {
                    bw = new BufferedWriter(new FileWriter(crel));
                    bw.write("0");
                    bw.newLine();
                    bw.close();
                }
                BufferedReader br = new BufferedReader(new FileReader(crel));
                totalWork = Integer.parseInt(br.readLine());
                totalWork++;
                bw = new BufferedWriter(new FileWriter(crel));
                bw.write(Integer.toString(totalWork));
                bw.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(rel));
                String line = br.readLine();
                String[] header = line.replaceAll("\n", "").split(", ");
                if (!(header[header.length - 1]
                        .equals(cal.getDayOfMonth() + "-" + cal.getMonth()))) {
                    String path = ATS.path.get(ATS.path.size() - 1) + "/" + "student.data";
                    StudentData stData = new StudentData(path);
                    stData.setAttend();
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(line.replaceAll("\n", ""));
                    while ((line = br.readLine()) != null) {
                        data.add(line.replaceAll("\n", ""));
                    }
                    ArrayList<String> upData = new ArrayList<String>();
                    String temp = data.get(0) + ", " + cal.getDayOfMonth() + "-" + cal.getMonth();
                    upData.add(temp);
                    for (int i = 1; i < data.size(); i++) {
                        temp = data.get(i) + ", -";
                        upData.add(temp);
                    }
                    BufferedWriter bw = new BufferedWriter(new FileWriter(rel));
                    for (int i = 0; i < data.size(); i++)
                        bw.write(upData.get(i) + "\n");
                    bw.close();
                    br.close();

                    String crel = ATS.path.get(ATS.path.size() - 1) + "/" + "config.txt";
                    f = new File(crel);
                    if (!f.exists()) {
                        bw = new BufferedWriter(new FileWriter(rel));
                        bw.write("0");
                        bw.close();
                    }
                    br = new BufferedReader(new FileReader(crel));
                    totalWork = Integer.parseInt(br.readLine());
                    totalWork++;
                    bw.write(Integer.toString(totalWork));
                    bw.close();
                    br.close();
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<String[]> data = new ArrayList<>();
        try {
            String crel = ATS.path.get(ATS.path.size() - 1) + "/" + "config.txt";
            BufferedReader br = new BufferedReader(new FileReader(crel));
            totalWork = Integer.parseInt(br.readLine());
            br.close();

            br = new BufferedReader(new FileReader(rel));
            String line = "";
            while ((line = br.readLine()) != null) {
                line.replaceAll("\n", "");
                String[] row = line.split(", ");
                data.add(row);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] header = data.get(0);
        String[][] rows = new String[data.size() - 1][header.length - 1];
        for (int i = 1; i < data.size(); i++) {
            rows[i - 1] = data.get(i);
        }
        total = rows.length;

        DefaultTableModel model = new DefaultTableModel(rows, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        return table;
    }

    public static JPanel loadAttendanceTable() {
        JPanel content = new JPanel();
        content.setBounds(ATS.WIDTH / 6, 0, 5 * ATS.WIDTH / 6, ATS.HEIGHT);
        content.setBackground(Color.BLACK);
        content.setLayout(null);

        JScrollPane sp1 = new JScrollPane(getCSVData());
        sp1.setBounds(ATS.WIDTH / 6 + 20, 100, 5 * ATS.WIDTH / 6 - 100, ATS.HEIGHT - 200);
        sp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JLabel l1 = new JLabel("Total Students: " + total);
        l1.setForeground(Color.BLUE);
        JLabel l2 = new JLabel("Total Working Days: " + totalWork);
        l2.setForeground(Color.BLUE);

        l1.setBounds(ATS.WIDTH / 6 + 20, 10, 5 * ATS.WIDTH / 6 - 10, 20);
        l2.setBounds(2 * ATS.WIDTH / 3 - 10, 10, 5 * ATS.WIDTH / 12 - 5, 20);

        content.add(l1);
        content.add(l2);
        content.add(sp1);

        return content;
    }

    public static void fillTodayEntry() {
        String rel = ATS.path.get(ATS.path.size() - 1) + "/" + cal.getMonth() + cal.getYear() + ".csv";
        String path = ATS.path.get(ATS.path.size() - 1) + "/student.data";
        StudentData sData = new StudentData(path);
        ArrayList<Student> sarr = sData.loadData();
        try {
            BufferedReader br = new BufferedReader(new FileReader(rel));
            String line = "";
            ArrayList<String> data = new ArrayList<String>();
            while ((line = br.readLine()) != null)
                data.add(line.replaceAll("\n", ""));
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(rel));
            bw.write(data.get(0) + "\n");
            for (int i = 1; i < data.size(); i++) {
                String[] temp = data.get(i).replaceAll("\n", "").split(", ");
                if (sarr.get(i - 1).roll.equals(temp[1]) && sarr.get(i - 1).attend == 1) {
                    String dwr = temp[0];
                    if (temp[temp.length - 1].equals("-"))
                        sarr.get(i - 1).total++;
                    else if (temp[temp.length - 1].equals("ABSENT"))
                        sarr.get(i - 1).total++;
                    temp[temp.length - 1] = "PRESENT";
                    for (int j = 1; j < temp.length; j++)
                        dwr += ", " + temp[j];
                    bw.write(dwr + "\n");
                } else if (sarr.get(i - 1).roll.equals(temp[1]) && sarr.get(i - 1).attend == 2) {
                    String dwr = temp[0];
                    if (temp[temp.length - 1].equals("PRESENT"))
                        sarr.get(i - 1).total--;
                    temp[temp.length - 1] = "ABSENT";
                    for (int j = 1; j < temp.length; j++)
                        dwr += ", " + temp[j];
                    bw.write(dwr + "\n");
                } else if (sarr.get(i - 1).roll.equals(temp[1]) && sarr.get(i - 1).attend == 0) {
                    String dwr = temp[0];
                    if (temp[temp.length - 1].equals("PRESENT"))
                        sarr.get(i - 1).total--;
                    temp[temp.length - 1] = " -";
                    for (int j = 1; j < temp.length; j++)
                        dwr += ", " + temp[j];
                    bw.write(dwr + "\n");
                } else {
                    bw.write(data.get(i) + "\n");
                }
            }
            sData.saveStudent(sarr);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel belowAttend(int x) {
        JPanel p = new JPanel(null);
        p.setBackground(Color.BLACK);
        ArrayList<Student> sarr = new StudentData(ATS.path.get(ATS.path.size() - 1) + "/student.data").loadData();
        JPanel child = new JPanel(new GridLayout(0, 2));
        child.setBackground(Color.CYAN);
        JLabel h1 = new JLabel("Roll No.");
        JLabel h2 = new JLabel("Name  -------> Total Attendance");

        h1.setFont(new Font("Tahoma", Font.BOLD, 18));
        h1.setForeground(Color.BLUE);
        h2.setFont(new Font("Tahoma", Font.BOLD, 18));
        h2.setForeground(Color.BLUE);

        child.add(h1);
        child.add(h2);

        for (int i = 0; i < sarr.size(); i++) {
            if (sarr.get(i).total <= x * totalWork / 100) {
                JLabel l1 = new JLabel(sarr.get(i).roll);
                l1.setFont(new Font("Tahoma", Font.BOLD, 18));
                l1.setForeground(Color.BLACK);

                JLabel l2 = new JLabel(sarr.get(i).name + "  -------> " + sarr.get(i).total);
                l2.setFont(new Font("Tahoma", Font.BOLD, 18));
                l2.setForeground(Color.BLACK);

                child.add(l1);
                child.add(l2);
            }
        }

        JScrollPane sp = new JScrollPane(child);
        sp.setBounds(10, 50, ATS.WIDTH - 20, ATS.HEIGHT - 50);
        JButton b = new JButton("EXIT");
        b.addActionListener(new StudentHandler());
        b.setBounds(ATS.WIDTH / 2 - 100, 0, 200, 50);
        b.setBackground(Color.RED);

        p.add(sp);
        p.add(b);
        return p;
    }

    public void run() {
        while (true) {
            cal = LocalDateTime.now();
            LocalDate d = LocalDate.of(cal.getYear(), cal.getMonth(), cal.getDayOfMonth());
            String date = cal.getDayOfMonth() + "/" + cal.getMonthValue() + "/" + cal.getYear() + "  "
                    + d.getDayOfWeek().name();
            String time = " ";
            if (cal.getHour() > 12)
                time += Integer.toString(cal.getHour() - 12) + ":" + cal.getMinute() + ":" + cal.getSecond() + " PM";
            else
                time += cal.getHour() + ":" + cal.getMinute() + ":" + cal.getSecond() + " AM";

            l1.setText("");
            l1.setText("Date: " + date + "  " + time);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save and Back")) {
            ATS.path.remove(ATS.path.size() - 1);
            HomePage.coursePane();
        } else if (e.getActionCommand() == "Register a Student") {
            String name = null, roll = null;
            name = JOptionPane.showInputDialog(null, "Enter Student's Name:", "Student Registration",
                    JOptionPane.PLAIN_MESSAGE);
            if (name != null)
                roll = JOptionPane.showInputDialog(null, "Enter Student Roll No.:", "Student Registration",
                        JOptionPane.PLAIN_MESSAGE);
            if (name != null && roll != null) {
                StudentData sData = new StudentData(ATS.path.get(ATS.path.size() - 1) + "/student.data");
                ArrayList<Student> sarr = sData.loadData();
                for (Student std : sarr) {
                    if (std.roll.equals(roll)) {
                        JOptionPane.showMessageDialog(null, "Error: Roll No. Already Exists!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                }
                sarr.add(new Student(roll, name, 0));
                sData.saveStudent(sarr);
                updateCSVData();
                HomePage.studentPane();
            }
        } else if (e.getActionCommand() == "Register from file") {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(ATS.f);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                ArrayList<Student> sarr = new ArrayList<Student>();
                StudentData sData = new StudentData(ATS.path.get(ATS.path.size() - 1) + "/student.data");
                sarr = sData.loadFromFile(selectedFile.getAbsolutePath());
                sData.saveStudent(sarr);
                updateCSVData();
                HomePage.studentPane();
            }
        } else if (e.getActionCommand() == "Take Attendance") {
            HomePage.attendancePane();
        } else if (e.getActionCommand() == "Submit") {
            ATS.clearState();
            String res = tf1.getText();
            int x = 0;
            if (res != null && res.length() != 0)
                x = Integer.parseInt(res);
            ATS.f.add(belowAttend(x));
            ATS.f.setVisible(true);
        } else if (e.getActionCommand() == "EXIT") {
            HomePage.studentPane();
        }
    }
}
