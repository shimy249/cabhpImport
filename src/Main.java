import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 4/20/2016.
 */
public class Main {

    public static ArrayList<SubEvent> subEvents;

    public static void main (String[] args){
        String filePath = args[0];
        int fName = Integer.parseInt(args[1]);
        int lName = Integer.parseInt(args[2]);
        int email = Integer.parseInt(args[3]);
        int ceu = Integer.parseInt(args[4]);

        subEvents = new ArrayList<>();

        readFile(filePath, fName, lName, email, ceu);
    }

    public static void readFile(String filePath, int f, int l, int e, int c){
        BufferedReader br = null;
        String line = "";
        String splitter = ",";
        ArrayList<String> subEventNames = new ArrayList<>();


        System.out.println(" " + f + " " + l + " " + " " + e + " " + c);

        try{
            br = new BufferedReader(new FileReader(filePath));

            line = br.readLine();
            int count = 0;
            while ((line = br.readLine()) != null){
                subEventNames.clear();
                //System.out.println(line);
                //System.out.println();
                String[] fields = line.split(splitter);
                String fName = null, lName = null, email = null, ceu = null;
                for(int i = 0; i < fields.length; ++i){
                    if(i == f)
                        fName = fields[i];
                    else if(i == l)
                        lName = fields[i];
                    else if(i == e)
                        email = fields[i];
                    else if(i == c)
                        ceu = fields[i];
                    else
                        if(!fields[i].equals(""))
                            subEventNames.add(fields[i]);
                }

                Attendee current = new Attendee(fName, lName, email, ceu);
                for(int i = 0; i < subEventNames.size(); i++){
                    //System.out.println(subEventNames.get(i));
                    //System.out.println(current.email);
                    if(subEventNames.get(i).equals("")){
                        //System.out.println("blank");
                        continue;
                    }
                    for(int j = 0; j < subEvents.size(); j++){

                        if(subEvents.get(j).name.equals(subEventNames.get(i))) {
                            if (!(subEvents.get(j).attendees.contains(current)))
                                subEvents.get(j).attendees.add(current);

                            //System.out.println(subEventNames.get(i));
                            continue;
                        }
                    }
                    if(!(subEvents.contains(new SubEvent(subEventNames.get(i))))){
                        SubEvent temp = new SubEvent(subEventNames.get(i));
                        temp.attendees.add(current);
                        subEvents.add(temp);
                        continue;
                        //System.out.println("new " + subEventNames.get(i));
                    }
                }
                if( count == 2)
                    //break;
                count ++;

            }
            Collections.sort(subEvents);


            for(int i = 0; i < subEvents.size(); i++){
                addSubEvent(subEvents.get(i));
                break;
            }


        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void addSubEvent(SubEvent subEvent) throws Exception {
        String currentEventId = "5716b9c06c12c4a0153f8f9d";
        URL url = new URL("http://192.168.2.64:3000/subevents");


        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        String params = "name="+subEvent.name+"&description=fill&location=fill&start=2016-04-25T20:00:00.000Z&end=2016-04-25T20:00:00.000Z&threshold=0";

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(params);
        wr.flush();
        wr.close();

        int responsecode = con.getResponseCode();
        System.out.println(responsecode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = in.readLine()) != null)
            response.append(inputLine);
        in.close();

        System.out.println(response.toString());
        String morenothing = con.getResponseMessage();

        JSONParser parser = new JSONParser();
        Object o = parser.parse(response.toString());
        JSONObject res = (JSONObject) o;
        System.out.println(res.get("_id"));
        String subId = (String)res.get("_id");
        url = new URL("http://192.168.2.64:3000/events/56ddfb21b29b071b0c107820/subevent");


        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        params = "subevent=" + res.get("_id");

        con.setDoOutput(true);
        wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(params);
        wr.flush();
        wr.close();

        in = new BufferedReader(new InputStreamReader(con.getInputStream()));


        response = new StringBuffer();

        while((inputLine = in.readLine()) != null)
            response.append(inputLine);
        in.close();

        System.out.println(response.toString());
        morenothing = con.getResponseMessage();

        ArrayList<Attendee> attendees = subEvent.attendees;

        for(int i = 0; i < attendees.size();i++){
            TimeUnit.SECONDS.sleep(1);
            System.out.println(attendees.get(i).fName);
            url = new URL("http://192.168.2.64:3000/attendees");


            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            params = "first_name="+attendees.get(i).fName+"&last_name="+attendees.get(i).lName+"&email="+attendees.get(i).email+"&regonline_id=10000";

            con.setDoOutput(true);
            wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));


            response = new StringBuffer();
            System.out.println(con.getResponseCode());

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();

            parser = new JSONParser();
            o = parser.parse(response.toString());
            morenothing = con.getResponseMessage();
            res = (JSONObject) o;
            System.out.println(res.get("_id"));
            String attendeeId = (String)res.get("_id");

            url = new URL("http://192.168.2.64:3000/attendance/");


            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            params = "subevent=" + subId+"&attendee="+attendeeId;

            con.setDoOutput(true);
            wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));


            response = new StringBuffer();
            System.out.println(con.getResponseCode());

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();

            System.out.println(response.toString());
            morenothing = con.getResponseMessage();
        }
    }
}
