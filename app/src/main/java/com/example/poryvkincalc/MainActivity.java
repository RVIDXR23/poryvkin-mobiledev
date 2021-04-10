  package com.example.poryvkincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Окно калькулятора
 */

public class MainActivity extends AppCompatActivity {

    //Кнопки
    Button mBtn0;
    Button mBtn1;
    Button mBtn2;
    Button mBtn3;
    Button mBtn4;
    Button mBtn5;
    Button mBtn6;
    Button mBtn7;
    Button mBtn8;
    Button mBtn9;

    TextView mDisplay;

    Button mClear;
    Button mBack;
    Button mComma;
    Button mSign;

    Button mPlus;
    Button mMinus;
    Button mDiv;
    Button mMul;
    Button mResult;

    //Состояние калькулятора
    float mValue = 0;
    String mOperator = "";
    boolean isWriteStopped = false;

    String divZero = "Делить на 0 нельзя!";
    boolean divOnZero = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn0 = findViewById(R.id.btn0);
        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mBtn3 = findViewById(R.id.btn3);
        mBtn4 = findViewById(R.id.btn4);
        mBtn5 = findViewById(R.id.btn5);
        mBtn6 = findViewById(R.id.btn6);
        mBtn7 = findViewById(R.id.btn7);
        mBtn8 = findViewById(R.id.btn8);
        mBtn9 = findViewById(R.id.btn9);

        mDisplay = findViewById(R.id.display);

        mBack = findViewById(R.id.btnBack);
        mClear = findViewById(R.id.btnClear);
        mComma = findViewById(R.id.btnComma);

        mSign = findViewById(R.id.btnSign);

        mPlus = findViewById(R.id.btnPlus);
        mMinus = findViewById(R.id.btnMinus);
        mDiv = findViewById(R.id.btnDiv);
        mMul = findViewById(R.id.btnMul);

