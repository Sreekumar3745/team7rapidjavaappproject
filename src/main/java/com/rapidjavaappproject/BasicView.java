/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rapidjavaappproject;

/**
 *
 * @author Nakera, Christian, Shyam 
 */

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Icon;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList; 
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import java.io.FileReader;
import java.io.BufferedReader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TableColumn;
import java.sql.*;
//import javafx.scene.layout.GridPane;

public class BasicView extends View {
//Chapter array
    public static String[] chapters = {"Chapter 1: Introduction to Computers, Programs, and Java ", 
        "Chapter 2: Elementary Programming", "Chapter 3: Selections",
        "Chapter 4: Mathematical Functions, Characters, and Strings", 
        "Chapter 5: Loops", "Chapter 6: Methods", "Chapter 7: Single-Dimensional Arrays", 
        "Chapter 8: Multidimensional Arrays", "Chapter 9: Objects and Classes", 
        "Chapter 10: Object-Oriented Thinking", "Chapter 11: Inheritance and Polymorphism", 
        "Chapter 12: Exception Handling and Text I/O", "Chapter 13: Abstract Classes and Interfaces",
        "Chapter 14: JavaFX Basics", "Chapter 15: Event-Driven Programming and Animations",
        "Chapter 16: JavaFX UI Controls and Multimedia", "Chapter 17: Binary I/O", 
        "Chapter 18: Recursion", "Chapter 19: Generics", 
        "Chapter 20: Lists, Stacks, Queues, and Priority Queues",
        "Chapter 21: Sets and Maps", "Chapter 22: Developing Efficient Algorithms", 
        "Chapter 23: Sorting", "Chapter 24: Implementing Lists, Stacks, Queues, and Priority Queues", 
        "Chapter 25: Binary Search Trees", "Chapter 26: AVL Trees", 
        "Chapter 27: Hashing", "Chapter 28: Graphs and Applications", 
        "Chapter 29: Weighted Graphs and Applications", 
        "Chapter 30: Aggregate Operations on Collection Streams",
        "Chapter 31: Advanced JavaFX", "Chapter 32: Multithreading and Parallel Programming", 
        "Chapter 33: Networking", "Chapter 34: Java Database Programming", 
        "Chapter 35: Advanced Database Programming", "Chapter 36: Internationalization", 
        "Chapter 37: Servlets", "Chapter 38: JavaServer Pages", "Chapter 39: JavaServer Faces", 
        "Chapter 40: Remote Method Invocation", "Chapter 41: Web Services", 
        "Chapter 42: Trees and B-Trees", "Chapter 43: Red-Black Trees", 
        "Chapter 44: Testing Using JUnit"};

    public static ObservableList<String> items
            = FXCollections.observableArrayList(chapters);
    public static ObservableList<String> items2; //section numbers
    public static ObservableList<String> items3; //question numbers
    public static ComboBox cbo = new ComboBox(); //Chapter selection
    public static ComboBox cbo2 = new ComboBox(); //Section selection
    public static ComboBox cbo3 = new ComboBox(); //Question selection
    //will hold the sections
    public static ArrayList<String> sArr = new ArrayList<String>(0);
    //will hold the questions
    public static ArrayList<Question> qArr = new ArrayList<Question>(0);
    public static Label label = new Label("Choose a Chapter: ");
    public static Button button = new Button("Enter");
    boolean noSect = true; //used for chapters with no sections
    public static String userName = "";
    public static String password = "";
    
    
    public BasicView(String name) {
        super(name);
        logIn();
    }
 
    @Override
    public void updateAppBar(AppBar appBar) {
        //may use line below later but for now Menu icon not needed
        //appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> System.out.println("Menu")));
        appBar.setTitleText("Chapter Quizzes");
    }
    
