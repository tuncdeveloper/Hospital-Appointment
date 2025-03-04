package test;

import com.formdev.flatlaf.FlatLightLaf;
import gui.MenuGui;
import service.DoctorService;
import service.PatientService;

import javax.swing.*;

public class Run {

    public static void main(String[] args) {

         DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
           //doctorService.generateRandomDoctors();
           //patientService.generateRandomPatients();
            MenuGui menuGui = new MenuGui();
            menuGui.initWindow();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
