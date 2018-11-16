import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.io.*;


/* Further Questions
    a. Make the robots move simultaneously. Avoid collisions. 
I made the robots move simultaneously. But I didn't implement their ability to avoid collisions. 
Which means, at some certain time of movements, different robots may be at the same position of the grid.

    b. ...
Yes, I did this by making each robot a separate object. The main function serves as a server. Each Robot object is a remote client.//#endregion

    c. ...
No, I didn't implement this.
*/


class Q2 {
    public static final char[] DIR = {'N', 'E', 'S', 'W'};
    public static final int[][] MOVES = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public static final Map<Character, Integer> DIR_MAP = createMap();

    private static Map<Character, Integer> createMap() {
        Map<Character, Integer> result = new HashMap<>();
        result.put('N', 0);
        result.put('E', 1);
        result.put('S', 2);
        result.put('W', 3);
        return Collections.unmodifiableMap(result);
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = null; 

        System.out.println("Please specify your input file name: ");
        try(Scanner scan = new Scanner(System.in))
        {
            String input_file = scan.nextLine();
            File file = new File(input_file); 
            br = new BufferedReader(new FileReader(file));

            String bound_str = br.readLine();
            String[] bound_temp = bound_str.split("\\s");

            int[] bound = new int[2];

            int bound_index = 0;
            for(String b : bound_temp)
            {
                if(b.length() != 0)
                {
                    bound[bound_index] = Integer.parseInt(b);
                }
                bound_index++;
                if(bound_index == 2) break;
            }
            

            // Create all robot objects
            ArrayList<Robot> robots = new ArrayList<>();
            String robot_info;
            String path;
            int path_max_length = 0;
            while ((robot_info = br.readLine()) != null)
            {
                if((path = br.readLine()) != null)
                {
                    robots.add(new Robot(robot_info, path, bound));
                    path_max_length = Math.max(path_max_length, path.length());
                }
                else
                {
                    System.out.println("Invalid text input");
                    return;
                }
            }

            // For every step, make every robot to move simultaneously
            for(int i = 0; i < path_max_length; i ++)
            {
                for(Robot rob : robots)
                {
                    rob.move(i);
                }
            }

            // Print the current state of each robot
            for(Robot rob : robots)
            {
                rob.print();
            }
        } 
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        catch(IOException e)
        {
            System.out.println("IOException");
        }
        finally
        {
            br.close();
        }
    }
}

class Robot
    {
        int x, y, heading;
        int[] bound;
        String path;

        public Robot(String robot_info, String path, int[] bound)
        {
            String[] info = robot_info.split("\\s");
            this.x = Integer.parseInt(info[0]);
            this.y = Integer.parseInt(info[1]);
            this.heading = Q2.DIR_MAP.get(info[2].charAt(0));
            this.path = path;
            this.bound = bound;
        }

        public void move(int step)
        {
            if(step >= path.length())
            {
                return;
            }
            char in = path.charAt(step);
            if(in == 'M')
            {
                int next_x = x + Q2.MOVES[heading][0];
                int next_y = y + Q2.MOVES[heading][1];
                if(inBound(x, y))
                {
                    this.x = next_x;
                    this.y = next_y;
                }
            }
            else
            {
                if(in == 'L')
                {
                    heading = heading == 0 ? Q2.DIR.length - 1 : heading - 1;
                }
                else if(in == 'R')
                {
                    heading = heading == Q2.DIR.length - 1 ? 0 : heading + 1;
                }
            }
        }

        private boolean inBound(int x, int y)
        {
            return x >= 0 && y >= 0 && x <= bound[0] && y <= bound[1];
        }

        public void print()
        {
            System.out.println(this.x + " " + this.y + " " + Q2.DIR[heading]);
        }
    }

