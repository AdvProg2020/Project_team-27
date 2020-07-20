package model.fileTransfer;
//package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;


import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.accounts.Seller;


import java.io.IOException;
import java.util.List;

public class SelectFileChooserExample
        extends Application {

    private Text actionStatus;
    private Stage savedStage;
    private static final String titleTxt = "JavaFX File Chooser Example 1";
    public static Seller seller;
    public static String [] a ;


    public static void main(String [] args) {

        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle(titleTxt);

        // Window label
        Label label = new Label("Select File Choosers");
        label.setTextFill(Color.DARKBLUE);
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
        HBox labelHb = new HBox();
        labelHb.setAlignment(Pos.CENTER);
        labelHb.getChildren().add(label);

        // Buttons
        Button btn1 = new Button("Choose a file...");
        btn1.setOnAction(new SingleFcButtonListener());
        HBox buttonHb1 = new HBox(10);
        buttonHb1.setAlignment(Pos.CENTER);
        buttonHb1.getChildren().addAll(btn1);


        // Status message text
        actionStatus = new Text();
        actionStatus.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
        actionStatus.setFill(Color.FIREBRICK);

        // Vbox
        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(25, 25, 25, 25));;
        vbox.getChildren().addAll(labelHb, buttonHb1, actionStatus);

        // Scene
        Scene scene = new Scene(vbox, 500, 300); // w x h
        primaryStage.setScene(scene);
        primaryStage.show();

        savedStage = primaryStage;
    }

    private class SingleFcButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            showSingleFileChooser();
        }
    }

    public static void setSeller(Seller seller) {
        SelectFileChooserExample.seller = seller;
    }

    private void showSingleFileChooser() {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        seller.getFiles().add(selectedFile);

        if (selectedFile != null) {

            actionStatus.setText("File selected: " + selectedFile.getName());
        }
        else {
            actionStatus.setText("File selection cancelled.");
        }
    }

 /*   private class MultipleFcButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            showMultipleFileChooser();
        }
    }

    private void showMultipleFileChooser() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PDF files");
        fileChooser.setInitialDirectory(new File("X:\\testdir\\two"));
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("PDF Files", "*.pdf"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(savedStage);

        if (selectedFiles != null) {

            actionStatus.setText("PDF Files selected [" + selectedFiles.size() + "]: " +
                    selectedFiles.get(0).getName() + "..");
        }
        else {
            actionStatus.setText("PDF file selection cancelled.");
        }
    }

  */
}
