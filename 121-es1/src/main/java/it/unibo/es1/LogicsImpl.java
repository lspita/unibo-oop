package it.unibo.es1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics {

	private class Counter {

		private int value;
		private final int maxValue;

		public Counter(final int maxValue) {
			this.maxValue = maxValue;
		}
	
		public int getValue() {
			return this.value;
		}
	
		public void increment() {
			if (!this.isEnabled()) {
				return;
			}

			this.value++;
		}

		public boolean isEnabled() {
			return this.value < this.maxValue; 
		}
		
	}

	private final List<Counter> counters;
	private final int maxValue;

	public LogicsImpl(int size) {
		this.maxValue = size;
		this.counters = IntStream.range(0, size)
							.mapToObj(i -> new Counter(this.maxValue))
							.collect(Collectors.toList());
	}

	@Override
	public int size() {
		return this.counters.size();
	}

	@Override
	public List<Integer> values() {
		return this.counters.stream()
			.map(Counter::getValue)
			.collect(Collectors.toList());
	}

	@Override
	public List<Boolean> enablings() {
		return this.counters.stream()
					.map(Counter::isEnabled)
					.collect(Collectors.toList());
	}

	@Override
	public int hit(int elem) {
		final var counter = this.counters.get(elem);
		counter.increment();
		return counter.getValue();
	}

	@Override
	public String result() {
		return this.counters.stream()
					.mapToInt(Counter::getValue)
					.mapToObj(Integer::toString)
					.collect(Collectors.joining("|"));
	}

	@Override
	public boolean toQuit() {
		if (this.counters.isEmpty()) {
			return true;
		}
		final var match = Integer.valueOf(this.counters.get(0).getValue());
		return this.counters.stream()
				.mapToInt(Counter::getValue)
				.allMatch(match::equals);
	}
}
