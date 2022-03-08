/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webbrowser;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import static com.sun.javafx.image.impl.ByteArgb.accessor;
import com.sun.javafx.webkit.Accessor;
import com.sun.javafx.webkit.ThemeClientImpl;
import com.sun.javafx.webkit.UIClientImpl;
import com.sun.javafx.webkit.WebPageClientImpl;
import com.sun.webkit.WebPage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import javafx.scene.web.*;
import javafx.stage.Stage;
import static javafx.concurrent.Worker.State;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebHistory.Entry;

/*

*/

public class WebBrowser extends Application {
    
    TextField textLink;
    WebEngine webEngine;    
    History his;
    Scene scene1;
    Scene scene;
    ObservableList<WebHistory.Entry> entries;
    TableView  HTable;
    Stage window;



    
    

    @Override
    public void start(final Stage primaryStage) {
        window = primaryStage;
        //create an object of PFile
        PFile pFile = new PFile();
        
        
        
        final ProgressBar progress=new ProgressBar();
        
        
        his=new History();
        // Create the WebView
        WebView webView = new WebView();
        
         
        
        
        // Create the WebEngine
        webEngine = webView.getEngine();

        
        // load google as first page
        webEngine.load("https:\\www.google.com");
        
        // ***********************************************************************
         WebHistory history = webEngine.getHistory();
              entries = history.getEntries();
        
        BorderPane borderPane = new BorderPane();        
        HBox progressHbox = new HBox();
        
        progress.prefWidthProperty().bind(progressHbox.widthProperty());
        progress.prefHeightProperty().bind(progressHbox.heightProperty());
        progressHbox.setHgrow(progress, Priority.ALWAYS);
        progressHbox.getChildren().add(progress);
        ToolBar toolBar=new ToolBar();
        HBox hBox = new HBox(5);
        VBox vBox = new VBox();
        
        
        //Create a TextField for Address Link...
        textLink = new TextField("https://www.google.com");
        textLink.setPromptText("Enter Link Here!");
        HBox.setHgrow(textLink, Priority.ALWAYS);
        
        //set Action to TextField
        textLink.setOnKeyPressed((event) ->{
            if(event.getCode() ==KeyCode.ENTER)
                
                // call method go(Stage);
                go(window);
        });
        
         
        //Create a Button in order to open the link...
        Button goBtn = new Button("", new ImageView("file:images\\searcher.png"));       
        goBtn.setMinSize(35,25);        
        //set action to goBtn
            goBtn.setOnAction((event) -> go(window));
        
            
        //create a button to reload the page
        Button reloadBtn = new Button("", new ImageView("file:images\\rotate.png"));        
        reloadBtn.setMinSize(35,25);      
        //set action when clicked
        reloadBtn.setOnAction((event) -> webEngine.reload());
        
                
        // create button for adding bookmarks
        Button bookmarksBtn = new Button("", new ImageView("file:images\\star.png"));
        bookmarksBtn.setMinSize(35,25); 
        bookmarksBtn.setOnAction(e -> 
        {
            window.setScene(setScene());
           
        
        });
        
        
        // Backward button
        Button backBtn=new Button("",new ImageView("file:images\\left-arrow.png"));
        backBtn.disableProperty().bind(webEngine.getHistory().currentIndexProperty().isEqualTo(0));
        backBtn.setStyle("-fx-background-color: #ff0000;");
        backBtn.setMinSize(30,25); 
        backBtn.setOnAction(e -> webEngine.load(goBack(history)));
        
        
        // Forward Button
        Button forwardBtn=new Button("",new ImageView("file:images\\right-arrow.png"));
        //backBtn.disableProperty().bind(webEngine.getHistory().currentIndexProperty().();
        forwardBtn.setMinSize(30,25); 
        forwardBtn.setOnAction(e -> webEngine.load(goForward(history)));
        
        
        
        
        //put HBox into borderPane...
        toolBar.getItems().addAll(backBtn,forwardBtn);
        toolBar.getItems().add(new Separator());
        toolBar.getItems().addAll(goBtn,textLink,reloadBtn,bookmarksBtn);
        toolBar.setPadding(new Insets(3,3,3,3));
        vBox.getChildren().addAll(toolBar,progressHbox);
        //put hBox in top of the scene
        borderPane.setTop(vBox);
        
        
        progress.progressProperty().bind(webEngine.getLoadWorker().progressProperty());


        
        //Changing link to the current one in textLink 
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() 
		{
            @Override
            public void changed(ObservableValue<? extends State> ov, State oldState, State newState){
                if (newState == State.SUCCEEDED)                   
                   textLink.setText(webEngine.getLocation());
                   window.getIcons().add(loadFavIcon(textLink.getText()));
                }});
                
               

            
               
