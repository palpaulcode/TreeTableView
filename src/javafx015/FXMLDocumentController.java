/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx015;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

/**
 *
 * @author TURNKEY
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    private JFXTreeTableView<User> treeView;
    @FXML
    private JFXTextField input;
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        JFXTreeTableColumn<User, String> deptName = new JFXTreeTableColumn<>("department");
        deptName.setPrefWidth(150);
        deptName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return param.getValue().getValue().department;
            }
        });
        
        JFXTreeTableColumn<User, String> ageCol = new JFXTreeTableColumn<>("Age");
        ageCol.setPrefWidth(150);
        ageCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return param.getValue().getValue().age;
            }
        });
        
        JFXTreeTableColumn<User, String> nameCol = new JFXTreeTableColumn<>("Name");
        nameCol.setPrefWidth(150);
        nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return param.getValue().getValue().userName;
            }
        });
        
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User("Computer department","23","CD 1"));
        users.add(new User("sales department","22","Employee 1"));
        users.add(new User("sales department","22","Employee 2"));
        users.add(new User("sales department","25","Employee 4"));
        users.add(new User("sales department","25","Employee 5"));
        users.add(new User("IT Department","42","ID 2"));
        users.add(new User("HR Department","22","HR 1"));
        users.add(new User("HR Department","22","HR 2"));
        
        final TreeItem<User> root = new RecursiveTreeItem<User>(users, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(deptName, ageCol, nameCol);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        
        input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(new Predicate<TreeItem<User>>() {
                    @Override
                    public boolean test(TreeItem<User> user) {
                        Boolean flag = user.getValue().department.getValue().contains(newValue)||user.getValue().userName.getValue().contains(newValue);
                        return flag;
                    }
                });
            }
        });
    }    
    
    class User extends RecursiveTreeObject<User> {
    
        StringProperty userName;
        StringProperty age;
        StringProperty department;

        public User(String department, String age, String userName) {
            this.department = new SimpleStringProperty(department);
            this.userName = new SimpleStringProperty(department);
            this.age = new SimpleStringProperty(age);
    }
}
}
