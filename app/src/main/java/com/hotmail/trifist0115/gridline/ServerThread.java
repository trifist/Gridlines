package com.hotmail.trifist0115.gridline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Tristan (wpcheng@iflytek.com) on 2018/1/9.
 */

public class ServerThread extends Thread {
    private static final int PORT = 2345;
    private ServerSocket serverSocket;
    private boolean isRunning = true;

    public ServerThread() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    @Override
    public void run() {
        while(isRunning) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                DataInputStream is = new DataInputStream(socket.getInputStream());
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                String response = getResponse(is.readUTF());
                os.writeUTF(response);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exit() {
        isRunning = false;
    }

    private String getResponse(String input) {
        if("hello".equals(input)) {
            return "hello";
        } else if("get_time".equals(input)) {
            return "1000";
        } else {
            return "error: invalid input";
        }
    }
}
