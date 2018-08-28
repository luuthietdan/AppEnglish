package com.example.lausecdan.intent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;
    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER " +
//                QuizContract.QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("My mother___(buy) some  food at the grocery store", "is buying", "was bought", "is buy", 1);
        addQuestion(q1);
        Question q2 = new Question("Luke___(not study) Japanese in the library. He's at home with his friends.",
                "isn't study", "isn't studying", "was studied", 2);
        addQuestion(q2);
        Question q3 = new Question("___(She,run) down the street?",
                "She is running", "did she run", "Is she running", 3);
        addQuestion(q3);
        Question q4 = new Question("My cat___(eat) now.",
                "eatting", "ate", "eats", 1);
        addQuestion(q4);
        Question q5 = new Question("What___(you,wait) for?",
                "are you wait", "are you waiting", "did you wait", 2);
        addQuestion(q5);
        Question q6 = new Question("Listen! Our teacher___(speak).",
                "speaks", "spoke", "is speaking", 3);
        addQuestion(q6);
//        Question q1 = new Question("Easy: A is correct",
//                "A", "B", "C", 1, Question.DIFFICULTY_EASY);
//        addQuestion(q1);
//        Question q2 = new Question("Medium: B is correct",
//                "A", "B", "C", 2, Question.DIFFICULTY_MEDIUM);
//        addQuestion(q2);
//        Question q3 = new Question("Medium: C is correct",
//                "A", "B", "C", 3, Question.DIFFICULTY_MEDIUM);
//        addQuestion(q3);
//        Question q4 = new Question("Hard: A is correct",
//                "A", "B", "C", 1, Question.DIFFICULTY_HARD);
//        addQuestion(q4);
//        Question q5 = new Question("Hard: B is correct",
//                "A", "B", "C", 2, Question.DIFFICULTY_HARD);
//        addQuestion(q5);
//        Question q6 = new Question("Hard: C is correct",
//                "A", "B", "C", 3, Question.DIFFICULTY_HARD);
//        addQuestion(q6);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
       // cv.put(QuizContract.QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuizContract.QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
               // question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

//    public ArrayList<Question> getQuestions(String difficulty) {
//        ArrayList<Question> questionList = new ArrayList<>();
//        db = getReadableDatabase();
//
//        String[] selectionArgs = new String[]{difficulty};
//        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME +
//                " WHERE " + QuizContract.QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
//
//        if (c.moveToFirst()) {
//            do {
//                Question question = new Question();
//                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
//                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
//                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
//                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
//                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
//                question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_DIFFICULTY)));
//                questionList.add(question);
//            } while (c.moveToNext());
//        }
//
//        c.close();
//        return questionList;
//    }
}
