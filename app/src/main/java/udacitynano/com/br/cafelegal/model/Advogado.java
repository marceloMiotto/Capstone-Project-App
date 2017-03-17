package udacitynano.com.br.cafelegal.model;



public class Advogado extends Pessoa {

    private String numeroInscricaoOAB;
    private String seccional;
    private String tipoInscricao;
    private String telefoneComercial;
    private String confirmadoCNA;
    private String twitter;
    private String linkedIn;
    private String especialistaUm;
    private String especialistaDois;
    private String notificationRegistration;
    private double latitude;
    private double longitude;


    public Advogado() { }

    public Advogado(long id, String nome, String nomeMeio, String sobrenome, String email, int cep, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais, String sexo, String numeroInscricaoOAB, String seccional, String tipoInscricao, String telefoneComercial, String confirmadoCNA, String twitter, String linkedIn
            , String especialistaUm, String especialistaDois, String notificationRegistration, double latitude, double longitude) {
        super(id, nome, nomeMeio, sobrenome, email, cep, endereco, numero,complemento, bairro, cidade, estado, pais, sexo);
        this.numeroInscricaoOAB = numeroInscricaoOAB;
        this.seccional = seccional;
        this.tipoInscricao = tipoInscricao;
        this.telefoneComercial = telefoneComercial;
        this.confirmadoCNA = confirmadoCNA;
        this.twitter = twitter;
        this.linkedIn = linkedIn;
        this.especialistaUm = especialistaUm;
        this.especialistaDois = especialistaDois;
        this.notificationRegistration = notificationRegistration;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNumeroInscricaoOAB() {
        return numeroInscricaoOAB;
    }

    public void setNumeroInscricaoOAB(String numeroInscricaoOAB) {
        this.numeroInscricaoOAB = numeroInscricaoOAB;
    }

    public String getSeccional() {
        return seccional;
    }

    public void setSeccional(String seccional) {
        this.seccional = seccional;
    }

    public String getTipoInscricao() {
        return tipoInscricao;
    }

    public void setTipoInscricao(String tipoInscricao) {
        this.tipoInscricao = tipoInscricao;
    }

    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getConfirmadoCNA() {
        return confirmadoCNA;
    }

    public void setConfirmadoCNA(String confirmadoCNA) {
        this.confirmadoCNA = confirmadoCNA;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getEspecialistaUm() {
        return especialistaUm;
    }

    public void setEspecialistaUm(String especialistaUm) {
        this.especialistaUm = especialistaUm;
    }

    public String getEspecialistaDois() {
        return especialistaDois;
    }

    public void setEspecialistaDois(String especialistaDois) {
        this.especialistaDois = especialistaDois;
    }

    public String getNotificationRegistration() {
        return notificationRegistration;
    }

    public void setNotificationRegistration(String notificationRegistration) {
        this.notificationRegistration = notificationRegistration;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
