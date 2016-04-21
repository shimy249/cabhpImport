/**
 * Created by Administrator on 4/20/2016.
 */
public class Attendee {

    public String fName;
    public String lName;
    public String email;
    public String ceu;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attendee attendee = (Attendee) o;

        if (fName != null ? !fName.equals(attendee.fName) : attendee.fName != null) return false;
        if (lName != null ? !lName.equals(attendee.lName) : attendee.lName != null) return false;
        if (email != null ? !email.equals(attendee.email) : attendee.email != null) return false;
        return ceu != null ? ceu.equals(attendee.ceu) : attendee.ceu == null;

    }

    @Override
    public int hashCode() {
        int result = fName != null ? fName.hashCode() : 0;
        result = 31 * result + (lName != null ? lName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (ceu != null ? ceu.hashCode() : 0);
        return result;
    }

    public Attendee(String f, String l, String e, String c){
        fName = f;
        lName = l;
        email = e;
        ceu = c;

    }
}
