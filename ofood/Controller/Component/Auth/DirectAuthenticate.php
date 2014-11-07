<?php
App::uses('FormAuthenticate', 'Controller/Component/Auth');
/**
 * Description of DirectAuthenticate
 *
 * @author Himanshu
 */
class DirectAuthenticate extends FormAuthenticate {

    public function authenticate(CakeRequest $request, CakeResponse $response) {
        return $this->getUser($request);
    }

    public function getUser(CakeRequest $request) {
        //debug(env('PHP_AUTH_USER'));
        if (@$request['data']['User']['username'] === 'admin' && @$request['data']['User']['password'] === 'admin@123') {
            return array(
                'User' => array(
                    'id' => '1', 
                    'username' => 'admin', 
                    'password' => 'admin@123'
                )
            );
        } else {
            return false;
        }
    }
    
    public function unauthenticated(CakeRequest $request, CakeResponse $response) {
        $response->header('location: '.$request->base ."/pages/login");
    }
}