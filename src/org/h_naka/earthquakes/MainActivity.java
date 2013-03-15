package org.h_naka.earthquakes;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.Button;
import android.widget.EditText;
import android.view.WindowManager.LayoutParams;

public class MainActivity extends Activity {

    private ListView m_infoListView;
    private Button m_getButton;
    private DataProcess m_dataProcess;
    private EditText m_dateEditText;
    private InfoList m_infoList;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // ソフトキーボードを表示させない
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.main);

        initInstance();
        setInterface();

        m_dataProcess.initDate();
        m_dataProcess.getEarthQuakesInformation();
    }

    private void initInstance() {
        m_infoListView = (ListView)findViewById(R.id.infoListView);
        m_getButton    = (Button)findViewById(R.id.getButton);
        m_dateEditText = (EditText)findViewById(R.id.dateEditText);
        m_dataProcess  = new DataProcess(this);
        m_infoList     = new InfoList(this);
    }

    private void setInterface() {
        m_getButton.setOnClickListener(m_dataProcess);
    }

    public ListView getInfoListView() {
        return m_infoListView;
    }

    public EditText getDateEdit() {
        return m_dateEditText;
    }

    public InfoList getInfoList() {
        return m_infoList;
    }
}
