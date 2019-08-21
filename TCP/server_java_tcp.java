/*
[1]Java - Networking,"ttps://www.tutorialspoint.com/java/java_networking.html"
[2]Sockets programming in Java: A tutorial,"ttps://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html"
Algorithm
1)Input Port Number
2)Run while until true:
    a) Create Connection Socket
    b)Accept Sonnection
3)Recive data from client
    a)Read data from buffer
4)Modify String
5)Calculate Expression
6)Send Answer to client
7)Run while loop till absoltue value of answer
    a) Send "SOCKET PROGRAMMING".
8) End while (7)
9) End While(2)
10)Stop
*/
    import java.io.*;
    import java.io.DataOutputStream;
    import java.net.ServerSocket;
    import java.net.Socket;
    import java.util.Scanner;
    import java.util.regex.Pattern;


    public class server_java_tcp {


          public static void main(String[] args) throws Exception {
    
              String Equation; // Variable to store reciven from client
              int length;//Length of client message
              int port; //port number on server
        
              Scanner sc = new Scanner(System.in);
              System.out.println("Enter the port Number");
              //port = sc.nextInt();
                port = Integer.parseInt(args[0]);
              ServerSocket socket = new ServerSocket(port);//[1]
              System.out.println("Waiting...");
        
              while (true) {

            Socket connectionSocket = socket.accept();//[1]
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));//[2]
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());//[2]
            try{
           // length=inFromClient.read();  //[1]
            Equation = inFromClient.readLine();//[1]
                System.out.println(Equation);
            Equation= Equation.replaceAll("\\s+","");//Replace blank charecters in equation by null 2 + 2 becomes 2+2
            String[] Answer = run(Equation);//Computation
            outToClient.writeBytes(Answer[0]+'\n');//Send 
            
            int repeat =Integer.parseInt(Answer[1]);
          //Logic to send SOCKET PROGRAMMING 
           if(repeat<0){ 
               repeat=0-repeat;
           }
           while(repeat!=0){
               outToClient.writeBytes("SOCKET PROGRAMMING"+'\n');
               repeat--;
           }
           outToClient.writeBytes("STOP"+'\n');
       }catch(Exception e){
                System.out.println("ERROR");
       }
        }
    }
          public static String[] run(String Equation){
             
              String[] Answer= new String[2];
              char x=getoperator(Equation); // GET OPERATOR 
              boolean temp=validate(getoperator(Equation), Equation);//VALIDATE EQUATION
            if(temp==true){
                String[] S = expression(Equation,x);
                int result= calculate(S,x);
                Answer[0] = "Solution"+":"+result + '\n';
                Answer[1] = result+"";
                       }else{

            Answer[0] = "Error in equation"+ '\n';

            Answer[1] = "0";  
             }   
            return Answer;
          }
          public static char getoperator(String S){ //GET MATHEMATICAL OPERATOR

              char temp;
              if(S.contains("+")){
                  temp='+';
              }else{
                   if(S.contains("-")){
                       temp='-';
                   }else{
                       if(S.contains("*")){
                           temp='*';
                       }else{
                           if(S.contains("/")){
                               temp='/';
                           }else{
                               temp='x';
                           }
                       }
                   }
              }



              return temp;
          }
          public static boolean validate(char a,String S){ //VALIDATE THE EQUATION FOR EG. 2++ IS INCORRECT

           boolean temp=true;
            if(a=='x'){
                temp=false;
            }else{
                if(S.charAt(S.indexOf(a)+1)==a||S.charAt(S.indexOf(a)-1)==a){
                    temp=false;
                }else
                {
                    temp=true;
                }
            }
              return temp;   
          }
          public static String[] expression(String S,char x){ //SPLIT FROM OPERATOR TO GET OPERAND
              String[] A=new String[3];
              if(x=='-'){
            A = S.trim().split(x+"");}
              else{
                  A=S.trim().split(Pattern.quote(x+""));
              }



              return A;
          }
          public static int calculate(String[] S,char s){  //CALCULATE MATHEMATICAL OPERATION
              int temp=-1;
             int a = Integer.parseInt(S[0]);
             int b = Integer.parseInt(S[1]);
             switch (s){
                 case '+':{
                      temp=a+b;
                     break;
                 }
                 case '-':{
                     temp=a-b;
                     break;
                 }
                 case '*':{
                     temp=a*b;
                     break;
                 }
                 case '/':{
                     if(b!=0)
                     temp=a/b;
                     else 
                         temp=999999; //set to infinite value for divide by zero error
                     break; 
                 }
             }


              return temp;
          }
    }


