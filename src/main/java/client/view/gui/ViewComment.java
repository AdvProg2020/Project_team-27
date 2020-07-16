package client.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import model.request.CommentRequest;

public class ViewComment {
    @FXML
    private Label commentProduct;
    @FXML
    private Label commentTitle;
    @FXML
    private Label nullAddCommentError;
    @FXML
    private Label commentError;
    @FXML
    private Label comment;
    private static CommentRequest commentRequest;
    private static Parent priRoot;

    public static void setPriRoot(Parent priRoot) {
        ViewComment.priRoot = priRoot;
    }

    public static void setCommentRequest(CommentRequest commentRequest) {
        ViewComment.commentRequest = commentRequest;
    }

    @FXML
    public void initialize()  {
        commentTitle.setText(commentRequest.getTitle());
        comment.setText(commentRequest.getContent());
        commentProduct.setText(commentRequest.getProduct());
    }

    public void handleBackAddCommentButton(ActionEvent actionEvent) {
        Scene pageTwoScene = new Scene(priRoot);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }
}
