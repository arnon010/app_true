package com.truedigital.vhealth.ui.base.exception;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class MvpViewNotAttachedException extends RuntimeException{
    public MvpViewNotAttachedException(){
        super( "Please call Presenter.attachView(MvpBaseView) before" +
                " requesting data to the View" );
    }
}
