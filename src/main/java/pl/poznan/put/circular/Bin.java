package pl.poznan.put.circular;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface Bin {
  @Value.Parameter
  double radiansStart();

  @Value.Parameter
  List<Circular> data();
}