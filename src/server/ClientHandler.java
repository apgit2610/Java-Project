package server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

class ClientHandler extends Thread {
    private Socket client;
    private ArrayList<Socket> clients;

    public ClientHandler(Socket client, ArrayList<Socket> clients) {
        this.client = client;
        this.clients = clients;
    }

    public void run() {
        try {
            InputStream input = client.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                for (Socket s : clients) {
                    if (s != client) {
                        OutputStream output = s.getOutputStream();
                        output.write(buffer, 0, bytesRead);
                        output.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
