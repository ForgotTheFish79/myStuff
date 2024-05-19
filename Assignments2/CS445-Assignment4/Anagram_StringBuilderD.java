import java.io.*;
import java.util.*;

public class Anagram_StringBuilderD
{
    public static void main(String[] args) throws IOException
    {


        Scanner fileScan = new Scanner(new FileInputStream("dictionary.txt"));
        String st;
        StringBuilder sb;

        HashSet<String> S= new HashSet<String>();
        TrieST2 D = new TrieST2();
        while (fileScan.hasNext())
        {

            st = fileScan.nextLine();  // get next word from the file
            D.put(new StringBuilder(st), new StringBuilder(st));  // use the word as both the key and the value
        }
        fileScan.close();
        System.out.println("What file would you like to use as anagram?");
        Scanner kb =new Scanner(System.in);
        String fileName = kb.nextLine();
        System.out.println("What file would you like to store your anagrams to?");
        String saveFileName = kb.nextLine();
        kb.close();
        Scanner test1 = new Scanner(new FileInputStream(fileName));
        long startTime = System.currentTimeMillis();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFileName), "utf-8"))) {
            //writer.write("something");

            while (test1.hasNext()) {
                //System.out.println(S);
                S = new HashSet<String>();
                st = test1.nextLine();
                System.out.println("Here are the results for '" + st + "': ");
                writer.write("Here are the results for '" + st + "': \n");

                st = st.replace(" ", "");
                multWordAnagram(new StringBuilder(), new StringBuilder(st), D, S, true);
                // singleWordAnagram(new StringBuilder(), new StringBuilder(st), D,S);
                List<String> list = new ArrayList<>(S);

                int index = 1;
                int total = list.size();
                while (list.size() > 0) {
                    List<String> temp = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        String[] splitter = list.get(i).split(" ");
                        //System.out.println(list);
                        if (splitter.length == index) {
                            temp.add(list.remove(i));
                            i--;
                        }
                    }
                    if (temp.size() > 0) {
                        System.out.println("There were " + temp.size() + " " + index + "-word solutions: ");
                        writer.write("There were " + temp.size() + " " + index + "-word solutions: \n");
                        Collections.sort(temp);
                        Anagram_StringBuilderD.printList(temp);
                        for(String s:temp)
                        {
                            writer.write(s+" \n");
                        }
                    }
                    index++;
                }
                //Anagram.printList(list);
                System.out.println("There were a total of " + total + " solutions\n");
                writer.write("There were a total of " + total + " solutions\n\n");
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(((float)(endTime-startTime))/1000.0);
    }


    public static void printList(List<String> L)
    {//semi obsolete Helper Method since we just add everything gets saved there.
        for(String item: L)
        {
            System.out.println(item);
        }
    }
    //ShowPerms, ShowPermWOrdRec and singleWordAnagram are obsolte but they were instrumental for my development of the multWordAnagram Method

    public static void multWordAnagram(StringBuilder prefix, StringBuilder str, TrieST2 D,HashSet<String> S,boolean first) {
        int n = str.length();
        int ans = D.searchPrefix(prefix);//using SP to prune branches
        if (ans==0) //if prefix is not a prefix/word, stop trying
        {
            return;
        }
        else if (n == 0&&(ans>1)) {
            // Base case: If str is empty and predix is a word or a prefix word, a  single word anagram has been discovered;
            S.add(prefix.toString());
        }
        else if ((ans>1))// check for multiple words
        {//make a sub problem of the original.
            if(first)//we only check if its the first iteration, since we cut down on letter pool this somehow works/
            {
                for (int i = 0; i < n; i++) {//problem is with in here not anymore!!:)
                    StringBuilder newPrefix = new StringBuilder(prefix);
                    newPrefix.append(str.charAt(i));
                    StringBuilder newStr = new StringBuilder(str);
                    newStr.deleteCharAt(i);

                    multWordAnagram(newPrefix, newStr, D, S,true);

                }
            }
            HashSet<String> temp = new HashSet<String>();
            multWordAnagram(new StringBuilder(),str,D,temp,false);
            for(String s:temp)
            {
                int i = prefix.length();
                prefix.append(" ");
                prefix.append(s);
                S.add(prefix.toString());
                prefix.replace(i,prefix.length(),"");

            }
        }
        else {// only one word solution?
            for (int i = 0; i < n; i++) {

                //System.out.println(prefix+" is a word/predix");
                StringBuilder newPrefix = new StringBuilder(prefix);
                newPrefix.append(str.charAt(i));
                StringBuilder newStr = new StringBuilder(str);
                newStr.deleteCharAt(i);
                multWordAnagram(newPrefix, newStr, D, S,true);
            }
        }
    }



}
