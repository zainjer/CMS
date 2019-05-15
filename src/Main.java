import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("\n\t\t~CLASS MANAGEMENT SYSTEM~");
        UserSelect();
    }
    static void UserSelect() {
        System.out.println("\n-----------USER ROLE------------");
        System.out.println("\tPlease select an option");
        System.out.println("1. Teacher Login");
        System.out.println("2. Student Login");
        System.out.println("0. Exit");
        int option = OptionSelect(2);
        switch (option){
            case 1:
                TeacherLogin();
                break;
            case 2:
                StudentLogin();
                break;
            case 0:
                quit();
                break;
        }
    }
    static int OptionSelect(int highestOptionNumber){
        System.out.print("~: ");
        Scanner takeInput = new Scanner(System.in);
        int loginOption=-1;
        try {
            loginOption = Integer.parseInt(takeInput.nextLine());
            if (loginOption>highestOptionNumber||loginOption<0){
                System.out.println("Please select a valid option:");
                OptionSelect(highestOptionNumber);
                return loginOption;
            }
        }
        catch (Exception e)
        {
            System.out.println("Please Enter a numeric value:");
            OptionSelect(highestOptionNumber);
        }

        return loginOption;
    }
    static void StudentLogin() {
        Scanner x = OpenFile("C:\\CMS\\studentLogin.txt");
        String StudentPass="";
        int StudentId=-1;
        System.out.println("\n-------------Student Login-------------");
        System.out.println("~Enter 0 to go back~");
        System.out.println("Enter your Student ID Number: ");
        int id = OptionSelect(1000);
        if (id==0)
            UserSelect();
        System.out.println("Enter your password");
        Scanner pass = new Scanner(System.in);
        String password = pass.nextLine();
        if (password=="0")
            UserSelect();

        while (x.hasNext()){
           StudentId = Integer.parseInt(x.next());
           StudentPass = x.next();
           if(StudentId == id){
               break;
           }
        }
        x.close();
        if(StudentPass.equals(password)){
            StudentLoginMenu(id);
        }
        else{
            System.out.println("Invalid Student Id or password. Please Try again.");
            StudentLogin();
        }
        x.close();

    }
    static void TeacherLogin() {
        Scanner x = OpenFile("C:\\CMS\\teacherLogin.txt");
        String s = x.next();
        x.close();
        System.out.println("=========TEACHER LOGIN=======");
        System.out.println("~Enter 0 to go back~");
        System.out.println("Enter your Password:");
        Scanner pass = new Scanner(System.in);
        String password = pass.nextLine();
        if(password=="0")
            UserSelect();
        if(s.equals(password)){
            System.out.println("\t\tLOGIN SUCCESSFUL! ~ Weclome Teacher");
            TeacherLoginMenu();
        }
        else{
            System.out.println("Invalid Password, Please try again...");
            TeacherLogin();
        }
    }
    static void StudentLoginMenu(int id) {
        Scanner x = OpenFile("C:\\CMS\\records\\0"+id+".txt");
        String[] records= new String[14];
        int i = 0;
        while(x.hasNext()){
            records[i]=x.nextLine();
            i++;
        }
        x.close();
        System.out.println("\n==Welcome '"+records[0]+"' to Student Menu!==");
        System.out.println("1. Get Records of PHY");
        System.out.println("2. Get Records of CHEM");
        System.out.println("3. Get Records of URDU");
        System.out.println("4. Change Password");

        System.out.println("0. Logout");
        int option = OptionSelect(4);
        switch (option){
            case 1:
                System.out.println("======PHYSICS======");
                System.out.println(records[1]+"\n"+records[3]+"\n"+records[4]+"\n"+records[5]);
                System.out.println("*******************");
                StudentLoginMenu(id);
            case 2:
                System.out.println("======CHEM======");
                System.out.println(records[1]+"\n"+records[7]+"\n"+records[8]+"\n"+records[9]);
                System.out.println("****************");
                StudentLoginMenu(id);
            case 3:
                System.out.println("======URDU======");
                System.out.println(records[1]+"\n"+records[11]+"\n"+records[12]+"\n"+records[13]);
                System.out.println("****************");
                StudentLoginMenu(id);
            case 4:
                changePasswordS(id);
                StudentLoginMenu(id);
            case 0:
                UserSelect();
        }
    }

    private static void changePasswordS(int id) {
        String[] data = GetDataFromFile("C:\\CMS\\studentLogin.txt",getNTotalStudents());
        System.out.println("Enter new password: ");
        Scanner in = new Scanner(System.in);
        String newp = in.nextLine();
        data[id-1] = id+" "+newp;
        int i = 0;
        Formatter z;
        try{
            z= new Formatter("C:\\CMS\\studentLogin.txt");
            i=0;
            for (String ss:data){
                if(data[i]!=null)
                    z.format(data[i]+"\n");
                i++;
            }
            z.close();
            System.out.println(" Your password is changed to: "+newp);
        }
        catch (Exception e){
            System.out.println("Record not found deleted");
        }
    }

    static void TeacherLoginMenu() {

        System.out.println("\n====Teacher Login Menu====");
        System.out.println("1. Fetch a Student Record");
        System.out.println("2. Add a new Student");
        System.out.println("3. Update record of a student");
        System.out.println("4. Fetch All students list");
        System.out.println("5. Delete a Student");
        System.out.println("6. Change your password");
        System.out.println("0. Logout to Login Menu");
        int option = OptionSelect(6);
        switch (option){
            case 1:
                System.out.println("Enter Student ID: ");
                int id = OptionSelect(1000);
                String[] s =FetchRecord(id);
                System.out.println("-------------------------");
                System.out.print("Student Name: ");
                for (String ss : s)
                    System.out.println(ss);
                System.out.println("-------------------------");
                TeacherLoginMenu();
                break;
            case 2:
                AddNewStudent();
                TeacherLoginMenu();
                break;
            case 3:
                System.out.print("\nEnter Student ID: ");
                int i = OptionSelect(1000);
                UpdateRecordMenu(i);
                break;
            case 4:
                FetchStudentList();
                TeacherLoginMenu();
                break;
            case 5:
                System.out.println("Enter Student ID");
                int iid = OptionSelect(1000);
                deleteStudent(iid);
                TeacherLoginMenu();
            case 6:
                ChangeTeacherPassword();
                TeacherLoginMenu();
            case 0:
                UserSelect();
                break;
        }
}

    static void ChangeTeacherPassword() {
        Scanner x = OpenFile("C:\\CMS\\teacherLogin.txt");
        String s = x.next();
        x.close();
        System.out.println("Enter Old password: ");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        if(input.equals(s)){
            System.out.println("Enter new password: ");
            input = in.nextLine();
            Formatter m;
            try{
                m = new Formatter("C:\\CMS\\teacherLogin.txt");
                m.format(input);
                m.close();
            }
            catch (Exception e ){

            }
            System.out.println("Password Changed Successfully!");
        }
        else{
            System.out.println("Incorrect Old Password!");

        }

    }

    static void deleteStudent(int id) {
        Scanner x = OpenFile("C:\\CMS\\records\\0"+id+".txt");
        System.out.println("Do you want to delete "+x.nextLine()+"'s Record?");
        x.close();
        System.out.print("Y/N: ");
        Scanner y =  new Scanner(System.in);
        String yn = y.nextLine();
        if(yn.equals("Y")||yn.equals("y")){
          File file = new File("C:\\CMS\\records\\0"+id+".txt");
            file.delete();
            Scanner t = OpenFile("C:\\CMS\\studentLogin.txt");
            String[] record = new String[getNTotalStudents()];
            int i =0;
            String check="",ID=String.valueOf(id);
            while (t.hasNext()){
                if(i==getNTotalStudents())
                    break;
                else{
                    check = t.nextLine();
                    if(check.indexOf(ID)!=-1){
                        i--;
                    }
                    else
                        if(check!=null){
                            record[i]=check;
                        }
                    i++;
                }
            }
            t.close();
            Formatter z;
            try{
                z= new Formatter("C:\\CMS\\studentLogin.txt");
                i=0;
                for (String ss:record){
                    if(record[i]!=null)
                        z.format(record[i]+"\n");
                        i++;
                }
                z.close();
                }
            catch (Exception e){
                System.out.println("Record not found deleted");
            }
            }
        else if(yn.equals("N")||yn.equals("n")){
            System.out.println("Record was not deleted");
        }else{
            System.out.print("Please Enter a valid Input: ");
            deleteStudent(id);
     }
    }
    static Scanner OpenFile(String filepath) {
        Scanner StudentFile = new Scanner("");
        try {
            StudentFile = new Scanner(new File(filepath));
        }
        catch (Exception e){
            System.out.println("Student Records not found. Program will now quit");
        }
        return StudentFile;
    }
    static String[] FetchRecord(int id){


        Scanner x = OpenFile("C:\\CMS\\records\\0"+id+".txt");
        String[] s = new String[14];
        int i = 0;
        while(x.hasNext()){
            s[i] = x.nextLine();
            i++;
        }
        x.close();
//
        return s;
    }
    static void AddNewStudent(){

        int newStudentID = getTotalStudents()+1;
        Scanner x = new Scanner(System.in);
        System.out.println("-----------Adding New Student-----------");
        System.out.println("Enter Student Full Name:");
        String name = x.nextLine();
        updateRecord("C:\\CMS\\records\\0"+newStudentID+".txt",name,newStudentID,"0","PHY","CHEM","URDU",0,0,"Unattended",0,0,"Unattended",0,0,"Unattended");
        updateID(newStudentID);
        System.out.printf("\nNew Student "+name+" has been added with ID: "+newStudentID+" Password: ABCD\nDo you want to update their record? Y/N\n");
        Scanner in = new Scanner(System.in);
        if(in.nextLine().equals("Y")||in.nextLine().equals("y")){
            UpdateRecordMenu(newStudentID);
        }
    }
    static void updateID(int id){
        Scanner x = OpenFile("C:\\CMS\\studentLogin.txt");
        Formatter y;
        String s[] = new String[id-1];
        int i=0;
        try{
            while(x.hasNext()){
                s[i] = x.nextLine();
                i++;
            }
        }
        catch(Exception e){ }
        x.close();
        try{
            y = new Formatter("C:\\CMS\\studentLogin.txt");
            i=0;
            for(String ss: s){
                y.format(s[i]+"\n");
                i++;
            }
            y.format(id+" ABCD\n");
            y.close();
        }
        catch (Exception e){
            System.out.println("Could not update ID");
        }
    }
    static void updateRecord(String filename, String name,int id,String attendance, String sub1,String sub2,String sub3, int sub1q, int sub1a,String sub1p,int sub2q, int sub2a,String sub2p,int sub3q, int sub3a,String sub3p){
        Formatter z;
        try {
            z = new Formatter(filename);
            z.format(name);
            z.format("\nAttendance: %s out of 50",attendance);
            z.format("\n"+sub1+":-");
            z.format("\n"+"Quiz: "+sub1q);
            z.format("\n"+"Assignment: "+sub1a);
            z.format("\n"+"Papers: "+sub1p);
            z.format("\n"+sub2+":-");
            z.format("\n"+"Quiz: "+sub2q);
            z.format("\n"+"Assignment: "+sub2a);
            z.format("\n"+"Papers: "+sub2p);
            z.format("\n"+sub3+":-");
            z.format("\n"+"Quiz: "+sub3q);
            z.format("\n"+"Assignment: "+sub3a);
            z.format("\n"+"Papers: "+sub3p);
            z.close();
        }
        catch (Exception e){
            System.out.println("Records not written");
        }

    }
    static int getTotalStudents(){
        Scanner x = OpenFile("C:\\CMS\\studentLogin.txt");
        int i = 0;
        String lastid="";
        while(x.hasNext()){
            lastid = x.next();
            x.nextLine();
            i++;
        }
        return Integer.parseInt(lastid);
    }
    static int getNTotalStudents(){
        Scanner x = OpenFile("C:\\CMS\\studentLogin.txt");
        int i = 0;
        while(x.hasNext()){
            x.nextLine();
            i++;
        }
        return i;
    }
    static void FetchStudentList(){

        Scanner x = OpenFile("C:\\CMS\\studentLogin.txt");
        int total=getTotalStudents();
        String id[] = new String[total];
        int i = 0;
        try {
            while (x.hasNext()){
                id[i]=x.next();
                x.nextLine();
                i++;

            }
        }
        catch (Exception e){
        }
        x.close();
        String[] names = new String[total+1];
        for(i=0; i<total; i++){
            Scanner y;
            try {
                y = new Scanner(new File("C:\\CMS\\records\\0"+id[i]+".txt"));
                names[i] = y.nextLine();
                y.close();
            }
            catch (Exception e){
                i++;
            }


        }
        System.out.println("-------List of Students-------");
        for(i=0; i<total; i++){
            if(id[i]==null)
                break;
            System.out.println("ID: "+id[i]+"\tName: "+names[i]);
        }
    }
    static void UpdateRecordMenu(int id) {
        Scanner x = OpenFile("C:\\CMS\\records\\0"+id+".txt");
        String name = x.nextLine();
        x.close();
        System.out.println("-----Records of "+name+" --------");
        System.out.println("1. Update Quiz Marks");
        System.out.println("2. Update Assignment Marks");
        System.out.println("3. Update Attendance");
        System.out.println("4. Update Papers");
        System.out.println("0. Go back");
        int o = OptionSelect(4);
        if (o==0)
            TeacherLoginMenu();
        int i = SubjectSelect();
        switch (o){
            case 1:
                UpdateMarks(i,id,1);
                break;
            case 2:
                UpdateMarks(i,id,2);
                break;
            case 3:
                UpdateAttendance(id);
                break;
            case 4:
                UpdateMarks(i,id,3);
                break;
        }
        UpdateRecordMenu(id);

    }
    static void UpdateAttendance(int id) {
        //Getting the data
        String record[] = GetDataFromFile("C:\\CMS\\records\\0"+id+".txt");

        //Getting Input
        System.out.println("Enter Attendance out of 50: ");
        Scanner at = new Scanner(System.in);
        String attendance = at.nextLine();
        Formatter y;
        try{
            y = new Formatter("C:\\CMS\\records\\0"+id+".txt");
            int i =0;
            for(String ss : record){
                if(i==1){
                    y.format("Attendance: %s out of 50\n",attendance);
                }else{
                    y.format(record[i]+"\n");
                }
                i++;
            }
            y.close();
        }catch (Exception e){

        }
    }
    static String[] GetDataFromFile(String filepath) {
        Scanner x = OpenFile(filepath);
        String[] record = new String[14];
        int i =0;
        while (x.hasNext()){
            record[i]=x.nextLine();
            i++;
        }
        x.close();
        return record;
    }
    static String[] GetDataFromFile(String filepath,int total) {
        Scanner x = OpenFile(filepath);
        String[] record = new String[total];
        int i =0;
        while (x.hasNext()){
            record[i]=x.nextLine();
            i++;
        }
        x.close();
        return record;
    }
    static void UpdateMarks(int subject,int id,int MarksType) {

        //Getting the data
        Scanner x = OpenFile("C:\\CMS\\records\\0"+id+".txt");
        String[] record = new String[14];
        int i =0;
        while (x.hasNext()){
            record[i]=x.nextLine();
            i++;
        }

        int line = lineSelector(subject,MarksType);
        Scanner mark = new Scanner(System.in);
        String marks=mark.nextLine();

        //Putting the data back
        Formatter y;
        try{
            y = new Formatter("C:\\CMS\\records\\0"+id+".txt");
            i =0;
            for(String ss : record){
                if(i==line) {
                    if(line==3||line==7||line==11)
                    y.format("Quiz: " + marks + "\n");
                    if(line==4||line==8||line==12)
                        y.format("Assignment: " + marks + "\n");
                    if(line==5||line==9||line==13)
                        y.format("Papers: " + marks + "\n");
                }
                else
                    y.format(record[i]+"\n");

                i++;
            }
            y.close();
        }
        catch (Exception e){

        }
    }
    static int lineSelector(int subject,int markstype){
        String sub[] = new String[3];
        sub[0]="PHYSICS";
        sub[1]="CHEMISTRY";
        sub[2]="URDU";
        String Markstype[] = new String[]{"Quiz","Assignment","Papers"};
            System.out.printf("\nEnter %s marks for %s:",Markstype[markstype-1],sub[subject-1]);
        if(subject==1){
            if(markstype==1)
                return 3;
            if(markstype==2)
                return 4;
            if(markstype==3)
                return 5;
        }else if(subject==2){
            if(markstype==1)
                return 7;
            if(markstype==2)
                return 8;
            if(markstype==3)
                return 9;
        }else if(subject==3){
            if(markstype==1)
            return 11;
            if(markstype==2)
                return 12;
            if(markstype==3)
                return 13;
        }
        return 0;
    }
    static int SubjectSelect() {
        System.out.println("Select Subject:");
        System.out.println("1. PHY");
        System.out.println("2. CHEM");
        System.out.println("3. URDU");
        return OptionSelect(3);
    }
    static void quit() {
        System.out.print("\nThank you for using CMS.");
        System.exit(0);
    }
}
