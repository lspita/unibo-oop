package it.unibo.arrays;

import java.util.Arrays;
import java.util.HashMap;

class WorkWithArrays {

    static int countOccurrencies(final int[] array, final int element) {
        int occurrencies = 0;
        for (final int currentElement : array) {
            if (currentElement == element) {
                occurrencies++;
            }
        }
        return occurrencies;
    }

    static int[] evenElements(final int[] array) {        
        final int nEvens = (int) Math.ceil((double) array.length / 2.0);
        final int[] result = new int[nEvens];

        for (int i = 0, j = 0; i < array.length; i++) {
            if (i % 2 == 0) {
                result[j] = array[i];
                j++;
            }
        }

        return result;
    }

    static int[] oddElements(final int[] array) {
        final int nOdd = (int) Math.floor((double) array.length / 2.0);
        final int[] result = new int[nOdd];

        for (int i = 0, j = 0; i < array.length; i++) {
            if (i % 2 != 0) {
                result[j] = array[i];
                j++;
            }
        }

        return result;
    }

    static int mostRecurringElement(final int[] array) {
        HashMap<Integer, Integer> occurrencies = new HashMap<Integer, Integer>(); 
        Integer maxVal = null;

        for (int x: array) {
            int currentOccurencies = occurrencies.getOrDefault(x, 0);
            occurrencies.put(x, currentOccurencies + 1);
        
            if (maxVal == null || occurrencies.get(x) > occurrencies.get(maxVal)) {
                maxVal = x;
            }
        }

        return maxVal;
    }

