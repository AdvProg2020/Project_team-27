package client.view.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.accounts.Seller;
import model.fileTransfer.FileToShow;
import model.fileTransfer.SelectFileChooserExample;
import server.Server;

import java.io.File;
import java.io.IOException;

public class SellFileFx {
    @FXML
    TableView<FileToShow> fileList = new TableView<>();
    TableColumn<FileToShow, String> sellerName = new TableColumn<>("seller name");
    TableColumn<FileToShow, File> fileName = new TableColumn<>("file name");
    ObservableList<FileToShow> data = FXCollections.observableArrayList();

    public void ini() {
        for (Seller seller : Seller.getAllSellers()) {
            for (File file : seller.files) {
                FileToShow fileToShow = new FileToShow();
                fileToShow.file = file;
                fileToShow.setSeller(seller.getName());
            }
        }
        data.addAll(FileToShow.getFileToShows());
    }

    @FXML
    public void initialize() {
        ini();
        sellerName.setCellValueFactory(new PropertyValueFactory<FileToShow, String>("seller"));
        fileName.setCellValueFactory(new PropertyValueFactory<FileToShow, File>("file"));
        fileList.getColumns().addAll(fileName, sellerName);
        fileList.setItems(data);
    }

    public void clickedColumn(ActionEvent mouseEvent) {

        FileToShow selectedFile = fileList.getSelectionModel().getSelectedItem();

        SelectFileChooserExample.seller.getFileWithName(selectedFile.file.getName());
        SelectFileChooserExample.seller.files.add(selectedFile.file);
        Server.file = selectedFile.file;
        Server.main(SelectFileChooserExample.a);
    }
}