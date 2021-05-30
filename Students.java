import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Students {
    public static void main(String[] args) {
        Socialgraph graph= addToGraph();
        graph.printEdges();
    }
    public static Socialgraph addToGraph(){
        Socialgraph graph = new Socialgraph();
        Random rand= new Random();
        
        // create an arraylist to store the names to be generated
        List <String> name= new ArrayList<>();
        name.add("James");
        name.add("Celine");
        name.add("Tom");
        name.add("Harry Potter");
        name.add("Thomas");
        name.add("Hermione Granger");
        name.add("Ron Weasly");
        name.add("Albus Dumbledore");
        name.add("Draco Malfoy");
        name.add("James Potter");
        name.add("Albus Dumbledore");
        name.add("Rebeus Hagrid");
        name.add("Severus Snape");
        name.add("Lucy Weasly");
        name.add("Molly Weasly");
        name.add("Lily Evans");
        name.add("Lily Luna Potter");
        name.add("Albus Severus Potter");
        name.add("Ginny Weasly");
        name.add("Petunia Dursley");
        name.add("Vernon Dursley");
        name.add("Thomas Edison");
        name.add("Lulu");
        
        //generate all vertex
        for (int i=0; i<10; i++){
            int choice= name.size(); //get the size of the set of namelist
            int chosenNameIndex= rand.nextInt(choice); //generate the index of the name
            String Name= name.get(chosenNameIndex); //get the genereated name
            int dive= rand.nextInt(101); //generate diving rate
            int lunchStart= rand.nextInt(1400-1100+1)+1100; //generate lunch starting time
            int lunchPeriod=rand.nextInt(60-5+1)+5; //generate lucnch period
            graph.addVertex(Name, dive, lunchStart, lunchPeriod); //add the info to the vertex
            name.remove(chosenNameIndex); //remove the chosen name
        }
        
        
        
        //generate friends for students
        // i<9 cuz the 10th students cannot generate new friends anymore T.T
        for (int i=0; i<9; i++){
            ArrayList <String> students= graph.getAllStudents();
            String person= students.get(i);
            int noOfNewFriends= rand.nextInt(10-i);// generate the number for new friends
            
            for (int j=0; j<noOfNewFriends; j++){
                int F_index= rand.nextInt(9-i-j)+i;
                String friend= students.get(F_index);
                double repPtoF= rand.nextInt(10)+1; //rep point of person to friend
                double repFtoP= rand.nextInt(10)+1; //rep point of friend to person
                graph.addUndirectedEdge(person, friend, repFtoP, repFtoP);
                students.remove(F_index);
            }
            
        }
           
        System.out.print("# List of all students : [");
        ArrayList<String> studentsList = graph.getAllStudents();
        for(int i=0; i<studentsList.size()-1; i++){
            System.out.print(studentsList.get(i) + ", ");
        }
        System.out.println(studentsList.get(studentsList.size()-1) + "]");
        System.out.println("");
        return graph;
    }
}
