package Java.ATS;

class Auth {
    public static void loginPage() {
        ATS.clearState();
        ATS.f.add(LoginPage.loginUI());
        ATS.f.setVisible(true);
    }

    public static void regPage() {
        ATS.clearState();
        ATS.f.add(RegPage.registerUI());
        ATS.f.setVisible(true);
    }

    public static void homePage() {
        ATS.clearState();
        HomePage.programmePane();
    }
}