        //put bookmarksHbox to top of the scene under hBox
        //borderPane.setTop(bookmarksHbox);

        borderPane.setCenter(webView);
        scene = new Scene(borderPane,600,600);
        hBox.getStylesheets().add("style1/button-styles.css");
        window.setScene(scene);
        window.show();
       
    }
    
    
    public static void main(String[] args) {       
        launch(args);
    }
    
    
    public void go(Stage primaryStage){
                            
		// LOad the Start-Page
		webEngine.load("https://www."+textLink.getText().toString());
		
		// Update the stage title when a new web page title is available
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() 
		{
            @Override
            public void changed(ObservableValue<? extends State> ov, State oldState, State newState) 
            {
                if (newState == State.SUCCEEDED) 
                {
                    //primaryStage.setTitle(webEngine.getLocation());
                	primaryStage.setTitle(webEngine.getTitle());
                        textLink.setText(webEngine.getLocation());                        
                }
            }
        });
    }
    
    
    //loading fav Icon
    Image loadFavIcon(String location) {
    try {
        String faviconUrl = String.format("http://www.google.com/s2/favicons?domain_url=%s", URLEncoder.encode(location, "UTF-8"));
        Image favicon = new Image(faviconUrl, true);
        ImageView iv = new ImageView(favicon);
        return favicon;
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex); // not expected
        }
    }

    

    
//Adding function to Backward button
            public String goBack(WebHistory history) {

                int currentIndex = history.getCurrentIndex();
                Platform.runLater(() -> history.go(-1));
                return entries.get(currentIndex).getUrl();
            }
 
            
 //Adding function to Forward button           
            public String goForward(WebHistory history){    
                
                int currentIndex=history.getCurrentIndex();
                Platform.runLater(()-> { history.go(1); } );
                return entries.get(currentIndex<entries.size()-1?currentIndex+1:currentIndex).getUrl();
            }
           
  
  
//            public Scene historyScene(){
//                BorderPane root= new BorderPane();
//                TableView  HTable =new TableView();
//                HTable.setPrefSize(350, 200);
//                HTable.setEditable(true);
//        
//            TableColumn url=new TableColumn("URL");
//            url.setCellValueFactory(new PropertyValueFactory("url"));
//
//            TableColumn title=new TableColumn("Title");
//            title.setCellValueFactory(new PropertyValueFactory("title"));
//
//            TableColumn date=new TableColumn("Date");
//            date.setCellValueFactory(new PropertyValueFactory("lastVisitedDate"));
//            
//            HTable.getColumns().addAll(url,title,date);
//                    
//            root.getChildren().add(HTable);
//            
//            HTable.setItems(entries);
//                Scene scene2=new Scene(root);
//                return scene2;
//            }
            

            public Scene setScene() {
        

        
        // Delet Button
        Button deletBtn=new Button("Delet");
        deletBtn.setOnAction(e -> {
   deleteButtonClicked();
        });
        

        
        // ClearAll Button
        Button backBtn=new Button("go back");
        backBtn.setOnAction(e -> {
            
          window.setScene(scene);
            
        });
        
        
        // ************* Table *****************
        HTable =new TableView();
        HTable.setPrefSize(550, 600);
        
        TableColumn url=new TableColumn("URL");
        url.setCellValueFactory(new PropertyValueFactory("url"));
        url.prefWidthProperty().bind(HTable.widthProperty().divide(2));
        url.setResizable(true);
        
        TableColumn title=new TableColumn("Title");
        title.setCellValueFactory(new PropertyValueFactory("title"));
        title.prefWidthProperty().bind(HTable.widthProperty().divide(4));
        
        TableColumn date=new TableColumn("Date");
        date.setCellValueFactory(new PropertyValueFactory("lastVisitedDate"));
        date.prefWidthProperty().bind(HTable.widthProperty().divide(4));
        
        HTable.getColumns().addAll(url,title,date);
        HTable.setItems(entries);
        
        HBox btnHBox=new HBox(10);
        btnHBox.getChildren().addAll(deletBtn,backBtn);
        
        
        BorderPane root = new BorderPane();
        root.setCenter(HTable);
        root.setTop(btnHBox);
        
        scene1 = new Scene(root, 600, 650);
  //      scene1.setUserAgentStylesheet(getClass().getResource("history-scene.css").toExternalForm());
        return scene1;

    }
            
public void deleteButtonClicked(){
    ObservableList<WebHistory.Entry> allStrings, stringSelected;
    allStrings = HTable.getItems();


    stringSelected = HTable.getSelectionModel().getSelectedItems();    
    stringSelected.forEach(allStrings::remove);
}
            
        }
