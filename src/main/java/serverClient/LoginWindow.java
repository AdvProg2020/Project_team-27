package serverClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.accounts.Customer;
import model.accounts.Seller;
import model.off.Auction;
import model.productRelated.Product;


public class LoginWindow implements Runnable {

    public Customer customerWithHigherPrice;
    public Product productToBuy;
    public Seller sellerWhoHasPro;
    public double highPrice = 0;
    public boolean isSaleOrNot = false;
    public Label label = new Label();

    Stage window;
    public AnchorPane anchorPane;
    static Scene scene1, scene2;
    private Auction auction;
    Text login;
    Label lblWelcome, lblUser, lblIp;
    TextField txtUser, txtIp, messageField;
    Button loginBtn, clearBtn, sendBtn, logoutBtn; //browse for images
    TextArea textArea = new TextArea();
    VBox vBox;
//    Image chatImage = new Image("ChatImage.png", 200, 200, true, true);

    InetAddress ip;
    DatagramSocket socket;
    int port = 4293, ID = -1;
    Thread send, receive, listen, run, updateClients;
    boolean running = false;
    boolean text = false, typeAttempt = true, seenAttempt = false, messageReceived = false, clientStatus;
    String disconnectedUser;
//     Server server=new Server(4293);


    public static Scene getScene1() {
        return scene1;
    }



    List<ServerClient> connectedClients = new ArrayList<>();

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }


    //    public static void main(String[] args) {
//        start();
//    }

    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Login Window");
        lblWelcome = new Label("Welcome");
        lblWelcome.setFont(Font.font("Serif", FontWeight.BOLD, 15));
        lblWelcome.setPadding(new Insets(0, 0, 0, 140));
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(25, 50, 0, 25));
        Separator separator = new Separator();
        Separator sep1 = new Separator();
        sep1.setVisible(true);
        sep1.setPadding(new Insets(25, 0, 0, 0));
        login = new Text("Please Login");
        login.setFont(Font.font("Serif", FontWeight.BOLD, 20));
        vBox = new VBox();
        vBox.getChildren().addAll(lblWelcome, sep1, login);

        lblUser = new Label("User name");
        grid.add(lblUser, 0, 0);
        txtUser = new TextField();
        grid.add(txtUser, 1, 0);
        lblIp = new Label("Host IP");
        grid.add(lblIp, 0, 1);
        txtIp = new TextField();
        grid.add(txtIp, 1, 1);
        loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            window.setScene(clientWindow(txtIp.getText(), port));
            //for current time
            long clientCurrentTime = System.currentTimeMillis();
            // long timeDifference=server.getServerStartTime()-clientCurrentTime;

        });
        clearBtn = new Button("Clear");
        clearBtn.setOnAction(e -> {
            txtUser.clear();
            txtIp.clear();
        });