    static int[] sortArray(final int[] array, final boolean isDescending) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }

        if (result.length < 2) {
            return result;
        }

        boolean loop = true;
        
        while (loop) {
            loop = false;
            for (int i = 0; i < result.length - 1; i++) {
                if (
                    (!isDescending && result[i] > result[i + 1]) ||
                    (isDescending && result[i] < result[i + 1])
                ){
                    int swap = result[i];
                    result[i] = result[i + 1];
                    result[i + 1] = swap;

                    loop = true;
                }
            }
        }

        return result;
    }

    static double average(final int[] array) {
        double sum = 0.0;
        for (int x : array) {
            sum += x;
        }

        return sum / (double) array.length;
    }

    static double computeVariance(final int[] array) {
        final double avg = average(array);

        double sum = 0.0;
        for (int x : array) {
            sum += Math.pow((double)x - avg, 2);
        }

        return sum / (double)array.length;
    }

    static int[] revertUpTo(final int[] array, final int element) {
        final int[] result = new int[array.length];
        int targetIndex = 0;
        
        while (targetIndex < array.length && array[targetIndex] != element) {
            targetIndex++;
        }
        
        for (int i = targetIndex, j = 0; i >= 0; i--) {
            result[i] = array[j];
            j++;
        }

        for (int i = targetIndex + 1; i < array.length; i++) {
            result[i] = array[i];
        }

        return result;
    }

    static int[] duplicateElements(final int[] array, final int times) {
        final int[] returnValue = new int[array.length * times];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < times; j++) {
                returnValue[i * times + j] = array[i];
            }
        }
        return returnValue;
    }

    /** Testing methods **/

    /* Utility method for testing countOccurr method */
    static boolean testCountOccurrencies() {
        return countOccurrencies(new int[] { 1, 2, 3, 4 }, 1) == 1
            && countOccurrencies(new int[] { 0, 2, 3, 4 }, 1) == 0
            && countOccurrencies(new int[] { 7, 4, 1, 9, 3, 1, 5 }, 2) == 0
            && countOccurrencies(new int[] { 1, 2, 3, 3, 4, 5 }, 3) == 2
            && countOccurrencies(new int[] { 1, 2, 1, 3, 4, 1 }, 1) == 3;
    }

    /* Utility method for testing evenElems method */
    static boolean testEvenElements() {
        return Arrays.equals(evenElements(new int[] { 1, 2, 3, 4 }), new int[] { 1, 3 })
            && Arrays.equals(evenElements(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }), new int[] { 1, 3, 5, 7, 9 })
            && Arrays.equals(evenElements(new int[] { 4, 6, 7, 9, 1, 5, 23, 11, 73 }), new int[] { 4, 7, 1, 23, 73 })
            && Arrays.equals(
                evenElements(new int[] { 7, 5, 1, 24, 12, 46, 23, 11, 54, 81 }),
                new int[] { 7, 1, 12, 23, 54 }
        );
    }

    /* Utility method for testing oddElems method */
    static boolean testOddElements() {
        return Arrays.equals(oddElements(new int[] { 1, 2, 3, 4 }), new int[] { 2, 4 })
            && Arrays.equals(oddElements(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }), new int[] { 2, 4, 6, 8 })
            && Arrays.equals(oddElements(new int[] { 4, 6, 7, 9, 1, 5, 23, 11, 73 }), new int[] { 6, 9, 5, 11 })
            && Arrays.equals(
                oddElements(new int[] { 7, 5, 1, 24, 12, 46, 23, 11, 54, 81 }),
                new int[] { 5, 24, 46, 11, 81 }
            );
    }

    /* Utility method for testing getMostRecurringElem method */
    static boolean testMostRecurringElement() {
        return mostRecurringElement(new int[] { 1, 2, 1, 3, 4 }) == 1
            && mostRecurringElement(new int[] { 7, 1, 5, 7, 7, 9 }) == 7
            && mostRecurringElement(new int[] { 1, 2, 3, 1, 2, 3, 3 }) == 3
            && mostRecurringElement(new int[] { 5, 11, 2, 11, 7, 11 }) == 11;
    }

    /* Utility method for testing sortArray method */
    static boolean testSortArray() {
        return Arrays.equals(sortArray(new int[] { 3, 2, 1 }, false), new int[] { 1, 2, 3 })
            && Arrays.equals(sortArray(new int[] { 1, 2, 3 }, true), new int[] { 3, 2, 1 })
            && Arrays.equals(
                sortArray(new int[] { 7, 4, 1, 5, 9, 3, 5, 6 }, false),
                new int[] { 1, 3, 4, 5, 5, 6, 7, 9 }
            );
    }

    /* Utility method for testing computeVariance method */
    static boolean testComputeVariance() {
        return computeVariance(new int[] { 1, 2, 3, 4 }) == 1.25
            && computeVariance(new int[] { 1, 1, 1, 1 }) == 0
            && computeVariance(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }) == 8.25;
    }

    /* Utility method for testing revertUpTo method */
    static boolean testRevertUpTo() {
        return
            Arrays.equals(
                revertUpTo(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 5),
                new int[] { 5, 4, 3, 2, 1, 6, 7, 8, 9, 10 }
            )
            && Arrays.equals(
                revertUpTo(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 3),
                new int[] { 3, 2, 1, 4, 5, 6, 7, 8, 9, 10 }
            )
            && Arrays.equals(
                revertUpTo(new int[] { 1, 2, 3 }, 3),
                new int[] { 3, 2, 1 }
            );
    }

    /* Utility method for testing dupElems method */
    static boolean testDuplicateElements() {
        return Arrays.equals(duplicateElements(new int[] { 1, 2, 3 }, 2), new int[] { 1, 1, 2, 2, 3, 3 })
            && Arrays.equals(duplicateElements(new int[] { 1, 2 }, 5), new int[] { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2 });
    }

    public static void main(final String[] args) {
        System.out.println("testCountOccurr: " + testCountOccurrencies());
        System.out.println("testEvenElems: " + testEvenElements());
        System.out.println("testOddElems: " + testOddElements());
        System.out.println("testGetMostRecurringElem: " + testMostRecurringElement());
        System.out.println("testSortArray: " + testSortArray());
        System.out.println("testComputeVariance: " + testComputeVariance());
        System.out.println("testRevertUpTo: " + testRevertUpTo());
        System.out.println("testDupElems: " + testDuplicateElements());
    }
}
