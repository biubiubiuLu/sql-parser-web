package com.lk.sql.parser.type.inference;

/**
 * @author lukai
 * @creare 2021-12-31 14:55
 */
import java.util.Optional;


public interface ArgumentCount {

    /**
     * Enables custom validation of argument counts after {@link #getMinCount()} and {@link
     * #getMaxCount()} have been validated.
     *
     * @param count total number of arguments including each argument for a vararg function call
     */
    boolean isValidCount(int count);

    /**
     * Returns the minimum number of argument (inclusive) that a function can take.
     *
     * <p>{@link Optional#empty()} if such a lower bound is not defined.
     */
    Optional<Integer> getMinCount();

    /**
     * Returns the maximum number of argument (inclusive) that a function can take.
     *
     * <p>{@link Optional#empty()} if such an upper bound is not defined.
     */
    Optional<Integer> getMaxCount();
}
