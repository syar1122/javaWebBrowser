/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webbrowser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.TableView;
import javafx.scene.web.WebHistory;

public class PFile {
    
    File file =new File("hhistory.txt");
    WebBrowser webB = new WebBrowser();
    
    void writeToFile() throws IOException {
        
        FileWriter writer = new FileWriter(file);
      //  writer.write(webB.entrylist.get(0));
    
}
    public void read() throws FileNotFoundException, IOException{
        WebBrowser webBrowser=new WebBrowser();
        List<String> myArray = new ArrayList<String>();
        
        
        

        
    }

    
}
