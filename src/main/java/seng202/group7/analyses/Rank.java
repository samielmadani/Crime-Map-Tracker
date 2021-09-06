package seng202.group7.analyses;

import seng202.group7.Report;

import java.util.*;

public class Rank {
    public ArrayList<Tuple> frequencyRank(ArrayList<Report> data){


        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ArrayList<Tuple> list = new ArrayList<Tuple>();

        for(Report report : data){
            String primary = report.getPrimaryDescription();
            map.put(primary, map.getOrDefault(primary, 0) + 1);
        }

        for(Map.Entry<String, Integer> entry : map.entrySet()){
            list.add(new Tuple(entry.getKey(), entry.getValue()));
        }

        Collections.sort(list, (a, b) -> (int)a.y - (int)b.y);

        return list;
    }
}
