package com.future.getfood;
/*this is launching activity*/
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.future.foodimg.DetectNetwork;
import com.future.getfood.R;
import com.future.listscroll.IInfiniteScrollListener;
import com.future.listscroll.InfiniteScrollListView;
import com.future.listscroll.InfiniteScrollOnScrollListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class DishesActivity extends ActionBarActivity implements
		IInfiniteScrollListener {

	private static final String LOG_TAG = DishesActivity.class.getSimpleName();
	protected InfiniteScrollListView listView;
	private MyAdapter adapter;
	private InfiniteScrollOnScrollListener scrollListener;
	private ListTask listTask;
	private boolean executing = false;
	ArrayList<String> items;
	HttpResponse response;
	String s;
	EditText search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		listView = (InfiniteScrollListView) findViewById(R.id.list_view);
		search = (EditText) findViewById(R.id.editText1);
		scrollListener = new InfiniteScrollOnScrollListener(this);
		listView.setListener(scrollListener);
		adapter = new MyAdapter(this);
		listView.setAdapter(adapter);
		getdetail();
		// Populate initial list
		items = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			String str = "Index: " + String.valueOf(i);
			items.add(str);
		}
		listView.appendItems(items);

	}

	@Override
	public void endIsNear() {
		if (!executing) {

			// Toast.makeText(this, "End is near", Toast.LENGTH_SHORT).show();
			executing = true;
			listTask = new ListTask();
			listTask.execute(listView.getRealCount());
		}
	}

	// Item visibility code
	@Override
	public void onScrollCalled(int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {

	}

	private class ListTask extends AsyncTask<Integer, Void, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(Integer... params) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ArrayList<String> items = new ArrayList<String>();
			if (params[0] < 40) {
				for (int i = params[0]; i < (params[0] + 8); i++) {
					String str = "Index: " + String.valueOf(i);
					items.add(str);
				}
			}
			return items;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			listView.appendItems(result);
			executing = false;

			if (result.size() > 0) {
				Toast.makeText(getApplicationContext(),
						"Loaded " + String.valueOf(result.size()) + " items",
						Toast.LENGTH_SHORT).show();
			} else {

				Toast.makeText(getApplicationContext(),
						"No more items to load", Toast.LENGTH_SHORT).show();
			}
		}
	}

	// method for sending user detail on server
	protected void getdetail() {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(DishesActivity.this);

			@Override
			protected void onPreExecute() {
				// what to do before background task
				dialog.setMessage("Validating... ");
				dialog.setIndeterminate(true);
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {

				// do your background operation here
				try {
					String url = getResources().getString(R.string.url)
							+ "api/app/appFirstStart.json";

					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyyMMdd_HHmmss");
					String currentDateandTime = sdf.format(new Date());

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

					// entity.addPart("data[User][username]", new
					// StringBody(username));
					// entity.addPart("data[User][email]", new
					// StringBody(emailid));
					// entity.addPart("data[User][mobile_number]", new
					// StringBody(mobile));
					// entity.addPart("data[User][photo]", new
					// ByteArrayBody(byteuserimg,
					// currentDateandTime + ".png"));
					//
					//
					//
					// entity.addPart("data[User][password]", new StringBody(
					// passowrd));
					//
					// entity.addPart("data[User][id_proof]", new
					// ByteArrayBody(ba,
					// currentDateandTime + ".png"));
					//
					// entity.addPart("data[User][address]", new
					// StringBody(address1));
					// entity.addPart("data[User][city]", new
					// StringBody(city1));
					// entity.addPart("data[User][state]", new
					// StringBody(state1));
					// entity.addPart("data[User][country]", new
					// StringBody(country1));
					// entity.addPart("data[User][type]", new
					// StringBody(chk_value));
					//
					// entity.addPart("data[User][device_token]", new
					// StringBody("hhjjs65667676"));
					//
					// entity.addPart("data[User][latitude]", new
					// StringBody("345667"));
					// entity.addPart("data[User][longitude]", new
					// StringBody("7645434"));

					httppost.setEntity(entity);

					response = httpclient.execute(httppost);

					s = EntityUtils.toString(response.getEntity());
					Log.e("fhgfhj", s);

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// what to do when background task is completed
				//
				// try{
				//
				// JSONObject obj=new JSONObject(s);
				// JSONObject obj1=obj.getJSONObject("data");
				// String error=obj1.getString("error");
				// String msg=obj1.getString("msg");
				// //alert(msg);
				// }catch(Exception e){
				//
				// e.printStackTrace();
				// }
				dialog.cancel();
			}

		};
		if ((DetectNetwork.hasConnection(getApplicationContext())))
			updateTask.execute((Void[]) null);

	}

}
