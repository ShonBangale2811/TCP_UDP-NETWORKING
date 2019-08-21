/*
[1]Java - Networking,"ttps://www.tutorialspoint.com/java/java_networking.html"
[2]Sockets programming in Java: A tutorial,"ttps://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html"
Algorithm
1)Input Port Number
2)Run while until true:
    a) Recive Connection Socket
    b)Accept Datagram
3)Recive data from client
    a)Read data length from Datagram
    b)wait for 500ms
    c)if : Recived:
        Send ACK
        else
        Timeout
4)Modify String
5)Calculate Expression
6)Send Answer to client in form of Datagram 
    a) Wait for ACK for 1s
    b) if no ACK
        resend 3 times
        else
PRINT : Reult Transmission Failed
7)Run while loop till absoltue value of answer
    a) Send "SOCKET PROGRAMMING".
8) End while (7)
9) End While(2)
10)Stop
*/




import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class server_java_udp {

    public static void main(String args[]) throws Exception {
        int port_nos;
        String Equation = null;
        Scanner sc = new Scanner(System.in);
     //   System.out.println("Enter the port Number");
        port_nos =  Integer.parseInt(args[0]);//sc.nextInt();

        DatagramSocket serverSocket = new DatagramSocket(port_nos);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        byte[] sendDataACK = new byte[1024];
        byte[] sendData1 = new byte[1024];
        System.out.println("Waiting...");

        while (true) {
            DatagramPacket RP_len = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(RP_len);
            String len = new String(RP_len.getData());
            InetAddress IPAddress = null;
            int port = 0;
            serverSocket.setSoTimeout(500);
            try {

                DatagramPacket RP = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(RP);
                Equation = new String(RP.getData());
                Equation = Equation.replaceAll("\\s+", "");
                IPAddress = RP.getAddress();

                port = RP.getPort();
            //    System.out.println("port" + port);
              //  System.out.println("IP" + IPAddress);


                String ack = "ACK";
                sendDataACK = ack.getBytes();
                DatagramPacket sendPacket_ack
                        = new DatagramPacket(sendDataACK, sendDataACK.length, IPAddress, port);
                serverSocket.send(sendPacket_ack);
            } catch (SocketTimeoutException e) {
                System.out.println("Timeout" + e);
            }
            String[] Answer = new String[3];
       //     System.out.println("From Client: " + Equation);
            Answer = run(Equation);
            sendData = Answer[0].getBytes();
            DatagramPacket sendPacket
                    = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
            serverSocket.setSoTimeout(1000);
            try {
                int repeat = Integer.parseInt(Answer[1]);
                if (repeat < 0) {
                    repeat = 0 - repeat;
                }
                String r = "";
                while (repeat != 0) {
                    r = "SOCKET PROGRAMMING";
                    sendData1 = r.getBytes();
                    DatagramPacket sendPacket1
                            = new DatagramPacket(sendData1, sendData1.length, IPAddress, port);
                    serverSocket.send(sendPacket1);
                    repeat--;
                    r = "";
                }

            } catch (SocketTimeoutException e) {
                System.out.println(e);
                System.exit(1);
            }
            break;

        }

    }

    public static String[] run(String Equation) {
        String[] Answer = new String[3];
        char x = getoperator(Equation);
        boolean temp = validate(getoperator(Equation), Equation);

        if (temp == true) {
            String[] S = expression(Equation, x);
            int result = calculate(S, x);
         //   System.out.println("Result " + result);
            Answer[0] = "Solution" + ":" + result + '\n';
            Answer[1] = result + "";
        } else {
            Answer[0] = "Error in equation" + '\n';
            Answer[1] = "0";
        }
        return Answer;
    }

    public static char getoperator(String S) {

        char temp;
        if (S.contains("+")) {
            temp = '+';
        } else {
            if (S.contains("-")) {
                temp = '-';
            } else {
                if (S.contains("*")) {
                    temp = '*';
                } else {
                    if (S.contains("/")) {
                        temp = '/';
                    } else {
                        temp = 'x';
                    }
                }
            }
        }

        return temp;
    }

    public static boolean validate(char a, String S) {

        boolean temp = true;
        if (a == 'x') {
            temp = false;
        } else {
            if (S.charAt(S.indexOf(a) + 1) == a || S.charAt(S.indexOf(a) - 1) == a) {
                temp = false;
            } else {
                temp = true;
            }
        }
        return temp;
    }

    public static String[] expression(String S, char x) {
        String[] A = new String[3];
        if (x == '-') {
            A = S.trim().split(x + "");
        } else {
            A = S.trim().split(Pattern.quote(x + ""));
        }

        return A;
    }

    public static int calculate(String[] S, char s) {
        int temp = -1;
        int a = Integer.parseInt(S[0]);
        int b = Integer.parseInt(S[1]);
        switch (s) {
            case '+': {
                temp = a + b;
                break;
            }
            case '-': {
                temp = a - b;
                break;
            }
            case '*': {
                temp = a * b;
                break;
            }
            case '/': {
                if (b != 0) {
                    temp = a / b;
                } else {
                    temp = 999999; 
                }
                break;
            }
        }

        return temp;
    }
}
