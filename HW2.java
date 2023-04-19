import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import java.util.logging.*;
import java.util.Scanner;

public class HW2 {
    public static Scanner iScanner = new Scanner(System.in);

    public static void main(String[] args) throws ParseException, IOException{

        Request(); // формирование строки запроса из Json 
        //BubbleSort(); //сортировка пузырьком
        //Calculator(); // калькулятор
        //JSONparserText(); //
    }
    /*Дана строка sql-запроса "select * from students where ". Сформируйте часть WHERE этого запроса, 
    используя StringBuilder. Данные для фильтрации приведены ниже в виде json-строки.
    Если значение null, то параметр не должен попадать в запрос.
    Параметры для фильтрации: {"name":"Ivanov", "country":"Russia", "city":"Moscow", "age":"null"} */
    public static void Request() {
        
        String str ="{\"name\":\"Ivanov\", \"country\":\"Russia\", \"city\":\"Moscow\", \"age\":\"null\"}";
        str = str.replace("{", "");
        str = str.replace("}", "");

        StringBuilder stRequest = new StringBuilder("select * from students where ");
        String [] strArray = str.split(",");
           for (String st : strArray) {
            String [] elementArray = st.split(":");
            if (!elementArray[1].equals("\"null\"")) {
                stRequest.append(elementArray[0]);
                stRequest.append("=");
                stRequest.append(elementArray[1]);
                stRequest.append(" and ");
            }
        }
        stRequest.delete(stRequest.length()-5, stRequest.length());
        System.out.println(stRequest.toString());
    }
    
    /* Реализуйте алгоритм сортировки пузырьком числового массива, результат после каждой итерации запишите в лог-файл. */
    public static void BubbleSort() {
        try (FileWriter fil = new FileWriter("log.txt", false))  {
            int size = 10;
            int [] array = matrix(size);
            printArray(array);
            String result = "";
            int temp = 0;
            for (int i = 0; i < size-1; i++) {
                result = "";
                for (int j = size - 1; j > i; j--) {
                    if (array[j - 1] > array[j]){
                        temp = array[j]; 
                        array[j] = array[j-1];
                        array[j-1] = temp;
                    }
                }
                for (int element : array) {
                    result+=element;
                    result+=", ";
                }
                fil.write(result);
                fil.append('\n');
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static int[] matrix(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(1, 10);
        }
        return array;
    }
    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+", ");
        } 
        System.out.println();
    }
 /*[{"фамилия":"Иванов","оценка":"5","предмет":"Математика"},{"фамилия":"Петрова","оценка":"4","предмет":"Информатика"},
 {"фамилия":"Краснов","оценка":"5","предмет":"Физика"}]
Написать метод(ы), который распарсит json и, используя StringBuilder, создаст строки вида: 
Студент [фамилия] получил [оценка] по предмету [предмет].
Пример вывода:
Студент Иванов получил 5 по предмету Математика.
Студент Петрова получил 4 по предмету Информатика.
Студент Краснов получил 5 по предмету Физика. */
    public static void JSONparserText() throws ParseException {
        String str = "[{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"},{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"},{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}]";
        StringBuilder stBuilder = new StringBuilder();
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(str);

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
            stBuilder.setLength(0);
            String name = (String) jsonObject.get("фамилия");
            stBuilder.append("Студент " + name);
    
            String mark = (String) jsonObject.get("оценка");
            stBuilder.append(" получил " + mark);

            String subject = (String) jsonObject.get("предмет");
            stBuilder.append(" по предмету " + subject);

            System.out.println(stBuilder);  
            
        }


        }
 /* К калькулятору из предыдущего дз добавить логирование. */
    public static void Calculator() {
    try {
        Logger logger = Logger.getLogger(HW2.class.getName());
        FileHandler fh = new FileHandler("calculator.log"); 
        logger.addHandler(fh);
        SimpleFormatter sFormat = new SimpleFormatter();
        fh.setFormatter(sFormat);
        logger.setUseParentHandlers(false);
        logger.log(Level.INFO, "Logging start");
        Scanner iScanner = new Scanner(System.in);
        String isCont = "yes";
        while (!isCont.equals("no")) {
            System.out.print("Введите 1-ое число: ");
            String str1 = iScanner.nextLine();
            Double number1 = Double.parseDouble(str1);
            System.out.print("Введите 2-ое число: ");
            String str2 = iScanner.nextLine();
            Double number2 = Double.parseDouble(str2);
            System.out.print("Введите операцию (+, -, *, /): ");
            String operation = iScanner.nextLine(); 
            if (operation.equals("+")) {
                String expr = String.format("%f + %f = %f", number1,number2,number1+number2);
                System.out.print(expr);  
                logger.log(Level.INFO, expr);
            }
            else if (operation.equals("-")) {
                String expr = String.format("%f - %f = %f", number1,number2,number1-number2);
                System.out.print(expr); 
                logger.log(Level.INFO, expr);
            }
            else if (operation.equals("*")) {
                String expr = String.format("%f * %f = %f", number1,number2,number1*number2);
                System.out.print(expr);  
                logger.log(Level.INFO, expr);
            }
            else if (operation.equals("/")){
                String expr = String.format("%f / %f = %f", number1,number2,number1/number2);
                System.out.print(expr);  
                logger.log(Level.INFO, expr);
            }
            else {
                System.out.println("Неправильно введена операция");
                logger.log(Level.INFO, "Operation is not supported");
            }
            System.out.println();
    
            System.out.print("Вы хотите выполнить еще одну операцию (yes/no): ");
            isCont = iScanner.nextLine();
        }
    } catch(IOException ex) {
        ex.printStackTrace();
    }
    iScanner.close();
   
}
  

}
