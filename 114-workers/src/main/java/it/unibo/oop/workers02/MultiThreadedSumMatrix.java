package it.unibo.oop.workers02;

import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import it.unibo.oop.workers01.MultiThreadedListSumWithStreams;

public class MultiThreadedSumMatrix implements SumMatrix {

    private final int nthread;

    /**
     * @param nthread no. of thread performing the sum.
     */
    public MultiThreadedSumMatrix(final int nthread) {
        this.nthread = nthread;
    }

    private static class Worker extends Thread {
        private final double[][] matrix;
        private final int rowLength;
        private final int startPos;
        private final int nelem;
        private double res;

        Worker(
            final double[][] matrix, 
            final int rowLength,
            final int startPos,
            final int nelem
        ) {
            super();
            this.matrix = matrix;
            this.rowLength = rowLength;
            this.startPos = startPos;
            this.nelem = nelem;
        }

        @Override
        @SuppressWarnings("PMD.SystemPrintln")
        public void run() {
            final var endPos = this.startPos + this.nelem - 1;
            final var startRow = this.startPos / this.rowLength;
            final var endRow = endPos / this.rowLength;
            
            for (int i = startRow; i <= endRow && i < this.matrix.length; i++) {
                final var startCol = i == startRow ? this.startPos % this.rowLength : 0;
                final var endCol = i == endRow ? endPos % this.rowLength : this.rowLength - 1;
                for (int j = startCol; j <= endCol && j < this.rowLength; j++) {
                    this.res += this.matrix[i][j];
                }
            }
        }

        /**
         * Returns the result of summing up the integers within the list.
         * 
         * @return the sum of every element in the array
         */
        public double getResult() {
            return this.res;
        }

    }

    @Override
    public double sum(double[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0.0;
        }
        
        final int rowLength = matrix[0].length;
        final int totalMatrixSize = matrix.length * rowLength;
        final int size = totalMatrixSize % nthread + totalMatrixSize / nthread;
        /*
         * Build a stream of workers
         */
        return IntStream
                .iterate(0, start -> start + size)
                .limit(nthread)
                .mapToObj(start -> new Worker(
                    matrix, 
                    rowLength, 
                    start, 
                    size
                ))
                // Start them
                .peek(Thread::start)
                // Join them
                .peek(MultiThreadedSumMatrix::joinUninterruptibly)
                // Get their result and sum
                .mapToDouble(Worker::getResult)
                .sum();
    }

    @SuppressWarnings("PMD.AvoidPrintStackTrace")
    private static void joinUninterruptibly(final Thread target) {
        var joined = false;
        while (!joined) {
            try {
                target.join();
                joined = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
