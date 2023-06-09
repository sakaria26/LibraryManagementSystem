package com.thelibrary.database;

import com.thelibrary.models.Issue;
import com.thelibrary.models.Member;
import javafx.collections.ObservableList;

public interface MemberDAO {
    Member getMember(String username, String password);
    ObservableList<Member> getPendingMemberList();
    ObservableList<Member> getMemberList();
    ObservableList<Issue> getMemberIssues(String memberid);
    void signUp(String name, String surname, String emailAddress, String phoneNumber);
    void addMember(Member member);
    void deleteMember(Member member);
    void sp_ChangeMemberPassword(String userID, String password);
}
