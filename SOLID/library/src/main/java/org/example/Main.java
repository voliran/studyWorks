package org.example;

import org.example.dao.*;
import org.example.db.DatabaseUtil;
import org.example.ui.LibraryCLI;

public class Main {
    public static void main(String[] args) {
        DatabaseUtil.initDatabase();
        LibraryCLI libraryCLI = new LibraryCLI();
        libraryCLI.start();
    }
}