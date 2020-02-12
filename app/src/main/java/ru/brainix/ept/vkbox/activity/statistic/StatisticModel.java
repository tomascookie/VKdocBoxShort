package ru.brainix.ept.vkbox.activity.statistic;

import android.os.AsyncTask;
import android.util.Log;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StatisticModel {

	private String finalCount;


	public String getCount(){

		DocGetCount docGetCount = new DocGetCount();
		docGetCount.execute();

		String outCount = "0";
		try {
			outCount = docGetCount.get();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}




		return outCount + "/2000";
	}

	class DocGetCount extends AsyncTask<Void, Void, String> {



		@Override
		protected String doInBackground(Void... voids) {

			finalCount = "0";

			VKRequest request = new VKRequest("docs.get", VKParameters.from("type", "0"));

			request.executeSyncWithListener(new VKRequest.VKRequestListener() {

				@Override
				public void onComplete(VKResponse response) {

					try {


						JSONObject jsonObject = response.json.getJSONObject ("response");
						JSONArray items = jsonObject.getJSONArray ("items");
						int count = jsonObject.getInt("count");

						Log.i("MODEL!  count ", " "+count);

						finalCount = String.valueOf(count);




					} catch (JSONException e) {
						e.printStackTrace();
					}

				}


				@Override
				public void onError(VKError error) {
				}

				@Override
				public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
				}
			});

			return finalCount;

		}


	}



}
