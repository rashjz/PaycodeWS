package heren.team.serv.ws.domain;

/**
 *
 * @author Vusal Hasanli
 */
public class CpfrResult {
    private int status;
    private String note;

    public CpfrResult() {
        this.status = 0;
        this.note = "";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "CpfrResult{" + "status=" + status + ", note=" + note + '}';
    }           
}
