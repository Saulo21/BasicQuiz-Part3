package es.ulpgc.eite.da.basicquizlab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CheatActivity extends AppCompatActivity {

  public static final String TAG = "Quiz.CheatActivity";

  public final static String EXTRA_ANSWER = "EXTRA_ANSWER";
  public final static String EXTRA_CHEATED = "EXTRA_CHEATED";

  //Etiqueta para guardar si el usuario ha hecho click en yes
  public final static String KEY_USER_CHEAT_BUTTON = "USER_CHEAT_BUTTON";


  private Button noButton, yesButton;
  private TextView answerText;

  private int currentAnswer;
  private boolean answerCheated;
  //Variable para ver si se ha hecho click en yes
  private boolean yesButtonClicked = false;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);

    getSupportActionBar().setTitle(R.string.cheat_title);

    if(savedInstanceState != null) { // recreando activity

      // fijando estado de cheat

      yesButtonClicked=savedInstanceState.getBoolean(KEY_USER_CHEAT_BUTTON);
      currentAnswer=savedInstanceState.getInt(EXTRA_ANSWER);
      answerCheated=savedInstanceState.getBoolean(EXTRA_CHEATED);

      // aplicar estado

    }

    initLayoutData();
    linkLayoutComponents();
    enableLayoutButtons();
  }

  private void initLayoutData() {
    currentAnswer = getIntent().getExtras().getInt(EXTRA_ANSWER);
  }

  private void linkLayoutComponents() {
    noButton = findViewById(R.id.noButton);
    yesButton = findViewById(R.id.yesButton);

    answerText = findViewById(R.id.answerText);
  }

  private void enableLayoutButtons() {

    if(yesButtonClicked){
      yesButton.setEnabled(false);
      noButton.setEnabled(false);
      if(currentAnswer == 1){
        answerText.setText(R.string.true_text);
      } else {
        answerText.setText(R.string.false_text);
      }
    } else {
      noButton.setOnClickListener(v -> onNoButtonClicked());
      yesButton.setOnClickListener(v -> onYesButtonClicked());
    }
  }

  private void returnCheatedStatus() {
    Log.d(TAG, "returnCheatedStatus()");
    Log.d(TAG, "answerCheated: " + answerCheated);

    Intent intent = new Intent();
    intent.putExtra(EXTRA_CHEATED, answerCheated);
    setResult(RESULT_OK, intent);

    finish();
  }

  @Override
  public void onBackPressed() {
    Log.d(TAG, "onBackPressed()");

    returnCheatedStatus();
  }


  private void onYesButtonClicked() {
    yesButton.setEnabled(false);
    noButton.setEnabled(false);
    answerCheated = true;
    yesButtonClicked=true;

    if(currentAnswer == 0) {
      answerText.setText(R.string.false_text);
    } else {
      answerText.setText(R.string.true_text);

    }
  }

  private void onNoButtonClicked() {
    yesButton.setEnabled(false);
    noButton.setEnabled(false);

    returnCheatedStatus();
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putBoolean(KEY_USER_CHEAT_BUTTON, yesButtonClicked);
    outState.putInt(EXTRA_ANSWER,currentAnswer);
    outState.putBoolean(EXTRA_CHEATED, answerCheated);
  }
}
