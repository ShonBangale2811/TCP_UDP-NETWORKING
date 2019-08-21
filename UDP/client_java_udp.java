/*
[1]Java - Networking,"https://www.tutorialspoint.com/java/java_networking.html"
[2]Sockets programming in Java: A tutorial,"https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html"
Algorithm
1)Input Hostname,Validate.
2)Input Port Number,Validate.
3)Create DataGram Socket for Connection using port number, Hostname/IP ADDR.
4)Input Equation from user
5)Send Equation to Server
    a)Write equation length and form datagram packet
    b)Write equation and form datagram packet
    c) wait for ack for 1s
    d)if no ACK RESEND
    e) If resent 3 times with no ACK :
        Print Equation Sending Failed.
6)Recvive Result length
7)Wait for 500ms:
    a)Recive Result.
    b)Send ACK
    c) If no Result exit;
8)Run while loop till buffer not empty
    a) Display Message
9) End while (7)
10)Stop
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class client_java_udp {

    public static void main(String args[]) throws Exception {
        String hostname;
        String Equation;
        int port_nos = 0;
        DatagramSocket clientSocket = new DatagramSocket();//[1] Create Socket
        byte[] sendDatatoserver = new byte[1024];
        byte[] receiveDatafromserver = new byte[1024];

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));//[2] For Data Transmission
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the host name");
        hostname = sc.nextLine();// validate Host Name
        if (hostname.equals("localhost") || hostname.equals("127.0.0.1") || hostname.equals("169.226.22.15")) {
            InetAddress IPAddress = InetAddress.getByName(hostname);
            System.out.println("Enter the port Number");//Validate Port Number
            port_nos = sc.nextInt();

            if (port_nos < 65535) {
                System.out.println("Please Enter The Equation");

                Equation = inFromUser.readLine();

                String len = Equation.length() + "";
                sendDatatoserver = len.getBytes();
                //Send Length
                DatagramPacket SP_len = new DatagramPacket(sendDatatoserver, sendDatatoserver.length, IPAddress, port_nos);
                clientSocket.send(SP_len);
                //Send Equation
                sendDatatoserver = Equation.getBytes();

                DatagramPacket SP_eq = new DatagramPacket(sendDatatoserver, sendDatatoserver.length, IPAddress, port_nos);
                clientSocket.send(SP_eq);

                clientSocket.setSoTimeout(1000);
                int check = 3;
                String ack = "";
                boolean sent;
                do {
                    try {
                        //Recive ACK
                        DatagramPacket RP_ACK = new DatagramPacket(receiveDatafromserver, receiveDatafromserver.length);
                        clientSocket.receive(RP_ACK);
                        ack = new String(RP_ACK.getData());
                        System.out.println(ack);
                        sent = true;
                        break;
                    } catch (IOException e) {

                        check--;
                        System.out.println(e);
                        sent = false;

                    }

                } while (check > 0 || "ACK".equals(ack));
                clientSocket.setSoTimeout(100000);
                try {
                    if (sent) {
                        //Recive length
                        DatagramPacket RP = new DatagramPacket(receiveDatafromserver, receiveDatafromserver.length);
                        clientSocket.receive(RP);
                        String Answer = new String(RP.getData());
                        System.out.println(Answer);
                        String repeat = "";
                        while (true) {
                            if (true) {
                                //Recive Message
                                DatagramPacket RP1 = new DatagramPacket(receiveDatafromserver, receiveDatafromserver.length);
                                clientSocket.receive(RP1);
                                repeat = new String(RP1.getData());
                                System.out.println(repeat);
                            } else {
                                clientSocket.close();
                                System.exit(1);

                            }
                        }

                    } else {
                        clientSocket.close();
                        System.exit(1);
                    }
                } catch (Exception e) {
                    clientSocket.close();
                    System.exit(1);

                }
            } else {
                System.out.println("Invalid port number. Terminating.");
                clientSocket.close();
            }

        } else {
            System.out.println("Invalid Host name CONNECTION TERMINATED");
            clientSocket.close();
        }
        clientSocket.close();
        System.exit(1);

    }
}
