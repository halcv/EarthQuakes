package org.h_naka.earthquakes;

import java.util.List;
import java.util.ArrayList;

public class InfoList {

    private MainActivity m_activity;
    private InfoAdapter m_adapter;
    private List<EarthQuakesInfo> m_info;

    public InfoList(MainActivity activity) {
        m_activity = activity;
        createAdapter();
    }

    private void createAdapter() {
        m_info = new ArrayList<EarthQuakesInfo>();
        m_adapter = new InfoAdapter(m_activity,-1,m_info);
        m_activity.getInfoListView().setAdapter(m_adapter);
    }

    public void addInfo(EarthQuakesInfo info) {
        m_adapter.add(info);
    }

    public void deleteAllInfo() {
        m_adapter.clear();
    }
}
