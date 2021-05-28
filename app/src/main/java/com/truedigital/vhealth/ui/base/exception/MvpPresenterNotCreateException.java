package com.truedigital.vhealth.ui.base.exception;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class MvpPresenterNotCreateException extends RuntimeException{
    public MvpPresenterNotCreateException(){
        super( "Please call createPresenter() before" +
                " requesting data to the Presenter" );
    }
}
