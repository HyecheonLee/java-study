package com.hyecheon.termination.ex02;

import java.math.BigInteger;
import java.util.List;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/05
 */
public class ComplexCalculation {


    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        var result = BigInteger.ZERO;
        final var calculatingThread1 = new PowerCalculatingThread(base1, power1);
        final var calculatingThread2 = new PowerCalculatingThread(base2, power2);
        final var threads = List.of(calculatingThread1, calculatingThread2);
        for (PowerCalculatingThread thread : threads) {
            thread.start();
        }
        for (PowerCalculatingThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (PowerCalculatingThread thread : threads) {
            result = result.add(thread.getResult());
        }
        return result;
    }


    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            for (var i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }


        public BigInteger getResult() {
            return result;
        }
    }
}
