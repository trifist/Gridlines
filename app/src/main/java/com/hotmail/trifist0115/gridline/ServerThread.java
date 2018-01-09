package com.hotmail.trifist0115.gridline;

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
    private static int time = 0;
    private static boolean canJump = false;

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
            if(canJump()) {
                setCanJump(false);
                return "" + getTime();
            }
            return "error: cannot jump now";
        } else {
            return "error: invalid input";
        }
    }

    public synchronized static int getTime() {
        return time;
    }

    public synchronized static void setTime(int time) {
        ServerThread.time = time;
    }

    public synchronized static boolean canJump() {
        return canJump;
    }

    public synchronized static void setCanJump(boolean canJump) {
        ServerThread.canJump = canJump;
    }
}
