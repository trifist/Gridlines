package com.hotmail.trifist0115.gridline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Tristan (wpcheng@iflytek.com) on 2018/1/9.
 */

public class ServerThread extends Thread {
    private static final int PORT = 2345;
    private ServerSocket serverSocket;
    private Socket socket;
    private boolean isRunning = true;

    public ServerThread() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    @Override
    public void run() {
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(isRunning) {
            try {
                if(socket == null) {
                    break;
                }
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                byte [] buffer = new byte[1024];
                int len = is.read(buffer, 0, 1024);
                String response = getResponse(new String(buffer).substring(0, len));
                os.write(response.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
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
