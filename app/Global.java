

import play.Application;
import play.GlobalSettings;
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.lang.reflect.Method;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Global extends GlobalSettings  {
	
	 @Override
	    public Action onRequest(Http.Request request, Method method)
	    {
	        return super.onRequest(request, method);
	    }

	    @Override
	    public <T> T getControllerInstance(Class<T> aClass) throws Exception
	    {
	        return super.getControllerInstance(aClass);
	    }

	    @Override
	    public F.Promise<Result> onBadRequest(Http.RequestHeader requestHeader, String s)
	    {
	        return F.Promise.pure((Result) Results.badRequest("What are trying to do? " + s));
	    }

	    @Override
	    public F.Promise<Result> onError(Http.RequestHeader requestHeader, Throwable throwable)
	    {
	        return F.Promise.pure((Result) Results.internalServerError("Oops! Something happened. " + throwable.getMessage()));
	    }

	    @Override
	    public F.Promise<Result> onHandlerNotFound(Http.RequestHeader requestHeader)
	    {
	        return super.onHandlerNotFound(requestHeader);
	    }
	    
		@Override
		public void onStart(Application application) {
			
			ObjectMapper om = new ObjectMapper();
			om.setVisibilityChecker(om.getSerializationConfig().getDefaultVisibilityChecker()
	                .withFieldVisibility(Visibility.ANY));
			
		    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Json.setObjectMapper(om);
			
			super.onStart(application);
		}
		
		@Override
		public void onStop(Application app) {
			super.onStop(app);
			
		}
}
