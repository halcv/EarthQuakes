package org.h_naka.earthquakes;

import java.io.InputStream;
import java.lang.IllegalArgumentException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.IllegalStateException;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class DataDownloadAsyncTask extends
		AsyncTask<String, Void, String> {

    private MainActivity m_activity;
    private OnDataDownloadListener m_listener = null;
    private ProgressDialog m_dialog;

    public DataDownloadAsyncTask(MainActivity activity) {
        m_activity = activity;
    }
    
    @Override
    protected void onPreExecute() {
        m_dialog = new ProgressDialog(m_activity);
        m_dialog.setIndeterminate(true);
        m_dialog.setCancelable(false);
        m_dialog.setMessage(m_activity.getResources().getString(R.string.getQuakeInfo));
        m_dialog.show();
    }

	@Override
	protected String doInBackground(String... param) {
        return getDataFromUrl(param[0]);
	}
    
    @Override
    protected void onPostExecute(String data) {
        m_listener.onFinishProcess(data);
        m_dialog.dismiss();
    }

    public void setOnDataDownloadListener(OnDataDownloadListener listener) {
        m_listener = listener;
    }

    private String getDataFromUrl(String url) {
        InputStream is = null;
        String result = null;
        boolean isSuccess = true;
        
        // http get
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch(IllegalStateException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch(Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        if (isSuccess == false) {
            return null;
        }
        
        // convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"SHIFT-JIS"));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            result = builder.toString();
        } catch(IOException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch(Exception e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            try {
                is.close();
            } catch(IOException e) {
                e.printStackTrace();
                isSuccess = false;;
            }
        }

        if (isSuccess == true) {
            return result;
        } else {
            return null;
        }
    }
}


interface OnDataDownloadListener {
    public void onFinishProcess(String data);
}