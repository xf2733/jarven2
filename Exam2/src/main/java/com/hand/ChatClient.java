package com.hand;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;


public class ChatClient {

	public static void main(String[] args) {

		Socket s = null;

		File sendfile = new File("target.pdf");
		FileInputStream fis = null;
		byte[] buffer = new byte[4096 * 5];
		OutputStream os = null;
		
		if(!sendfile.exists()){
			System.out.println("客户端：要发送的文件不存在");
			return;
		}

		try {
			s = new Socket("127.0.0.1", 4004);
		}catch (IOException e) {
			System.out.println("未连接到服务器");
		}
		
		try {
			fis = new FileInputStream(sendfile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		
		try {
			PrintStream ps = new PrintStream(s.getOutputStream());
			ps.println("111/#" + sendfile.getName() + "/#" + fis.available());
			ps.flush();
		} catch (IOException e) {
			System.out.println("服务器连接中断");
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			
			os = s.getOutputStream();
			
			int size = 0;
			
			while((size = fis.read(buffer)) != -1){
				System.out.println("客户端发送数据包，大小为" + size);
				os.write(buffer, 0, size);
				os.flush();
			}
		} catch (FileNotFoundException e) {
			System.out.println("客户端读取文件出错");
		} catch (IOException e) {
			System.out.println("客户端输出文件出错");
		}finally{
			
			try {
				if(fis != null)
					fis.close();
			} catch (IOException e) {
				System.out.println("客户端文件关闭出错");
			}
		}
		
	}
}