        mResult = findViewById(R.id.btnResult);

        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClick(v);
            }
        };

        //Подписки
        mBtn0.setOnClickListener(numberListener);
        mBtn1.setOnClickListener(numberListener);
        mBtn2.setOnClickListener(numberListener);
        mBtn3.setOnClickListener(numberListener);
        mBtn4.setOnClickListener(numberListener);
        mBtn5.setOnClickListener(numberListener);
        mBtn6.setOnClickListener(numberListener);
        mBtn7.setOnClickListener(numberListener);
        mBtn8.setOnClickListener(numberListener);
        mBtn9.setOnClickListener(numberListener);

        View.OnClickListener operatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperatorListener(v);
            }
        };

        mPlus.setOnClickListener(operatorListener);
        mMinus.setOnClickListener(operatorListener);
        mDiv.setOnClickListener(operatorListener);
        mMul.setOnClickListener(operatorListener);

        View.OnClickListener resultListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResultListener(v);
            }
        };

        mResult.setOnClickListener(resultListener);

        View.OnClickListener signListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignListener(v);
            }
        };

        mSign.setOnClickListener(signListener);

        View.OnClickListener clearListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearListener(v);
            }
        };

        mClear.setOnClickListener(clearListener);

        View.OnClickListener backListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackListener(v);
            }
        };

        mBack.setOnClickListener(backListener);

        View.OnClickListener commaListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommaListener(v);
            }
        };

        mComma.setOnClickListener(commaListener);
    }

    /**
     * обработка нажатия на числовую кнопку
     *
     * @param button - кнопка
     */
    public void onNumberClick(View button) {
        String number = ((Button) button).getText().toString();
        String display = mDisplay.getText().toString();

        if (display.equals("0") || isWriteStopped) {
            display = number;
            isWriteStopped = false;
        }
        else if(!isWriteStopped) {
            display += number;
        }

        mDisplay.setText(display);
    }

    /**
     * обработка нажатия на оператор
     *
     * @param button - кнопка
     */
    public void onOperatorListener(View button) {

        //1
        String operator = ((Button)button).getText().toString();
        mOperator = operator;

        //2

        String display = mDisplay.getText().toString();
        if(!display.equals(divZero))
            mValue = Float.parseFloat(display);

        //3
        mDisplay.setText("0");
    }

    /**
     * обработка нажатия на =
     *
     * @param button - кнопка
     */
    public void onResultListener(View button) {

        //1
        String display = mDisplay.getText().toString();
        float value = Float.parseFloat(display);

        //2
        float result = value;

        //3
        switch (mOperator) {
            case "+": {
                result = mValue + value;
                break;
            }
            case "-": {
                result = mValue - value;
                break;
            }
            case "*": {
                result = mValue * value;
                break;
            }
            case "/": {
                if(value!=0)
                    result = mValue / value;
                else
                    divOnZero = true;
                break;
            }

            //другие операторы
        }

        //4
        DecimalFormat format = new DecimalFormat("0.######");
        format.setRoundingMode(RoundingMode.DOWN);
        String resultText;
        if(divOnZero!=true)
            resultText = format.format(result).replace(",", ".");
        else
            resultText = divZero;

        //5
        System.out.println(resultText);
        mDisplay.setText(resultText);

        //6
        mValue = result;
        mOperator = "";
        isWriteStopped = true;
        divOnZero = false;
    };
    /**
     * обработка нажатия на +/-
     * @param button - кнопка
     */
    public void onSignListener(View button){

        float value = Float.parseFloat(mDisplay.getText().toString());
        value = value*-1;

        DecimalFormat format = new DecimalFormat("0.######");
        //format.setRoundingMode(RoundingMode.DOWN);

        String resValue = format.format(value).replace(",", ".");

        mDisplay.setText(String.valueOf(resValue));
    }

    /**
     * обработка нажатия на C
     * @param button - кнопка
     */
    public void onClearListener(View button){
        mDisplay.setText("0");
        mValue = 0;
        mOperator = "";
    }

    /**
     * обработка нажатия на BACK
     * @param button - кнопка
     */
    public void onBackListener(View button){
        String text = mDisplay.getText().toString().replace(",", ".");
        System.out.println(text);
        System.out.println(isWriteStopped);
        if(isWriteStopped==false && text.length()>1)
            mDisplay.setText(removeLastChar(text));
        else if(isWriteStopped==false && !text.equals("0"))
            mDisplay.setText("0");
    }

    /**
     * метод для удаления последнего символа
     * @param str - значение на дисплее
     * @return - возращает строку без символа(ов)
     */
    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }

    /**
     * обработка нажатия на .
     * @param button - кнопка
     */
    public void onCommaListener(View button){
        String text = mDisplay.getText().toString();

        String lastSymbol = Character.toString(text.charAt(text.length()-1));
        String addComma = text + ".";

        boolean isHasComma = false;
        for(int i = 0; i < text.length(); i++)
            if(Character.toString(text.charAt(i)).equals("."))
                isHasComma = true;

        if (isHasComma == false)
            mDisplay.setText(addComma);
    }

    /**
     * Создание меню
     * @param menu - объект меню
     * @return успешность обработки
     */
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Создание меню
     * @param item - элемент меню
     * @return успешность обработки
     */
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Обработка вариантов значений
        switch (item.getItemId()) {
            // Нажатие на кнопку "Настройки"
            case R.id.action_settings:
                startSettings();
                return true;
            //Нажатие на кнопку "О приложении"
            case R.id.about:
                about();
                return true;
            //Нажатие на кнопку "Копировать"
            case R.id.copy:
                copy();
                return true;
            //Нажатие на кнопку "Вставить"
            case R.id.paste:
                paste();
                return true;
            //Неизвезстное значение обрабатывается родителем
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Вставка текста в дисплей
     */
    private void paste() {
        // Получаем менеджера
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        // Убедимся что менеджер доступен
        if(clipboard != null) {
            // Проверим что в буфере обмена есть текст
            if(clipboard.hasPrimaryClip()
            && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                // Получим данные
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

                // Преобразуем данные к строке
                String pasteData = item.getText().toString();

                // Проверим является ли наша строка числом
                // и только тогда установим на экран
                if(isNumeric(pasteData))
                    mDisplay.setText(pasteData);
            }
        }
    }

    /**
     * Скопировать текст с дисплея в буфер обмена
     */
    private void copy() {
        // Получаем менеджера
        ClipboardManager clipboard = (ClipboardManager)
            getSystemService(Context.CLIPBOARD_SERVICE);

        // Убедимся что менеджер доступен
        if(clipboard != null) {
            // Создаем вырезанные данные из текста
            ClipData clip = ClipData.newPlainText("", mDisplay.getText());

            // Устанавливаем данные в буфер обмена
            clipboard.setPrimaryClip(clip);
        }
    }

    /**
     * Открытие экрана "О приложении"
     */
    private void about() {
        //Создаем намерение на вызов экрана About
        Intent activityIntent = new Intent(getApplicationContext(), About.class);

        //Запускаем экран передав намерение
        startActivity(activityIntent);

    }

    private void startSettings() {
    }

    /**
     * Проверка строки на число
     * @param text - текст на проверку
     * @return - успешность проверки
     */
    public static boolean isNumeric (String text) {
        // Если текст пустой, то провал
        if(text == null)
            return false;

        // Пробуем сделать конвертацию
        try
        {
            // Метод бросает исключение в случае провала
            Double.parseDouble(text);
        }
        // Ловим исключение
        catch (NumberFormatException e)
        {
            // Конвертация не удалась - провал
            return false;
        }
        // Конвертация удалась - успех
        return true;
    }

}