//        ImageView chatImageView = new ImageView(chatImage);
        StackPane stackPane = new StackPane();

        stackPane.setAlignment(Pos.TOP_RIGHT);

        HBox hBox = new HBox();
        hBox.setSpacing(15);
        hBox.getChildren().addAll(loginBtn, clearBtn);
        grid.add(hBox, 1, 2);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vBox);
        borderPane.setCenter(grid);
        borderPane.setRight(stackPane);
        borderPane.setBottom(hBox);
        scene1 = new Scene(borderPane, 600, 500);
        window.setOnCloseRequest(e -> {
            if (clientStatus) {
                String disconnected = "/d/" + ID + "/e/";
                send(disconnected, 2);
                running = false;
                window.close();
            }
        });
        window.setScene(scene1);
        window.show();

    }

    private Scene clientWindow(String address, int port) {

        boolean checkConnect = openConnection(address, port);
        if (checkConnect) {
            try {
                ServerClient serverClient = new ServerClient(txtUser.getText(),InetAddress.getByName(address),port,ID);
                connectedClients.add(serverClient);

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String message = "/c/" + txtUser.getText() + "/e/";
            send(message.getBytes());
        }
        running = true;
        run = new Thread(this, "Running");
        run.start();

        window.setTitle(txtUser.getText() + " Window");
        textArea.setEditable(false);
        textArea.setMaxWidth(500);
        textArea.setMinHeight(400);
        textArea.setPadding(new Insets(0, 0, 0, 10));
        messageField = new TextField();
        messageField.setPromptText("Type message here");
        messageField.setMinWidth(500);
//        messageField.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                System.out.println(messageField.getText());
//                send(messageField.getText(), 0);
//                typeAttempt = true;
//                messageField.setText("");
//            } else {
////                send(" : is Typing", 1);
////                typeAttempt=true;
////                System.out.println(txtUser.getText()+" is Typing");
//            }
//        });
        sendBtn = new Button("Send");
        sendBtn.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                System.out.println(messageField.getText());
                send(messageField.getText(), 0);
                typeAttempt = true;
                //console(messageField.getText());
                messageField.setText("");
            }  //                send(" : is Typing", 1);
            //                typeAttempt=true;
            //                System.out.println(txtUser.getText()+" is Typing");
            //                send(messageField.getText(), 0);

        });
        logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            System.out.println("Logout : " + clientStatus);
            // if (clientStatus) {
            window.setScene(scene1);
            String disconnected = "/d/" + ID + "/e/";
            send(disconnected.getBytes());
            System.out.println("Disconnection Request ");
            running = false;
            //  }
            window.setScene(scene1);
            textArea.clear();
            txtUser.clear();
            txtIp.clear();
        });
        HBox hBox = new HBox();
        hBox.getChildren().add(logoutBtn);
        hBox.setAlignment(Pos.TOP_RIGHT);
        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(textArea);
        HBox hBox1 = new HBox();
        hBox1.setPadding(new Insets(0, 0, 10, 10));
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(messageField, sendBtn);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(vBox1);
        borderPane.setBottom(hBox1);

