package udacitynano.com.br.cafelegal.model;


import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("unused")
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
    private double latitude;
    private double longitude;


    public Advogado() { }

    public Advogado(long id, String nome, String nomeMeio, String sobrenome, String email, int cep, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais, String sexo, String numeroInscricaoOAB, String seccional, String tipoInscricao, String telefoneComercial, String twitter, String linkedIn
            , String especialistaUm, String especialistaDois, String notificationRegistration) {
        super(id, nome, nomeMeio, sobrenome, email, cep, endereco, numero,complemento, bairro, cidade, estado, pais, sexo,notificationRegistration);
        this.numeroInscricaoOAB = numeroInscricaoOAB;
        this.seccional = seccional;
        this.tipoInscricao = tipoInscricao;
        this.telefoneComercial = telefoneComercial;
        this.twitter = twitter;
        this.linkedIn = linkedIn;
        this.especialistaUm = especialistaUm;
        this.especialistaDois = especialistaDois;

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


    //Parcelable
    public static final Parcelable.Creator<Advogado> CREATOR = new Parcelable.Creator<Advogado>() {
        public Advogado createFromParcel(Parcel in) {
            return new Advogado(in);
        }

        public Advogado[] newArray(int size) {
            return new Advogado[size];
        }
    };


    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(numeroInscricaoOAB);
        out.writeString(seccional);
        out.writeString(tipoInscricao);
        out.writeString(telefoneComercial);
        out.writeString(twitter);
        out.writeString(linkedIn);
        out.writeString(especialistaUm);
        out.writeString(especialistaDois);
    }

    private Advogado(Parcel in) {
        super(in);

        this.numeroInscricaoOAB = in.readString();
        this.seccional = in.readString();
        this.tipoInscricao = in.readString();
        this.telefoneComercial = in.readString();
        this.twitter = in.readString();
        this.linkedIn = in.readString();
        this.especialistaUm = in.readString();
        this.especialistaDois = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
