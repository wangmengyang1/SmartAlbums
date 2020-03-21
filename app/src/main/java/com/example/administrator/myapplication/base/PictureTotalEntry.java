package com.example.administrator.myapplication.base;

import com.mylhyl.pickpicture.PictureTotal;

public class PictureTotalEntry {
    private boolean isCheck;
    private PictureTotal pictureTotal;

    public PictureTotalEntry(boolean isCheck, PictureTotal pictureTotal) {
        this.isCheck = isCheck;
        this.pictureTotal = pictureTotal;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public PictureTotal getPictureTotal() {
        return pictureTotal;
    }

    public void setPictureTotal(PictureTotal pictureTotal) {
        this.pictureTotal = pictureTotal;
    }
}
