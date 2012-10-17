package fr.escape.app;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

final class Batch {

	private final Queue<Render> batch = new LinkedList<Render>();

	public boolean push(Render render) {
		Objects.requireNonNull(batch);
		return batch.offer(render);
	}

	public boolean isEmpty() {
		return batch.isEmpty();
	}

	public Render pop() {
		return batch.poll();
	}

}

