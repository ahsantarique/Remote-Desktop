/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remote;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author User
 */
public class ActiveList extends JFrame {

    private JList clientName;
    private static String[] names;
    private static String[] ips;
    private int count;
    private String user;

    private Socket csocket;

    public void setClient(Socket c) {
        this.csocket = c;
    }

    public void set() throws IOException {

        //InputStream in;
        //String address = "C:/Users/User/Desktop/" + user + ".txt";


    }

    public ActiveList(Socket c) throws IOException {
        super("Active Clients");
        System.out.println("Active List!");
        setLayout(new BorderLayout());
        setSize(240, 320);
        this.user = user;
          
        
        this.csocket = c;
        
        names = new String[100];
        ips = new String[100];
        
        
        
        
        int i = 0;

        String input;
        InputStream ips = csocket.getInputStream();
        System.out.println("Inside set()!");
        InputStreamReader isr = new InputStreamReader(ips);
        System.out.println("Inside set()!");
        BufferedReader in = new BufferedReader(isr);


        int j = 0;
        while ((input = in.readLine()) != null && j < 100) {
            System.out.println("okay");
            System.out.println(j);
            names[j] = input;
            j++;
        }
        System.out.println("okay");        
        clientName = new JList(names);
        clientName.setVisibleRowCount(count);
        clientName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane pane = new JScrollPane(clientName);
        pane.setSize(50, 40);
        add(pane);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientName.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = clientName.getSelectedIndex();
                String otherpc = (String) clientName.getSelectedValue();

                // ClientInitiator ci = new ClientInitiator();
                System.out.println(otherpc);
                System.out.println(names[index]);
                
                ClientInitiator.ip = otherpc;
                ClientInitiator.main(null);
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
    }
}
