package functions.util;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Turns consumers into pass-throughs
 */
public class Visitor<T>
{
  private T value;

  private Visitor() {}

  private Visitor<T> withValue(final T value)
  {
    this.value = value;
    return this;
  }

  public static <T> Visitor<T> of(final T value)
  {
    final Visitor<T> visitor = new Visitor<>();
    return visitor.withValue(value);
  }

  public <U> Visitor<T> visit(final BiConsumer<? super T, ? super U> consumer,
                              final U value)
  {
    consumer.accept(this.value, value);
    return this;
  }

  public T get()
  {
    return value;
  }

  public static <T> T visit(final Consumer<? super T> consumer,
      final T visited)
  {
    consumer.accept(visited);
    return visited;
  }

  public static <T, U> T visit(final BiConsumer<? super T, ? super U> consumer,
                               final T visited,
                               final U value)
  {
    consumer.accept(visited, value);
    return visited;
  }

  @SuppressWarnings("UnusedReturnValue")
  @SafeVarargs
  public static <T> T visit(final T visited,
                            final Consumer<T>... consumers)
  {
    Arrays.asList(consumers).forEach(consumer -> consumer.accept(visited));
    return visited;
  }
}
