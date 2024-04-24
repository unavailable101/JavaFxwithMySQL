package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    public ToggleButton tbNight;
    @FXML
    public Button logout;
    @FXML
    public Button changePassword;
    @FXML
    public Button tbChangeUsername;
    @FXML
    public AnchorPane home;
    @FXML
    public Label txtTitle; //di sha mu ila nimo huehue
    @FXML
    public TextField tfNoteTitle;
    @FXML
    public Button btnCreateNote;
    @FXML
    public TextArea taNoteContents;
    @FXML
    public Button deleteAccnt;
    @FXML
    public AnchorPane apYourNotes;
    @FXML
    public Label lblUsername;
    @FXML
    public Button btnDeleteAllNotes;
    @FXML
    public VBox vbOutput;
    @FXML
    public AnchorPane apViewNotes;
    @FXML
    public TextField tfNoteTitle_view;
    @FXML
    public TextArea taNoteContents_view;
    @FXML
    public Button btnSaveChanges;
    @FXML
    public Button btnClose_view;
    @FXML
    public Button btnDelete_view;
    @FXML
    public Label lblName;
    @FXML
    public Label lblEmail;
    @FXML
    public Label lblNotesCount;
    protected URL loc;
    protected  ResourceBundle rsbundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loc = location;
        rsbundle = resources;
        lblUsername.setText(HelloApplication.current_username);
        vbOutput.getChildren().clear();
        String name;
        String email;

        try (ResultSet currUser = ReadData.userProfile();){
            if (currUser.next()){
                name = currUser.getString("firstName") + " " + currUser.getString("lastName");
                email = currUser.getString("email");
                lblName.setText(name);
                lblEmail.setText(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lblNotesCount.setText(String.valueOf(ReadData.noteCount()));

        if (CreateTable.notesTable()) {
            try (
                    ResultSet yourNotes = ReadData.all_notes();
            ) {
                while (yourNotes.next()) {
                    int note_id = yourNotes.getInt("id");
                    String title = yourNotes.getString("title");
                    String contents = yourNotes.getString("contents");

                    Label note_title = new Label(title);
                    note_title.setPrefWidth(500);
                    Button view = new Button("View");
                    Button delete_note = new Button("Delete");
                    HBox hbox = new HBox(note_title, view, delete_note);
                    hbox.setSpacing(10);
                    vbOutput.getChildren().add(hbox);
                    vbOutput.setSpacing(5);

                    view.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            System.out.println("view btn pressed! note_id: " + note_id);
                            apViewNotes.setVisible(true);
                            vbOutput.setVisible(false);
                            tfNoteTitle_view.setText(title);
                            taNoteContents_view.setText(contents);
                            btnDeleteAllNotes.setVisible(false);
                            btnSaveChanges.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    UpdateData.updateNote(note_id, tfNoteTitle_view.getText(), taNoteContents_view.getText());
                                    initialize(loc, rsbundle);
                                    onCloseViewNote();
                                }
                            });

                            btnDelete_view.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    onDeleteNote(note_id);
                                    initialize(loc, rsbundle);
                                    onCloseViewNote();
                                }
                            });
                        }
                    });

                    delete_note.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            System.out.println("delete btn pressed! note_id: " + note_id);
                            onDeleteNote(note_id);
                            initialize(loc, rsbundle);
                        }
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onNightModeClick() {
        if (tbNight.isSelected()) {
            tbNight.getParent().setStyle("-fx-background-color: WHITE");
            lblUsername.setTextFill(Color.BLACK);
            txtTitle.setTextFill(Color.BLACK);
            tbNight.setText("NIGHT");
        } else {
            tbNight.getParent().setStyle("-fx-background-color: BLACK");
            lblUsername.setTextFill(Color.WHITE);
            txtTitle.setTextFill(Color.WHITE);
            tbNight.setText("DAY");
        }
    }

    public void onLogout(){
        System.out.println("Current user to logout: " + HelloApplication.current_uid);
        HelloApplication.current_uid = -1;
        HelloApplication.primaryStage.setScene(HelloApplication.primaryScene);
    }

    public void onChangePassword(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Changing Password");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        VBox whole = new VBox();
        PasswordField old_pwd = new PasswordField();
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("Enter your old password to confirm:"), old_pwd);

        PasswordField new_pwd = new PasswordField();
        HBox content2 = new HBox();
        content2.setAlignment(Pos.CENTER_LEFT);
        content2.setSpacing(10);
        content2.getChildren().addAll(new Label("Enter your password:"), new_pwd);

        whole.getChildren().addAll(content, content2);

        dialog.getDialogPane().setContent(whole);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                if (ReadData.getPassword(old_pwd.getText().toString())) {
                    return new_pwd.getText();
                } else {
                    System.out.println("Hacker ka noh?");
                }
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals(null)){
                UpdateData.updatePassword(HelloApplication.current_uid, result.get());
            }
        }
    }

    public void onChangeUsername(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Changing Username");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField newUsername = new TextField();
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("Enter your new username:"), newUsername);

        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return newUsername.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals(null)){
                UpdateData.updateUsername(HelloApplication.current_uid, result.get());
                HelloApplication.current_username = result.get();
                initialize(loc, rsbundle);
            }
        }
    }
    public void onDeleteAccount(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delete Account");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("Are you sure you want to delete your account? There is no turning back"));

        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return String.valueOf(HelloApplication.current_uid);
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals(null)){
                DeleteData.deleteAccount(HelloApplication.current_uid);
                HelloApplication.primaryStage.setScene(HelloApplication.primaryScene);
            }
        }
    }

    public void onCreateNote(ActionEvent actionEvent) {
        if ( !CreateTable.notesTable() ) CreateTable.createNote();
        InsertData.addNote(tfNoteTitle.getText(), taNoteContents.getText());
        tfNoteTitle.clear();
        taNoteContents.clear();
        initialize(loc, rsbundle);
    }

    public void onDeleteAllNotes(ActionEvent actionEvent) {
        DeleteData.deleteAllNotes(HelloApplication.current_uid);
        initialize(loc, rsbundle);
    }
    public void onDeleteNote(int note_id){
        DeleteData.deleteNote(note_id);
        initialize(loc, rsbundle);
    }

    public void onCloseViewNote() {
        apViewNotes.setVisible(false);
        btnDeleteAllNotes.setVisible(true);
        vbOutput.setVisible(true);
    }
}