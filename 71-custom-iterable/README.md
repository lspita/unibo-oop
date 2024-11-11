# INSTRUCTIONS

**Ensure that all parts of the exercise are correct, not just the end of them.**

## Part 1: iterable without policy

1. Implement the `IterableWithPolicy<T>` interface via a generic class with a constructor that takes an array of `T` elements.
2. Leave the `setIterationPolicy` method empty (for now).
3. Implement an inner class that implements the `Iterator<T>` interface so that it iterates over the elements of the array given to the constructor.
4. Implement the `iterator()` method so that it returns an instance of the inner class.
5. Test the implementation by running the `TestIterablePlain` class.

## Part 2: iterable with policy

1. Add a new constructor to the newly created class that takes two arguments: an array of `T` elements and a `Predicate<T>` that will be used to filter the elements during the iteration.
2. Modify the 1-ary constructor so that it calls the 2-ary constructor with a `Predicate<T>` that always returns `true`.
3. Implement the `setIterationPolicy` method so that it sets the `Predicate<T>` that will be used to filter the elements during the iteration.
4. Modify the implementation of the inner class (the `Iterator<T>` implementation) so that it uses the `Predicate<T>` to filter the elements during the iteration.
5. Test the implementation by running the `TestIterableWithPolicy` class.