    public void logIn() {
        AppPane logPane = new AppPane();
        
        //displays if login details are incorrect
        //or if username is already taken (for registration)
        //or if registration was successful
        Label lb0 = new Label("");
        
        Label userLabel = new Label("Username: ");
        TextField userField = new TextField();
        HBox userPane = new HBox(userLabel, userField);
        userPane.setAlignment(Pos.CENTER);
        
        Label pwLabel = new Label("Password: ");
        PasswordField pwField = new PasswordField();
        HBox pwPane = new HBox(pwLabel, pwField);
        pwPane.setAlignment(Pos.CENTER);
        
        Button logBtn = new Button("Log In");
        Button regBtn = new Button("Register");
        HBox btnPane = new HBox(regBtn, logBtn);
        btnPane.setSpacing(10);
        btnPane.setAlignment(Pos.CENTER);
        
        logBtn.setOnAction(e-> {
            if (logPane.tryLogin(userField.getText(), pwField.getText())) {
                doStuff();
            }
            else {
                lb0.setText("Username or password incorrect.");
                lb0.setTextFill(Color.RED);
            }
        });
        
        regBtn.setOnAction(e-> {
           if (logPane.register(userField.getText(), pwField.getText())) {
               lb0.setText("Registration successful. You may log in now.");
               lb0.setTextFill(Color.GREEN);
           }
           else {
               lb0.setText("Username already taken.");
               lb0.setTextFill(Color.RED);
           }
        });
        
        VBox midPane = new VBox(userPane, pwPane, btnPane, lb0);
        logPane.setCenter(midPane);
        midPane.setAlignment(Pos.CENTER);
        midPane.setSpacing(20); 
        setCenter(logPane);
    }
    
    public void doStuff() {
        AppPane appPane = new AppPane();
        
        
        Button backBtn = new Button("Log Out");
        //should redirect to a user report screen as mentioned in class
        Button repBtn = new Button("User Report");
        
        cbo.getItems().clear();
        Label lb0 = new Label("");
        
        cbo.setPrefWidth(250);
        cbo.setValue("Chapters: ");
        cbo.getItems().addAll(items);
        
        cbo2.setPrefWidth(250);
        cbo2.setValue("Sections: ");
        
        cbo3.setPrefWidth(250);
        cbo3.setValue("Questions: ");
        
        cbo.setOnAction(e -> appPane.getSections(items.indexOf(cbo.getValue())));
        cbo2.setOnAction(e -> appPane.getQuestions(items.indexOf(cbo.getValue()),
               items2.indexOf(cbo2.getValue())));
        
        button.setOnAction(e -> {
            if ((cbo.getValue() == "Chapters: ")||(cbo2.getValue() == "Sections: ")
                    ||(cbo3.getValue() == "Questions: ")) {
                lb0.setText("You must choose a Chapter, Section, and Question.");
            }
            else {
                appPane.displayQuestion(items3.indexOf(cbo3.getValue()));
            }
        });
        
        backBtn.setOnAction(e-> {
            //log out
            userName = "";
            password = "";
            logIn();
        });
        
        repBtn.setOnAction(e-> {
           //go to user report
            appPane.displayUserReport("all"); 
        });
        
        HBox topPane = new HBox(backBtn, repBtn);
        topPane.setSpacing(100);
        topPane.setAlignment(Pos.CENTER);
        VBox midPane = new VBox(cbo, cbo2, cbo3, button, lb0);
        appPane.setTop(topPane);
        appPane.setCenter(midPane);
        midPane.setAlignment(Pos.CENTER);
        midPane.setSpacing(20); 
        setCenter(appPane);
        button.setGraphic(new Icon(MaterialDesignIcon.LANGUAGE));
  
    }
    class AppPane extends BorderPane {
        //address will need to be changed to location of the files
        //String address = "C:\\Users\\Christian\\Desktop\\mcquestions\\";
        String address = "C:\\Users\\Pavi\\Downloads\\mcquestions\\";
        private Connection connection = null;
        
