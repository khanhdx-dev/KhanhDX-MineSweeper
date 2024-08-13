package com.khanhdx.app.models;

import java.util.Scanner;

public class ScannerWrapper {
    private Scanner scanner;

    public Scanner getScanner() {
        scanner = new Scanner(System.in);
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public ScannerWrapper() {
        scanner = new Scanner(System.in);
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public String next() {
        return scanner.next();
    }

    public Integer nextInt() {
        return scanner.nextInt();
    }

    public ScannerWrapper refresh() {
        return new ScannerWrapper();
    }
}
