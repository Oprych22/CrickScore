package cricket.scorer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.StrictMode;
import android.util.Log;

public class JSONSend {

	
	private static final String TAG = "JSONSEND";
	private static JSONSend jsonsend = null;
	
	private JSONSend()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	}
	
	public static JSONSend getInstance()
	{
		if(jsonsend == null)
		{
			jsonsend = new JSONSend();
		}
		return jsonsend;

	}
	
	@SuppressWarnings("unchecked")
	public boolean sendJSONToServer(final JSONObject jsonarray) throws Exception 
	{
		
		 try {
			    String path = "http://92.232.46.97/CrickScore/restutils.php";
	            HttpClient httpclient= new DefaultHttpClient();
	            HttpResponse response;
	            HttpPost httppost= new HttpPost (path);
	            StringEntity se=new StringEntity (jsonarray.toString());
	            httppost.setEntity(se);
	            System.out.print(se);
	            httppost.setHeader("Accept", "application/json");
	            httppost.setHeader("Content-type", "application/json");

	            response=httpclient.execute(httppost);
	            Log.e(TAG, response.toString());
	            Log.e(TAG, String.valueOf(response.getStatusLine().getStatusCode()));

	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            Log.e(TAG, "Cannot establish Connection");
	            return false;
	        }
		
		
		return true;
	}
}

