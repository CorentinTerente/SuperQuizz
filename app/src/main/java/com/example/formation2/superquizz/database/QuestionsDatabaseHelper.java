package com.example.formation2.superquizz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.formation2.superquizz.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "questionDatabase";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_QUESTION = "question";

    private static final String KEY_QUESTION_ID = "id";
    private static final String KEY_QUESTION_TITLE = "title";
    private static final String KEY_QUESTION_ANSWER1 = "answer1";
    private static final String KEY_QUESTION_ANSWER2 = "answer2";
    private static final String KEY_QUESTION_ANSWER3 = "answer3";
    private static final String KEY_QUESTION_ANSWER4 = "answer4";
    private static final String KEY_QUESTION_GOOD_ANSWER = "good_answer";
    private static final String KEY_QUESTION_USER_ANSWER = "user_answer";
    private static final String KEY_QUESTION_URL_IMG = "author_img_url";
    private static final String KEY_QUESTION_HAVE_RESPOND = "have_respond";

    private static QuestionsDatabaseHelper instance;


    public static synchronized QuestionsDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionsDatabaseHelper(context.getApplicationContext());
        }

        return instance;
    }


    private QuestionsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUESTION = "CREATE TABLE " + TABLE_QUESTION +
                "(" +
                KEY_QUESTION_ID + " INTEGER PRIMARY KEY," +
                KEY_QUESTION_TITLE + " TEXT," +
                KEY_QUESTION_ANSWER1 + " TEXT," +
                KEY_QUESTION_ANSWER2 + " TEXT," +
                KEY_QUESTION_ANSWER3 + " TEXT," +
                KEY_QUESTION_ANSWER4 + " TEXT," +
                KEY_QUESTION_GOOD_ANSWER + " INTEGER," +
                KEY_QUESTION_USER_ANSWER + " TEXT," +
                KEY_QUESTION_URL_IMG + " TEXT," +
                KEY_QUESTION_HAVE_RESPOND + " INTEGER" +
                ")";

        db.execSQL(CREATE_TABLE_QUESTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
            onCreate(db);
        }
    }

    public void addQuestion(Question newQuestion) throws Exception {

        if (newQuestion.getQuestionId() == this.getQuestion(newQuestion.getQuestionId()).getQuestionId()) {
            throw new Exception("already exists");
        }

        SQLiteDatabase db = getWritableDatabase();


        db.beginTransaction();
        try {

            String questionTitle = newQuestion.getTitle();
            String questionAnswer1 = newQuestion.getPropositions().get(0);
            String questionAnswer2 = newQuestion.getPropositions().get(1);
            String questionAnswer3 = newQuestion.getPropositions().get(2);
            String questionAnswer4 = newQuestion.getPropositions().get(3);
            int questionGoodAnswer = newQuestion.getGoodResponse();

            ContentValues values = new ContentValues();
            values.put(KEY_QUESTION_TITLE, questionTitle);
            values.put(KEY_QUESTION_ANSWER1, questionAnswer1);
            values.put(KEY_QUESTION_ANSWER2, questionAnswer2);
            values.put(KEY_QUESTION_ANSWER3, questionAnswer3);
            values.put(KEY_QUESTION_ANSWER4, questionAnswer4);
            values.put(KEY_QUESTION_GOOD_ANSWER, questionGoodAnswer);

            db.insertOrThrow(TABLE_QUESTION, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("Error", "insert fail");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteQuestion(Question q) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(TABLE_QUESTION, KEY_QUESTION_ID + " = ?", new String[]{String.valueOf(q.getQuestionId())});
            db.setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }


    public void updateQuestion(Question questionAnswered) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            int questionId = questionAnswered.getQuestionId();
            ContentValues values = new ContentValues();
            values.put(KEY_QUESTION_GOOD_ANSWER, questionAnswered.getGoodResponse());
            values.put(KEY_QUESTION_ANSWER1, questionAnswered.getPropositions().get(0));
            values.put(KEY_QUESTION_ANSWER2, questionAnswered.getPropositions().get(1));
            values.put(KEY_QUESTION_ANSWER3, questionAnswered.getPropositions().get(2));
            values.put(KEY_QUESTION_ANSWER4, questionAnswered.getPropositions().get(3));
            values.put(KEY_QUESTION_TITLE, questionAnswered.getTitle());


            db.update(TABLE_QUESTION, values, KEY_QUESTION_ID + " = ?", new String[]{String.valueOf(questionId)});

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Error", "Update fail");
        } finally {
            db.endTransaction();
        }
    }

    public void updateQuestionAnswered(Question questionAnswered) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            String userAnswer = questionAnswered.getUserResponse();
            int questionId = questionAnswered.getQuestionId();
            ContentValues values = new ContentValues();
            values.put(KEY_QUESTION_USER_ANSWER, userAnswer);
            values.put(KEY_QUESTION_GOOD_ANSWER, questionAnswered.getGoodResponse());
            values.put(KEY_QUESTION_ANSWER1, questionAnswered.getPropositions().get(0));
            values.put(KEY_QUESTION_ANSWER2, questionAnswered.getPropositions().get(1));
            values.put(KEY_QUESTION_ANSWER3, questionAnswered.getPropositions().get(2));
            values.put(KEY_QUESTION_ANSWER4, questionAnswered.getPropositions().get(3));
            values.put(KEY_QUESTION_TITLE, questionAnswered.getTitle());
            values.put(KEY_QUESTION_HAVE_RESPOND,questionAnswered.getHaveRespond());


            db.update(TABLE_QUESTION, values, KEY_QUESTION_ID + " = ?", new String[]{String.valueOf(questionId)});

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Error", "Update fail");
        } finally {
            db.endTransaction();
        }
    }

    public List<Question> getAllQuestion() {
        List<Question> questionList = new ArrayList<>();

        String QUESTIONS_SELECT_QUERY =
                String.format("SELECT * FROM %s", TABLE_QUESTION);

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(QUESTIONS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Question newQuestion = new Question(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_TITLE)));

                    newQuestion.setQuestionId(cursor.getInt(cursor.getColumnIndex(KEY_QUESTION_ID)));

                    newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER1)));
                    newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER2)));
                    newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER3)));
                    newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER4)));

                    newQuestion.setGoodResponse(cursor.getInt(cursor.getColumnIndex(KEY_QUESTION_GOOD_ANSWER)));

                    newQuestion.setUserResponse(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_USER_ANSWER)));
                    newQuestion.setHaveRespond(cursor.getInt(cursor.getColumnIndex(KEY_QUESTION_HAVE_RESPOND)));
                    questionList.add(newQuestion);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Error", "Fail to select");
        } finally {
            cursor.close();
        }

        return questionList;
    }

    public List<Question> getUnAnsweredQuestion() {
        List<Question> questionList = new ArrayList<>();

        String QUESTIONS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s IS NULL", TABLE_QUESTION, KEY_QUESTION_USER_ANSWER);

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(QUESTIONS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Question newQuestion = new Question(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_TITLE)));

                    newQuestion.setQuestionId(cursor.getInt(cursor.getColumnIndex(KEY_QUESTION_ID)));
                    newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER1)));
                    newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER2)));
                    newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER3)));
                    newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER4)));

                    newQuestion.setGoodResponse(cursor.getInt(cursor.getColumnIndex(KEY_QUESTION_GOOD_ANSWER)));


                    newQuestion.setUserResponse(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_USER_ANSWER)));
                    newQuestion.setImageUrl(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_URL_IMG)));
                    questionList.add(newQuestion);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Error", "Fail to select");
        } finally {
            cursor.close();
        }

        return questionList;
    }

    public Question getQuestion(int id) {
        Question question = new Question();

        String QUESTIONS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = %s", TABLE_QUESTION, KEY_QUESTION_ID, id);

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(QUESTIONS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                Question newQuestion = new Question(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_TITLE)));

                newQuestion.setQuestionId(cursor.getInt(cursor.getColumnIndex(KEY_QUESTION_ID)));
                newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER1)));
                newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER2)));
                newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER3)));
                newQuestion.getPropositions().add(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ANSWER4)));

                newQuestion.setGoodResponse(cursor.getInt(cursor.getColumnIndex(KEY_QUESTION_GOOD_ANSWER)));


                newQuestion.setUserResponse(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_USER_ANSWER)));
                question = newQuestion;
            }
        } catch (Exception e) {
            Log.e("Error", "Fail to select");
        } finally {
            cursor.close();
        }
        return question;
    }


    public void synchroniseDatabaseQuestions(List<Question> serverQuestions) {


        List<Question> databaseQuestions = getAllQuestion();


        // Here we will choose if we need to add or to update the question return by the server
        for (Question serverQuestion : serverQuestions) {
            boolean found = false;
            for (Question dataBaseQuestion : databaseQuestions) {
                if (serverQuestion.getQuestionId() == dataBaseQuestion.getQuestionId()) {
                    found = true;
                    break;
                }
            }

            if (found) {
                updateQuestion(serverQuestion);
            } else {
                try {
                    addQuestion(serverQuestion);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Now we want to delete the question if thy are not on the server anymore
        for (Question dataBaseQuestion : databaseQuestions) {
            boolean found = false;
            for (Question serverQuestion : serverQuestions) {
                if (serverQuestion.getQuestionId() == dataBaseQuestion.getQuestionId()) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                deleteQuestion(dataBaseQuestion);
            }
        }
    }
}