import java.util.ArrayList;

/**
 * Created by Administrator on 4/20/2016.
 */
public class SubEvent implements Comparable<SubEvent> {

    public String name;
    public ArrayList<Attendee> attendees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubEvent subEvent = (SubEvent) o;

        return name != null ? name.equals(subEvent.name) : subEvent.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public SubEvent(String n){
        name = n;
        attendees = new ArrayList<>();

    }

    @Override
    public int compareTo(SubEvent that) {
        //String number = name.substring(name.indexOf(" ")+1, name.indexOf(":"));
        //System.out.println(number);
        if (Integer.parseInt(name.substring(name.indexOf(" ")+1, name.indexOf(":")))< Integer.parseInt(that.name.substring(that.name.indexOf(" ")+1, that.name.indexOf(":")))) {
            return -1;
        } else if (Integer.parseInt(name.substring(name.indexOf(" ")+1, name.indexOf(":")))> Integer.parseInt(that.name.substring(that.name.indexOf(" ")+1, that.name.indexOf(":")))) {
            return 1;
        }
        return 0;
    }
}
