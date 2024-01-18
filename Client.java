import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;

    Client(){

        try {
            socket =  new Socket("127.0.0.1", 7777);

            startReading();
            startWriting();
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }

    public void startReading(){

        // create thread to read from server
        Runnable r = ()->{
            try {
                DataInputStream din=new DataInputStream(socket.getInputStream());  

                System.out.println("Receiving messages from server");
                while(!socket.isClosed()){
                    String content = din.readLine();
                    if(content.equalsIgnoreCase("exit")){
                        socket.close();
                    }
                    System.out.println("Server: "+ content);
                }
                // now receive continuolsy from server and print it to console
                
            } catch (Exception e) {
                // TODO: handle exception
            }
             

        };

        new Thread(r).start();

    }

    public void startWriting(){
        Runnable r = ()->{
            try {
                 PrintWriter dout=new PrintWriter(socket.getOutputStream());  
                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
                while(!socket.isClosed()){
                    System.out.println("Write message to server:");
                    //read data from  
                    String message = br.readLine();
                    dout.println(message);
                    dout.flush();
                    if(message.equalsIgnoreCase("exit")){
                        socket.close();
                    }

                }
            } catch (Exception e) {
                // TODO: handle exception
            }
           
        };
        new Thread(r).start();
    }


    public static void main(String[] args){
        new Client();
    }
}
