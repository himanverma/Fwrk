<?php
App::uses("AppController", "Controller");
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of WebserviceController
 *
 * @author Himanshu
 */
class WebserviceController extends AppController{
    public function beforeFilter() {
        parent::beforeFilter();
        if($this->request->params['prefix'] == "api"){
            
        }
        debug($this->request->params);
        exit;
    }
    public function api_login(){
        
    }
}
