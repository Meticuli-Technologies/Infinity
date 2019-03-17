package com.meti.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/17/2019
 */
public class ClientMain {
    public static void main(String[] args) {
        ClientMain main = new ClientMain();
        main.init();
        main.start();
    }

    private void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Connect to a Server");

        InetAddress address = getAddress(scanner);
    }

    private InetAddress getAddress(Scanner scanner) {
        InetAddress address;
        while (true) {
            System.out.print("Enter in an address");
            String addressToken = scanner.next();
            try {
                address = InetAddress.getByName(addressToken);
                break;
            } catch (UnknownHostException e) {
                System.out.println("Invalid address: " + addressToken);
            }
        }
        return address;
    }

    private void start() {

    }
}
