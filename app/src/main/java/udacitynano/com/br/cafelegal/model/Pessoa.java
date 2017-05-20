package udacitynano.com.br.cafelegal.model;


import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("unused")
public abstract class Pessoa implements Parcelable {

    private long   id; //this is the server Id
    private String nome;
    private String nomeMeio;
    private String sobrenome;
    private String email;
    private int    cep;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String sexo;
    private String notificationRegistration;

    public Pessoa() {
    }

    public Pessoa(long id, String nome, String nomeMeio, String sobrenome, String email, int cep, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais, String sexo, String notificationRegistration) {
        this.id = id;
        this.nome = nome;
        this.nomeMeio = nomeMeio;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.sexo = sexo;
        this.notificationRegistration = notificationRegistration;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeMeio() {
        return nomeMeio;
    }

    public void setNomeMeio(String nomeMeio) {
        this.nomeMeio = nomeMeio;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNotificationRegistration() {
        return notificationRegistration;
    }

    public void setNotificationRegistration(String notificationRegistration) {
        this.notificationRegistration = notificationRegistration;
    }

    //Parcelable

    public void writeToParcel(Parcel out, int flags) {

        out.writeLong(id);
        out.writeString(nome);
        out.writeString(nomeMeio);
        out.writeString(sobrenome);
        out.writeString(email);
        out.writeInt(cep);
        out.writeString(endereco);
        out.writeString(numero);
        out.writeString(complemento);
        out.writeString(bairro);
        out.writeString(cidade);
        out.writeString(estado);
        out.writeString(pais);
        out.writeString(sexo);
        out.writeString(notificationRegistration);
    }

    protected Pessoa(Parcel in) {

        this.id = in.readLong();
        this.nome = in.readString();
        this.nomeMeio = in.readString();
        this.sobrenome = in.readString();
        this.email = in.readString();
        this.cep = in.readInt();
        this.endereco = in.readString();
        this.numero = in.readString();
        this.complemento = in.readString();
        this.bairro = in.readString();
        this.cidade = in.readString();
        this.estado = in.readString();
        this.pais =in.readString();
        this.sexo = in.readString();
        this.notificationRegistration =in.readString();


    }



}

