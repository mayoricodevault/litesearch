package com.litesearch;

import static com.litesearch.Mail.EmailValidation.isAddressValid;

public class Main {

    public static CustomSearch serpSearch;
    public static void main(String[] args) throws Exception {
	// write your code here



        if(args.length == 0) {
            System.out.println("Please include a Business email as the first parameter.");
            return;
        }

        //if (true) return;

        String keyword =  args[0];
        String target_name = args[1];
        if (!isAddressValid(keyword))
            throw new Exception( "Address is not valid!" );

        serpSearch.search(keyword,target_name, false, 1 );



    }

}
