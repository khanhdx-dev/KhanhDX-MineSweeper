package com.khanhdx.app;

import com.khanhdx.app.services.MineSweeper;
import com.khanhdx.app.services.impl.MineSweeperImpl;

public class Main {

    private static MineSweeper mineSweeper;

    public static void main(String[] args) {
        mineSweeper = new MineSweeperImpl();
        mineSweeper.start();
    }
}
