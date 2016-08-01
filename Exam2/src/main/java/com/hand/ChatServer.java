package com.hand;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	public static void main(String[] args) {
		
		ServerSocket ss = null;
		Socket s = null;
		
		File file = null;
		FileOutputStream fos = null;
		
		InputStream is = null;
		
		byte[] buffer = new byte[4096 * 5];
		
		String comm = null;
		
		
		try {
			ss = new ServerSocket(4004);
			s = ss.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			InputStreamReader isr = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			comm = br.readLine();
		} catch (IOException e) {
			System.out.println("服务器与客户端断开连接");
		}
		
		int index = comm.indexOf("/#");
		
		String xieyi = comm.substring(0, index);
		if(!xieyi.equals("111")){
			System.out.println("服务器收到的协议码不正确");
			return;
		}
		
		comm = comm.substring(index + 2);
		index = comm.indexOf("/#");
		String filename = comm.substring(0, index).trim();
		String filesize = comm.substring(index + 2).trim();
		
		
		file = new File(filename);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("服务器端创建文件失败");
			}
		}else{
			System.out.println("本路径已存在相同文件，进行覆盖");
		}
		
		
		
		try {
			fos = new FileOutputStream(file);
			long file_size = Long.parseLong(filesize);
			is = s.getInputStream();
			int size = 0;
			long count = 0;
			
			while(count < file_size){
				size = is.read(buffer);
				
				fos.write(buffer, 0, size);
				fos.flush();
				
				count += size;
				System.out.println("服务器端接收到数据包，大小为" + size);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("服务器写文件失败");
		} catch (IOException e) {
			System.out.println("服务器：客户端断开连接");
		}finally{
			try {
				if(fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}