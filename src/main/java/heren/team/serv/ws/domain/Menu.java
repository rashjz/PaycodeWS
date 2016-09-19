package heren.team.serv.ws.domain;

import java.math.BigDecimal;

/**
 *
 * @author Mobby
 */
public class Menu {

    private BigDecimal id;
    private String name;
    private String type;

    public Menu() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
