package com.thelibrary.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Issue {
    private StringProperty IssueID;
    private StringProperty MemberID;
    private StringProperty MediaID;
    private StringProperty MediaName;
    private Date IssueDate;
    private IntegerProperty Period;
    private Date ReturnDate;
    private int Fine;

    public String getIssueID() {
        return issueIDProperty().get();
    }

    public void setIssueID(String issueID) {
        this.issueIDProperty().set(issueID);
    }

    public StringProperty issueIDProperty() {
        if (IssueID == null)
            IssueID = new SimpleStringProperty(this, "issue ID");
        return IssueID;
    }

    public String getMemberID() {
        return MemberID.get();
    }

    public void setMemberID(String memberID) {
        this.MemberID.set(memberID);
    }

    public StringProperty memberIDProperty() {
        if (MemberID == null)
            MemberID = new SimpleStringProperty(this, "member ID");
        return MemberID;
    }


    public String getMediaID() {
        return mediaIDProperty().get();
    }

    public void setMediaID(String mediaID) {
        this.mediaIDProperty().set(mediaID);
    }

    public StringProperty mediaIDProperty() {
        if (MediaID == null)
            MediaID = new SimpleStringProperty(this, "media ID");
        return MediaID;
    }


    public Date getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(Date issueDate) {
        IssueDate = issueDate;
    }

    public int getPeriod() {
        return periodProperty().get();
    }

    public void setPeriod(int period) {
        this.periodProperty().set(period);
    }

    public IntegerProperty periodProperty() {
        if (Period == null)
            Period = new SimpleIntegerProperty(this, "period");
        return Period;
    }

    public Date getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(Date returnDate) {
        ReturnDate = returnDate;
    }

    public int getFine() {
        return this.Fine;
    }

    public String getMediaName() {
        return mediaNameProperty().get();
    }

    public StringProperty mediaNameProperty() {
        if (MediaName == null)
            MediaName = new SimpleStringProperty(this, "media name");
        return MediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaNameProperty().set(mediaName);
    }

    public void setFine(int fine) {
        this.Fine = fine;
    }

    public Issue(StringProperty issueID, StringProperty memberID, StringProperty mediaID, Date issueDate, IntegerProperty period, Date returnDate, int fine) {
        this.IssueID = issueID;
        this.MemberID = memberID;
        this.MediaID = mediaID;
        this.IssueDate = issueDate;
        this.Period = period;
        this.ReturnDate = returnDate;
        this.Fine = fine;
    }

    public Issue() {
        this.IssueID = issueIDProperty();
        this.MemberID = memberIDProperty();
        this.MediaID = mediaIDProperty();
        this.IssueDate = new Date();
        this.Period = periodProperty();
        this.ReturnDate = new Date();
        this.Fine = 0;
    }
}
