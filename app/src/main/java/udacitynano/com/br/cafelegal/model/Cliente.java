package udacitynano.com.br.cafelegal.model;


public class Cliente extends Pessoa {

    public Cliente() { }

    public Cliente(long id, String nome, String nomeMeio, String sobrenome, String email, int cep, String endereco, String numero,String complemento, String bairro, String cidade, String estado, String pais, String sexo, String notificationRegistration) {
        super(id, nome, nomeMeio, sobrenome, email, cep, endereco, numero, complemento, bairro, cidade, estado, pais, sexo,notificationRegistration);
    }
}
