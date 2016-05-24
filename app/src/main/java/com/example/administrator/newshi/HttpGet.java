package com.example.administrator.newshi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2015/11/21 0021.
 */
public class HttpGet {
    private static String PATH="http://192.168.43.120/mywebdb/";
    private static URL url;
    private String postStr;
    public HttpGet(String s1,String s2){
        String urlxx;
        urlxx=PATH+s2;
        postStr=s1;
        try {
            url=new URL(urlxx);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String startLink(){
        try {
            //System.out.println(PATH);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            //System.out.println("open link");
            con.setConnectTimeout(10000);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            byte[] data=postStr.getBytes();
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");//x-www-form-urlencoded
            con.setRequestProperty("Content-Length", String.valueOf(data.length));

            OutputStream out=con.getOutputStream();
            out.write(data, 0, data.length);
            out.close();
            InputStream in=con.getInputStream();

            int requestCode=con.getResponseCode();
            if(requestCode==200){
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                byte[] datain=new byte[1024];
                int len=0;
                String result="";
                if(in!=null){
                    while((len=in.read(datain))!=-1){
                        outputStream.write(datain,0,len);
                    }
                    result=new String(outputStream.toByteArray(),"utf-8");
                  //  System.out.println(result);
                    out.close();
                    in.close();
                    return result;
                }
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
