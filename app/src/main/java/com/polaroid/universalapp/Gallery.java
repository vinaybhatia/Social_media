package com.polaroid.universalapp;

import java.io.Serializable;

public class Gallery implements Serializable {
    String img_id;
    String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    String fileName;
    boolean staggeredStatus;
    boolean selected;
    int position;
    boolean isShowLoader;

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getStaggeredStatus() {
        return staggeredStatus;
    }

    public void setStaggeredStatus(boolean staggeredStatus) {
        this.staggeredStatus = staggeredStatus;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isStaggeredStatus() {
        return staggeredStatus;
    }

    public boolean isShowLoader() {
        return isShowLoader;
    }

    public void setShowLoader(boolean showLoader) {
        isShowLoader = showLoader;
    }
}
