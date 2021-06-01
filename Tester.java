package at.ls.neo4j.socialpath;

import java.util.Scanner;

/**
 *
 * @author CHEESE
 */
public class Tester {
    public static void main(String[] args) {
        Socialgraph graph= Students.addToGraph();
        graph.printEdges();
        System.out.println("");
        menu(graph);  
    } 
    
    public static void menu(Socialgraph graph){
        Scanner s= new Scanner (System.in);
        String mentor, mentee;
        System.out.println("Which Event you would like to perform now?");
        System.out.println("1. Event 1: Teaching a stranger to solve lab questions");
        System.out.println("2. Event 2: Chit-chat");
        System.out.println("3. Event 3: Your road to glory");
        System.out.println("4. Arranging books");
        System.out.println("5. Event 5: meet your crush");
        System.out.println("6. Event 6: Friendship");
        System.out.println("7. Exit this system");
        System.out.println("Please Enter your choice [1-7]");
        int choice= s.nextInt();

        switch (choice){
            case 1:
                System.out.println("Enter the name of mentor: ");
                s.nextLine();
                mentor= s.nextLine();
                System.out.println("");
                while (!graph.hasVertex(mentor)){
                    System.out.println("Please Enter a name that is in the graph.");
                    mentor=s.nextLine();
                    System.out.println("");
                }
                System.out.println("Enter the name of mentee: ");
                mentee=s.nextLine();
                System.out.println("");
                while (!graph.hasVertex(mentee)){
                    System.out.println("Please Enter a name that is in the graph.");
                    mentee=s.nextLine();
                    System.out.println("");
                }
                Event.event1(mentor, mentee, graph);
                System.out.println("");
                System.out.println("Do you wish to continue for event 2? (yes/no)");
                String decision= s.nextLine();
                if (decision.equalsIgnoreCase("yes")){
                    Event.event2(mentor, mentee, graph);
                }
                break;
                
            case 2:
                break;
                   
            case 3:
                graph.printEdges();
                Event.event3(graph);
                break;
                
            case 4:
                Event4.Event4();
                break;
                
            case 5:
                 break;
                 
            case 6:
                break;
                
            case 7:
                    System.exit(0);
                    
            default:
                System.out.println("Invalid choice");
                System.out.println("Please enter a valid choice");
                System.out.println("");
                menu(graph);
                break;
        }

        System.out.println("");
        menu(graph);
    }
    

    
    public static boolean check (String mentor, String mentee){
        if (!mentor.equals(mentee)){
            return false;
        }
        else{
            System.out.println("Mentor and mentee cannot be the same person.");
            return true;
        }
    }
            
}
