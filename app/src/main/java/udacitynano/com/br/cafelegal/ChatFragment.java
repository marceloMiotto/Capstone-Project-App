package udacitynano.com.br.cafelegal;


import android.content.Context;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import udacitynano.com.br.cafelegal.model.CafeLegalMessage;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

@SuppressWarnings("unused")
public class ChatFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener {

    private String mUsername;

    private Button mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<CafeLegalMessage, MessageViewHolder> mFirebaseAdapter;
    private ProgressBar mProgressBar;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAnalytics mFirebaseAnalytics;
    private EditText mMessageEditText;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "ChatFragment";
    private static  String MESSAGES_CHILD;
    private static final int DEFAULT_MSG_LENGTH_LIMIT = 100;
    private static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private static final String MESSAGE_URL = "http://cafelegal.firebase.google.com/message/";
    String MESSAGE_NOME_ADVOGADO;
    String MESSAGE_ADVOGADO_OAB;
    String MESSAGE_NOME_CONVIDA;

    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        // Required empty public constructor
    }


    public static ChatFragment newInstance(String messageChild, String messageNomeAdvogado, String messageAdvogadoOAB, String messageNomeConvida) {
        Bundle bundle = new Bundle();
        bundle.putString("messageChild",messageChild);
        bundle.putString("messageNomeAdvogado",messageNomeAdvogado);
        bundle.putString("messageAdvogadoOAB",messageAdvogadoOAB);
        bundle.putString("messageNomeConvida",messageNomeConvida);
        ChatFragment chatFragment = new ChatFragment();
        return chatFragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            MESSAGES_CHILD =  bundle.getString("messageChild");
            MESSAGE_NOME_ADVOGADO =  bundle.getString("messageNomeAdvogado");
            MESSAGE_ADVOGADO_OAB =  bundle.getString("messageAdvogadoOAB");
            MESSAGE_NOME_CONVIDA =  bundle.getString("messageNomeConvida");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);


        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (getArguments() != null) {
            readBundle(getArguments());
        }else{
            MESSAGES_CHILD = getActivity().getIntent().getStringExtra(getActivity().getString(R.string.adapter_extra_convite));
            MESSAGE_NOME_ADVOGADO = getActivity().getIntent().getStringExtra(getActivity().getString(R.string.adapter_extra_nome_advoagdo));
            MESSAGE_ADVOGADO_OAB = "OAB: " + getActivity().getIntent().getStringExtra(getActivity().getString(R.string.adapter_extra_advogado_oab));
            MESSAGE_NOME_CONVIDA = getActivity().getIntent().getStringExtra(getActivity().getString(R.string.adapter_extra_nome_convida));

        }


        if(UserType.isAdvogado(getActivity())){
            if(MESSAGE_NOME_ADVOGADO.equals("X")){
                mUsername = ANONYMOUS;
            }else{
                mUsername = MESSAGE_NOME_ADVOGADO +" - "+ MESSAGE_ADVOGADO_OAB;
            }
        }else{
            if(MESSAGE_NOME_CONVIDA.equals("X")){
                mUsername = ANONYMOUS;
            }else{
                mUsername = MESSAGE_NOME_CONVIDA;
            }
        }

        getActivity().setTitle(getString(R.string.title_activity_chat));

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) view.findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(true);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<CafeLegalMessage, MessageViewHolder>(
                CafeLegalMessage.class,
                R.layout.item_cafe_legal_message,
                MessageViewHolder.class,
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)) {

            @Override
            protected CafeLegalMessage parseSnapshot(DataSnapshot snapshot) {
                CafeLegalMessage cafeLegalMessage = super.parseSnapshot(snapshot);
                if (cafeLegalMessage != null) {
                    cafeLegalMessage.setId(snapshot.getKey());
                }
                return cafeLegalMessage;
            }

            @Override
            protected void populateViewHolder(final MessageViewHolder viewHolder,
                                              CafeLegalMessage cafeLegalMessage, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                if (cafeLegalMessage.getText() != null) {
                    viewHolder.messageTextView.setText(cafeLegalMessage.getText());
                    viewHolder.messageTextView.setVisibility(TextView.VISIBLE);
                    Log.e("Debug","Nomes: "+mUsername+" "+cafeLegalMessage.getName());
                    if(mUsername.equals(cafeLegalMessage.getName())){
                        viewHolder.mMessengerImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.user_green_144));
                    }else{
                        viewHolder.mMessengerImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.user_red_144));
                    }
                } else {
                    viewHolder.messageTextView.setVisibility(TextView.GONE);
                }

                viewHolder.messengerTextView.setText(cafeLegalMessage.getName());

                if (cafeLegalMessage.getText() != null) {
                    FirebaseAppIndex.getInstance().update(getMessageIndexable(cafeLegalMessage));
                }

                FirebaseUserActions.getInstance().end(getMessageViewAction(cafeLegalMessage));
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);


        // Initialize Firebase Measurement.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        mMessageEditText = (EditText) view.findViewById(R.id.messageEditText);
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



        mSendButton = (Button) view.findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CafeLegalMessage cafeLegalMessage = new CafeLegalMessage(mMessageEditText.getText().toString(), mUsername);
                mFirebaseDatabaseReference.child(MESSAGES_CHILD).push().setValue(cafeLegalMessage);
                mMessageEditText.setText("");
                mFirebaseAnalytics.logEvent(MESSAGE_SENT_EVENT, null);
            }
        });

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


        public static class MessageViewHolder extends RecyclerView.ViewHolder {
            public TextView messageTextView;
            public TextView messengerTextView;
            public ImageView mMessengerImageView;

            public MessageViewHolder(View v) {
                super(v);
                messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
                messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
                mMessengerImageView = (ImageView) itemView.findViewById(R.id.messengerImageView);
            }
        }


    private Action getMessageViewAction(CafeLegalMessage cafeLegalMessage) {
        return new Action.Builder(Action.Builder.VIEW_ACTION)
                .setObject(cafeLegalMessage.getName(), MESSAGE_URL.concat(cafeLegalMessage.getId()))
                .setMetadata(new Action.Metadata.Builder().setUpload(false))
                .build();
    }


    private Indexable getMessageIndexable(CafeLegalMessage cafeLegalMessage) {
        PersonBuilder sender = Indexables.personBuilder()
                .setIsSelf(Objects.equals(mUsername, cafeLegalMessage.getName()))
                .setName(cafeLegalMessage.getName())
                .setUrl(MESSAGE_URL.concat(cafeLegalMessage.getId() + "/sender"));

        PersonBuilder recipient = Indexables.personBuilder()
                .setName(mUsername)
                .setUrl(MESSAGE_URL.concat(cafeLegalMessage.getId() + "/recipient"));

        return Indexables.messageBuilder()
                .setName(cafeLegalMessage.getText())
                .setUrl(MESSAGE_URL.concat(cafeLegalMessage.getId()))
                .setSender(sender)
                .setRecipient(recipient)
                .build();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

}

