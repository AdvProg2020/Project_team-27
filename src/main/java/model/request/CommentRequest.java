package model.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Seller;
import model.productRelated.Comment;
import model.productRelated.CommentStatus;
import model.productRelated.Product;
import client.view.FileHandling;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CommentRequest extends Request {

    private String title = null;
    private String content = null;
    private String personToVote = null;
    private String product = null;
    private String id = null;
    private static ArrayList<CommentRequest> allCommentRequests = new ArrayList<>();
    public static Type commentRequestType = new TypeToken<ArrayList<CommentRequest>>() {
    }.getType();

    public CommentRequest(String requestID) throws IOException {
        super(requestID);
        allCommentRequests.add(this);
        if (Account.getAccountWithUsername(this.getSeller()) instanceof Customer) {
            Customer customer = (Customer) Account.getAccountWithUsername(this.getSeller());
            customer.addCommentRequest(this);
        }
        writeInJ();
    }

    @Override
    public void declineRequest() throws IOException {
        Request.getAllRequests().remove(this);
        allCommentRequests.remove(this);
        Comment comment = Comment.getCommentFromId(id);
        comment.setCommentStatus(CommentStatus.NOTAPPROVEDBYTHEMANAGER);
        if (Account.getAccountWithUsername(this.getSeller()) instanceof Customer) {
            Customer customer = (Customer) Account.getAccountWithUsername(this.getSeller());
            customer.removeCommentRequest(this);
        }
        writeInJ();
    }

    @Override
    public void acceptRequest() throws IOException {
        Comment comment = Comment.getCommentFromId(id);
        if (Account.getAccountWithUsername(personToVote) instanceof Customer){
            Customer customer = (Customer) Account.getAccountWithUsername(personToVote);
            comment.setDetail(title, content, customer);
            comment.setCommentStatus(CommentStatus.CONFIRMED);
            Product.getProductById(product).setComment(comment);
//            Product.getProductById(product).proComments.add(comment);
            for (Comment proComment : Product.getProductById(product).proComments) {
                if (proComment.getId().equals(comment.getId())){
                    proComment = comment;
                }
            }
            Request.getAllRequests().remove(this);
            allCommentRequests.remove(this);
        }

        if (Account.getAccountWithUsername(this.getSeller()) instanceof Customer) {
            Customer customer = (Customer) Account.getAccountWithUsername(this.getSeller());
            customer.removeCommentRequest(this);
        }
        writeInJ();
        Seller.writeInJ();
    }

    public void setId(String id) throws IOException {
        this.id = id;
        writeInJ();

    }

    public void setTitle(String title) throws IOException {
        this.title = title;
        writeInJ();

    }

    public void setContent(String content) throws IOException {
        this.content = content;
        writeInJ();

    }

    public void setPersonToVote(String personToVote) throws IOException {
        this.personToVote = personToVote;
        writeInJ();
    }

    public void setProduct(String product) throws IOException {
        this.product = product;
        writeInJ();
    }

    public static void setAllCommentRequests(ArrayList<CommentRequest> allCommentRequests) {
        CommentRequest.allCommentRequests = allCommentRequests;
    }

    public static ArrayList<CommentRequest> getAllCommentRequests() {
        return allCommentRequests;
    }

    public static void writeInJ() throws IOException {
        FileHandling.setGson(new Gson());
        String json = FileHandling.getGson().toJson(CommentRequest.allCommentRequests, commentRequestType);
        FileHandling.writeInFile(json, "commentRequest.json");
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPersonToVote() {
        return personToVote;
    }

    public String getProduct() {
        return product;
    }

    public String getId() {
        return id;
    }
}