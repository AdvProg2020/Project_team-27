package serverClient;

import java.util.Scanner;

public class ServerMain {
    int port;
    static Server server;

    public ServerMain(int port){
        this.port=port;
        server=new Server(port);
        System.out.println("Server Started ");
    }
    public static void main(String []args){
        int port;
        if(args.length!=1){
            System.out.println("Please Enter port number For Server ");
//        return;
        }
        Scanner scanner = new Scanner(System.in);
        port=scanner.nextInt();
        new ServerMain(port);
    }


}
