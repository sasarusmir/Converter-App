package com.originalit.converter.currencyconverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;

public class DownloaderTask extends AsyncTask<Void, Void, Void> {
	
	private Context context;
	
	public DownloaderTask(Context context) {
        this.context = context;
    }
	
	
	@Override
	protected Void doInBackground(Void... params) {
		getInternetdata();
		return null;		
	}
	/*
	public String getInternetDataString() throws Exception {

		BufferedReader in = null;
		String data = null;

		try {
			HttpClient client = new DefaultHttpClient();

			URI website = new URI(
					"http://api.kursna-lista.info/b5d70d2f59bba48555843914fa431c99/kursna_lista/json");
			HttpGet request = new HttpGet();
			request.setURI(website);
			HttpResponse response = client.execute(request);
			response.getStatusLine().getStatusCode();

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String l = "";
			String nl = System.getProperty("line.separator");
			while ((l = in.readLine()) != null) {
				sb.append(l + nl);
			}
			in.close();
			data = sb.toString();
			Log.i("Download: ", sb.toString());
			return data;
		} finally {
			if (in != null) {
				try {
					in.close();
					return data;
				} catch (Exception e) {
					Log.e("GetMethodEx", e.getMessage());
				}
			}

		}
	}
	*/
	private void getInternetdata(){
		String urlString = "http://api.kursna-lista.info/b5d70d2f59bba48555843914fa431c99/kursna_lista/json";
		InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        String filename = "currencyTable.json";
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return;
            }
            // download the file
            input = connection.getInputStream();
            output = context.openFileOutput(filename, Context.MODE_PRIVATE);
//            output = new BufferedOutputStream(new FileOutputStream(context.getFilesDir()+filename));

            byte data[] = new byte[4096];
//            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                }
//                total += count;
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
	}

}
