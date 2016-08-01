package com.hand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class App 
{
    public static void main( String[] args )
    {
        try {
        	
			URL url=new URL("http://files.saas.hand-china.com/java/target.pdf");
			HttpURLConnection httpUrl=(HttpURLConnection) url.openConnection();
			httpUrl.connect();
			BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
			File file = new File("target.pdf");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			int len=1000;
			byte[] input = new byte[len];
			StringBuilder builder =new StringBuilder();
			while((len=bis.read(input))!=-1){
				bos.write(input, 0, len);
			}
			
			bos.flush();
			bos.close();
			bis.close();
			httpUrl.disconnect();
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
