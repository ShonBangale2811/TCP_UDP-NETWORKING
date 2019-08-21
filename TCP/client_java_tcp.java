/*
[1]Java - Networking,"https://www.tutorialspoint.com/java/java_networking.html"
[2]Sockets programming in Java: A tutorial,"https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html"
Algorithm
1)Input Hostname,Validate.
2)Input Port Number,Validate.
3)Create Socket Connection using port number, Hostname/IP ADDR.
4)Input Equation from user
5)Send Equation to Server
    a)Write equation to buffer
6)Read Answer from buffer.Display to user
7)Run while loop till buffer not empty
    a) Display Message
8) End while (7)
9)Stop
 */

import java.io.*;
import java.io.DataOutputStream;
import java.net.Socket;

public class client_java_tcp {

    public static void main(String argv[]) throws Exception {
        String Equation;
        String Answer;
        String host = "";
        int port_nos = 0;
        Socket clientSocket = null; //Declare Client Socket Object[1]
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));//[2]

        System.out.println("Enter the hostname");
        host = inFromUser.readLine();
        if (host.equals("localhost") || host.equals("127.0.0.1") || host.equals("169.226.22.15")) {//validate hostname

            System.out.println("Enter the port Number");
            port_nos = Integer.parseInt(inFromUser.readLine());
            if (port_nos < 65535) { //validate port number

                try {
                    clientSocket = new Socket(host, port_nos);//connect to server
                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    System.out.println("Enter the Equation : ");
                    Equation = inFromUser.readLine();
                    System.out.println(Equation);
                    //send data to buffer
                    outToServer.writeBytes(Equation + '\n');
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    //get data from server
                    Answer = inFromServer.readLine();
                    System.out.println(Answer);
                    String repeat = "Start";//SOCKET PROGRAMMING ABSOLUTE VALUE LOOP
                    while (!"STOP".equals(repeat)) {

                        repeat = inFromServer.readLine();
                        if (!"STOP".equals(repeat)) {
                            System.out.println(repeat);
                        }
                    }

                    clientSocket.close();
                } catch (Exception e) {

                    System.out.println("Could not connect to server. Terminating.");
                    System.out.println(e);
                    clientSocket.close();
                }
            } else {
                System.out.println("Invalid port number. Terminating.");clientSocket.close();
            }

        } else {
            System.out.println("Invalid Host name CONNECTION TERMINATED");clientSocket.close();
        }

    }

}
