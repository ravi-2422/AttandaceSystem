package Java.ATS;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Student implements Serializable {
    public String roll;
    public String name;
    public int total;
    public int attend;

    Student() {
        roll = null;
        name = null;
        total = 0;
        attend = 0;
    }

    Student(String roll, String name, int total) {
        this.roll = roll;
        this.name = name;
        this.total = total;
    }
}

class StudentData {
    String rel = null;

    StudentData(String path) {
        rel = path;
    }

    public ArrayList<Student> loadData() {
        ArrayList<Student> arr = new ArrayList<Student>();
        try {
            File f = new File(rel);
            if (f.exists()) {
                FileInputStream fin = new FileInputStream(rel);
                ObjectInputStream ois = new ObjectInputStream(fin);
                while (fin.available() > 0) {
                    Student obj = (Student) ois.readObject();
                    arr.add(obj);
                }
                ois.close();
                fin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public ArrayList<Student> loadFromFile(String path) {
        ArrayList<Student> arr = new ArrayList<Student>();
        try {
            Scanner sc = new Scanner(new File(path));
            sc.useDelimiter(",");
            String headerLine = sc.nextLine();
            while (!(headerLine.contains("Name") || headerLine.contains("Roll") || headerLine.contains("name"))
                    && sc.hasNext()) {
                headerLine = sc.nextLine();
            }
            String[] columnNames = headerLine.split(",");

            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] row = data.split(",");
                String name = null, roll = null, total = "0";
                for (int i = 0; i < row.length; i++) {
                    if (columnNames[i].contains("Name") || columnNames[i].contains("name")) {
                        name = row[i];
                    } else if (columnNames[i].contains("Roll") || columnNames[i].contains("roll")) {
                        roll = row[i];
                    } else if (columnNames[i].contains("Total") || columnNames[i].contains("total")) {
                        total = row[i];
                    }
                    if (name != null && roll != null && total != null)
                        break;
                }
                arr.add(new Student(roll, name, Integer.parseInt(total)));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public void saveStudent(ArrayList<Student> sarr) {
        try {
            FileOutputStream fout = new FileOutputStream(rel);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            Collections.sort(sarr, new Comparator<Student>() {
                @Override
                public int compare(Student s1, Student s2) {
                    return s1.roll.compareTo(s2.roll);
                }
            });

            File dir = new File(rel);
            dir.setWritable(true);
            
            for (Student obj : sarr) {
                oos.writeObject(obj);
            }

            oos.close();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAttend() {
        ArrayList<Student> sarr = loadData();
        for(Student std : sarr) {
            std.attend = 0;
        }
        saveStudent(sarr);
    }
}