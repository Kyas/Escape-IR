package fr.escape.graphics;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import fr.escape.graphics.render.GraphicsRender;

/**
 * 
 * No Thread-Safe
 */
public final class GraphicsBatchRender {

	private final Queue<GraphicsRender> batch;

	private GraphicsBatchRender() {
		batch = new LinkedList<GraphicsRender>();
	}
	
	public boolean push(GraphicsRender render) {
		Objects.requireNonNull(batch);
		return batch.offer(render);
	}

	public boolean isEmpty() {
		return batch.isEmpty();
	}

	public GraphicsRender pop() {
		return batch.poll();
	}
	
	public static GraphicsBatchRender getInstance() {
		return GraphicsBatchRenderHolder.INSTANCE;
	}
	
	private static class GraphicsBatchRenderHolder {
		public static final GraphicsBatchRender INSTANCE = new GraphicsBatchRender();
	}

}

