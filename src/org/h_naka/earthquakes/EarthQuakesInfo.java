package org.h_naka.earthquakes;

public class EarthQuakesInfo {
    private String m_date;
    private String m_quake;
    private String m_tsunami;
    private String m_focus;
    private String m_depth;
    private String m_magnitude;
    
    public void setDate(String date) {
        m_date = date;
    }

    public String getDate() {
        return m_date;
    }
    
    public void setQuake(String quake) {
        m_quake = quake;
    }

    public String getQuake() {
        return m_quake;
    }

    public void setTsunami(String tsunami) {
        m_tsunami = tsunami;
    }

    public String getTsunami() {
        return m_tsunami;
    }

    public void setFocus(String focus) {
        m_focus = focus;
    }

    public String getFocus() {
        return m_focus;
    }

    public void setDepth(String depth) {
        m_depth = depth;
    }

    public String getDepth() {
        return m_depth;
    }

    public void setMagnitude(String magnitude) {
        m_magnitude = magnitude;
    }

    public String getMagnitude() {
        return m_magnitude;
    }
}
