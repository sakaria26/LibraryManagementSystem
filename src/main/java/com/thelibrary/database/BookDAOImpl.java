package com.thelibrary.database;

import com.thelibrary.models.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.Objects;

import static com.thelibrary.util.DatabaseUtil.connect;
import static com.thelibrary.util.GeneralUtil.mediaID;

public class BookDAOImpl implements BookDAO{
    /**
    Adding Book to the database
     */
    @Override
    public  String addBook(String title, String author, int publicationyear, String genre, double price, String description, File cover, int pagecount){
        try {
            FileInputStream input = new FileInputStream(cover);
            Connection connection = connect();
            assert connection != null;
            CallableStatement addBook = Objects.requireNonNull(connect()).prepareCall("{call sp_AddBook(?,?,?,?,?,?,?,?,?,?)}");
            String bookid = mediaID("book",title);
            addBook.setString(1, bookid);
            addBook.setString(2, title);
            addBook.setString(3, author);
            addBook.setInt(4, publicationyear);
            addBook.setString(5, genre);
            addBook.setDouble(6, price);
            addBook.setString(7, description);
            addBook.setBinaryStream(8,(InputStream)input,(int)cover.length());
            addBook.setInt(9, pagecount);
            addBook.registerOutParameter(10, Types.BOOLEAN);

            addBook.executeUpdate();

            boolean success = addBook.getBoolean(10);
            if (success) {
                return title+" has been saved with ID: "+ bookid;
            }else {
                return "Error Occurred. Please try again";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete Book
     * */
    @Override
    public void deleteBook(Book book){
        try {
            Connection connection = connect();
            assert connection != null;
            CallableStatement deleteBook = connection.prepareCall("{call sp_DeleteBook(?)}");
            deleteBook.setString(1, book.getMediaid());
            deleteBook.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Book> getAllBooks() {
        ObservableList<Book> bookObservableList = FXCollections.observableArrayList();
        try (PreparedStatement getBooks = Objects.requireNonNull(connect()).prepareStatement("select * from book")){
            ResultSet resultSet = getBooks.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setMediaid(resultSet.getString("bookid"));
                book.setName(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublicationyear(resultSet.getInt("publicationyear"));
                book.setGenre(resultSet.getString("genre"));
                book.setPrice(resultSet.getDouble("price"));
                book.setDescription(resultSet.getString("description"));
                book.setPagecount(resultSet.getInt("pagecount"));
                book.setStatus(resultSet.getString("status"));
                String imageName = resultSet.getString("title") + ".png";
                InputStream inputStream = resultSet.getBinaryStream("cover");
                OutputStream outputStream = new FileOutputStream(new File("./assets/images/" + imageName));
                byte[] content = new byte[1024];
                int size = 0;

                while ((size = inputStream.read(content)) != -1) {
                    outputStream.write(content, 0, size);
                }
                outputStream.close();
                inputStream.close();

                Image image = new Image("file:./assets/images/" + imageName, 200, 342, true, true);
                book.setCover(image);
                bookObservableList.add(book);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookObservableList;
    }

    @Override
    public ObservableList<Book> getAvailableBooks() {
        ObservableList<Book> bookObservableList = FXCollections.observableArrayList();
        try (PreparedStatement getBooks = Objects.requireNonNull(connect()).prepareStatement("select * from book where status = 'inhouse'")){
            ResultSet resultSet = getBooks.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setMediaid(resultSet.getString("bookid"));
                book.setName(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublicationyear(resultSet.getInt("publicationyear"));
                book.setGenre(resultSet.getString("genre"));
                book.setPrice(resultSet.getDouble("price"));
                book.setDescription(resultSet.getString("description"));
                book.setPagecount(resultSet.getInt("pagecount"));
                book.setStatus(resultSet.getString("status"));
                String imageName = resultSet.getString("title") + ".png";
                InputStream inputStream = resultSet.getBinaryStream("cover");
                OutputStream outputStream = new FileOutputStream(new File("./assets/images/" + imageName));
                byte[] content = new byte[1024];
                int size = 0;

                while ((size = inputStream.read(content)) != -1) {
                    outputStream.write(content, 0, size);
                }
                outputStream.close();
                inputStream.close();

                Image image = new Image("file:./assets/images/" + imageName, 200, 342, true, true);
                book.setCover(image);
                bookObservableList.add(book);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookObservableList;
    }


}