        AppPane() {
        }
        
        public boolean tryLogin(String name, String pw) {
            //checks if login details correct
            //also "logs in" if true
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/user", "scott", "tiger");
                System.out.println("Driver loaded.");
                
                String command = "SELECT * FROM UserQuizSummary WHERE userName = '" + 
                        name + "' AND password = '" + pw + "'";
                Statement statement = connection.createStatement();
                rs = statement.executeQuery(command);
                //if userName and password are correct, save them and return true
                if (rs.absolute(1)) {
                    userName = name;
                    password = pw;
                    return true;
                }
                //otherwise return false
                else {
                    return false;
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Something went wrong with logIn.");
                return false;
            }
            finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        
        public boolean register(String name, String pw) {
            //checks if username already exists
            //also registers new user to database if not
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/user", "scott", "tiger");
                System.out.println("Driver loaded.");
                
                String command = "SELECT * FROM UserQuizSummary WHERE userName = '" + 
                        name + "'";
                Statement statement = connection.createStatement();
                rs = statement.executeQuery(command);
                //if userName already exists return false
                if (rs.absolute(1)) {
                    return false;
                }
                //otherwise register user
                else {
                    //qID for Chapter 6, Section 3, Question 44: 06.03.44
                    userName = name;
                    password = pw;
                    command = "INSERT INTO UserQuizSummary" + 
                            " ( userName, password, qID, trial )"
                        + " VALUES ('" + userName + "', '" + password 
                        + "', '00.00.00', '0')";
                    statement.executeUpdate(command);
                    
                    return true;
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Something went wrong with register.");
                return false;
            }
            finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        
        public void displayUserReport(String status) {
            //displays user report
            /*
            ResultSet rs = null;
            try {
                items2.clear();
                items3.clear();
                cbo2.getItems().clear();
                cbo3.getItems().clear();
                qArr.clear();
                this.getChildren().clear();
                HBox backPane = new HBox(); //pane for back button
                backPane.setPadding(new Insets(5, 0, 3, 0));
                Button backBtn = new Button("Back to Selection Menu");
                backPane.getChildren().add(backBtn);
                this.setTop(backPane);
                
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/user", "scott", "tiger");
                System.out.println("Driver loaded.");
                
                //TableView table = new TableView();
                //table.setEditable(false);
                if (status == "all") {
                    //TableColumn chaptCol = new TableColumn("Chapter");
                    //TableColumn questCol = new TableColumn("Total Answered Questions");
                    //TableColumn tryCol = new TableColumn("Total Answer Attempts");
                    //TableColumn validCol = new TableColumn("Total Correct Attempts");
                    
                    
                }
                
                backBtn.setOnAction(e -> {
                    this.getChildren().clear();
                    doStuff();
                });
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Something went wrong with displayUserReport.");
            }
            finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            */
        }
        
        public void getSections(int index) {  
            cbo2.getItems().clear();
            cbo2.setValue("Sections: ");
            cbo3.getItems().clear();
            cbo3.setValue("Questions: ");
            sArr.clear();
            noSect = true;
            try {
              if ((index+1) > 0) {
                String ch = "chapter" + Integer.toString(index+1) + ".txt";
                BufferedReader br = new BufferedReader(new FileReader(address + ch));
                br.readLine();
                br.readLine();
                String currentLine;
                String s = null;
                 
                while ((currentLine = br.readLine()) != null) {
                    //if the line begins with Section...
                    if (currentLine.matches("^(Section.*)")) {
                        noSect = false;
                        String o;
                        br.mark(1000); //marks current place in file
                        if (!(o = br.readLine()).matches("^(Section\\s.*)")) {
                            String stg = currentLine.substring(currentLine.indexOf(' ') + 1).trim();
                            if (stg.matches("^(\\d.*)")) {
                                //save section number to s
                                s = stg.replaceAll("[^0-9\\.\\-]", "");
                                sArr.add(s);
                            } else {
                                //save section number to s
                                s = stg;
                                sArr.add(s);
                            }
                        }
                        br.reset(); //resets back to last mark
                    }
                }
                br.close(); //close BufferedReader
                
                //section placeholder for chapters with no sections
                if (noSect) {
                    sArr.add("--");
                    //if someone can think of something better to put here for 
                    //the placeholder please don't hesitate to change it
                }
                
                //put section numbers into cbo2
                String[] n = new String[sArr.size()];
                sArr.toArray(n);
                items2 = FXCollections.observableArrayList(n);
                cbo2.getItems().addAll(items2);
                
              }       
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
            } 
        }
        
        public void getQuestions(int chapt, int sect) {
            try {
                cbo3.getItems().clear();
                cbo3.setValue("Questions: ");
                qArr.clear();
                
             if ((chapt+1) > 0) {   
                String ch = "chapter" + Integer.toString(chapt+1) + ".txt";
                BufferedReader br = new BufferedReader(new FileReader(address + ch));
                String currentLine;
                //will hold the question numbers
                ArrayList<String> qNums = new ArrayList<String>(0);
                int sInt = -1; //temp for section int
                String s = null; //temp for section double
                String quests = null; //temp for question/ask
                String k = null; //temp for key
                String h = null; //temp for hint
                //will temporarily hold the answers for each question
                ArrayList<String> opts = new ArrayList<String>(0);
                
                if (noSect) {
                    sInt++; //allows code below to still work even with no sections
                }
                while ((currentLine = br.readLine()) != null) {
                    //if the line begins with Section or chapter has no sections...
                    if (currentLine.matches("^(Section\\s.*)|(Sections\\s.*)")
                            || noSect ) {
                        String p;
                        br.mark(1000); //marks current place in file
                        if (!(p = br.readLine()).matches("^(Section\\s.*)")
                            && !noSect) {
                            String stg = currentLine.substring(currentLine.indexOf(' ')+1).trim();
                            if (stg.matches("^(\\d.*)")) {
                                //save section number to s
                                s = stg.replaceAll("[^0-9\\.\\-]", "");
                                sInt++;
                            }
                            else {
                                //save section name to s
                                s = stg;
                                sInt++;
                            }
                        }
                        
                        br.reset(); //resets back to last mark
                        //gets questions from specified section
                        if (sInt == sect) {
                            while ((currentLine = br.readLine()) != null) {
                                //if the line begins with Section...
                                if (currentLine.matches("^(Section\\s.*)")) {
                                    break;
                                } //if the line begins with #...
                                else if (currentLine.matches("(\\#).*")) {
                                    //saves previous data in new Question and adds to qArr 
                                    if (quests != null) {
                                        if (noSect) {
                                            s = Integer.toString(chapt+1) + ".00";
                                        }
                                        Question q = new Question(quests, opts, k, s, sInt, h);
                                        qArr.add(q);
                                        opts.clear();
                                        quests = null;
                                        k = null;
                                        h = null;
                                    }
                                } //if line is one of the answer options...
                                else if (currentLine.matches("^((a\\.)|(b\\.)|(c\\.)|(d\\.)|(e\\.)|(f\\.)).*")) {
                                    //accounts for multi-line answers
                                    String o;
                                    br.mark(1000); //marks current place in file
                                    while (!(o = br.readLine()).matches("^((a\\.)|(b\\.)|(c\\.)|(d\\.)|(e\\.)|(f\\.)|(Key:)).*")) {
                                        currentLine += ("\n" + "   " + o);
                                    }
                                    br.reset(); //resets back to last mark
                                    opts.add(currentLine.trim());
                                } //if line begins with Key:...
                                else if (currentLine.matches("^(Key:).*")) {
                                    String str = currentLine.replaceAll("(Key:)", "");
                                    str.trim();
                                    if (str.length() > 5) {
                                        //saves key to k and hint to h
                                        k = str.substring(0, str.indexOf(' '));
                                        h = str.substring(str.indexOf(' ')+1).trim();
                                    } else {
                                        //if no hint, just saves key to k
                                        k = str;
                                    }
                                } //if line states the question...
                                else if (currentLine.matches("^(\\d{1,2}\\.\\s).*")) {
                                    //add question number to qNums
                                    String num = currentLine.substring(0, currentLine.indexOf('.'));
                                    qNums.add(num);
                                    //accounts for multi-line questions
                                    String o;
                                    br.mark(1000); //mark current place
                                    while (!(o = br.readLine()).matches("^((a\\.)|(b\\.)|(c\\.)|(d\\.)|(e\\.)|(f\\.)).*")) {
                                        currentLine += ("\n" + "   " + o);
                                    }
                                    br.reset(); //resets back to last mark
                                    quests = currentLine;
                                }
                                String nextLine;
                                br.mark(1000); //marks current place in file
                                //checks if this is last line of file
                                //if it is saves last question
                                if (((nextLine = br.readLine()) == null) && 
                                        (quests != null)) {
                                    if (noSect) {
                                            s = Integer.toString(chapt+1) + ".00";
                                        }
                                    Question q = new Question(quests, opts, k, s, sInt, h);
                                    qArr.add(q);
                                    opts.clear();
                                    quests = null;
                                    k = null;
                                    h = null;
                                }
                                br.reset(); //resets back to last mark
                                
                            }
                        }
                    }
                }
                br.close(); //close BufferedReader
                //put question numbers into cbo3
                String[] n = new String[qNums.size()];
                qNums.toArray(n);
                items3 = FXCollections.observableArrayList(n);
                cbo3.getItems().addAll(items3);
            }  
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            
        }
        
        public void displayQuestion(int num) {
            this.getChildren().clear(); //clear pane
            HBox backPane = new HBox(); //pane for back button
            backPane.setPadding(new Insets(5, 0, 3, 0));
            Button backBtn = new Button("Back to Selection Menu");
            backPane.getChildren().add(backBtn);
            this.setTop(backPane);
            
            Label quest = new Label(qArr.get(num).getAsk()); //holds question
            quest.setMaxWidth(300);
            quest.setWrapText(true);
            ScrollPane questPane = new ScrollPane(); //pane for question
            questPane.setPadding(new Insets(3, 5, 3, 5));
            questPane.setMaxHeight(375);
            questPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            VBox qv = new VBox(quest);
            questPane.setContent(qv);
            
            String[] answers = qArr.get(num).getOptions();
            ScrollPane ansPane = new ScrollPane();
            ansPane.setMaxHeight(150);
            VBox v = new VBox(); 
            v.setSpacing(10);
            CheckBox[] checkList = new CheckBox[answers.length];
            ToggleGroup group = new ToggleGroup();
            if (qArr.get(num).getKey().length() == 1) {
                //dynamically generate radiobuttons
                for (int i = 0; i < answers.length; i++) {
                    RadioButton rb = new RadioButton(answers[i] + "\n");
                    rb.setToggleGroup(group);
                    rb.setMaxWidth(300);
                    rb.setWrapText(true);
                    rb.setUserData(answers[i].substring(0, answers[i].indexOf('.')).trim());
                    v.getChildren().add(rb);
                }
            }
            else {
                //dynamically generate checkboxes
                for (int i = 0; i < answers.length; i++) {
                    CheckBox cb = new CheckBox(answers[i] + "\n");
                    cb.setMaxWidth(300);
                    cb.setWrapText(true);
                    cb.setUserData(answers[i].substring(0, answers[i].indexOf('.')).trim());
                    checkList[i] = cb;
                    v.getChildren().add(cb);
                }
            }
            
            ansPane.setContent(v);
            
            Button subBtn = new Button("Submit");
            
            
            //clear Section and Question data and return to selection menu
            backBtn.setOnAction(e -> {
                items2.clear();
                items3.clear();
                cbo2.getItems().clear();
                cbo3.getItems().clear();
                qArr.clear();
                this.getChildren().clear();
                doStuff();
            });
            
            VBox vPane = new VBox(questPane, ansPane);
            vPane.setSpacing(0);
            VBox centerPane = new VBox(vPane);
            centerPane.setAlignment(Pos.CENTER);
            centerPane.setSpacing(20);
            
            //show hint button and panes
            if (qArr.get(num).getHint() != null) {
                HBox sHPane = new HBox(); //will hold hint button and submit button
                sHPane.setAlignment(Pos.CENTER);
                sHPane.setSpacing(10);
                VBox vSHPane = new VBox(); //will hold sHPane and hintPane
                vSHPane.setMaxWidth(280);
                vSHPane.setAlignment(Pos.CENTER);
                ToggleButton hintBtn = new ToggleButton("Show/Hide Hint: ");
                ScrollPane hintPane = new ScrollPane();
                hintPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                hintPane.setMinHeight(0);
                hintPane.setMaxHeight(50);
                VBox hintVPane = new VBox();
                hintPane.setContent(hintVPane);
                sHPane.getChildren().addAll(hintBtn, subBtn);
                vSHPane.getChildren().addAll(sHPane, hintPane);
                centerPane.getChildren().add(vSHPane);
                hintBtn.setOnAction(e -> {
                    if (hintBtn.isSelected()) {
                        Label lb2 = new Label(qArr.get(num).getHint());
                        lb2.setWrapText(true);
                        lb2.setMaxWidth(245);
                        hintVPane.getChildren().add(lb2);
                    }
                    else {
                        hintVPane.getChildren().clear();
                    }
                });
            }
            else {
                centerPane.getChildren().add(subBtn);
            }
            
            Label lb2 = new Label(""); //will display answer feedback
            centerPane.getChildren().add(lb2);
            
            
            //Submit + Answer Feedback
            subBtn.setOnAction(e -> {
                
                    String qid = "";
                    //if chapter number is less than 10, adds 0 to front
                    if (qArr.get(num).getSection().indexOf('.') < 2) {
                        qid = "0" + qArr.get(num).getSection().substring(0, 
                                (qArr.get(num).getSection().indexOf('.')+1));
                    }
                    else {
                        qid = qArr.get(num).getSection().substring(0, 
                                (qArr.get(num).getSection().indexOf('.')+1));
                    }
                    //holds just the section number
                    String temp = qArr.get(num).getSection().substring(
                                qArr.get(num).getSection().indexOf('.')+1);
                    //if section number is less than 10, adds 0 in front of it
                    if (temp.length() < 2) {
                        qid += "0" + temp;
                    }
                    else {
                        qid += temp;
                    }
                    //if question number is less than 10, adds zero in front of it
                    if (num < 9){
                        qid += ".0" + Integer.toString(num+1);
                    }
                    else {
                        qid += "." + Integer.toString(num+1);
                    }
                    
                    //for single answer keys
                    if (qArr.get(num).getKey().length() == 1) {
                        if (group.getSelectedToggle() == null) {
                            lb2.setText("Choose an answer.");
                            lb2.setTextFill(Color.DARKSLATEGREY);
                        } else {
                            lb2.setText("");
                            String ans = group.getSelectedToggle().getUserData().toString();
                            String k = qArr.get(num).getKey();
                            if (k.equals(ans)) {
                                lb2.setText("Correct!");
                                lb2.setTextFill(Color.GREEN);
                                updateDB(qid, num, "true");
                            } else {
                                lb2.setText("Incorrect.");
                                lb2.setTextFill(Color.RED);
                                updateDB(qid, num, "false");
                            }
                        }
                    } //for multiple answer keys
                    else {
                        String ans = "";
                        for (int i = 0; i < checkList.length; i++) {
                            if (checkList[i].isSelected()) {
                                ans += checkList[i].getUserData().toString();
                            }
                        }
                        if (ans == "") {
                            lb2.setText("Choose an answer.");
                            lb2.setTextFill(Color.DARKSLATEGREY);
                        } else {
                            lb2.setText("");
                            String k = qArr.get(num).getKey();
                            if (k.equals(ans)) {
                                lb2.setText("Correct!");
                                lb2.setTextFill(Color.GREEN);
                                updateDB(qid, num, "true");
                            }
                            else {
                                lb2.setText("Incorrect.");
                                lb2.setTextFill(Color.RED);
                                updateDB(qid, num, "false");
                        }
                    }
                }
                
            });
            
            BorderPane btnPane = new BorderPane();
            btnPane.setPadding(new Insets(3, 0, 5, 0));
            if(num > 0) {
                Button preBtn = new Button("Previous Question");
                HBox preBtnBox = new HBox(preBtn);
                preBtnBox.setAlignment(Pos.BOTTOM_LEFT);
                btnPane.setLeft(preBtn);
                preBtn.setOnAction(e -> displayQuestion(num-1));
            }
            if(num < (qArr.size()-1)) {
                Button nxtBtn = new Button("Next Question");
                HBox nxtBtnBox = new HBox(nxtBtn);
                nxtBtnBox.setAlignment(Pos.BOTTOM_RIGHT);
                btnPane.setRight(nxtBtn);
                nxtBtn.setOnAction(e -> displayQuestion(num+1));
            }
            
            this.setCenter(centerPane);
            this.setBottom(btnPane);
            
        }
        
        public void updateDB(String qid, int num, String valid) {
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost/user", "scott", "tiger");

                //gets maximum trial number for this question for user
                String command = "SELECT trial FROM UserQuizSummary"
                        + " WHERE userName = '" + userName + "' AND "
                        + "qID = '" + qid + "'";

                Statement statement = connection.createStatement();
                rs = statement.executeQuery(command);
                int trialInt = 0;
                ArrayList<Integer> temp = new ArrayList();
                if (rs.absolute(1)) {
                    int i = 1;
                    
                    while (rs.next()) {
                        temp.add(Integer.parseInt("" + rs.getObject(i)));   
                    }
                    for (int j = 0; j < temp.size(); j++) {
                        if (temp.get(j) > trialInt) {
                            trialInt = temp.get(j);
                        }
                    }
                    trialInt++;
                    if (trialInt == 1) {
                        trialInt++;
                    }
 
                } else {
                    trialInt = 1;
                }

                command = "INSERT INTO UserQuizSummary"
                        + "( userName, password, qID, trial, isValid )"
                        + " VALUES ('" + userName + "', '" + password
                        + "', '" + qid + "', '" + trialInt + "', '" + valid + "')";
                statement.executeUpdate(command);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Something went wrong with submit.");
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        
    }
    
    static class Question {
        String ask; //holds the question
        String[] options; //holds the options for answering
        String key; //holds the key
        String section; //holds (chapter#).(section#)
        int sectInt; //holds the section variable
        String hint; //holds the hint
        
        public Question () {
            
        }
        
        public Question (String asks, ArrayList<String> optList, String k, String s, int sInt, String h) {
            ask = asks;
            Object[] temp = optList.toArray();
            options = Arrays.copyOf(temp, temp.length, String[].class);
            key = k;
            section = s;
            sectInt = sInt;
            hint = h;
        }
        
        public String getAsk() {
            return ask;
        }
        
        public String[] getOptions() {
            return options;
        }
        
        public String getKey() {
            return key;
        }
        
        public String getSection() {
            return section;
        }
        
        public int getSectInt() {
            return sectInt;
        }
        
        public String getHint() {
            return hint;
        }
    }   
}