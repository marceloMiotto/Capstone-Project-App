package udacitynano.com.br.cafelegal.model;


import java.util.Date;

public class Convite {


    private long id;
    private long convidaId;
    private long respondeId;
    private String dataCriacao;
    private String aceito;
    private String chatFirebase;
    private String especialidade;
    private String areaLocation;

    public Convite() {
    }

    public Convite(long conviteServerId, long convidaId, long respondeId, String dataCriacao, String aceito, String chatFirebase, String especialidade, String areaLocation) {
        this.id = conviteServerId;
        this.convidaId = convidaId;
        this.respondeId = respondeId;
        this.dataCriacao = dataCriacao;
        this.aceito = aceito;
        this.chatFirebase = chatFirebase;
        this.especialidade = especialidade;
        this.areaLocation = areaLocation;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConvidaId() {
        return convidaId;
    }

    public void setConvidaId(long convidaId) {
        this.convidaId = convidaId;
    }

    public long getRespondeId() {
        return respondeId;
    }

    public void setRespondeId(long respondeId) {
        this.respondeId = respondeId;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getAceito() {
        return aceito;
    }

    public void setAceito(String enviado) {
        this.aceito = enviado;
    }

    public String getChatFirebase() {
        return chatFirebase;
    }

    public void setChatFirebase(String chatFirebase) {
        this.chatFirebase = chatFirebase;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getAreaLocation() {
        return areaLocation;
    }

    public void setAreaLocation(String areaLocation) {
        this.areaLocation = areaLocation;
    }
}