//        scene2.getStylesheets().add("ChatCSS.css");

        if (messageReceived) {
            scene2.setOnMousePressed(e -> {
                seenAttempt = true;
                send("message seen", 3);
//            seenAttempt=false;
                //System.out.println("Message Seen ");
            });
        }
        if (isSaleOrNot){
            Label proName = new Label();
            Label customerWithHighest = new Label();
            customerWithHighest.setText("Seller : " + Seller.getSellerWithUsername(productToBuy.getSeller()).getName());
            proName.setText("Product name : " + productToBuy.getProductName());
            proName.setLayoutY(100);
            proName.setLayoutX(10);
            customerWithHighest.setLayoutX(10);
            customerWithHighest.setLayoutY(150);
            proName.setVisible(true);
            proName.setTextFill(Color.DARKRED);
            customerWithHighest.setVisible(true);
            customerWithHighest.setTextFill(Color.DARKRED);
            anchorPane = new AnchorPane();
            anchorPane.getChildren().addAll(proName,customerWithHighest);
            label.setVisible(false);
            anchorPane.getChildren().add(label);
            borderPane.setRight(anchorPane);
        }
        scene2 = new Scene(borderPane, 600, 500);
        return scene2;
    }

    private void console(String message) {
        message.replace("/c/"," ");
        message.replace("/e/"," ");
        textArea.appendText(message + " \n");
    }

    private boolean openConnection(String address, int port) {
        try {
            ip = InetAddress.getByName(address);
            socket = new DatagramSocket();
            console(txtUser.getText() + " Attempting to create a socket Connection ");
        } catch (UnknownHostException | SocketException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void send(String message, int text) {
        //  if(message.isEmpty()) return;
        //String text=message;
        System.out.println(txtUser.getText()+ "           This IS TeXt");
        if (isSaleOrNot){
            try {
                double price = Double.parseDouble(message);
                label.setVisible(true);
                if (price > highPrice){
                    highPrice = price;
                    customerWithHigherPrice = Customer.getCustomerWithUsername(txtUser.getText());
                    System.out.println(customerWithHigherPrice.getName() + " my customer name");
                    label.setText("Highest offer till now : " + price);
                    label.setLayoutY(50);
                    label.setLayoutX(10);
                }
                else {
                    label.setText("Highest offer till now : " + highPrice);
                }
            }catch (NumberFormatException e){
                System.out.println("not num");
            }
        }
        console(txtUser.getText()+" : "+message);
        switch (text) {
            case 0:
                if (message.isEmpty()) {
                    System.out.println("Message is empty");
                    return;
                }
                if (message.startsWith("/time/")) {
                    send(message.getBytes());
                } else if (message.startsWith("/ip/")) {
                    send(message.getBytes());
                } else if (message.startsWith("/number/")) {
                    send(message.getBytes());
                }
                message = "/m/" + txtUser.getText() + " : " + message + "/e/";
                send(message.getBytes());

                break;

            case 1:
//                message = "/t/" + txtUser.getText() + " IS TYPING ";
//                send(message.getBytes());
                break;
            case 2://disconnected
                send(message.getBytes());
                break;
            case 3://seen
                message = "/s/" + "message seen" + "/e/";
                send(message.getBytes());
                break;
            default:
                break;
        }

    }

    private void send(final byte[] data) {
        send = new Thread("Send Thread") {
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        send.start();
    }

    private String receive() {
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String string = new String(packet.getData());
        System.out.println(string + " ******** ");
        return string;
    }

    private void listen() { // for checking to client and server
        listen = new Thread("Listen Thread") {
            int attempt;

            @Override
            public void run() {
                while (running) {
                    String message = receive();
                    if (message.startsWith("/c/")) {
                        ID = Integer.parseInt(message.split("/c/|/e/")[1]);
                        console("Successfully Connected to Server! Client ID : " + ID);
                    } else if (message.startsWith("/d/")) {
                        String disconnectUser = message.split("/d/|/i/")[1];
                        String disconnectID = message.split("/i/|/e/")[1];
                        String disconnectMessage = message.split("/d/|/e/")[1];
                        alertBox("Client Disconnected",disconnectMessage+ " left the Chat");
                        System.out.println("Disconnected User : " + disconnectUser);
                        System.out.println("Disconnected ID   : " + disconnectID);
                        disconnectedUser = disconnectUser;
                        console(disconnectUser + " left the Chat");
                        //////////////////////////////////////////////////////////////////////////////////
                        //console(message + "\n");
                        clientStatus = true;
                        System.out.println("sent cleint status " + clientStatus);
                    } else if (message.startsWith("/m/")) {
                        String text = message.substring(3);
                        text = text.split("/e/")[0];
                       // 88888888888888888888888888888888888888888888
                        console(text);
                        ////////////////////////////////////////////////////////////////////////////////
                        //console(message + "\n");
                        messageReceived = true;
                    } else if (message.startsWith("/time/")) {
                        String receivedTime = message.substring(6);
                        receivedTime = receivedTime.split("/e/")[0];
                        // System.out.println(receivedTime.length());
                        long serverTime = Long.parseLong(receivedTime);
                        long currentTime = System.currentTimeMillis();
                        long totalTime = currentTime - serverTime;
                        double sec = totalTime / 1000;
                        console(sec + " sec");

                        System.out.println(sec + " seconds");
                    } else if (message.startsWith("/ip/")) {
                        String receivedIp = message.substring(4);
                        receivedIp = receivedIp.split("/e/")[0];
                        System.out.println("server ip : " + receivedIp);
                        console("server ip : " + receivedIp);
                    } else if (message.startsWith("/number/")) {
                        String number = message.substring(8);
                        number = number.split("/e/")[0];
                        System.out.println("Number of clients located : " + number);
                    } else if (message.startsWith("/s/")) {
                        seenAttempt=!seenAttempt;
                        if (seenAttempt) {
                            String seenStatus = message.split("/s/|/e/")[1];
                            console(seenStatus);
                            seenAttempt = false;
                        }
                    }
                    if (message.startsWith("/t/")) {
                        if (typeAttempt) {
                            String textStatus = message.split("/t/")[1];
                            System.out.println(textStatus);
                            console(textStatus);
                            typeAttempt = false;
                        }

                    }
                }
            }
        };
        listen.start();
    }

    public void setIfAllIsSale(Customer customerWithHigherPrice , Product product){
//        this.sellerWhoHasPro = sellerWhoHasPro;
        this.customerWithHigherPrice = customerWithHigherPrice;
        this.productToBuy = product;
    }

    @Override
    public void run() {
        listen();
    }



    protected void alertBox(String header, String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setTitle(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
}

