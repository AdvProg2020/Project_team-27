package server.menus;

import model.accounts.Customer;
import model.accounts.Seller;
import model.request.CommentRequest;
import model.request.Request;
import model.log.BuyLog;
import model.productRelated.Comment;
import model.productRelated.CommentStatus;
import model.productRelated.Product;

import java.io.IOException;
import java.util.UUID;

public class ProductMenu {
    private static int outputNo = 0;
    private static Product selectedProduct;
    private static BuyLog buyLog;
    private static CommentRequest commentRequest;
    private static String productId;


    public static void setBuyLog(BuyLog buyLog) {
        ProductMenu.buyLog = buyLog;
    }

    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    public static void setSelectedProduct(Product selectedProduct) {
        ProductMenu.selectedProduct = selectedProduct;
    }

    public static BuyLog getBuyLog() {
        return buyLog;
    }

    public static String getProductId() {
        return productId;
    }

    public static void setProductId(String productId) {
        ProductMenu.productId = productId;
    }

    public static void addToCart() throws IOException {
        String uniqueID = UUID.randomUUID().toString();
        if (BuyLog.getFirstProduct()) {
            buyLog = new BuyLog(uniqueID);
        }
        buyLog.addProductToBuyLog(productId, 1);
    }

    public static int addComments() throws IOException {
        String commentId = LoginMenu.getLoginAccount().getUsername() + " comment on " + selectedProduct.getId();
        if (!Comment.isThereCommentWithId(commentId)) {
            //   String id = LoginMenu.getLoginAccount(). + "comment";
            if (!Request.isThereRequestFromID(commentId)) {
                Comment  comment = new Comment(commentId);
                selectedProduct.proComments.add(comment);
                Seller.writeInJ();
                comment.setCommentStatus(CommentStatus.WAITINGFORAPPROVAL);
                commentRequest = new CommentRequest(commentId);
                Seller.writeInJ();
                if(LoginMenu.getLoginAccount() instanceof Customer) {
                    Customer customer = (Customer) LoginMenu.getLoginAccount();
                    commentRequest.setPersonToVote(customer.getUsername());
                    commentRequest.setProduct(selectedProduct.getId());
                    Seller.writeInJ();
                }
                commentRequest.setId(commentId);
            } else commentRequest = (CommentRequest) Request.getRequestFromID(commentId);
            Seller.writeInJ();

            outputNo = 0;
            //  CommandProcessor.setSubMenuStatus(SubMenuStatus.COMMENTSTITLE);
            // CommandProcessor.setInternalMenu(InternalMenu.CHANGEDETAILS);
        } else outputNo = 3;
        return outputNo;
        // OutputMassageHandler.showProductOutput(outputNo);
    }

    public static int titleOfComment(String title) throws IOException {
        if (title.matches("(\\s*\\S+\\s*)+")) {
            //  CommandProcessor.setSubMenuStatus(SubMenuStatus.COMMENTSCONTENT);
            commentRequest.setTitle(title);
            outputNo = 0;
        } else outputNo = 2;
        return outputNo;
    }

    public static int contentOfComment(String content) throws IOException {
        if (content.matches("(\\s*\\S+\\s*)+")) {
            //  CommandProcessor.setSubMenuStatus(SubMenuStatus.COMMENTS);
            //  CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
            commentRequest.setContent(content);
        } else outputNo = 1;
        return outputNo;
    }

    /*


    public static void processAttributes() throws FileNotFoundException {
        // OutputHandler.attributes(selectedProduct.getId());
    }

    public static void processCompare(String productID) throws FileNotFoundException {
        if (Product.isThereProductWithId(productID)) {
            //   OutputHandler.compareProducts(selectedProduct.getId(), productID);
        } else OutputMassageHandler.showProductOutput(4);
    }

    //comment--------------------------------------------------------------------
    public static void processComments() throws FileNotFoundException {
        // CommandProcessor.setSubMenuStatus(SubMenuStatus.COMMENTS);
        // OutputHandler.showCommentOnOneProduct(selectedProduct.getId());
    }

      public static void processDigest() throws FileNotFoundException {
        // CommandProcessor.setSubMenuStatus(SubMenuStatus.DIGEST);
        selectedProduct = Product.getProductById(ProductsMenu.getProductId());
        // OutputHandler.digest(selectedProduct.getId());
    }
     */
}