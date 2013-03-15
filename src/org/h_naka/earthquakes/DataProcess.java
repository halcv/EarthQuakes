package org.h_naka.earthquakes;

import java.util.regex.PatternSyntaxException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.view.Gravity;

public class DataProcess
    implements OnClickListener,OnDataDownloadListener {

    private static final String URL="http://api.p2pquake.net/userquake?date=";
    
    MainActivity m_activity;
    
    public DataProcess(MainActivity activity) {
        m_activity = activity;
    }

    public void initDate() {
        SimpleDateFormat sdf = (SimpleDateFormat)DateFormat.getDateTimeInstance();
        sdf.applyPattern("yyyy/MM/dd");
        m_activity.getDateEdit().setText(sdf.format(new Date()));
    }
    
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
        case R.id.getButton:
            getEarthQuakesData();
            break;
        }
	}

    private void getEarthQuakesData() {
        m_activity.getInfoList().deleteAllInfo();
        DataDownloadAsyncTask task = new DataDownloadAsyncTask(m_activity);
        task.setOnDataDownloadListener(this);
        task.execute(createUrl());
    }

    private String createUrl() {
        
        return URL + m_activity.getDateEdit().getText();
    }

	@Override
	public void onFinishProcess(String data) {
        boolean isSuccess = false;
        String message = "";
        if (data != null) {
            if (data.length() >= 6) {
                if (data.substring(0,6).equals("ERROR!")) {
                    message = data;
                } else {
                    isSuccess = true;
                    createDataFormat(data);
                }
            } else {
                message = m_activity.getResources().getString(R.string.no_data);
            }
        } else {
            message = m_activity.getResources().getString(R.string.data_get_error);
        }

        if (isSuccess == false) {
            createToast(message);
        }
    }
    
    private void createDataFormat(String data) {
        String message = "";
        boolean isSuccess = false;
        try {
            String [] dataLineArray = data.split("\n");
            for (int i = 0;i < dataLineArray.length;i++) {
                EarthQuakesInfo quakesInfo = new EarthQuakesInfo();
                String [] dataArray = dataLineArray[i].split(",");
                String [] dataInfo = dataArray[2].split("/");
                
                createEarthQuakesInfo(quakesInfo,dataInfo);
                if (!quakesInfo.getDate().equals("") &&
                    !quakesInfo.getQuake().equals("") &&
                    !quakesInfo.getTsunami().equals("") &&
                    !quakesInfo.getFocus().equals("") &&
                    !quakesInfo.getDepth().equals("") &&
                    !quakesInfo.getMagnitude().equals("")) {
                    m_activity.getInfoList().addInfo(quakesInfo);
                }
            }
            isSuccess = true;
        } catch(PatternSyntaxException e) {
            e.printStackTrace();
            message = m_activity.getResources().getString(R.string.data_get_error);
        } catch(StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            message = m_activity.getResources().getString(R.string.data_get_error);
        } catch(Exception e) {
            e.printStackTrace();
            message = m_activity.getResources().getString(R.string.data_get_error);
        }

        if (isSuccess == false) {
            createToast(message);
        }
    }

    private void createEarthQuakesInfo(EarthQuakesInfo quakesInfo,String [] dataInfo) {
        for (int i = 0;i < dataInfo.length;i++) {
            switch(i) {
            case 0:
                String date = "";
                if (!dataInfo[0].equals("")) {
                    date = dataInfo[0].substring(3,dataInfo[0].length());
                }
                quakesInfo.setDate(date);
                break;
            case 1:
                String quake = "";
                if (!dataInfo[1].equals("")) {
                    quake = dataInfo[1];
                }
                quakesInfo.setQuake(quake);
                break;

            case 2:
                String tsunami = "";
                if (dataInfo[2].equals("1")) {
                    tsunami = "有";
                } else if (dataInfo[2].equals("0")) {
                    tsunami = "無";
                }
                quakesInfo.setTsunami(tsunami);
                break;
            case 4:
                String focus = "";
                if (!dataInfo[4].equals("")) {
                    focus = dataInfo[4];
                }
                quakesInfo.setFocus(focus);
                break;
            case 5:
                String depth = "";
                if (!dataInfo[5].equals("")) {
                    depth = dataInfo[5];
                }
                quakesInfo.setDepth(depth);
                break;
            case 6:
                String magnitude = "";
                if (!dataInfo[6].equals("")) {
                    magnitude = dataInfo[6];
                }
                quakesInfo.setMagnitude(magnitude);
            }
        }
    }

    private void createToast(String message) {
        Toast toast = Toast.makeText(m_activity,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    
    public void getEarthQuakesInformation() {
        getEarthQuakesData();
    }
}
