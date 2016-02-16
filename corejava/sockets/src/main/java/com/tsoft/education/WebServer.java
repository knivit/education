package com.tsoft.education;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * To test the Web Server, use nc:
 *
 * for ((i=0 ; i<10; i++ )) ; do
 *   echo $i | nc localhost 8080 &
 * done
 *
 */
public class WebServer {
    public static void main(String[] args) {
        WebServer ws = new WebServer();
        ws.execute();
    }

    private static final int PORT = 8080;

    private static final int THREADS_NUM = 10;

    private final ExecutorService es = Executors.newFixedThreadPool(THREADS_NUM);

    public void execute() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(String.format("WebServer started on port %d", PORT));
        while (true) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (SocketException se) {
                // Server's socket was closed by a thread
                // Swallow the message if this is a shutdown
                if (es.isShutdown()) {
                    break;
                }
                se.printStackTrace();
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            Runnable wst = new WorkingThread(es, serverSocket, clientSocket);
            es.execute(wst);
        }

        System.out.println("WebServer stopped");
    }

    class WorkingThread implements Runnable {
        private final ServerSocket serverSocket;
        private final Socket clientSocket;
        private final ExecutorService es;

        public WorkingThread(ExecutorService es, ServerSocket serverSocket, Socket clientSocket) {
            this.es = es;
            this.serverSocket = serverSocket;
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            log("Got a request");

            InputStream is;
            try {
                is = clientSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            InputStreamReader isr;
            try {
                isr = new InputStreamReader(is, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }

            BufferedReader br = new BufferedReader(isr);
            String answer;
            try {
                String line = br.readLine();
                log(line);
                answer = processInput(line);
            } catch (IOException e) {
                e.printStackTrace();
                answer = "Can't read from the socket\n";
            }

            // if we do write to socket's OutputStream then
            // we can't read from socket's InputStream anymore
            // (java.net.SocketException: Socket closed)
            // Sso collect the messages and print them after
            // all the socket's input will be processed
            log(answer);
            buildOutput(answer);
        }

        private String processInput(String command) {
            if ("shutdown".equalsIgnoreCase(command)) {
                es.shutdown();
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Got an error, trying to shut down the WebServer\n";
                }
                return "Shutting the WebServer down ...\n";
            }

            int sleepTime;
            try {
                sleepTime = Integer.parseInt(command);
            } catch (NumberFormatException nfe) {
                return "The sleep time must be an integer\n";
            }

            try {
                Thread.currentThread().sleep(sleepTime * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "Something interrupted the thread during its sleep\n";
            }

            return String.format("Has slept %d sec\n", sleepTime);
        }

        private void buildOutput(String text) {
            OutputStream os;
            try {
                os = clientSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            OutputStreamWriter osw;
            try {
                osw = new OutputStreamWriter(os, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }

            PrintWriter pw = new PrintWriter(osw);
            pw.print(text);
            pw.close();
        }

        private void log(String message) {
            System.out.println(String.format("Client %s> %s", clientSocket.toString(), message));
        }
    }
}