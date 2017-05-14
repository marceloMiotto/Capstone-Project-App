package udacitynano.com.br.cafelegal.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Cliente extends Pessoa {

    public Cliente() { }

    public Cliente(long id, String nome, String nomeMeio, String sobrenome, String email, int cep, String endereco, String numero,String complemento, String bairro, String cidade, String estado, String pais, String sexo, String notificationRegistration) {
        super(id, nome, nomeMeio, sobrenome, email, cep, endereco, numero, complemento, bairro, cidade, estado, pais, sexo,notificationRegistration);
    }

    //Parcelable
    public static final Parcelable.Creator<Cliente> CREATOR = new Parcelable.Creator<Cliente>() {
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };


    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        //out.writeInt(<attributes from Cliente>);
    }

    private Cliente(Parcel in) {
        super(in);
        //<attributes from Cliente> = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
