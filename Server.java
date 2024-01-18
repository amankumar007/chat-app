
import java.io.*;
import java.net.*;

public class Server {

    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is Ready to Connect System");
            System.out.println("Waiting");
            socket = server.accept();
            // get input stream =
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startWriting();
            startReading();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void startReading() {
        // to read data from client

        Runnable r = ()->{
            System.out.println("Reading messages from client...");
            
                try {
                    while(!socket.isClosed()){
                    // get client message and show to console 
                    String content = br.readLine();
                    System.out.println("Client: "+content);
                    if(content.equalsIgnoreCase("exit")){
                        socket.close();
                    } 
                }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
           
        };
        new Thread(r).start();
    }

    public void startWriting() {
        // to write data to client
        Runnable r = ()->{
            System.out.println("Writing message to client");
             try {
            while(!socket.isClosed()){
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
               
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    
                    System.out.println("Me: "+content);

                    if(content.equalsIgnoreCase("exit")){
                        socket.close();
                    }      

            }
             } catch (Exception e) {
                    // TODO: handle exception
                }
        };
        new Thread(r).start();
    }

    public static void main(String[] args) {
        new Server();
    }
}
