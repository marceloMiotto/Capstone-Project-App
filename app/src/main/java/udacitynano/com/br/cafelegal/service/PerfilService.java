package udacitynano.com.br.cafelegal.service;

import android.util.Log;

import udacitynano.com.br.cafelegal.model.Cliente;
import udacitynano.com.br.cafelegal.model.Pessoa;

public class PerfilService {


    public void updatePerfil(Pessoa pessoa){

       //TODO set perfil

        Log.e("Debug","Pessoa " + pessoa.toString());


    }

    public long createPerfil(Pessoa pessoa){

        //TODO create a user id

        return 1;

    }

    public Pessoa getPerfil(String userType){

        return new Cliente();
    }


}
