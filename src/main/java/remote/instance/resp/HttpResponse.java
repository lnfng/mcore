package remote.instance.resp;

import remote.invoker.Uresponse;

public class HttpResponse extends Uresponse {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object dealRawData() {
		return this.getRawData();
	}

	@Override
	public boolean isSucc() {
		return false;
	}
	
}