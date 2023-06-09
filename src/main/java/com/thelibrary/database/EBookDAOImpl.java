package com.thelibrary.database;

import com.thelibrary.models.EBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.Objects;

import static com.thelibrary.util.DatabaseUtil.connect;
import static com.thelibrary.util.GeneralUtil.mediaID;

public class EBookDAOImpl implements EBookDAO{


    @Override
    public String sp_AddEbook(String name, String author, int publicationyear, String genre, String description, File cover, File document, int pagecount, String format){
        try(CallableStatement addEbook = Objects.requireNonNull(connect()).prepareCall("{call sp_AddEbook(?,?,?,?,?,?,?,?,?,?,?)}")) {

            FileInputStream input = new FileInputStream(cover);
            FileInputStream eBookPDF = new FileInputStream(document);
            String ebookid = mediaID("ebook", name);

            addEbook.setString(1, ebookid);
            addEbook.setString(2, name);
            addEbook.setString(3, author);
            addEbook.setInt(4, publicationyear);
            addEbook.setString(5, genre);
            addEbook.setString(6, description);
            addEbook.setBinaryStream(7,(InputStream)input,(int)cover.length());
            addEbook.setBinaryStream(8,(InputStream)eBookPDF,(int)document.length() );
            addEbook.setInt(9, pagecount);
            addEbook.setString(10, format);
            addEbook.registerOutParameter(11, Types.BOOLEAN);

            addEbook.executeUpdate();

            boolean success = addEbook.getBoolean(11);
            if (success) {
                return name+" has been saved with ID: "+ ebookid;
            }else {
                return "Error Occurred. Please try again";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteEbook(EBook eBook) {
        try(CallableStatement deleteEBook = Objects.requireNonNull(connect()).prepareCall("{call sp_DeleteEBook(?)}")) {
            deleteEBook.setString(1, eBook.getMediaid());
            deleteEBook.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.getLocalizedMessage();
        }
    }

    @Override
    public ObservableList<EBook> getAllEbooks() {
        ObservableList<EBook> eBookObservableList = FXCollections.observableArrayList();
        try (PreparedStatement getEBooks = Objects.requireNonNull(connect()).prepareStatement("select * from ebook")){
            ResultSet resultSet = getEBooks.executeQuery();

            while (resultSet.next()) {
                EBook eBook = new EBook();
                eBook.setMediaid(resultSet.getString("ebookid"));
                eBook.setName(resultSet.getString("ebooktitle"));
                eBook.setAuthor(resultSet.getString("author"));
                eBook.setPublicationyear(resultSet.getInt("publicationyear"));
                eBook.setGenre(resultSet.getString("genre"));
                eBook.setDescription(resultSet.getString("description"));
                eBook.setPagecount(resultSet.getInt("pagecount"));
                eBook.setStatus(resultSet.getString("status"));
                String imageName = resultSet.getString("ebooktitle") + ".png";
                String documentName = resultSet.getString("ebooktitle")+".pdf";
                InputStream inputStream = resultSet.getBinaryStream("cover");
                InputStream bookStream = resultSet.getBinaryStream("document");
                OutputStream outputStream = new FileOutputStream(new File("./assets/images/" + imageName));
                OutputStream eBookOutput = new FileOutputStream(new File("./assets/document/" + documentName));
                byte[] content = new byte[1024];
                byte[] bookConent = new byte[1024];
                int size, documentsize = 0;

                while ((size = inputStream.read(content)) != -1) {
                    outputStream.write(content, 0, size);
                }
                outputStream.close();
                inputStream.close();

                while ((documentsize = bookStream.read(bookConent)) != -1){
                    eBookOutput.write(bookConent, 0, documentsize);
                }
                bookStream.close();
                eBookOutput.close();


                Image image = new Image("file:./assets/images/" + imageName, 200, 342, true, true);
                File document = new File("./assets/document/" + documentName);
                eBook.setCover(image);
                eBook.setDocument(document);
                eBookObservableList.add(eBook);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eBookObservableList;
    }

}
