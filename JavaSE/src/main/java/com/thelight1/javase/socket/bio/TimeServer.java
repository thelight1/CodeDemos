package com.thelight1.javase.socket.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TimeServer {

    public static void main(String[] args) throws IOException{
        int port = 8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start in port:" + port);
            Socket socket = null;
            while (true) {
                /**
                 * 阻塞IO：当没有客户端要连到server时，线程阻塞在accept()方法上
                 */
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           if (serverSocket != null) {
               System.out.println("The time server close");
               serverSocket.close();
               serverSocket = null;
           }
        }
    }

    static class TimeServerHandler implements Runnable {
        private Socket socket;

        public TimeServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader in = null;
            PrintWriter out = null;

            try {
                /**
                 * InputStream()和OutputStream()的read()、write()是也会发生阻塞
                 * read()方法
                 * 当socket的输入流进行读取操作的时候，会发生阻塞，直到下面三种情况
                 * 1）有数据可读
                 * 2）可用数据已经读取完毕
                 * 3）发生空指针或IO异常
                 *
                 * write()方法
                 * 当socket的输出流进行写入操作的时候，也会阻塞，直到要写入的数据写入完毕，或发生异常
                 */
                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                /**
                 * 注意下面 new PrintWriter第二个参数为true
                 */
                out = new PrintWriter(this.socket.getOutputStream(), true);
                String currentTime = null;
                String body = null;
                while (true) {
                    body = in.readLine();
                    if (body == null) {
                        break;
                    }
                    System.out.println("The time server receive order:" + body);
                    currentTime = "getCurrTime".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "invalid order!";
                    out.println(currentTime);
                }
            } catch (IOException e) {
                if (in != null) {
                    try {
                        in.close();
                        in = null;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (out != null) {
                    out.close();
                    out = null;
                }
                if (this.socket != null) {
                    try {
                        this.socket.close();
                        this.socket = null;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
