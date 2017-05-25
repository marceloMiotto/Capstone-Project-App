package udacitynano.com.br.cafelegal.service;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.ArrayList;

import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.data.DatabaseContract;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Pessoa;
import udacitynano.com.br.cafelegal.singleton.UserType;

public class PerfilService {

    private Context mContext;


    public PerfilService(Context context){
        mContext = context;
    }


    private long updateCreatePerfil(Pessoa pessoa){
        long result;
        result = updatePerfil(pessoa);
        if(result==0){
            result = createPerfil(pessoa);
        }

        return result;
    }

    private int updatePerfil(Pessoa pessoa) {

        Advogado advogado;
        String[] selectionArgs = {""};
        String selectionClause;

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

        if (UserType.isAdvogado(mContext)) {

            advogado = (Advogado) pessoa;

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

        }

        selectionArgs[0] = String.valueOf(pessoa.getId());
        selectionClause = DatabaseContract.PessoaEntry.TABLE_NAME+"."+DatabaseContract.PessoaEntry.COLUMN_ID_SERVER + " = ?";

        return mContext.getContentResolver().update(
                DatabaseContract.PessoaEntry.CONTENT_URI,
                pessoaValues,
                selectionClause,
                selectionArgs
        );

    }

    private long createPerfil(Pessoa pessoa) {


        long pessoaId;
        Advogado advogado = null;


        ContentValues pessoaValues = new ContentValues();
        if (UserType.isAdvogado(mContext)) {

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

        if (UserType.isAdvogado(mContext)) {

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
          
        }

        Uri insertedUri = mContext.getContentResolver().insert(
                DatabaseContract.PessoaEntry.CONTENT_URI,
                pessoaValues
        );


        pessoaId = ContentUris.parseId(insertedUri);
        return pessoaId;
    }

    public long updateCreateUserOnSQLite(Pessoa pessoa){
        pessoa.setId(UserType.getUserId(mContext));
        return updateCreatePerfil(pessoa);
    }

    public long updateCreateAdvogadoOnSQLite(Pessoa pessoa){
        return updateCreatePerfil(pessoa);
    }


    public void setSharedId(long id){
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(mContext.getString(R.string.preference_user_type_id), id);
        editor.apply();


    }

}





