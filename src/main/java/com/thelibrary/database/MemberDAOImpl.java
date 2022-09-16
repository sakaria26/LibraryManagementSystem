package com.thelibrary.database;

import com.thelibrary.models.Issue;
import com.thelibrary.models.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.thelibrary.util.DatabaseUtil.connect;
import static com.thelibrary.util.GeneralUtil.memberID;

public class MemberDAOImpl implements MemberDAO{

    @Override
    public Member getMember(String username, String password){
        Member member = new Member();
        try(PreparedStatement preparedStatement = Objects.requireNonNull(connect()).prepareStatement("select * from member where memberid = ? and memberpassword = ?")){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                member.setUsername(resultSet.getString("memberid"));
                member.setName(resultSet.getString("membername"));
                member.setSurname(resultSet.getString("membersurname"));
                member.setEmail(resultSet.getString("memberemail"));
                member.setPassword(resultSet.getString("memberpassword"));
                member.setPhoneNumber(resultSet.getString("phonenumber"));
                member.setNumberOfBooksIssued(Integer.parseInt(resultSet.getString("noofissues")));
                member.setMembershipStatus(resultSet.getString("membershipstatus"));

            }

        }catch (Exception exception){
            exception.printStackTrace();
        }
        return member;
    }

    @Override
    public ObservableList<Member> getPendingMemberList() {
        ObservableList<Member> memberObservableList = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = Objects.requireNonNull(connect()).prepareStatement("select * from member where membershipstatus = 'pending'")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Member member = new Member();
                member.setUsername(resultSet.getString("memberid"));
                member.setName(resultSet.getString("membername"));
                member.setSurname(resultSet.getString("membersurname"));
                member.setEmail(resultSet.getString("memberemail"));
                member.setPassword(resultSet.getString("memberpassword"));
                member.setPhoneNumber(resultSet.getString("phonenumber"));
                member.setNumberOfBooksIssued(Integer.parseInt(resultSet.getString("noofissues")));
                member.setMembershipStatus(resultSet.getString("membershipstatus"));
                memberObservableList.add(member);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return memberObservableList;
    }

    @Override
    public ObservableList<Member> getMemberList() {
        ObservableList<Member> memberObservableList = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = Objects.requireNonNull(connect()).prepareStatement("select * from member ")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Member member = new Member();
                member.setUsername(resultSet.getString("memberid"));
                member.setName(resultSet.getString("membername"));
                member.setSurname(resultSet.getString("membersurname"));
                member.setEmail(resultSet.getString("memberemail"));
                member.setPassword(resultSet.getString("memberpassword"));
                member.setPhoneNumber(resultSet.getString("phonenumber"));
                member.setNumberOfBooksIssued(Integer.parseInt(resultSet.getString("noofissues")));
                member.setMembershipStatus(resultSet.getString("membershipstatus"));
                memberObservableList.add(member);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return memberObservableList;
    }

    @Override
    public  ObservableList<Issue> getMemberIssues(String memberid){
        ObservableList<Issue> issueObservableList = FXCollections.observableArrayList();
        try (PreparedStatement viewIssues = Objects.requireNonNull(connect()).prepareStatement("select * from memberissues where memberid = '"+memberid+"'")){
            ResultSet resultSet = viewIssues.executeQuery();
            while (resultSet.next()){
                Issue issue = new Issue();
                issue.setIssueID(resultSet.getString("issueid"));
                issue.setMediaName(resultSet.getString("title"));
                issue.setPeriod(resultSet.getInt("periodindays"));
                issue.setReturnDate(resultSet.getDate("returndate"));
                issueObservableList.add(issue);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        System.out.println(memberid);
        System.out.println(issueObservableList);
        return issueObservableList;

    }

    @Override
    public void signUp(String name, String surname, String emailAddress, String phoneNumber){
        try (CallableStatement addMember = Objects.requireNonNull(connect()).prepareCall("{call sp_MemberSignUp(?,?,?,?,?)}")){
            addMember.setString(1, memberID(name, surname));
            addMember.setString(2, name);
            addMember.setString(3, surname);
            addMember.setString(4, emailAddress);
            addMember.setString(5, phoneNumber);
            addMember.executeUpdate();
        }catch (Exception exception){
            exception.printStackTrace();
        }


    }

    @Override
    public void addMember(Member member){
        try (CallableStatement callableStatement = Objects.requireNonNull(connect()).prepareCall("{call sp_AddMember(?)}")){
            callableStatement.setString(1, member.getUsername());
            callableStatement.executeUpdate();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteMember(Member member){
        try (CallableStatement callableStatement = Objects.requireNonNull(connect()).prepareCall("{call sp_DeleteMember(?)}")){
            callableStatement.setString(1, member.getUsername());
            callableStatement.executeUpdate();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void sp_ChangeMemberPassword(String userID, String password){
        try (CallableStatement callableStatement = Objects.requireNonNull(connect()).prepareCall("{call sp_ChangeMemberPassword(?, ?)}")){
            callableStatement.setString(1, userID);
            callableStatement.setString(2, password);
            callableStatement.executeUpdate();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

}


