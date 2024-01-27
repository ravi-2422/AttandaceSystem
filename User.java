package Java.ATS;

import java.io.*;

class User implements Serializable {
    String sal;
    String fname;
    String lname;
    String uname;
    String pass;
    
    void addUser(String s, String f, String l, String u, String p) {
        sal = s;
        fname = f;
        lname = l;
        uname = u;
        pass = p;

        String path = ATS.path.get(ATS.path.size()-1);
        File dir = new File(path);
        dir.mkdir();
        dir = new File(path + "/" + u);
        dir.mkdir();
        dir = new File(path + "/" + u + "/meta");
        dir.mkdir();
        String rel = path + "/" + u + "/meta/Config.admin";
        try {
            FileOutputStream fout = new FileOutputStream(rel);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            dir = new File(rel);
            dir.setWritable(true);
            oos.close();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean validate(String u, String p) {
        String rel =  ATS.path.get(ATS.path.size()-1) + "/" + u + "/meta/config.admin";
        File dir = new File(rel);
        if(dir.exists()) {
            loadUserState(rel);
            if(u.equals(ATS.user.uname) && p.equals(ATS.user.pass)) return true;
            return false;
        }
        return false;
    }

    static void loadUserState(String rel) {
        try {
            FileInputStream fin = new FileInputStream(rel);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Object obj = ois.readObject();
            ATS.user =  (User) obj;
            ois.close();
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getName() {
        return sal + " " + fname + " " + lname;
    }
}
