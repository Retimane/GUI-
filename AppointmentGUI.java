package com.gui;

import com.dentist.Appointment;
import com.dentist.AppointmentManager;
import com.dentist.AppointmentScheduler;
import com.users.Dentist;
import com.users.Patient;
import com.users.Staff;
import com.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AppointmentGUI {
    private static AppointmentManager appointmentManager;
    private static AppointmentScheduler appointmentScheduler;
    private static User currentUser;

    public static void main(String[] args) {
        appointmentManager = new AppointmentManager();
        appointmentScheduler = new AppointmentScheduler(appointmentManager);

        // Sample Users
        Dentist dentist = new Dentist("dr_smith", "password", "Dr. Smith");
        Staff staff = new Staff("admin", "admin_pass", "Admin");
        Patient patient = new Patient("john_doe", "user_pass", "John Doe", "987-654-3210", "No significant history");

        appointmentManager.addUser(dentist);
        appointmentManager.addUser(staff);
        appointmentManager.addUser(patient);

        // GUI
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Dentist Appointment System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");

        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(exitButton);

        frame.getContentPane().add(BorderLayout.CENTER, panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginDialog();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegistrationDialog();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            // Authenticate user
            currentUser = authenticateUser(username, new String(password));

            if (currentUser != null) {
                showMainOptions();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static User authenticateUser(String username, String password) {
        for (User user : appointmentManager.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static void showRegistrationDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField nameField = new JTextField();
        JTextField contactDetailsField = new JTextField();
        JTextField medicalHistoryField = new JTextField();

        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField,
                "Name:", nameField,
                "Contact Details:", contactDetailsField,
                "Medical History:", medicalHistoryField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            String name = nameField.getText();
            String contactDetails = contactDetailsField.getText();
            String medicalHistory = medicalHistoryField.getText();

            // Register new patient
            Patient newPatient = new Patient(username, new String(password), name, contactDetails, medicalHistory);
            appointmentManager.addUser(newPatient);
            JOptionPane.showMessageDialog(null, "Registration successful. You can now log in.", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void showMainOptions() {
        JFrame mainFrame = new JFrame("Dentist Appointment System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1));

        JButton viewAppointmentsButton = new JButton("View Appointments");
        JButton scheduleAppointmentButton = new JButton("Schedule Appointment");
        JButton manageAppointmentsButton = new JButton("Manage Appointments");
        JButton viewUsersButton = new JButton("View Users");

        mainPanel.add(viewAppointmentsButton);
        mainPanel.add(scheduleAppointmentButton);
        mainPanel.add(manageAppointmentsButton);
        mainPanel.add(viewUsersButton);

        mainFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointments();
            }
        });

        scheduleAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointmentScheduler();
            }
        });

        manageAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointmentManager();
            }
        });

        viewUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUsers();
            }
        });

        mainFrame.setSize(400, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static void showAppointments() {
        List<Appointment> appointments = appointmentManager.getAppointments();
        StringBuilder appointmentInfo = new StringBuilder("Appointments:\n");

        for (Appointment appointment : appointments) {
            appointmentInfo.append("ID: ").append(appointment.getAppointmentId())
                    .append(", Date: ").append(appointment.getDate())
                    .append(", Time: ").append(appointment.getTime())
                    .append(", Patient: ").append(appointment.getPatient().getName())
                    .append(", Dentist: ").append(appointment.getDentist().getName())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, appointmentInfo.toString(), "Appointments", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showAppointmentScheduler() {
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();

        Object[] message = {
                "Date (YYYY-MM-DD):", dateField,
                "Time:", timeField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Schedule Appointment", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String date = dateField.getText();
            String time = timeField.getText();

            if (appointmentScheduler.scheduleAppointment(date, time, (Patient) currentUser, (Dentist) appointmentManager.getUsers().get(0))) {
                JOptionPane.showMessageDialog(null, "Appointment scheduled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to schedule appointment. Dentist might not be available or slot is already booked.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void showAppointmentManager() {
        appointmentManager.manageAppointments(currentUser);
    }

    private static void showUsers() {
        List<User> users = appointmentManager.getUsers();
        StringBuilder userInfo = new StringBuilder("Users:\n");

        for (User user : users) {
            userInfo.append("Username: ").append(user.getUsername())
                    .append(", Type: ").append(user instanceof Dentist ? "Dentist" : (user instanceof Staff ? "Staff" : "Patient"))
                    .append(", Name: ").append(user instanceof Patient ? ((Patient) user).getName() : "")
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, userInfo.toString(), "Users", JOptionPane.INFORMATION_MESSAGE);
    }
}
