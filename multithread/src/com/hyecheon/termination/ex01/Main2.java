package com.hyecheon.termination.ex01;

import java.math.BigInteger;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/05
 */
public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread(new LongComputationTask(new BigInteger("2"), new BigInteger("100000000")));
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + "=" + pow(base, power));

        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            var result = BigInteger.ONE;

            for (var i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }

}
