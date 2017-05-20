package udacitynano.com.br.cafelegal.model;

@SuppressWarnings("unused")
public class CafeLegalMessage {

    private String id;
    private String text;
    private String name;

    public CafeLegalMessage() {
    }

    public CafeLegalMessage(String text, String name) {
        this.text = text;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    @SuppressWarnings("unused")
    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

}
