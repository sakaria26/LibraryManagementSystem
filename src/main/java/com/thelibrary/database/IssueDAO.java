package com.thelibrary.database;

import com.thelibrary.models.Issue;
import javafx.collections.ObservableList;

public interface IssueDAO {
    void createIssue(String mediaid, String memberid, int period);
    public ObservableList<Issue> getAllIssues();
    public Issue getIssue(String issueid);
    public void issueMedia(Issue issue);
    public void payFine(String issueid);
    public int returnMedia(Issue issue);
}
