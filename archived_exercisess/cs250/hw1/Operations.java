package hw1;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Operations {
    public static void main(String[] args) {
        System.out.println("Task 1");
        if(args.length != 3){
            System.out.println("Incorrect number of arguments have been provided. Program Terminating!");
            System.exit(1);
        }else{
            System.out.println("Correct number of arguments given.");
        }


        System.out.println("Task 2");
        LinkedHashMap<String, String> binary_deciaml_hex = new LinkedHashMap<>();

        for(int i = 0; i<args.length; i++){
            String current_arg = args[i];
            String first_2_char = current_arg.substring(0,2);
            if(first_2_char.equals("0b")){
                binary_deciaml_hex.put("Binary", current_arg);
            }
            else if(first_2_char.equals("0x")){
                binary_deciaml_hex.put("Hexadecimal", current_arg);
            }
            else{
                binary_deciaml_hex.put("Decimal", current_arg);
            }
        }
        for(String item : binary_deciaml_hex.keySet()){
            System.out.println(binary_deciaml_hex.get(item) +"="+ item);
        }

        System.out.println("Task 3");
        boolean did_we_fail = false;
        for(String item : binary_deciaml_hex.keySet()){
            String current_item = binary_deciaml_hex.get(item);
            if(item.equals("Binary")){
                String actual_number = current_item.substring(2);

                if(!(Pattern.matches("[0-1]+", actual_number))){
                    System.out.println(current_item + "=" + "false");
                    did_we_fail = true;
                }else
                    System.out.println(current_item + "=" + "true");
            }
            else if(item.equals("Hexadecimal")){
                String actual_number = current_item.substring(2);

                if(!(Pattern.matches("[a-fA-F0-9]+", actual_number))){
                    System.out.println(current_item + "=" + "false");
                    did_we_fail = true;
                }else
                    System.out.println(current_item + "=" + "true");

            }
            else if(item.equals("Decimal")){

                if(!(Pattern.matches("\\d+(\\.\\d+)?", current_item))){
                    System.out.println(current_item + "=" + "false");
                    did_we_fail = true;
                }else
                    System.out.println(current_item + "=" + "true");
            }
        }
        if(did_we_fail){
            System.exit(1);
        }

        System.out.println("Task 4");
        String first_binary = "";
        String second_binary = "";
        String third_binary = "";
        LinkedHashMap<String, String> binary = new LinkedHashMap<>();
        for(String item : binary_deciaml_hex.keySet()){
            String current_item = binary_deciaml_hex.get(item);
            String current_item_after2 = current_item.substring(2);
            
            if(item.equals("Binary")){
                double final_answer_to_decimal = 0;
                int power_to_raise_dec = 0;
                for(int i = current_item_after2.length()-1; i>=0; i--){
                    if (current_item_after2.charAt(i) == '1'){
                        final_answer_to_decimal += Math.pow(2, power_to_raise_dec);
                    }
                    power_to_raise_dec++;
                }
                String final_hex = find_hex_from_dec(final_answer_to_decimal);
                System.out.println("Start"+'='+current_item+','+"Binary"+'='+current_item+','+"Decimal"+'='+final_answer_to_decimal+','+"Hexadecimal"+"=0x"+final_hex);
                binary.put(current_item, current_item_after2);
                first_binary = current_item_after2;
            }
            else if(item.equals("Hexadecimal")){
                double final_answer_to_decimal = find_dec_from_hex(current_item_after2);
                String final_answer_to_binary = find_bin_from_dec(final_answer_to_decimal);
                System.out.println("Start"+'='+ current_item +','+"Binary"+"=0b"+ final_answer_to_binary +','+"Decimal"+'='+final_answer_to_decimal+','+"Hexadecimal"+ '='+ current_item);
                binary.put(current_item, final_answer_to_binary);
                second_binary = final_answer_to_binary;
            }
            else if(item.equals("Decimal")){
                String final_answer_to_hex = find_hex_from_dec(Double.parseDouble(current_item));
                String final_answer_to_binary = find_bin_from_dec(Double.parseDouble(current_item));
                System.out.println("Start"+'='+ current_item +','+"Binary"+"=0b"+ final_answer_to_binary +','+"Decimal"+'='+current_item+','+"Hexadecimal"+ "=0x"+ final_answer_to_hex);
                binary.put(current_item, final_answer_to_binary);
                third_binary = final_answer_to_binary;
            }

        }

        System.out.println("Task 5");
        LinkedHashMap<String, String> binary_replacement_ones_complement = new LinkedHashMap<>();
        for(String item : binary.keySet()){
            String temp_replacement = binary.get(item).replace('0', '5').replace('1', '8');
            temp_replacement = temp_replacement.replace('5', '1').replace('8', '0');
            System.out.println(item + "=" +binary.get(item)+ "=>"+temp_replacement);
            binary_replacement_ones_complement.put(item, temp_replacement);
        }

        System.out.println("Task 6");
        for(String item : binary_replacement_ones_complement.keySet()){
            String temp_2s_complement = binary_replacement_ones_complement.get(item);
            if(temp_2s_complement.charAt(temp_2s_complement.length()-1)=='0'){
                temp_2s_complement = temp_2s_complement.substring(0, temp_2s_complement.length()-1)+1;
            }else {
                int temp_remainder = 0;
                for(int i = temp_2s_complement.length()-1; i>=0; i--){
                   if(temp_2s_complement.charAt(i)=='1' && temp_remainder == 0){
                    temp_2s_complement = temp_2s_complement.substring(0, i) + '0' + temp_2s_complement.substring(i + 1);
                    temp_remainder = 1;
                   }else if (temp_2s_complement.charAt(i)=='0' && temp_remainder == 1){
                    temp_2s_complement = temp_2s_complement.substring(0, i) + '1' + temp_2s_complement.substring(i + 1);
                    break;
                   }
                }
            }

            System.out.println(item +  "=" +binary_deciaml_hex.get(item)+ "=>" + temp_2s_complement);
        }


        System.out.println("Task 7");
        String and_string = "";
        String or_string = "";
        String xor_string = "";
        int max_length = Math.max(first_binary.length(),Math.max(second_binary.length(), third_binary.length()));
        first_binary = String.format("%" + max_length + "s", first_binary).replace(' ', '0');
        second_binary = String.format("%" + max_length + "s", second_binary).replace(' ', '0');
        third_binary = String.format("%" + max_length + "s", third_binary).replace(' ', '0');

        for(int i = max_length -1; i >=0 ; i--){
            String comparitorxor = "0";
            String comparitoror = "0";
            String comparitorand = "0";
            if(first_binary.charAt(i) == second_binary.charAt(i)){
                comparitorxor = "0";
                if(first_binary.charAt(i) == '1'){
                    comparitoror = "1";
                    comparitorand = "1";
                }
                
            }else if(first_binary.charAt(i) != second_binary.charAt(i)){
                comparitorxor = "1";
                comparitoror = "1";
                comparitorand = "0";
            }
            if(comparitorxor.charAt(0) == third_binary.charAt(i)){
                comparitorxor = "0";
            }else{
                comparitorxor = "1";
            }
            if(comparitorand.charAt(0) == third_binary.charAt(i)){
                if(third_binary.charAt(i) == '1'){
                    comparitoror = "1";
                    comparitorand = "1";
                }
                
            }else{
                comparitoror = "1";
                comparitorand = "0";
            }
            and_string = comparitorand + and_string;
            or_string = comparitoror + or_string;
            xor_string = comparitorxor + xor_string;

        }
        System.out.println(first_binary + '|' + second_binary + '|' + third_binary + '=' + or_string);
        System.out.println(first_binary + '&' + second_binary + '&' + third_binary + '=' + and_string);
        System.out.println(first_binary + '^' + second_binary + '^' + third_binary + '=' + xor_string);

        System.out.println("Task 8");
        for(String item : binary.keySet()){
            String unshifted = binary.get(item);
            String shifted_left = unshifted + "00";
            String shifted_right = unshifted.substring(0, unshifted.length()-2);
            System.out.println(unshifted + "<<2="+shifted_left+","+unshifted + ">>2="+shifted_right);
        }

    }
    
    public static String find_bin_from_dec(double decimal_input){
        int temp_dec = (int) decimal_input;
        String final_bin = "";
        while (temp_dec > 0.0){
            int remainder = temp_dec % 2;
            final_bin = remainder + final_bin;
            temp_dec = temp_dec/2;
        }
        return final_bin;
    }
    public static String find_hex_from_dec(double final_answer_to_decimal){
        String final_hex = "";
        double temp_hex = final_answer_to_decimal/16;
        while (true){
            int interger = (int)temp_hex;
            double floating_point = temp_hex - interger;
            double hex_remaining = floating_point * 16;

            if(hex_remaining==10){
                final_hex += "a";
            }else if(hex_remaining==11){
                final_hex += "b";
            }else if(hex_remaining==12){
                final_hex += "c";
            }else if(hex_remaining==13){
                final_hex += "d";
            }else if(hex_remaining==14){
                final_hex += "e";
            }else if(hex_remaining==15){
                final_hex += "f";
            }else{
                final_hex += hex_remaining;
            }
            temp_hex = interger;

            if(interger == 0){
                break;
            }
            
        }
        return final_hex;
    }
    public static double find_dec_from_hex(String hexa_decimal){
        double deciaml_out = 0;
        int power_to_raise_dec = 0;
        for(int i = hexa_decimal.length()-1; i>=0; i--){
            int raise_for_loop = (int) Math.pow(16, power_to_raise_dec);
            if(hexa_decimal.charAt(i) == 'a'){
                deciaml_out += (10 * raise_for_loop);
            }else if(hexa_decimal.charAt(i) == 'b'){
                deciaml_out += (11* raise_for_loop);
            }else if(hexa_decimal.charAt(i) == 'c'){
                deciaml_out += (12 * raise_for_loop);
            }else if(hexa_decimal.charAt(i) == 'd'){
                deciaml_out += (13 * raise_for_loop);
            }else if(hexa_decimal.charAt(i) == 'e'){
                deciaml_out += (14 * raise_for_loop);
            }else if(hexa_decimal.charAt(i) == 'f'){
                deciaml_out += (15 * raise_for_loop);
            }else{
                String str_item = Character.toString(hexa_decimal.charAt(i));
                deciaml_out += (Integer.parseInt(str_item) * raise_for_loop);
            }
            power_to_raise_dec ++;
        }
        return deciaml_out;
    }
}
