/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webbrowser;

import javafx.application.Application;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;
import javafx.stage.Stage;
 
/**
 *
 * @author Best Technology
 */
public class History {
    
    Scene scene1;
    WebBrowser web;
    TableView  HTable;
    
    
    
    public Scene setScene() {
        
        

        
        // Delet Button
        Button deletBtn=new Button("Delete");
        
        // GoTo Button
        Button goToBtn=new Button();
        
        // ClearAll Button
        Button clearBtn=new Button();
        
        
        // ************* Table *****************
                TableView  HTable =new TableView();
        
        TableColumn url=new TableColumn("URL");
        url.setCellValueFactory(new PropertyValueFactory("url"));
        url.setMinWidth(600);
        
        TableColumn title=new TableColumn("Title");
        title.setCellValueFactory(new PropertyValueFactory("title"));
        
        TableColumn date=new TableColumn("Date");
        date.setCellValueFactory(new PropertyValueFactory("lastVisitedDate"));
        
        HTable.getColumns().addAll(url,title,date);
        HTable.setItems(web.entries);
        
        
        
        BorderPane root = new BorderPane();
        root.setCenter(HTable);
        root.setLeft(deletBtn);
        
        scene1 = new Scene(root, 300, 250);
        return scene1;

    }
    
    // function to delete Button
public void deleteButtonClicked(){
    ObservableList<WebHistory.Entry> allStrings, stringSelected;
    allStrings = HTable.getItems();


    stringSelected = HTable.getSelectionModel().getSelectedItems();
    
    stringSelected.forEach(allStrings::remove);
}
}
