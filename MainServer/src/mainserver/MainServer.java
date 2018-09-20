/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
    private ServerSocket ssocket;
    private int port = 5000;
    private String name;
    private Socket client;
    public MainServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        System.out.println("Starting..." + port);
        ssocket = new ServerSocket(port);

        client = null;
        while (true) {
            System.out.println("Waiting..");
            client = ssocket.accept();
            InetAddress ip = client.getInetAddress();
            
            byte[] ad = client.getInetAddress().getAddress();
            name = ad[0]+"."+ad[1]+"."+ad[2]+"."+ad[3];
            
            System.out.println("New client Connected to the server\nand its IP:" + ip + "\nipaddress:" + name);
            ClientHandler c = new ClientHandler(client);
           // c.setName(name);
            Thread t = new Thread(c);
            t.start();
        }
    }

    public static void main(String[] args) {

        int portNum = 5000;

        try {
            MainServer socketServer = new MainServer(portNum);
            socketServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO code application logic here
        /*int port = 5000;
         try {
         ServerSocket sc = new ServerSocket(port);
         System.out.println("Waiting For Client...");
         while (true) {
         Socket socket = sc.accept();
         InetAddress ip = socket.getInetAddress();
         String name = socket.getInetAddress().getHostName();
         System.out.println("New client Connected to the server\nand its IP:" + ip + "\nName:" + name);

         //Scanner scan = new Scanner(socket.getInputStream());
         BufferedReader bufin;
         bufin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         //System.out.println("adfa");

         String user = null;
         String pass = null;

         String ln;
         if ((ln = bufin.readLine()) != null) {
         user = ln;
         System.out.println(ln);
         }
         if ((ln = bufin.readLine()) != null) {
         pass = ln;
         System.out.println(ln);
         }

         bufin.close();
         /*if (scan.hasNext()) {
         user = scan.nextLine();
         }
         if (scan.hasNext()) {
         pass = scan.nextLine();
         }*/

        /*InputStream in;
         boolean flag = false, success = false;
         int ret = -1;
         try {
         in = new FileInputStream(new File("C:/Users/User/Desktop/team.txt"));
         BufferedReader reader = new BufferedReader(new InputStreamReader(in));
         // StringBuilder out = new StringBuilder();
         String line;
         while ((line = reader.readLine()) != null) {
         //  out.append(line);
         //System.out.println(line);
         if (line.equals(user)) {
         flag = true;
         String temp = reader.readLine();
         if (temp.equals(pass)) {
         System.out.println("Login Successfull");
         ret = 1;
         //JOptionPane.showMessageDialog(jButton1, "Login Successfull");
         //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         success = true;
         break;
         } else {
         System.out.println("Wrong Pass");
         ret = 0;
         //JOptionPane.showMessageDialog(jButton1, "Wrong Pass");
         //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         break;
         //JOptionPane.showMessageDialog();
         }
         }
         //       System.out.println("adfh");
         }
         if (flag == false) {
         System.out.println("No such user!");
         ret = -1;
         //JOptionPane.showMessageDialog(jButton1, "No such user!");
         }
         //System.out.println(out.toString());   //Prints the string content read from input stream

         reader.close();

         //PrintWriter out = new PrintWriter(socket.getOutputStream());
         BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         System.out.println("okay");
         out.write("okay");

         } catch (FileNotFoundException ex) {
         Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
         Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
         }

         /* PrintWriter out = new PrintWriter(socket.getOutputStream());
         out.println(ret);

         bufin.close();
         out.close();
         socket.close();
         sc.close();*/
        /* }
         } catch (IOException ex) {
         Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
    }

}
