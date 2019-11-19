package se.gewalli.kyminon;
import java.util.function.Consumer;
import java.util.function.Function;
/**
 * Represents either an Ok value or an Error value. 
 * @param <T>
 * @param <TError>
 */
public abstract class Result<T, TError> {

    private static class Ok<T, TError> extends Result<T, TError> {
        private final T value;

        public Ok(final T ok) {
            this.value = ok;
        }

        @Override
        public void match(final Consumer<T> onOk, final Consumer<TError> onError) {
            onOk.accept(value);
        }

        @Override
        public <TResult> TResult fold(final Function<T, TResult> onOk, final Function<TError, TResult> onError) {
            return onOk.apply(value);
        }

        @Override
        public <T1, T2> Result<T1, T2> bimap(final Function<T, T1> onOk, final Function<TError, T2> onError) {
            return new Ok<>(onOk.apply(value));
        }

        @Override
        public String toString() {
            return String.format("Ok{%s}", value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(final Object obj) {
            if (this.getClass().isInstance(obj)) {
                final Ok<T, TError> err = (Ok<T, TError>) obj;
                return value.equals(err.value);
            }
            return false;
        }

        @Override
        public <MappedOk> Result<MappedOk, TError> map(Function<T, MappedOk> onOk) {
            return new Ok<>(onOk.apply(value));
        }

    }

    private static class Error<T, TError> extends Result<T, TError> {
        private final TError value;

        Error(final TError error) {
            this.value = error;
        }

        @Override
        public void match(final Consumer<T> onOk, final Consumer<TError> onError) {
            onError.accept(value);
        }

        @Override
        public <TResult> TResult fold(final Function<T, TResult> onOk, final Function<TError, TResult> onError) {
            return onError.apply(value);
        }

        @Override
        public <T1, T2> Result<T1, T2> bimap(final Function<T, T1> onOk, final Function<TError, T2> onError) {
            return new Error<>(onError.apply(value));
        }

        @Override
        public String toString() {
            return String.format("error %s", value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(final Object obj) {
            if (this.getClass().isInstance(obj)) {
                final Error<T, TError> err = (Error<T, TError>) obj;
                return value.equals(err.value);
            }
            return false;
        }

        @Override
        public <MappedOk> Result<MappedOk, TError> map(Function<T, MappedOk> onOk) {
            return new Error<>(value);
        }
    }

    /**
     * Create a new Ok value of given type
     * @param <T>
     * @param <TError>
     * @param ok
     * @return
     */
    public static <T, TError> Result<T, TError> ok(final T ok) {
        return new Ok<>(ok);
    }

    /**
     * Create a new Error value of given type
     * 
     * @param <T>
     * @param <TError>
     * @param error
     * @return
     */
    public static <T, TError> Result<T, TError> error(final TError error) {
        return new Error<>(error);
    }
    /**
     * Match Ok value and consume result or match Error value and consume result
     * @param onOk consumer of Ok value
     * @param onError consumer of Error value
     */
    public abstract void match(Consumer<T> onOk, Consumer<TError> onError);
    /**
     * fold either an error or an ok value into the same type
     * @param <TResult> the resulting type
     * @param onOk mapper to apply when given an Ok value
     * @param onError mapper to apply when given an Error value
     * @return result after applying either map on Ok or Error value
     */
    public abstract <TResult> TResult fold(Function<T, TResult> onOk, Function<TError, TResult> onError);
    /**
     * @param <MappedOk>
     * @param <MappedError>
     * @param onOk
     * @param onError
     * @return
     */
    public abstract <MappedOk,MappedError> Result<MappedOk,MappedError> bimap(Function<T, MappedOk> onOk, Function<TError, MappedError> onError);
    /**
     * Map Ok value
     * @param <MappedOk>
     * @param onOk
     * @return
     */
    public abstract <MappedOk> Result<MappedOk,TError> map(Function<T, MappedOk> onOk);

}