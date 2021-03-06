/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ls.neo4j.socialpath;


import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Event {

    static double rep;
    static Random r = new Random();
    static int[] lunchTime = new int[3];
    static Scanner s= new Scanner(System.in);
    
    public static boolean event1 (String mentor, String mentee, Socialgraph graph){
        System.out.println("\n----------------------------------------------------");
        System.out.println("Event 1 : Teaching a stranger to solve lab qustions");
        boolean isFriendOf = graph.hasDirectedEdge(mentor, mentee) || graph.hasDirectedEdge(mentee, mentor); //check whether mentor & mentee are friends
        if(!isFriendOf){
            System.out.println("Mentor " + mentor + " is helping mentee " + mentee + " to solve lab questions");
            System.out.println("Teaching lab questions now...\n");
            
            rep = (Math.random() > 0.5) ? 10 : 2;
            graph.addDirectedEdge(mentor, mentee, rep); 
            System.out.println("Mentor " + mentor + " programming skill is " + (rep == 10 ? "good" : "bad"));
            System.out.println("The learning experience is " + (rep == 10 ? "pleasant" : "unpleasant"));
            System.out.println(mentor + "'s rep points relative to " + mentee + " is " + rep);
            System.out.println("----------------------------------------------------\n");
            graph.printEdges();
            return true;
        }
        else{
            System.out.println("They are friends. Unable to teach a friend.");
            return false;
        }
    }
    
    public static boolean event2(String mentor, String mentee, Socialgraph graph){
        System.out.println("\n----------------------------------------------------");
        System.out.println("Event 2 : Chit-chat");
        System.out.println("Please decide who to chit-chat with mentee " + mentee + " : ");
        String person = s.next();
        System.out.println("");
        if (!person.equals(mentor) && !person.equals(mentee)) { 
            boolean isFriendOfMentee = graph.hasDirectedEdge(mentee, person) || graph.hasDirectedEdge(person, mentee);//mentee : 3 ; person : 2
            boolean isFriendOfMentor = graph.hasDirectedEdge(mentor, person) || graph.hasDirectedEdge(person, mentor);//mentor : 1 ; person : 2
            boolean good = Math.random() > 0.5;
            
            if (isFriendOfMentee && !isFriendOfMentor) { //the person is mentee's friend but not mentor friend
                System.out.println("Mentee " + mentee + " is chit-chatting with his friend " + person);
                System.out.println("Chit-chatting now...\n");
                if (good) {
                    rep *= 0.5;
                    System.out.println(mentee + " and " + person + " are talking about " + mentor + "'s good side");
                } else {
                    rep *= -1;
                    System.out.println(mentee + " and " + person + " are talking about " + mentor + "'s bad side");
                }
                graph.addDirectedEdge(mentor, person, rep);
                System.out.println(mentor + "'s rep points relative to " + person + " is " + rep);
                System.out.println("----------------------------------------------------\n");
                graph.printEdges();
                return true;
            }
            else if(isFriendOfMentee && isFriendOfMentor){ //the person is mentee's friend and also mentor friend
                System.out.println("Mentee " + mentee + " is chit-chatting with his friend " + person);
                System.out.println("The mentee's friend is friend of mentor");
                System.out.println("Chit-chatting now...\n");
                double points = graph.getVertexRep(mentor, person);
                if (good){
                    rep = (rep*0.5) + points;
                    System.out.println(mentee + " and " + person + " are talking about " + mentor + "'s good side");
                } else {
                    rep = (rep*-1) + points;
                    System.out.println(mentee + " and " + person + " are talking about " + mentor + "'s bad side");
                }
                graph.replaceRepPoints(mentor, person, rep);
                System.out.println(mentor + "'s rep points relative to " + person + " is " + rep);
                System.out.println("----------------------------------------------------\n");
                graph.printEdges();
                return true;
            }
            else{
                System.out.println(mentee + " and " + person + " are strangers. Unable to chit-chat.");
                return false;
            }
        }
        else{
            System.out.println("Sorry! Mentee cannot chit-chat with mentor or himself");
            return false;
        }
    }
    
    //event 3        
    public static void event3(Socialgraph graph){
        int lunchPeriod;
        int averagePeriod = 0;
        int lunchCount = 0;
        
        
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("Event 3 : Your road to glory");
        
        System.out.println("");
        System.out.println("Who are you?");
        String you = s.nextLine();
        System.out.println("");
        while (!graph.hasVertex(you)){
            System.out.println("Please enter a name that is in the graph.");
            you=s.nextLine();
            System.out.println("");
        }
        
        System.out.println("How many persons would you like to observe?");
        int n= s.nextInt();
        System.out.println("");
        System.out.println("Who are the " + n + " persons that you interested? ");
        String[] interested = new String[n];
        s.nextLine();
        List valid= Arrays.asList(interested);
        for(int i=0; i<n; i++){
            //System.out.print((i+1)+". "); 
            String name= s.nextLine();
            System.out.println("");
            
            if (!valid.contains(name)&&graph.hasVertex(name)){
                interested[i]=name;
            }
            else if (valid.contains(name)){
                System.out.println("You had entered this name before, please enter another name.");
                i--;
            }
            else{
                System.out.println("Please enter a name that is in the graph.");
                i--;
            }
        }
        
        System.out.println("List of person that you are interested");
        for (int i=0; i<n; i++){
            System.out.println((i+1)+ ". " + interested[i]);
        }
        
        //2D array which stores every persons time intervals
        int[][] intervals = new int[n+1][2];
        
        System.out.println("");
        System.out.println("What time do you have lunch? [1100 to 1400]");
        int lunchStart = s.nextInt();
        System.out.println("");
        int h=lunchStart/100;
        int min= lunchStart%100;
        while(!(hour(h)&&minute(h, min))){
            System.out.println("Please Enter a valid time. [1100 to 1400]");
            lunchStart=s.nextInt();
            h=lunchStart/100;
            min= lunchStart%100;
            System.out.println("");
        }
        
        System.out.println("How long is your lunch period? [6-59(minutes)]");
        int period = s.nextInt();
        System.out.println("");
        while (!(period>=6 && period<=59)){
            System.out.println("Please Enter a valid lunch period [6-59 (minute)]");
            period=s.nextInt();
            System.out.println("");
        }
        
        //calculate your lunch interval and store in 2D array as the first element
        int lunchEnd = lunchStart + period;
        int minutes = lunchEnd % 100;
        int hour = lunchEnd / 100;
        
        if(minutes >= 60) { //take the last 2 numbers (minutes)
            minutes -= 60;
            hour += 1;
            lunchEnd = (hour*100) + minutes;
        }
        
        intervals[0][0] = lunchStart;
        intervals[0][1] = lunchEnd;
        System.out.println("Your lunch interval is " + intervals[0][0] + " to " + intervals[0][1]);
        
        for(int i=0; i<n; i++){
            System.out.println("\n------------------------------");
            System.out.println("Observing " + interested[i] + "...");
            
            for(int j=0; j<3; j++){ //observe for 3 days
                lunchTime[j] = LunchStart();
                lunchPeriod = LunchPeriod(lunchTime[j]);
                System.out.println("Day " + (j+1));
                System.out.println("Lunch time : " + lunchTime[j]);
                System.out.println("Lunch period [minute(s)] : " + lunchPeriod);
                System.out.println("");
            
                averagePeriod += lunchPeriod;
            }
            
            averagePeriod /= 3;
            System.out.println("Average lunch period : " + averagePeriod);
            computeLunchInterval(averagePeriod, n, intervals);
            System.out.println("");
            
            if(findIntersection(intervals, n)){ //intersection exist
                System.out.println("There exists intersection between " + you + " and " + interested[i]);
                System.out.println("Congratulations! " + you + " will have lunch with " + interested[i]);
                System.out.println("Having lunch...");
                System.out.println("");
                
                if (graph.hasDirectedEdge(you, interested[i]) && graph.hasDirectedEdge(interested[i], you)){ //they are friends alr
                    graph.addRepPoints(you, interested[i], 1); //your rep++
                    graph.addRepPoints(interested[i], you, 1); //your friend's rep ++
                }
                else if (graph.hasDirectedEdge(you, interested[i])){ //after having lunch, both of them are friend
                    graph.addRepPoints(you, interested[i], 1);
                    graph.addDirectedEdge(interested[i], you, 1.0); //become friends 
                }
                else if (graph.hasDirectedEdge(interested[i], you)){ //after having lunch, both of them are friend
                    graph.addDirectedEdge(you, interested[i], 1.0);  //become friends 
                    graph.addRepPoints(interested[i], you, 1);
                }
                else{ //they are not friends before this
                    graph.addUndirectedEdge(you, interested[i], 1.0, 1.0);
                }
                System.out.println( you + "'s rep point with " + interested[i] + " is increased by 1");
                System.out.println(interested[i] + "'s rep point with " + you + " is increased by 1");
                lunchCount++;
            }
            else{
                System.out.println("No intersection between " + you + " and " + interested[i]);
                System.out.println("Unfortunately " + you + " can't have lunch with " + interested[i]);
            }
            System.out.println("------------------------------");
            averagePeriod = 0; //set the averagePeriod to 0
        }
        
        System.out.println(you + " will have lunch with " + lunchCount + " person(s) in day 4");
        System.out.println("The maximum reputation " + you + " have gained were " + lunchCount);
        System.out.println("");
        
        graph.printEdges();
    }
    
    //get the earliest time -> average lunch time
    public static void computeLunchInterval(int averagePeriod, int n, int[][] intervals) {
        int startTime = 1100;
        
        //start time is the earliest time
        if (lunchTime[0] > lunchTime[1]) {
            startTime = Math.min(lunchTime[1], lunchTime[2]);
        } else if (lunchTime[1] > lunchTime[0]) {
            startTime = Math.min(lunchTime[0], lunchTime[2]);
        }
        
        int endTime = startTime + averagePeriod;
        int minutes = endTime % 100;
        int hour = endTime / 100;
        if(minutes >= 60) { //take the last 2 numbers (minutes)
            minutes -= 60;
            hour += 1;
            endTime = (hour*100) + minutes;
        }
        System.out.println("Lunch interval : " + startTime + " to " + endTime);
        
        //store start time and end time in 2D array
        for(int i=1; i<n+1; i++){
            intervals[i][0] = startTime;
            intervals[i][1] = endTime;
        }
    }
    
    //check if the hour entered is valid
    public static boolean hour (int h){
        if (h>=11&&h<=14){
            return true;
        }
        else{
            return false;
        }
    }
    
    //check if the minute entered is valid
    public static boolean minute(int h, int min){
        if (h==14&& min!=00){
            return false;
        }
        if (min>=0&&min<=59){
            return true;
        }
        else{
            return false;
        }
    }
    public static int LunchStart(){
        int hour=r.nextInt(14-11)+11;//generate the hour of taking lunch
            int min;
            String lunch;
            
            //generate the minute of having lunch
            if (hour!=13){
                min=r.nextInt(60);
            }
            else{
                min=r.nextInt(56);
            }
            if (min<10){
                lunch=hour+"0"+min;
            }
            else{
                lunch=hour+""+min;
            }
            
            int lunchStart= Integer.parseInt(lunch);
            return lunchStart;
    }
    
    public static int LunchPeriod (int lunchStart){
        int lunchPeriod;
        int minutes = lunchStart % 100;
        int hour = lunchStart / 100;
        
        //generate lunch period
        if (hour!=13){
            lunchPeriod=r.nextInt(60-5+1)+5; 
        }
        else{
            lunchPeriod=r.nextInt(60-5-minutes+1)+5;
        }
        return lunchPeriod;
    }
    
    public static boolean findIntersection(int[][] intervals, int n){
        //your lunch intervals
        int l = intervals[0][0]; //your lunch start
        int r = intervals[0][1]; //your lunch end
        
        for(int i=1; i<n+1; i++){
            if (intervals[i][0] > r || intervals[i][1] < l) {// If no intersection exists
                return false;
            }
            else{
                return true;
            }
        }
        return false;
    }
}

