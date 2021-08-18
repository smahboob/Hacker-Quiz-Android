package com.fandm.saad.hackerquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;
import com.fandm.saad.hackerquiz.database.QuizContract.*;
import com.fandm.saad.hackerquiz.models.Question;
import com.fandm.saad.hackerquiz.models.User;

import java.util.ArrayList;
import java.util.List;

public class QuizDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HackerQuiz.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    //constructor
    public QuizDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //on create is called when the first time u call the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserTable.TABLE_NAME + " ( " +
                UserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.COLUMN_USER_FULL_NAME + " TEXT, " +
                UserTable.COLUMN_USER_PHONE_ID + " TEXT, " +
                UserTable.COLUMN_USER_PYTHON_EASY_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_PYTHON_MED_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_PYTHON_HARD_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_JAVA_EASY_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_JAVA_MED_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_JAVA_HARD_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_CPP_EASY_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_CPP_MED_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_CPP_HARD_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_OOP_EASY_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_OOP_MED_SCORE + " TEXT, " +
                UserTable.COLUMN_USER_OOP_HARD_SCORE + " TEXT" + ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_1 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_2 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_4 + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY + " TEXT, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CORRECT_ANSWER + " INTEGER" + ")";

        //executes the above SQL statement
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        fillQuestionsTable();
    }

    //change database version or uninstall if u update database questions
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    //add a new user to the user table
    public void add_new_user(User user){

        this.db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserTable.COLUMN_USER_FULL_NAME, user.getFull_name());
        cv.put(UserTable.COLUMN_USER_PHONE_ID, user.getAndroid_device_id());

        cv.put(UserTable.COLUMN_USER_PYTHON_EASY_SCORE, user.getPython_score_easy());
        cv.put(UserTable.COLUMN_USER_PYTHON_MED_SCORE, user.getPython_score_medium());
        cv.put(UserTable.COLUMN_USER_PYTHON_HARD_SCORE, user.getPython_score_hard());

        cv.put(UserTable.COLUMN_USER_JAVA_EASY_SCORE, user.getJava_score_easy());
        cv.put(UserTable.COLUMN_USER_JAVA_MED_SCORE, user.getJava_score_medium());
        cv.put(UserTable.COLUMN_USER_JAVA_HARD_SCORE, user.getJava_score_hard());

        cv.put(UserTable.COLUMN_USER_CPP_EASY_SCORE, user.getCpp_score_easy());
        cv.put(UserTable.COLUMN_USER_CPP_MED_SCORE, user.getCpp_score_medium());
        cv.put(UserTable.COLUMN_USER_CPP_HARD_SCORE, user.getCpp_score_hard());

        cv.put(UserTable.COLUMN_USER_OOP_EASY_SCORE, user.getOop_score_easy());
        cv.put(UserTable.COLUMN_USER_OOP_MED_SCORE, user.getOop_score_medium());
        cv.put(UserTable.COLUMN_USER_OOP_HARD_SCORE, user.getOop_score_hard());

        db.insert(UserTable.TABLE_NAME, null, cv);
    }

    //this would check in the database if this user_id and user_email are already in the system
    public boolean user_already_exists(String android_device_id){
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{android_device_id};
        String query = "SELECT * FROM " + UserTable.TABLE_NAME + " WHERE " + UserTable.COLUMN_USER_PHONE_ID + " = ?";

        Cursor c = db.rawQuery(query,selectionArgs);

        boolean answer = c.getCount() != 0;
        c.close();
        return answer;
    }

    //load all questions from all category in table
    private void fillQuestionsTable(){
        loadPythonQuestions();
        loadJavaQuestions();
        loadCppQuestions();
        loadOopQuestions();
    }

    //load all the oop questions
    private void loadOopQuestions() {
        //oop easy
        Question q1 = new Question("Which one of the following is the process where an object of one class receives the properties of objects of another class?",
                "Encapsulation", "Inheritance", "Polymorphism", "Modularity",
                "oop", Question.DIFFICULTY_EASY,3);
        addQuestion(q1);

        Question q2 = new Question("What is an example of dynamic binding?",
                "Any method", "Method overloading", "Method overriding", "Compiling",
                "oop", Question.DIFFICULTY_EASY,3);
        addQuestion(q2);

        Question q3 = new Question("For which case would the use of a static attribute be appropriate?",
                "The number of people in each house in a small neighborhood", "The lot size for each house in a small neighborhood",
                "The color of each house in a small neighborhood", "The weather conditions for each house in a small neighborhood",
                "oop", Question.DIFFICULTY_EASY,4);
        addQuestion(q3);

        Question q4 = new Question("Why would you create an abstract class, if it can have no real instances?",
                "To avoid redundant coding in children", "To explore a hypothetical class",
                "To prevent unwanted method implementation", "To reserve memory for an unspecified class type",
                "oop", Question.DIFFICULTY_EASY,1);
        addQuestion(q4);

        Question q5 = new Question("When does static binding happen?",
                "Only when you export", "Both at compile time and runtime",
                "At compile time", "At runtime",
                "oop", Question.DIFFICULTY_EASY,3);
        addQuestion(q5);

        //oop medium
        Question q6 = new Question("What is encapsulation?",
                "Defining classes by focusing on what is important for a purpose", "Hiding the data and implementation details within a class",
                "Making all methods private", "Using words to define classes",
                "oop", Question.DIFFICULTY_MEDIUM,2);
        addQuestion(q6);

        Question q7 = new Question("Which code creates a new object from the Employee class?",
                "Employee current Employee = Employee.Create();", "Employee current Employee = new Employee();",
                "Employee currentEmployee;", "Employee currentEmployee = Employee.New();",
                "oop", Question.DIFFICULTY_MEDIUM,2);
        addQuestion(q7);

        Question q8 = new Question("Which type of constructor cannot have a return type?",
                "Default", "Copy",
                "Parameterized", "Constructors do not have a return type",
                "oop", Question.DIFFICULTY_MEDIUM,4);
        addQuestion(q8);

        Question q9 = new Question("Which type of inheritance ,when done continuously, is similar to a tree structure?",
                "Multilevel", "Hierarchical and multiple",
                "Hierarchical", "Multiple",
                "oop", Question.DIFFICULTY_MEDIUM,4);
        addQuestion(q9);

        Question q10 = new Question("Which of the following is NOT an advantage of using getters and setters?",
                "Getters and setters can speed up compilation.", "Getters and setters provide encapsulation of behavior.",
                "Getters and setters provide a debugging point for when a property changes at runtime.", "Getters and setters permit different access levels.",
                "oop", Question.DIFFICULTY_MEDIUM,1);
        addQuestion(q10);

        //oop hard
        Question q11 = new Question("In context of OOP, what is association?",
                "Association is a relationship where all objects have their own life cycle and there is no owner.",
                "Association is the process where model elements cooperate to provide higher-level behavior.",
                "Association is whole/part relationship where one object is composed of one or more other objects, each of which is considered a part of the whole.",
                " Association is where all objects have their own life cycle, but there is ownership, and child objects can not belong to another parent object.",
                "oop", Question.DIFFICULTY_HARD,1);
        addQuestion(q11);

        Question q12 = new Question("What is the result of using more abstraction?",
                "It can increase code vulnerability", "It can make code unsafe",
                "It can limit code readability", "It can be safer for coding",
                "oop", Question.DIFFICULTY_HARD,3);
        addQuestion(q12);

        Question q13 = new Question("Which type of function among the following shows polymorphism?",
                "Inline function", "Undefined function",
                "Virtual function", "Class member function",
                "oop", Question.DIFFICULTY_HARD,3);
        addQuestion(q13);

        Question q14 = new Question("What is the relationship between abstraction and encapsulation?",
                "Abstraction is about making relevant information visible, while encapsulation enables a programmer to implement the desired level of abstraction.",
                "Abstraction and encapsulation are essentially the same.",
                "Abstraction and encapsulation are unrelated.",
                "Encapsulation is about making relevant information visible, while abstraction enables a programmer to implement the desired level of encapsulation.",
                "oop", Question.DIFFICULTY_HARD,1);
        addQuestion(q14);

        Question q15 = new Question("There are five classes. Class E is derived from class D, D from C, C from B, and B from A. Which class constructor(s) will be called first if the object of E or D is created?",
                "C", "A",
                "B", "C and B",
                "oop", Question.DIFFICULTY_HARD,2);
        addQuestion(q15);

    }

    //load all the cpp questions
    private void loadCppQuestions() {
        //cpp easy
        Question q1 = new Question("What is a correct syntax to output 'Hello World' in C++?",
                "Console.WriteLine('Hello World')", "print('Hello World')", "cout << 'Hello World'", "System.out.println('Hello World')",
                "cpp", Question.DIFFICULTY_EASY,3);
        addQuestion(q1);

        Question q2 = new Question("Which data type is used to create a variable that should store text?",
                "String", "string", "txt", "myString",
                "cpp", Question.DIFFICULTY_EASY,1);
        addQuestion(q2);

        Question q3 = new Question("Which method can be used to find the length of a string?",
                "len()", "getSize()", "length()", "getLength()",
                "cpp", Question.DIFFICULTY_EASY,3);
        addQuestion(q3);

        Question q4 = new Question("Which header file lets us work with input and output objects?",
                "#include <stream>", "#include <inputstr>", "#include <iostream>", "#include <iosstring>",
                "cpp", Question.DIFFICULTY_EASY,3);
        addQuestion(q4);

        Question q5 = new Question("To declare an array in C++, define the variable type with:",
                "()", "[]", "{}", "<>",
                "cpp", Question.DIFFICULTY_EASY,2);
        addQuestion(q5);

        //cpp medium
        Question q6 = new Question("Choose the pure virtual function definition from the following?",
                "virtual void f()=0 { }", " void virtual f()=0 { }", "virtual void f() {} = 0;", "None of the above.",
                "cpp", Question.DIFFICULTY_MEDIUM,4);
        addQuestion(q6);

        Question q7 = new Question("'cin’ is an __",
                "Class", "Object", "Package", "Namespace",
                "cpp", Question.DIFFICULTY_MEDIUM,2);
        addQuestion(q7);

        Question q8 = new Question("Objects created using new operator are stored in __ memory?",
                "Heap", "Cache", "Stack", "None of the above",
                "cpp", Question.DIFFICULTY_MEDIUM,1);
        addQuestion(q8);

        Question q9 = new Question("A single line comment in C++ language source code can begin with _____?",
                ";", ":", "/*", "//",
                "cpp", Question.DIFFICULTY_MEDIUM,4);
        addQuestion(q9);

        Question q15 = new Question("Which of the following is not a valid ofstream argument?",
                "ios::app", "ios::trunc", "ios::create", "ios::noreplace",
                "cpp", Question.DIFFICULTY_MEDIUM,3);
        addQuestion(q15);

        //hard
        Question q10 = new Question("Which of the following is the proper declaration of a pointer?",
                "int x;", "int &x;", "ptr x;", "int *x;",
                "cpp", Question.DIFFICULTY_HARD,4);
        addQuestion(q10);

        Question q11 = new Question("Which of the following gives the memory address of integer variable a?",
                "*a;", "a;", "&a;", "address(a);",
                "cpp", Question.DIFFICULTY_HARD,3);
        addQuestion(q11);

        Question q12 = new Question("Which of the following gives the memory address of a pointer a?",
                "new", "malloc", "create", "value",
                "cpp", Question.DIFFICULTY_HARD,1);
        addQuestion(q12);

        Question q13 = new Question("Which of the following is the proper keyword to allocate memory?",
                "a;", "*a;", "&a;", "address(a);",
                "cpp", Question.DIFFICULTY_HARD,1);
        addQuestion(q13);

        Question q14 = new Question("Which of the following is the proper keyword to deallocate memory?",
                "free", "delete", "clear", "remove",
                "cpp", Question.DIFFICULTY_HARD,2);
        addQuestion(q14);

    }

    //load all java questions
    private void loadJavaQuestions() {
        //java easy
        Question q1 = new Question("What is a correct syntax to output 'Hello World' in Java?",
                "Console.WriteLine('Hello World')", "print('Hello World')", "echo('Hello World')", "System.out.println('Hello World')",
                "java", Question.DIFFICULTY_EASY, 4);
        addQuestion(q1);

        Question q2 = new Question("Java is short for 'JavaScript'",
                "False ", "True", "Javascript is a script version of Java.", "Java is a dependency for JavaScript.",
                "java", Question.DIFFICULTY_EASY, 1);
        addQuestion(q2);

        Question q3 = new Question("How do you start COMMENTS in Java code?",
                "#...", "//...", "/...", "/^...",
                "java", Question.DIFFICULTY_EASY, 2);
        addQuestion(q3);

        Question q4 = new Question("Which data type is used to create a variable named str that should store text?",
                "Text str", "String str", "string str", "Str str",
                "java", Question.DIFFICULTY_EASY, 2);
        addQuestion(q4);

        Question q5 = new Question("How do you create a variable with the numeric value 5?",
                "x = 5", "num x = 5", "int x = 5", "float x = 5",
                "java", Question.DIFFICULTY_EASY, 3);
        addQuestion(q5);

        Question q6 = new Question("How do you create a variable with the floating number 2.8?",
                "byte x = 2.8f", "float x = 2.8f", "x = 2.8f", "int x = 2.8f",
                "java", Question.DIFFICULTY_EASY, 2);
        addQuestion(q6);

        Question q7 = new Question("Which method can be used to find the length of a string?",
                "length()", "getLength()", "getSize()", "len()",
                "java", Question.DIFFICULTY_EASY, 1);
        addQuestion(q7);

        Question q8 = new Question("Which method can be used to return a string in upper case letters?",
                "upperCase()", "toUpperCase()", "to_upper_case()", "capitalize()",
                "java", Question.DIFFICULTY_EASY, 2);
        addQuestion(q8);

        Question q9 = new Question("To declare an array in Java, define the variable type with?",
                "()", "{}", "[]", "<>",
                "java", Question.DIFFICULTY_EASY, 3);
        addQuestion(q9);

        Question q10 = new Question("What is the correct way to create an object called myCar of class named Car?",
                "class Car = new myCar()", "Car new Car() = myCar", "obj myCar = new Class Car()", "Car myCar = new Car()",
                "java", Question.DIFFICULTY_EASY, 4);
        addQuestion(q10);


        //java medium
        Question q11 = new Question("What is the default value of double variable?",
                "0.0d", "0.0f", "0", "not defined",
                "java", Question.DIFFICULTY_MEDIUM, 1);
        addQuestion(q11);

        Question q12 = new Question("Which of the following is true about private access modifier?",
                "Variables, methods and constructors which are declared private can be accessed only by the members of the same class.",
                "Variables, methods and constructors which are declared private can be accessed by any class lying in same package.",
                "Variables, methods and constructors which are declared private in the superclass can be accessed only by its child class.",
                "None of the above.",
                "java", Question.DIFFICULTY_MEDIUM, 1);
        addQuestion(q12);

        Question q13 = new Question("What is an immutable object?",
                "An immutable object is an instance of an abstract class.",
                "An immutable object can't be changed once it is created.",
                "An immutable object can be changed once it is created.",
                "None of the below",
                "java", Question.DIFFICULTY_MEDIUM, 2);
        addQuestion(q13);

        Question q14 = new Question("What is an applet?",
                "Applet is a standalone java program.",
                "Applet is a tool.",
                "Applet is a run time environment.",
                "An applet is a Java program that runs in a Web browser.",
                "java", Question.DIFFICULTY_MEDIUM, 4);
        addQuestion(q14);

        Question q15 = new Question("What is synchronization?",
                "Synchronization is the process of writing the state of an object to byte stream.",
                "Synchronization is the process of writing the state of an object to another object.",
                "Synchronization is the capability to control the access of multiple threads to shared resources.",
                "None of the above.",
                "java", Question.DIFFICULTY_MEDIUM, 3);
        addQuestion(q15);

        Question q16 = new Question("Which method must be implemented by all threads?",
                "wait()", "start()", "stop()", "run()",
                "java", Question.DIFFICULTY_MEDIUM, 4);
        addQuestion(q16);

        //java hard
        Question q17 = new Question("Which of the below are reserved keyword in Java?",
                "array", "goto", "null", "number",
                "java", Question.DIFFICULTY_HARD, 2);
        addQuestion(q17);

        Question q18 = new Question("What are the valid statements for static keyword in Java?",
                "The static block in a class is executed every time an object of class is created.", "Methods cannot be static" ,
                "We can have static method implementations in interface.", "We can define static block inside a method.",
                "java", Question.DIFFICULTY_HARD, 3);
        addQuestion(q18);

        Question q19 = new Question("What is the use of final keyword in Java?",
                "When a class is made final, a sub class of it can not be created.", "When a method is final, it can not be overridden.",
                "When a variable is final, it can be assigned value only once.", "All of the above",
                "java", Question.DIFFICULTY_HARD, 4);
        addQuestion(q19);

        Question q20 = new Question("Which of the following is/are advantages of packages?",
                "Classes, even though they are visible outside their package, can have fields visible to packages only.",
                "Packages cannot avoid name clashes.",
                "We cannot have hidden classes that are used by the packages, but not visible outside.", "All of the above",
                "java", Question.DIFFICULTY_HARD, 1);
        addQuestion(q20);

        Question q21 = new Question("Java programs are?",
                "Faster than others", "Platform independent", "Not reusable", "Not scalable",
                "java", Question.DIFFICULTY_HARD, 2);
        addQuestion(q21);

    }

    //this creates new question objects and adds them to the database
    private void loadPythonQuestions() {
        //python easy
        Question q1 = new Question("What is a correct syntax to output \"Hello World\" in Python?",
                "echo(\"Hello World\")", "touch(\"Hello World\")", "print(\"Hello World\")", "std:out(\"Hello World\")",
                "python", Question.DIFFICULTY_EASY,3);
        addQuestion(q1);

        Question q2 = new Question("How do you create a variable with the floating number 2.8?",
                "x = floating_number(2.8)", "x = 2.8", "x = \"2.8\"", "All of the above",
                "python", Question.DIFFICULTY_EASY,2);
        addQuestion(q2);

        Question q3 = new Question("What is a correct syntax to return the first character in a string?",
                "x = sub(\"HelloWorld\")", "x = \"HelloWorld\"[0]", "sub(\"HelloWorld\",0,1)", "\"HelloWorld\"[-1]",
                "python", Question.DIFFICULTY_EASY,2);
        addQuestion(q3);

        Question q4 = new Question("Which operator can be used to compare two values?",
                "<>", "><", "==", "=",
                "python", Question.DIFFICULTY_EASY, 3);
        addQuestion(q4);

        Question q5 = new Question("L1 = [20, 40, 60, 80] \n L2 = [20, 40, “60”, 80]?",
                "L1 and L2 are equal", "L1 and L2 hold same types", "L1 and L2 are not equal", "L2 is illegal list type",
                "python", Question.DIFFICULTY_EASY, 3);
        addQuestion(q5);

        Question q6 = new Question("Consider the following for loop \n for x in range(0.5, 5.5, 0.5):?",
                "Last value of x is 0.6", "Last value of x is 6", "Each loop skips the value of x by 5.5", "Last Value of x is 5.5",
        "python", Question.DIFFICULTY_EASY, 4);
        addQuestion(q6);

        Question q7 = new Question("How to declare a set in python?",
                "x = set()", "x = list.as_set()", "x = {}", "x = hashset()",
                "python", Question.DIFFICULTY_EASY, 4);
        addQuestion(q7);

        Question q8 = new Question("What is the difference between a list and a tuple?",
                "Tuple is immutable and a list is not", "Tuple is mutable and a list is not", "Both are same", "Tuple cannot store strings unlike lists.",
                "python", Question.DIFFICULTY_EASY, 1);
        addQuestion(q8);

        Question q9 = new Question("Which of the following is true about dictionaries?",
                "Every item must be of the same type", "Item values can be updated", "A key value cannot be a booleans.", "A dictionary can store multiple items with same key.",
                "python", Question.DIFFICULTY_EASY, 2);
        addQuestion(q9);

        Question q10 = new Question("In a regular expression the asterisks (*) specifies?\"",
                "Matches any whitespace characters", "zero or more repetitions of the preceding RE", "Matches any non-alphanumeric character", "Find all substrings where the RE matches, and returns them as a list.",
                "python", Question.DIFFICULTY_EASY, 2);
        addQuestion(q10);


        //python medium
        Question q11 = new Question("Which operator has higher precedence in the following list?",
                "% (Modulus)", "** (Exponent)", "& (BitWise AND)", "> (Comparison)",
                "python", Question.DIFFICULTY_MEDIUM, 2);
        addQuestion(q11);

        Question q12 = new Question("Which of the following are objects of built-in type that are mutable?",
                "Lists", "Strings", "Tuples", "User Input",
                "python", Question.DIFFICULTY_MEDIUM, 1);
        addQuestion(q12);

        Question q13 = new Question("Which of the following is raised when a generated error does not fall into any category?",
                "RuntimeError", "SystemError", "NotImplementedError", "TypeError",
                "python", Question.DIFFICULTY_MEDIUM, 1);
        addQuestion(q13);

        Question q14 = new Question("Which of the following is the Base class for all errors that occur for numeric calculation?",
                "StandardError", "ZeroDivisionError", "FloatingPointError", "ArithmeticError",
                "python", Question.DIFFICULTY_MEDIUM, 4);
        addQuestion(q14);

        Question q15 = new Question("Which set method will keep only the items that are present in both sets?",
                "intersection_update() ", "symmetric_difference_update()", "discard()", "issubset()",
                "python", Question.DIFFICULTY_MEDIUM, 1);
        addQuestion(q15);

        Question q16 = new Question("What is true about exceptions in Python?",
                "Python does not allow you to create your own exceptions.", "Exceptions are strings.", "Exceptions are objects.", "There are no exceptions for invalid input.",
                "python", Question.DIFFICULTY_MEDIUM, 3);
        addQuestion(q16);

        Question q17 = new Question("What purpose does difflib from python standard library serve?",
                "This module provides different libraries required to run a function.", "This module provides the git difference in libraries of two similar files.", "This module is part of git standard library, not python.", "This module provides classes and functions for comparing sequences",
                "python", Question.DIFFICULTY_MEDIUM, 4);
        addQuestion(q17);

        Question q18 = new Question("How is pprint different from print?",
                "Both are the same.", "pprint can sort an array of numbers unlike print.", "pprint provides capability to pretty-print arbitrary Python data structure.","print can output more than 500 lines unlike pprint",
                "python", Question.DIFFICULTY_MEDIUM, 3);
        addQuestion(q18);

        Question q19 = new Question("What is true in Python?",
                "The glob module finds all the path names matching a specified pattern.", "You are not allowed to put multiple different types of elements in a list.", "Python sets are ordered and no repeated values are allowed.", "A module named pickle is used for image resizing.",
                "python", Question.DIFFICULTY_MEDIUM, 1);
        addQuestion(q19);

        Question q20 = new Question("What does the datetime module provide with?",
                "The datetime module return an object with only today's date and current time.", "The datetime module supplies classes for manipulating dates and times.", "The datetime module supplies exact GPS coordinates of current user.", "The datetime module returns a list of reminders from google calender.",
                "python", Question.DIFFICULTY_MEDIUM, 2);
        addQuestion(q20);


        //python hard
        Question q21 = new Question("Select the correct code to create a button under a parent window with command processButton?",
                "Button(set.text= ''Hello'' )", "Button(window ,text= ''Ok'' ,fg= ''black '')", "Button(window ,text= ''Hello'' ,command=processButton)", "Button(text= ''hello'' ,command=processButton)",
                "python", Question.DIFFICULTY_HARD, 3);
        addQuestion(q21);

        Question q22 = new Question("Create a class named Car, which will inherit the properties and methods from the Automobile class?",
                "class Automobile(Car):", "class Car(Automobile):", "Automobile Class(Car):", "class Car(inherit=Automobile)",
                "python", Question.DIFFICULTY_HARD, 2);
        addQuestion(q22);

        Question q23 = new Question("Which of the following commands would return the 7 first characters of the file in variable f?",
                "f = open(myFile.txt, \"r\").readLine(7)", "f = open(myFile.txt, \"r\").read(7)", "f = open(myFile.txt, \"r\").reachCharArray(7)", "f = open(myFile.txt, \"r\")[7]",
                "python", Question.DIFFICULTY_HARD, 2);
        addQuestion(q23);

        Question q24 = new Question("What does the following function return: \n x = lambda a : a + 10?",
                "Loop over argument a 10 times", "Take a negative a argument and convert it to a positive integer", "Multiply argument a by 2, and add 10 to the result.", "Add 10 to argument a, and return the result",
                "python", Question.DIFFICULTY_HARD, 4);
        addQuestion(q24);

        Question q25 = new Question("What does ~~~~~~5 evaluate to?",
                "+5", "-11", "+11", "-5",
                "python", Question.DIFFICULTY_HARD, 1);
        addQuestion(q25);

        Question q26 = new Question("What is a simple but incomplete version of a function?",
                "Function", "Stub", "A function developed using bottom-up approach.", "A function developed using top-down approach.",
                "python", Question.DIFFICULTY_HARD, 2);
        addQuestion(q26);

        Question q27 = new Question("Which of the following is correct about Python?",
                "It supports automatic garbage collection.", "It can be easily integrated with C, C++, COM, ActiveX, CORBA, and Java.", "Both of the above.", "None of the above.",
                "python", Question.DIFFICULTY_HARD, 3);
        addQuestion(q27);

        Question q28 = new Question("Which of the following statement(s) is TRUE?\nA hash function takes a message of arbitrary length and generates a fixed length code.\nA hash function takes a message of fixed length and generates a code of variable length.\nA hash function may give the same hash value for distinct messages.\n",
                "I only", "II and III only", "II only", "I and III only",
                "python", Question.DIFFICULTY_HARD, 4);
        addQuestion(q28);

        Question q29 = new Question("Which operator is overloaded by the or() function?",
                "|", "||", "//", "/",
                "python", Question.DIFFICULTY_HARD, 1);
        addQuestion(q29);

        Question q30 = new Question("Which function overloads the >> operator?",
                "more()", "gt()", "ge()", "None of the above",
                "python", Question.DIFFICULTY_HARD, 4);
        addQuestion(q30);




    }

    //this inserts the questions into the database
    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion_statement());
        cv.put(QuestionsTable.COLUMN_ANSWER_1, question.getAnswer_1());
        cv.put(QuestionsTable.COLUMN_ANSWER_2, question.getAnswer_2());
        cv.put(QuestionsTable.COLUMN_ANSWER_3, question.getAnswer_3());
        cv.put(QuestionsTable.COLUMN_ANSWER_4, question.getAnswer_4());
        cv.put(QuestionsTable.COLUMN_CATEGORY, question.getCategory());
        cv.put(QuestionsTable.COLUMN_CORRECT_ANSWER, question.getCorrect_answer());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    //this gets all the questions from the database
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String query = "SELECT * FROM " + QuestionsTable.TABLE_NAME;
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion_statement(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAnswer_1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_1)));
                question.setAnswer_2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_2)));
                question.setAnswer_3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_3)));
                question.setAnswer_4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_4)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setCorrect_answer(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CORRECT_ANSWER)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    //questions of certain type only
    public ArrayList<Question> getQuestionOfType(String category) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{category};
        String query = "SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.COLUMN_CATEGORY + " = ?";
        Cursor c = db.rawQuery(query,selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion_statement(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAnswer_1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_1)));
                question.setAnswer_2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_2)));
                question.setAnswer_3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_3)));
                question.setAnswer_4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_4)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCorrect_answer(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CORRECT_ANSWER)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    //questions of certain difficulty only
    public ArrayList<Question> getQuestionsOfDifficulty(String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        String query = "SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?";
        Cursor c = db.rawQuery(query,selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion_statement(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAnswer_1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_1)));
                question.setAnswer_2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_2)));
                question.setAnswer_3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_3)));
                question.setAnswer_4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_4)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCorrect_answer(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CORRECT_ANSWER)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    //this method extracts out the questions with the given difficulty and category
    public ArrayList<Question> getQuestionsOfCategoryAndDifficulty(String difficulty, String category) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty,category};
        String query = "SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ? " + " AND " + QuestionsTable.COLUMN_CATEGORY + " = ?";

        Cursor c = db.rawQuery(query,selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion_statement(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAnswer_1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_1)));
                question.setAnswer_2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_2)));
                question.setAnswer_3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_3)));
                question.setAnswer_4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_4)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCorrect_answer(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CORRECT_ANSWER)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    //get full user information provided their phone id
    public User getFullUserInformation(String android_id){
        User user = new User();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{android_id};
        String query = "SELECT * FROM " + UserTable.TABLE_NAME + " WHERE " + UserTable.COLUMN_USER_PHONE_ID + " = ?";
        Cursor c = db.rawQuery(query,selectionArgs);

        if (c.moveToFirst()) {
            user.setFull_name((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_FULL_NAME))));
            user.setAndroid_device_id((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_PHONE_ID))));

            user.setPython_score_easy((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_PYTHON_EASY_SCORE))));
            user.setPython_score_medium((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_PYTHON_MED_SCORE))));
            user.setPython_score_hard((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_PYTHON_HARD_SCORE))));

            user.setJava_score_easy((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_JAVA_EASY_SCORE))));
            user.setJava_score_medium((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_JAVA_MED_SCORE))));
            user.setJava_score_hard((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_JAVA_HARD_SCORE))));

            user.setCpp_score_easy((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_CPP_EASY_SCORE))));
            user.setCpp_score_medium((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_CPP_MED_SCORE))));
            user.setCpp_score_hard((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_CPP_HARD_SCORE))));

            user.setOop_score_easy((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_OOP_EASY_SCORE))));
            user.setOop_score_medium((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_OOP_MED_SCORE))));
            user.setOop_score_hard((c.getString(c.getColumnIndex(UserTable.COLUMN_USER_OOP_HARD_SCORE))));
        }
        c.close();
        return user;
    }

    //update the user score after each activity finishes
    public void updateUserScore(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserTable.COLUMN_USER_FULL_NAME, user.getFull_name());
        cv.put(UserTable.COLUMN_USER_PHONE_ID, user.getAndroid_device_id());
        cv.put(UserTable.COLUMN_USER_PYTHON_EASY_SCORE, user.getPython_score_easy());
        cv.put(UserTable.COLUMN_USER_PYTHON_MED_SCORE, user.getPython_score_medium());
        cv.put(UserTable.COLUMN_USER_PYTHON_HARD_SCORE, user.getPython_score_hard());
        cv.put(UserTable.COLUMN_USER_JAVA_EASY_SCORE, user.getJava_score_easy());
        cv.put(UserTable.COLUMN_USER_JAVA_MED_SCORE, user.getJava_score_medium());
        cv.put(UserTable.COLUMN_USER_JAVA_HARD_SCORE, user.getJava_score_hard());
        cv.put(UserTable.COLUMN_USER_CPP_EASY_SCORE, user.getCpp_score_easy());
        cv.put(UserTable.COLUMN_USER_CPP_MED_SCORE, user.getCpp_score_medium());
        cv.put(UserTable.COLUMN_USER_CPP_HARD_SCORE, user.getCpp_score_hard());
        cv.put(UserTable.COLUMN_USER_OOP_EASY_SCORE, user.getOop_score_easy());
        cv.put(UserTable.COLUMN_USER_OOP_MED_SCORE, user.getOop_score_medium());
        cv.put(UserTable.COLUMN_USER_OOP_HARD_SCORE, user.getOop_score_hard());

        String whereClause = UserTable.COLUMN_USER_PHONE_ID +"='";
        database.update(UserTable.TABLE_NAME, cv, whereClause+user.getAndroid_device_id()+"'", null);
        database.close();
    }

}
