package com.ph.pcsolottowatcher.pojos;

public class LottoGameBaseModel {
  private String type, name, historyLink, regex;
  private int tableNumber;

  public LottoGameBaseModel() {}

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHistoryLink() {
    return this.historyLink;
  }

  public void setHistoryLink(String historyLink) {
    this.historyLink = historyLink;
  }

  public int getTableNumber() {
    return this.tableNumber;
  }

  public void setTableNumber(int tableNumber) {
    this.tableNumber = tableNumber;
  }

  public String getRegex() {
    return this.regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
  }
}
