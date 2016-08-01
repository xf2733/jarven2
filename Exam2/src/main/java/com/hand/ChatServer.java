package com.hand;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class ChatServer {  
	
    private ServerSocket ss;  
    private Socket socket;  
    private BufferedInputStream in;  
    private BufferedOutputStream out;  
    
    public static void main(String[] args)   {  
        new ChatServer();  
    }  
    
    public ChatServer(){  
            try {
				ss = new ServerSocket(8899);  
				System.out.println("The server is waiting your input...");  
//				while(true)  {  
				    socket = ss.accept();  
				    in = new BufferedInputStream(socket.getInputStream());  
				    out = new BufferedOutputStream(socket.getOutputStream()); 
//				    out.write("收到client的连接" );  
				    FileOutputStream fos=new FileOutputStream("socket_target.pdf");
					BufferedOutputStream bis=new BufferedOutputStream(fos);
				    
				    byte[] input =new byte[1024];
					while(in.read(input)!=-1){
						bis.write(input);
					}
					
					bis.close();
					fos.close();
				    out.close();  
				    in.close();  
				    socket.close();  
				    ss.close();  
//				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
            
    
}  