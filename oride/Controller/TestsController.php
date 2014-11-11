<?php

App::uses('Controller', 'Controller');
/**
 * Tests Controller
 * @property Tests $Tests
 * 
 * 
 */
class TestsController extends AppController{
    
    public function beforeFilter() {
        parent::beforeFilter();
        $this->Auth->allow();
    }
    public function index(){
        $indexInfo['description'] = "App User Registration(post method)(2-d array) ";
        $indexInfo['url'] = FULL_BASE_URL.$this->webroot."api/users/registration.json";
        $indexInfo['parameters'] = 
        '<b>data[User][username] - </b>Username<br>
        <b>data[User][email] - </b> User email<br>
        <b>data[User][mobile_number] - </b>Mobile Number<br>
        <b>data[User][photo] - </b>Photo<br>
        <b>data[User][password] - </b>Password<br>
        <b>data[User][id_proof] - </b>Id Proof<br>
        <b>data[User][address] - </b>Address<br>
        <b>data[User][city] - </b>City<br>
        <b>data[User][state] - </b>State<br>
        <b>data[User][country] - </b>Country<br>
        <b>data[User][type] - </b>PASSENGER/DRIVER<br>
        <b>data[User][device_token] - </b>Device Token<br>
        <b>data[User][latitude] - </b>Latitude<br>
        <b>data[User][longitude] - </b>Longitude<br>';
        $indexarr[] = $indexInfo;
        
        
        
        
        $indexInfo['description'] = "App User Login(post method)(2-d array) ";
        $indexInfo['url'] = FULL_BASE_URL.$this->webroot."api/users/login.json";
        $indexInfo['parameters'] = 
        '<b>data[User][username] - </b>Username<br>
        <b>data[User][password] - </b>Password<br>
        <b>data[User][device_token] - </b>Device Token<br>';
        $indexarr[] = $indexInfo;
        
        
        
        
        $this->set('IndexDetail',$indexarr);
    }
    
}