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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.System.in;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;

/**
 *
 * @author User
 */
public class ClientHandler implements Runnable {

    private Socket client;
    private int ret;
    private String op; // 1 login // 2 signup

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public ClientHandler(Socket client) {
        this.client = client;

    }

    private void readResponse() throws IOException, InterruptedException {
        String userInput;
        BufferedReader bin = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String user = null;
        String pass = null;
        if ((userInput = bin.readLine()) != null) {
            user = userInput;
        }
        if ((userInput = bin.readLine()) != null) {
            pass = userInput;
        }
        if ((userInput = bin.readLine()) != null) {
            op = userInput;
        }
        if (op.equals("1")) {
            System.out.println("Login");
            InputStream in;
            boolean flag = false, success = false;
            ret = -1;
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
                            String teamFile = "C:/Users/User/Desktop/" + user + ".txt";
                            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(teamFile, true)));
                            //out.println(client.getInetAddress().getHostName());
                            //out.println(client.getInetAddress().getAddress());
                            byte[] ad = client.getInetAddress().getAddress();
                            String s = ad[0] + "." + ad[1] + "." + ad[2] + "." + ad[3];
                            out.println(s);

                            out.close();

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
                writeResponse(ret);
                reader.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (op.equals("2")) {

            String teamFile = "C:/Users/User/Desktop/" + user + ".txt"; // later
            new FileWriter(teamFile, true);
            //PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:/Users/User/Desktop/team.txt", true)));
            InputStream in;
            boolean flag = false, success = false;
            ret = -1;
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
                        System.out.println("Failed");
                        ret = -1; // not created  
                        break;
                    }
                    reader.readLine();
                    //       System.out.println("adfh");
                }
                if (flag == false) {
                    System.out.println("Successful!");
                    ret = 1;
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:/Users/User/Desktop/team.txt", true)));
                    out.println(user);
                    out.println(pass);
                    out.close();
                    //JOptionPane.showMessageDialog(jButton1, "No such user!");
                }
                //System.out.println(out.toString());   //Prints the string content read from input stream
                writeResponse(ret);
                reader.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
            }

            //out.println(user);
            //out.println(pass);
            //
            //out.close();
        }
        
        
        
        
        
        
        else if (op.equals("3")) {
            String teamFile = "C:/Users/User/Desktop/" + user + ".txt";
            InputStream in = new FileInputStream(new File(teamFile));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String tempFile = "C:/Users/User/Desktop/" + user + "temp.txt";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(tempFile)));
            String line;
            byte[] ad = client.getInetAddress().getAddress();
            String s2 = ad[0] + "." + ad[1] + "." + ad[2] + "." + ad[3];
            
            System.out.println("Terminating..... "+ s2);
            while ((line = reader.readLine()) != null) {
                if (line.equals(s2)) {
                    continue;
                }
                out.println(line);
            }
            reader.close();
            out.close();

            File oldfile = new File(teamFile);
            if (oldfile.delete()) {
                System.out.println("OldFile Deleted!");
            }
            File temp = new File(tempFile);
            File newfile = new File(teamFile);
            if (temp.renameTo(newfile)) {
                System.out.println("TempFile Renamed!");
            }
            if (temp.delete()) {
                System.out.println("TempFile Deleted!");
            }
        } 
        
        
        
        
        
        
        
        else if (op.equals("4")) {
            InputStream in;
            String address = "C:/Users/User/Desktop/" + user + ".txt";
            try {
                in = new FileInputStream(new File(address));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                // StringBuilder out = new StringBuilder();
                String line;
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                //out.flush();
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    out.write(line);
                    out.newLine(); // may be necessary
                    //ips[i++] = line;
                }
                //out.flush();
                //
                out.close();
                reader.close();
                System.out.println("No problem in ClientHandler!");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    /*
     public void read() throws FileNotFoundException, IOException {
     String userInput;
     BufferedReader bin = new BufferedReader(new InputStreamReader(client.getInputStream()));

     String user = null;
     String op = null;
     if ((userInput = bin.readLine()) != null) {
     user = userInput;
     }
     if ((userInput = bin.readLine()) != null) {
     op = userInput;
     }
 
     bin.close();
     }
     */

    public void writeResponse(int ret) throws IOException, InterruptedException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        String var = "" + ret;

        out.write(var);
        //out.newLine();
        out.flush();
        out.close();
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread:" + Thread.currentThread().getName());
            readResponse();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
