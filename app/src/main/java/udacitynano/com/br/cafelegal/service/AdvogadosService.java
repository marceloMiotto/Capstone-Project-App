package udacitynano.com.br.cafelegal.service;


import android.content.Context;
import android.view.View;

import java.util.List;

import udacitynano.com.br.cafelegal.data.Database;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Convite;

public class AdvogadosService {

    private List<Advogado> advogados = Database.getAdvogados();

    public AdvogadosService(){




        advogados.add(new Advogado(1,"Test 1","","sobTest","@",0,"","","","","","","","","","","","","","","","",""));
        advogados.add(new Advogado(2,"Test 1","","sobTest","@",0,"","","","","","","","","","","","","","","","",""));
        advogados.add(new Advogado(3,"Test 1","","sobTest","@",0,"","","","","","","","","","","","","","","","",""));
    }

    public List<Advogado> getAdvogados(){
        return advogados;
    }

}
