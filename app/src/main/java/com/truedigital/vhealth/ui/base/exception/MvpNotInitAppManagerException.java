package com.truedigital.vhealth.ui.base.exception;

public class MvpNotInitAppManagerException extends RuntimeException{
    public MvpNotInitAppManagerException(){
        super( "AppManager.init(this); at Application" );
    }
}
