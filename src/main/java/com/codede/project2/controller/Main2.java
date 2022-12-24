package com.codede.project2.controller;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int value = sc.nextInt();

        long startTime = System.currentTimeMillis();

        int count = 0;

        for (int i = 9; i <= value; i++) {
            int x = i;
            while (x > 8) {
                if (x % 10 == 9) {
                    count++;
                }
                x = x / 10;
            }
        }
        System.out.println("so lan xuat hien " + count);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime)+ " mls");
    }
}
