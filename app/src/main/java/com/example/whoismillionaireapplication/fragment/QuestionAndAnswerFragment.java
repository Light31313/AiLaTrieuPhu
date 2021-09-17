package com.example.whoismillionaireapplication.fragment;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whoismillionaireapplication.FragmentViewModel;
import com.example.whoismillionaireapplication.MainViewModel;
import com.example.whoismillionaireapplication.R;
import com.example.whoismillionaireapplication.dao.HighScoreDao;
import com.example.whoismillionaireapplication.dao.QuestionAndAnswerDao;
import com.example.whoismillionaireapplication.entity.HighScore;
import com.example.whoismillionaireapplication.entity.QuestionAndAnswer;
import com.example.whoismillionaireapplication.utils.AppDatabase;
import com.example.whoismillionaireapplication.utils.ScoreDatabase;

import java.util.List;
import java.util.Random;

public class QuestionAndAnswerFragment extends Fragment implements View.OnClickListener {
    private QuestionAndAnswerDao questionAndAnswerDao;
    private HighScoreDao highScoreDao;
    private QuestionAndAnswer questionAndAnswer;
    private TextView txtQuestion;
    private TextView[] txtAnswers;
    private TextView txtTime, txtLevel;
    private TextView txtResult;
    private ConstraintLayout clQuestionAndAnswer;
    private int level, penaltyLevel;
    private int time, countDown;
    private FragmentViewModel fragmentViewModel;
    private CountDownTimer timer, delay2sAndMakeNewLevel;
    private Dialog gameOverDialog;
    private String textResult, caseA, caseB, caseC, caseD;
    private Animation transitionUpAnim, transitionDownAnim;
    private MainViewModel mainViewModel;
    private Random random;
    private AnimationDrawable blinkAnim;
    private TextView txtChosenAnswer;
    private boolean isClickStop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_and_answer, container, false);
        txtQuestion = view.findViewById(R.id.txt_question);
        txtAnswers = new TextView[4];
        txtAnswers[0] = view.findViewById(R.id.txt_answer1);
        txtAnswers[1] = view.findViewById(R.id.txt_answer2);
        txtAnswers[2] = view.findViewById(R.id.txt_answer3);
        txtAnswers[3] = view.findViewById(R.id.txt_answer4);
        txtTime = view.findViewById(R.id.txt_time);
        txtLevel = view.findViewById(R.id.txt_level);
        clQuestionAndAnswer = view.findViewById(R.id.cl_question_and_answer);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        initEvent();
        showAndMakeNextLevel();
    }


    private void initComponent() {
        transitionUpAnim = AnimationUtils.loadAnimation(getContext(), R.anim.transion_up);
        transitionDownAnim = AnimationUtils.loadAnimation(getContext(), R.anim.transition_down);
        questionAndAnswerDao = AppDatabase.getInstance(getContext()).questionAndAnswerDao();
        highScoreDao = ScoreDatabase.getInstance(getContext()).highScoreDao();
        random = new Random();
        level = 1;
        time = 60;
        countDown = time;
        initGameOverDialog();
        timer = new CountDownTimer(200000, 1000) {
            @Override
            public void onTick(long l) {
                String textTime = countDown + "";
                txtTime.setText(textTime);
                countDown--;
                if (countDown < 0) {
                    onFinish();
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                if (level - 1 == 15) {
                    penaltyLevel = 15;
                } else if (level - 1 >= 10) {
                    penaltyLevel = 10;
                } else if (level - 1 > 5) {
                    penaltyLevel = 5;
                } else {
                    penaltyLevel = 0;
                }
                textResult = getString(R.string.result, penaltyLevel);
                txtResult.setText(textResult);
                txtResult.append(getString(R.string.timeOut));
                gameOverDialog.show();
            }
        };
        delay2sAndMakeNewLevel = new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                fragmentViewModel.setVisible(false);
                clQuestionAndAnswer.startAnimation(transitionUpAnim);
                clQuestionAndAnswer.setVisibility(View.VISIBLE);
                enableAllClickListener(true);
                makeQuestion(questionAndAnswer);
            }
        };

        fragmentViewModel = new ViewModelProvider(requireActivity()).get(FragmentViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }


    private void initEvent() {
        txtAnswers[0].setOnClickListener(this);
        txtAnswers[1].setOnClickListener(this);
        txtAnswers[2].setOnClickListener(this);
        txtAnswers[3].setOnClickListener(this);
        mainViewModel.getEvent5050().observe(getViewLifecycleOwner(), isSet -> {
            if (isSet) {
                int trueAnswer = questionAndAnswer.getTrueCase();
                int count = 0;
                while (count < 2) {
                    int getRandomAnswer = random.nextInt(4) + 1;
                    if ((trueAnswer != getRandomAnswer) && txtAnswers[getRandomAnswer - 1].isEnabled()) {
                        txtAnswers[getRandomAnswer - 1].setText("");
                        txtAnswers[getRandomAnswer - 1].setEnabled(false);
                        count++;
                    }
                }
                mainViewModel.setEvent5050(false);
            }
        });
        mainViewModel.getStopEvent().observe(getViewLifecycleOwner(), isStop -> {
            if (isStop) {
                timer.cancel();
                isClickStop = true;
                String textResult = getString(R.string.result, level - 1);
                txtResult.setText(textResult);
                txtResult.append(getString(R.string.resign));
                gameOverDialog.show();
                mainViewModel.setStopEvent(false);
            }
        });
        mainViewModel.getOtherEvent().observe(getViewLifecycleOwner(), isSet -> {
            if (isSet) {
                fragmentViewModel.setTrueCase(questionAndAnswer.getTrueCase());
                mainViewModel.setOtherEvent(false);
            }
        });
    }

    private QuestionAndAnswer randomQuestionAndAnswer(int level) {
        List<QuestionAndAnswer> questionAndAnswers = questionAndAnswerDao.getByLevel(level);
        Random random = new Random();
        int randomIndex = 0;
        if (!questionAndAnswers.isEmpty())
            randomIndex = random.nextInt(questionAndAnswers.size());
        return questionAndAnswers.get(randomIndex);
    }

    private void showAndMakeNextLevel() {

        questionAndAnswer = randomQuestionAndAnswer(level);
        getAnswerString();
        fragmentViewModel.setLevel(level);
        //hide this fragment and show QuestionFragment
        clQuestionAndAnswer.startAnimation(transitionDownAnim);
        // Disable all QuestionAndAnswerFragment components
        clQuestionAndAnswer.setVisibility(View.GONE);


        fragmentViewModel.setVisible(true);

        delay2sAndMakeNewLevel.start();
    }

    private void initGameOverDialog() {
        gameOverDialog = new Dialog(getActivity());
        gameOverDialog.setContentView(R.layout.dialog_result);
        gameOverDialog.setCanceledOnTouchOutside(false);
        AppCompatButton btnReplay = gameOverDialog.findViewById(R.id.btn_replay);
        AppCompatButton btnBack = gameOverDialog.findViewById(R.id.btn_back);
        AppCompatButton btnSaveData = gameOverDialog.findViewById(R.id.btn_save_data);
        EditText edtEnterName = gameOverDialog.findViewById(R.id.edt_enter_name);
        txtResult = gameOverDialog.findViewById(R.id.txt_result);
        btnReplay.setOnClickListener(view -> {
            getParentFragmentManager().beginTransaction().remove(QuestionAndAnswerFragment.this).commit();
            gameOverDialog.dismiss();
            getActivity().recreate();
        });
        btnBack.setOnClickListener(view -> {
            gameOverDialog.dismiss();
            getActivity().finish();
        });
        btnSaveData.setOnClickListener(view -> {
            if (isClickStop) {
                highScoreDao.create(new HighScore(edtEnterName.getText().toString().trim(), level - 1));
            } else
                highScoreDao.create(new HighScore(edtEnterName.getText().toString().trim(), penaltyLevel));
            btnSaveData.setText("");
            btnSaveData.setBackgroundResource(R.drawable.ic_baseline_check_24);
            btnSaveData.setEnabled(false);
        });
    }

    private void showResult() {
        timer.cancel();
        String textCongratulation;
        int penaltyLevel;
        if (level - 1 == 15) {
            penaltyLevel = 15;
            textCongratulation = getString(R.string.reach15);
        } else if (level - 1 >= 10) {
            penaltyLevel = 10;
            textCongratulation = getString(R.string.reach10);
        } else if (level - 1 > 5) {
            penaltyLevel = 5;
            textCongratulation = getString(R.string.reach5);
        } else {
            penaltyLevel = 0;
            textCongratulation = getString(R.string.reach0);
        }
        String textResult = getString(R.string.result, penaltyLevel);
        txtResult.setText(textResult);
        txtResult.append(textCongratulation);
        gameOverDialog.show();
    }

    private void getAnswerString() {
        caseA = getString(R.string.answerA, questionAndAnswer.getCaseA());
        caseB = getString(R.string.answerB, questionAndAnswer.getCaseB());
        caseC = getString(R.string.answerC, questionAndAnswer.getCaseC());
        caseD = getString(R.string.answerD, questionAndAnswer.getCaseD());
    }

    private void makeQuestion(QuestionAndAnswer questionAndAnswer) {

        txtQuestion.setText(questionAndAnswer.getQuestion());
        txtAnswers[0].setText(caseA);
        txtAnswers[1].setText(caseB);
        txtAnswers[2].setText(caseC);
        txtAnswers[3].setText(caseD);
        String textLevel = getString(R.string.question, level);
        txtLevel.setText(textLevel);

        //restart timer
        countDown = time;
        timer.start();
    }


    private void enableAllClickListener(boolean enable) {
        for (TextView answer : txtAnswers) {
            answer.setEnabled(enable);
        }
        mainViewModel.setEnableClickListener(enable);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        final int caseA = R.id.txt_answer1;
        final int caseB = R.id.txt_answer2;
        final int caseC = R.id.txt_answer3;
        final int caseD = R.id.txt_answer4;

        int trueAnswer = questionAndAnswer.getTrueCase();
        enableAllClickListener(false);
        timer.cancel();
        switch (id) {
            case caseA:
                txtAnswers[0].setBackgroundResource(R.drawable.frame_chooseans);
                txtChosenAnswer = txtAnswers[0];
                break;
            case caseB:
                txtAnswers[1].setBackgroundResource(R.drawable.frame_chooseans);
                txtChosenAnswer = txtAnswers[1];
                break;
            case caseC:
                txtAnswers[2].setBackgroundResource(R.drawable.frame_chooseans);
                txtChosenAnswer = txtAnswers[2];
                break;
            case caseD:
                txtAnswers[3].setBackgroundResource(R.drawable.frame_chooseans);
                txtChosenAnswer = txtAnswers[3];
                break;
        }
        new CountDownTimer(4000, 4000) {
            @Override
            public void onTick(long l) {
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    txtChosenAnswer.post(() -> {
                        if ((id == caseA && trueAnswer == 1) || (id == caseB && trueAnswer == 2) || (id == caseC && trueAnswer == 3) || (id == caseD && trueAnswer == 4)) {
                            txtChosenAnswer.setBackgroundResource(R.drawable.anim_drawable_blink_true);
                        } else {
                            txtChosenAnswer.setBackgroundResource(R.drawable.anim_drawable_blink_false);
                        }
                        blinkAnim = (AnimationDrawable) txtChosenAnswer.getBackground();
                        blinkAnim.start();
                    });
                });
                thread.start();
            }

            @Override
            public void onFinish() {
                blinkAnim.stop();
                txtChosenAnswer.setBackgroundResource(R.drawable.frame_question);
                if ((id == caseA && trueAnswer == 1) || (id == caseB && trueAnswer == 2) || (id == caseC && trueAnswer == 3) || (id == caseD && trueAnswer == 4)) {
                    level++;
                    if (level < 16) {
                        showAndMakeNextLevel();
                    } else {
                        showResult();
                    }
                } else {
                    showResult();
                }
            }
        }.start();

    }


}
