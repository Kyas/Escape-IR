package fr.escape.graphics;

import java.util.LinkedList;
import java.util.Queue;

import fr.escape.graphics.render.GraphicsStringRender;

public class GraphicsPoolRender {
	
	private final static int CORE_GTEXTURE_OP = 8;
	private final static int CORE_GSTRING = 16;
	private final static int CORE_GTEXTURE = 64;
	
	private final Queue<GraphicsStringRender> poolGStringRender;
	
	private GraphicsPoolRender() {
		
		poolGStringRender = new LinkedList<GraphicsStringRender>();
		
		init();
	}
	
	private void init() {
		for(int i = 0; i < CORE_GSTRING; i++) {
			poolGStringRender.offer(new GraphicsStringRender());
		}
	}
	
	public GraphicsStringRender fetchGraphicsStringRender() {
		
		GraphicsStringRender render = poolGStringRender.poll();
		
		if(render == null) {
			render = new GraphicsStringRender();
		}
		
		return render;
	}

	public static GraphicsPoolRender getInstance() {
		return GraphicsPoolRenderHolder.INSTANCE;
	}

	private static class GraphicsPoolRenderHolder {
		public static final GraphicsPoolRender INSTANCE = new GraphicsPoolRender();
	}
}
