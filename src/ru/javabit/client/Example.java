package ru.javabit.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Example {

    private final static int BUFSIZE = 20;

    public static void main(String[] args) throws Exception {
//        String server = args[0];
//        int port = Integer.parseInt(args[1]);

        String server = "localhost";
        int port = 8082;

        //double value = Double.valueOf(args[2]).doubleValue();
        double value = 20;

        Socket s = new Socket(server, port);
        OutputStream os = s.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeDouble(value);

        InputStream is = s.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        value = dis.readDouble();

        System.out.println(value);
        s.close();
    }
}
