package udacitynano.com.br.cafelegal.data;

import java.util.ArrayList;
import java.util.List;

import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Convite;

public class Database {

    private static List<Convite> convites = new ArrayList<>();
    private static List<Advogado> advogados = new ArrayList<>();

    public static List<Convite> getConvites(){

        return convites;
    }

    public static List<Advogado> getAdvogados(){

        return advogados;
    }


}