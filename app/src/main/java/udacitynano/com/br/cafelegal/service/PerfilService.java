package udacitynano.com.br.cafelegal.service;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.data.DatabaseContract;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Cliente;
import udacitynano.com.br.cafelegal.model.Pessoa;
import udacitynano.com.br.cafelegal.singleton.UserType;

public class PerfilService {

    private Context mContext;
    private View mView;

    public PerfilService(Context context, View view){
        mContext = context;
        mView = view;
    }


    private long updateCreatePerfil(Pessoa pessoa){
        long result = 0;
        result = updatePerfil(pessoa);
        if(result==0){
            result = createPerfil(pessoa);
        }

        return result;
    }

    private int updatePerfil(Pessoa pessoa) {

        Advogado advogado = null;
        String[] selectionArgs = {""};
        String selectionClause = "";

        ContentValues pessoaValues = new ContentValues();

        Log.e("Debug","Valores para o update - nome "+pessoa.getNome());

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

        if (UserType.getInstance(mContext).isAdvogado()) {

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

        int updateUri = mContext.getContentResolver().update(
                DatabaseContract.PessoaEntry.CONTENT_URI,
                pessoaValues,
                selectionClause,
                selectionArgs
        );
        Log.e("Debug","Perfil Service update return "+updateUri);
        return updateUri;

    }

    private long createPerfil(Pessoa pessoa) {


        long pessoaId = 0;
        Advogado advogado = null;


        ContentValues pessoaValues = new ContentValues();
        if (UserType.getInstance(mContext).isAdvogado()) {

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

        if (UserType.getInstance(mContext).isAdvogado()) {

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

        Uri insertedUri = mContext.getContentResolver().insert(
                DatabaseContract.PessoaEntry.CONTENT_URI,
                pessoaValues
        );


        pessoaId = ContentUris.parseId(insertedUri);

        Log.e("Debug","Insert service pessoaId "+pessoaId);

        return pessoaId;
    }

    public long updateCreateUserOnSQLite(Pessoa pessoa){
        pessoa.setId(UserType.getInstance(mContext).getUserId());
        return updateCreatePerfil(pessoa);
    }


    public void setSharedId(long id){
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(mContext.getString(R.string.preference_user_type_id), id);
        editor.commit();

        Log.e("Debug","Shared User id: "+id);
    }

}





