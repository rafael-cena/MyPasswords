package com.example.mypasswords;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    public static List<MyPassword> passwordList = new ArrayList<>();

    private Singleton() {
    }
}
