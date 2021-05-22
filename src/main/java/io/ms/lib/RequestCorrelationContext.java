package io.ms.lib;

public class RequestCorrelationContext {
	
	private static final ThreadLocal<RequestCorrelationContext> CONTEXT = 
			new ThreadLocal<RequestCorrelationContext>();
	
	private String correlationId;
	
	protected RequestCorrelationContext()  {}
	
	public static RequestCorrelationContext getCurrent() {
		RequestCorrelationContext context = CONTEXT.get();
		if (context == null) {
			context = new RequestCorrelationContext();
			CONTEXT.set(context);
		}
		
		return context;
	}
	
	public static void clearCurrent() {
		CONTEXT.remove();
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

}