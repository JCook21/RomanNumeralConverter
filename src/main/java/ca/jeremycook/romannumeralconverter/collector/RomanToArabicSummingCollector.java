package ca.jeremycook.romannumeralconverter.collector;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Collector class for correctly combining values when converting from a Roman Numeral back to an integer.
 * This takes a Stream of a reversed roman numeral, cast to its Arabic number representation.
 * This Stream is then cast to a single integer.
 */
public class RomanToArabicSummingCollector implements Collector<Integer, LinkedList<Integer>, Integer> {

    /**
     * A function that creates and returns a new mutable result container.
     *
     * @return a function which returns a new, mutable result container
     */
    @Override
    public Supplier<LinkedList<Integer>> supplier() {
        return LinkedList::new;
    }

    /**
     * A function that folds a value into a mutable result container.
     * As the roman numeral has been reversed we can compare the current value with the previous on in the list.
     * If the previous value is greater than the current value add -value to the list to be summed.
     * For example, IX will be mapped to XI, then 10 and 1.
     * To get 9 from this we need to add -1 to 10.
     * This algorithm allows us to handle compound roman numerals where the initial numeral is less than the second.
     *
     * @return a function which folds a value into a mutable result container
     */
    @Override
    public BiConsumer<LinkedList<Integer>, Integer> accumulator() {
        return (acc, value) -> {
            Integer valueToAdd = value;
            if (acc.size() > 0) {
                Integer previous = acc.getLast();
                valueToAdd = previous <= value ? value : -value;
            }
            acc.add(valueToAdd);
        };
    }

    /**
     * A function that accepts two partial results and merges them.  The
     * combiner function may fold state from one argument into the other and
     * return that, or may return a new result container.
     *
     * @return a function which combines two partial results into a combined
     * result
     */
    @Override
    public BinaryOperator<LinkedList<Integer>> combiner() {
        return (list1, list2) -> {
            throw new RuntimeException("Parallel streams not supported with this collector");
        };
    }

    /**
     * Perform the final transformation from the intermediate accumulation type
     * {@code A} to the final result type {@code R}.
     * <p>
     * <p>If the characteristic {@code IDENTITY_TRANSFORM} is
     * set, this function may be presumed to be an identity transform with an
     * unchecked cast from {@code A} to {@code R}.
     *
     * @return a function which transforms the intermediate result to the final
     * result
     */
    @Override
    public Function<LinkedList<Integer>, Integer> finisher() {
        return (acc) -> acc.stream()
                .mapToInt(i -> i)
                .sum();
    }

    /**
     * Returns a {@code Set} of {@code Collector.Characteristics} indicating
     * the characteristics of this Collector.  This set should be immutable.
     *
     * @return an immutable set of collector characteristics
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
