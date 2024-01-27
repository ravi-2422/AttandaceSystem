package Java.ATS;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class HomePage {
    public static JTextField tf = null;

    public static void info(JPanel tool) {
        JLabel l1 = new JLabel("WELCOME:   " + ATS.user.getName());
        l1.setFont(new Font("Tahoma", Font.BOLD, 18));
        l1.setForeground(Color.cyan);
        l1.setBounds(5, 5, ATS.WIDTH / 6 - 5, 20);
        tool.add(l1, BorderLayout.NORTH);
    }

    public static void programmePane() {
        ATS.clearState();
        ATS.f.add(ProgrammeHandler.createDegree());
        ATS.f.add(ProgrammeHandler.loadDegree());
        ATS.f.setVisible(true);
    }

    public static void batchPane() {
        ATS.clearState();
        ATS.f.add(BatchHandler.createBatch());
        ATS.f.add(BatchHandler.loadBatch());
        ATS.f.setVisible(true);
    }

    public static void departmentPane() {
        ATS.clearState();
        ATS.f.add(DepartmentHandler.createDepartment());
        ATS.f.add(DepartmentHandler.loadDepartment());
        ATS.f.setVisible(true);
    }

    public static void coursePane() {
        ATS.clearState();
        ATS.f.add(CourseHandler.createSubject());
        ATS.f.add(CourseHandler.loadSubject());
        ATS.f.setVisible(true);
    }

    public static void studentPane() {
        ATS.clearState();
        ATS.f.add(StudentHandler.createStudent());
        ATS.f.add(StudentHandler.loadAttendanceTable());
        ATS.f.setVisible(true);
    }

    public static void attendancePane() {
        ATS.clearState();
        ATS.f.add(AttendanceHandler.createAttendance());
        ATS.f.add(AttendanceHandler.loadAttedance());
        ATS.f.setVisible(true);
    }

    public static void changeUI(ArrayList<Student> sarr) {
        ATS.clearState();
        ATS.f.add(UIChanger.changeUI(sarr));
        ATS.f.setVisible(true);
    }
}
