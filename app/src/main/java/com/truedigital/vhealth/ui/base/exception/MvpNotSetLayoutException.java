package com.truedigital.vhealth.ui.base.exception;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class MvpNotSetLayoutException extends RuntimeException{
    public MvpNotSetLayoutException(){
        super( "getLayoutView() not return 0" );
    }
}
