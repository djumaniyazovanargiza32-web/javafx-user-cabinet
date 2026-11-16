package org.itproger.usercabinet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class UserCabinetController {

    @FXML
    private TextField login_field;

    @FXML
    private TextField email_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    private Button update_btn;

    @FXML
    private Label info_label;

    private final DB db = new DB();
    private int userId = 1;

    @FXML
    void initialize() {
        try {
            String[] userData = db.getUser("Admin");
            if (userData != null) {
                userId = Integer.parseInt(userData[0]);
                login_field.setText(userData[1]);
                email_field.setText(userData[2]);
            } else {
                info_label.setText("User Admin not found in DB");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            info_label.setText("Database connection error");
        }

        update_btn.setOnAction(event -> {
            String newLogin = login_field.getText().trim();
            String newEmail = email_field.getText().trim();
            String newPass = pass_field.getText().trim();

            if (newLogin.isEmpty() || newEmail.isEmpty() || newPass.isEmpty()) {
                info_label.setStyle("-fx-text-fill: #e86c25;");
                info_label.setText("Please fill in all fields!");
                return;
            }

            try {
                if (db.isLoginExists(newLogin, userId)) {
                    info_label.setStyle("-fx-text-fill: #e86c25;");
                    info_label.setText("Error: this login already exists!");
                    return;
                }

                db.updateUser(userId, newLogin, newEmail, newPass);

                login_field.clear();
                email_field.clear();
                pass_field.clear();

                info_label.setStyle("-fx-text-fill: #4caf50;");
                info_label.setText("Data successfully updated!");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                info_label.setStyle("-fx-text-fill: #e86c25;");
                info_label.setText("Error updating data!");
            }
        });
    }
}