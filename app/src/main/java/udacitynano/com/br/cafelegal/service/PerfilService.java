package udacitynano.com.br.cafelegal.service;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import udacitynano.com.br.cafelegal.data.DatabaseContract;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Pessoa;
import udacitynano.com.br.cafelegal.singleton.UserType;

public class PerfilService {


    public int updatePerfil(Context context, Pessoa pessoa) {

        Advogado advogado = null;
        String[] selectionArgs = {""};
        String selectionClause = "";

        ContentValues pessoaValues = new ContentValues();

        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_NOME, pessoa.getNome());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_NOME_MEIO, pessoa.getNomeMeio());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_SOBRENOME, pessoa.getSobrenome());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_EMAIL, pessoa.getEmail());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_CEP, pessoa.getCep());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_ENDERECO, pessoa.getEndereco());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_NUMERO, pessoa.getNumero());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_COMPLEMENTO, pessoa.getComplemento());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_BAIRRO, pessoa.getBairro());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_CIDADE, pessoa.getCidade());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_ESTADO, pessoa.getEstado());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_PAIS, pessoa.getPais());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_SEXO, pessoa.getSexo());

        if (UserType.getInstance(context).isAdvogado()) {

            advogado = (Advogado) pessoa;

            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_NUMERO_INSC_OAB, advogado.getNumeroInscricaoOAB());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_SECCIONAL, advogado.getSeccional());
            Log.e("Debug","Perfil Service seccional: "+advogado.getSeccional());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_TIPO_INSCRICAO, advogado.getTipoInscricao());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_FONE_COMERCIAL, advogado.getTelefoneComercial());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_CONFIRMADO_CNA, advogado.getConfirmadoCNA());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_TWITTER, advogado.getTwitter());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_LINKEDIN, advogado.getLinkedIn());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_ESPECIALISTA_UM, advogado.getEspecialistaUm());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_ESPECIALISTA_DOIS, advogado.getEspecialistaDois());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_NOTIFICATION_REGISTRADO, advogado.getNotificationRegistration());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_LATITUTE, advogado.getLatitude());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_LONGITUDE, advogado.getLongitude());

        }

        selectionArgs[0] = String.valueOf(pessoa.getId());
        selectionClause = DatabaseContract.PessoaEntry.TABLE_NAME+"."+DatabaseContract.PessoaEntry.COLUMN_ID_SERVER + " = ?";

        Log.e("Debug","Perfil Service pessoaValues "+pessoaValues);
        Log.e("Debug","Perfil Service  selectionClause "+selectionClause);
        Log.e("Debug","Perfil Service  selectionArgs "+selectionArgs[0]);

        int updateUri = context.getContentResolver().update(
                DatabaseContract.PessoaEntry.CONTENT_URI,
                pessoaValues,
                selectionClause,
                selectionArgs
        );

        return updateUri;

    }

    public long createPerfil(Context context, Pessoa pessoa) {


        long pessoaId = 0;
        Advogado advogado = null;


        ContentValues pessoaValues = new ContentValues();
        if (UserType.getInstance(context).isAdvogado()) {

            advogado = (Advogado) pessoa;
        }


        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_ID_SERVER, pessoa.getId());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_NOME, pessoa.getNome());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_NOME_MEIO, pessoa.getNomeMeio());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_SOBRENOME, pessoa.getSobrenome());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_EMAIL, pessoa.getEmail());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_CEP, pessoa.getCep());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_ENDERECO, pessoa.getEndereco());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_NUMERO, pessoa.getNumero());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_COMPLEMENTO, pessoa.getComplemento());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_BAIRRO, pessoa.getBairro());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_CIDADE, pessoa.getCidade());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_ESTADO, pessoa.getEstado());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_PAIS, pessoa.getPais());
        pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_SEXO, pessoa.getSexo());

        if (UserType.getInstance(context).isAdvogado()) {

            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_NUMERO_INSC_OAB, advogado.getNumeroInscricaoOAB());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_SECCIONAL, advogado.getSeccional());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_TIPO_INSCRICAO, advogado.getTipoInscricao());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_FONE_COMERCIAL, advogado.getTelefoneComercial());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_CONFIRMADO_CNA, advogado.getConfirmadoCNA());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_TWITTER, advogado.getTwitter());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_LINKEDIN, advogado.getLinkedIn());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_ESPECIALISTA_UM, advogado.getEspecialistaUm());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_ESPECIALISTA_DOIS, advogado.getEspecialistaDois());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_NOTIFICATION_REGISTRADO, advogado.getNotificationRegistration());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_LATITUTE, advogado.getLatitude());
            pessoaValues.put(DatabaseContract.PessoaEntry.COLUMN_LONGITUDE, advogado.getLongitude());

        }

        Uri insertedUri = context.getContentResolver().insert(
                DatabaseContract.PessoaEntry.CONTENT_URI,
                pessoaValues
        );


        pessoaId = ContentUris.parseId(insertedUri);

        Log.e("Debug","Insert service pessoaId "+pessoaId);

        return pessoaId;
    }

}





