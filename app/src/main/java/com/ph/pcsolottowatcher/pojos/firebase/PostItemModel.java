package com.ph.pcsolottowatcher.pojos.firebase;

import com.ph.pcsolottowatcher.pojos.BaseFirebaseModel;

public class PostItemModel extends BaseFirebaseModel {
  private String lottoName, number, comments, image;
  private int likes;
  private Object time;

  public String getLottoName() {
    return this.lottoName;
  }

  public void setLottoName(String lottoName) {
    this.lottoName = lottoName;
  }

  public String getNumber() {
    return this.number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getComments() {
    return this.comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public int getLikes() {
    return this.likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public Object getTime() {
    return this.time;
  }

  public void setTime(Object time) {
    this.time = time;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
