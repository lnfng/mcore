package remote;

import remote.instance.req.HttpRequest;
import remote.instance.resp.HttpResponse;
import remote.invoker.common.RequestConfig;
import remote.invoker.http.HttpInvoker;

public class PlainTest {

    public static void main(String[] args) throws Exception {
        String url = "http://localhost/shop/admin/appAction/action!runTest.do?ajax=yes";
        String reqJson = "Nothing";
        HttpRequest req = new HttpRequest();
		req.setConfig(new RequestConfig(url));
		req.setReqStr(reqJson);

		HttpInvoker httpInvoker = HttpInvoker.getInstance();
		HttpResponse resp = httpInvoker.execute(req, HttpResponse.class);

		System.out.println(req.generateReqStr());
		System.out.println(resp.getRawData());
    }

}
