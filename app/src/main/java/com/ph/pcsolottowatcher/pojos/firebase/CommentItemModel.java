package com.ph.pcsolottowatcher.pojos.firebase;

import com.ph.pcsolottowatcher.pojos.BaseFirebaseModel;

public class CommentItemModel extends BaseFirebaseModel {
  private String comment;

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
